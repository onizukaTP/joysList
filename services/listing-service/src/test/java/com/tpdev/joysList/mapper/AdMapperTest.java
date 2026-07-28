package com.tpdev.joysList.mapper;

import com.tpdev.joysList.dto.AdRequestDto;
import com.tpdev.joysList.entity.Category;
import com.tpdev.joysList.entity.Subcategory;
import com.tpdev.joysList.entity.category.HousingAd;
import com.tpdev.joysList.entity.enums.AdType;
import com.tpdev.joysList.entity.enums.HousingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AdMapperTest {

    private AdMapper adMapper;

    @BeforeEach
    void setUp() {
        adMapper = new AdMapper();
    }

    @Test
    @DisplayName("Should map AdRequestDto to HousingAd entity correctly")
    void shouldMapDtoToHousingAd() {
        // Given
        Category category = new Category();
        category.setId(1L);
        category.setName(AdType.HOUSING);

        Subcategory subcategory = new Subcategory();
        subcategory.setId(5L);
        subcategory.setName("Apartment Rentals");
        subcategory.setCategory(category);

        AdRequestDto dto = new AdRequestDto();
        dto.setTitle("Luxury Apartment");
        dto.setDescription("Spacious 2BHK apartment in downtown");
        dto.setPrice(1500.0);
        dto.setLocation("Downtown");
        dto.setIsFree(false);
        dto.setDeliveryAvailable(true);
        dto.setHousingType(HousingType.APARTMENT);
        dto.setNumberOfBeds((byte) 2);
        dto.setNumberOfBathrooms((byte) 2);

        Long postedById = 10L;

        // When
        HousingAd housingAd = adMapper.toHousingAd(dto, subcategory, postedById);

        // Then
        assertThat(housingAd).isNotNull();
        assertThat(housingAd.getTitle()).isEqualTo("Luxury Apartment");
        assertThat(housingAd.getDescription()).isEqualTo("Spacious 2BHK apartment in downtown");
        assertThat(housingAd.getPrice()).isEqualTo(1500.0);
        assertThat(housingAd.getLocation()).isEqualTo("Downtown");
        assertThat(housingAd.getPostedById()).isEqualTo(10L);
        assertThat(housingAd.getSubcategory()).isEqualTo(subcategory);
        assertThat(housingAd.getType()).isEqualTo(HousingType.APARTMENT);
        assertThat(housingAd.getNumberOfBeds()).isEqualTo((byte) 2);
        assertThat(housingAd.getNumberOfBathrooms()).isEqualTo((byte) 2);
    }
}
