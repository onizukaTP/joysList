package com.tpdev.joysList.service.impl;

import com.tpdev.joysList.dto.AdRequestDto;
import com.tpdev.joysList.entity.Category;
import com.tpdev.joysList.entity.CustomUserDetails;
import com.tpdev.joysList.entity.Subcategory;
import com.tpdev.joysList.entity.UserEntity;
import com.tpdev.joysList.entity.category.HousingAd;
import com.tpdev.joysList.entity.enums.AdType;
import com.tpdev.joysList.exception.ResourceNotFound;
import com.tpdev.joysList.kafka.producer.AdEventProducer;
import com.tpdev.joysList.mapper.AdMapper;
import com.tpdev.joysList.repo.AdRepository;
import com.tpdev.joysList.repo.SubcategoryRepository;
import com.tpdev.joysList.service.category.HousingAdService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdFacadeServiceImplTest {

    @Mock
    private HousingAdService housingAdService;

    @Mock
    private AdMapper mapper;

    @Mock
    private SubcategoryRepository subcategoryRepository;

    @Mock
    private AdRepository repository;

    @Mock
    private AdEventProducer adEventProducer;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AdFacadeServiceImpl adFacadeService;

    private UserEntity mockUser;
    private Subcategory mockSubcategory;

    @BeforeEach
    void setUp() {
        mockUser = UserEntity.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .build();

        Category category = new Category();
        category.setId(10L);
        category.setName(AdType.HOUSING);

        mockSubcategory = new Subcategory();
        mockSubcategory.setId(100L);
        mockSubcategory.setName("Apartment");
        mockSubcategory.setCategory(category);
    }

    private void mockSecurityContext() {
        CustomUserDetails userDetails = new CustomUserDetails(mockUser);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @DisplayName("Should create Housing Ad successfully and publish event")
    void shouldCreateHousingAdSuccessfully() {
        // Given
        mockSecurityContext();

        AdRequestDto dto = new AdRequestDto();
        dto.setSubcategoryId(100L);
        dto.setAdType(AdType.HOUSING);
        dto.setTitle("Cozy Studio");
        dto.setDescription("Great studio apartment");
        dto.setPrice(800.0);
        dto.setLocation("Downtown");

        HousingAd mappedAd = new HousingAd();
        mappedAd.setId(500L);
        mappedAd.setTitle("Cozy Studio");
        mappedAd.setSubcategory(mockSubcategory);
        mappedAd.setPostedById(1L);

        when(subcategoryRepository.findById(100L)).thenReturn(Optional.of(mockSubcategory));
        when(mapper.toHousingAd(eq(dto), eq(mockSubcategory), eq(1L))).thenReturn(mappedAd);
        when(housingAdService.createAd(any(HousingAd.class))).thenReturn(mappedAd);

        // When
        var createdAd = adFacadeService.createAd(dto);

        // Then
        assertThat(createdAd).isNotNull();
        assertThat(createdAd.getId()).isEqualTo(500L);
        assertThat(createdAd.getTitle()).isEqualTo("Cozy Studio");

        verify(adEventProducer, times(1)).publishAdCreated(any());
    }

    @Test
    @DisplayName("Should throw ResourceNotFound when subcategory does not exist")
    void shouldThrowExceptionWhenSubcategoryNotFound() {
        // Given
        AdRequestDto dto = new AdRequestDto();
        dto.setSubcategoryId(999L);

        when(subcategoryRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> adFacadeService.createAd(dto))
                .isInstanceOf(ResourceNotFound.class)
                .hasMessageContaining("Subcategory not found");
    }

    @Test
    @DisplayName("Should delete Ad successfully and publish deleted event")
    void shouldDeleteAdSuccessfully() {
        // Given
        mockSecurityContext();

        HousingAd existingAd = new HousingAd();
        existingAd.setId(500L);
        existingAd.setTitle("Studio Apartment");
        existingAd.setPostedById(1L);

        when(repository.findById(500L)).thenReturn(Optional.of(existingAd));

        // When
        adFacadeService.deleteAd(500L);

        // Then
        verify(repository, times(1)).deleteById(500L);
        verify(adEventProducer, times(1)).publishAdDeleted(any());
    }
}
