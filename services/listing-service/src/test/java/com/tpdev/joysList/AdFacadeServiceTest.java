package com.tpdev.joysList;

import com.tpdev.joysList.dto.AdRequestDto;
import com.tpdev.joysList.entity.Ad;
import com.tpdev.joysList.entity.enums.AdType;
import com.tpdev.joysList.exception.ResourceNotFound;
import com.tpdev.joysList.kafka.producer.AdEventProducer;
import com.tpdev.joysList.repo.AdRepository;
import com.tpdev.joysList.service.AdFacadeService;
import com.tpdev.joysList.service.category.ForSaleAdService;
import com.tpdev.joysList.service.category.HousingAdService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdFacadeServiceTest {

    @Mock private AdRepository repository;
    @Mock private AdEventProducer adEventProducer;
    @Mock private HousingAdService housingAdService;
    @Mock private ForSaleAdService forSaleAdService;
    // mock other category services as needed

    @InjectMocks
    private AdFacadeService adFacadeService;

    @Test
    void updateAd_shouldUpdateFieldsAndSave() {
        Ad existing = Ad.builder()
                .id(1L)
                .title("Old title")
                .description("Old desc")
                .price(1000.0)
                .location("Chennai")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Ad.class))).thenAnswer(i -> i.getArgument(0));

        AdRequestDto dto = new AdRequestDto();
        dto.setTitle("New title");
        dto.setDescription("New desc");
        dto.setPrice(2000.0);
        dto.setLocation("Bangalore");

        Ad result = adFacadeService.updateAd(1L, dto);

        assertThat(result.getTitle()).isEqualTo("New title");
        assertThat(result.getPrice()).isEqualTo(2000.0);
        verify(repository).save(any(Ad.class));
    }

    @Test
    void updateAd_shouldThrow_whenAdNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        AdRequestDto dto = new AdRequestDto();
        dto.setTitle("Whatever");

        assertThatThrownBy(() -> adFacadeService.updateAd(99L, dto))
                .isInstanceOf(ResourceNotFound.class)
                .hasMessageContaining("99");
    }

    @Test
    void deleteAd_shouldPublishEvent_afterDeletion() {
        Ad ad = Ad.builder().id(1L).title("Test ad").build();
        when(repository.findById(1L)).thenReturn(Optional.of(ad));
        doNothing().when(repository).deleteById(1L);
        doNothing().when(adEventProducer).publishAdDeleted(any());

        adFacadeService.deleteAd(1L);

        verify(repository).deleteById(1L);
        verify(adEventProducer).publishAdDeleted(any());
    }

    @Test
    void deleteAd_shouldThrow_whenAdNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adFacadeService.deleteAd(99L))
                .isInstanceOf(ResourceNotFound.class);

        verify(repository, never()).deleteById(any());
        verify(adEventProducer, never()).publishAdDeleted(any());
    }
}