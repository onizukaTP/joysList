package com.tpdev.joysList.dto;

import com.tpdev.joysList.entity.enums.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AdRequestDto {
    private AdType adType;

    // --- Common Ad fields ---
    private String title;
    private String description;
    private Double price;
    private String location;
    private Boolean hasImage;
    private Boolean postedToday;
    private Boolean isFree;
    private Boolean deliveryAvailable;
    private Long subcategoryId;

    // --- HousingAd fields ---
    private Byte numberOfBeds;
    private Byte numberOfBathrooms;
    private Boolean catsOk;
    private Boolean dogsOk;
    private Boolean furnished;
    private HousingType housingType;
    private Laundry laundry;
    private Parking parking;
    private RentPeriod rentPeriod;

    // --- ForSaleAd fields ---
    private SoldBy soldBy;
    private Condition condition;

    // --- EventAd fields ---
    private List<EventType> eventTypes;

    // --- GigsAd fields ---
    private Gigs gigs;
    private PaymentStatus status;

    // --- JobAd fields ---
    private Boolean nonProfitOrganization;
    private Boolean internship;
    private Boolean telecommutingOk;
    private EmploymentType employmentType;
    private List<JobType> jobTypes;

    // --- ServiceAd fields ---
    private ServiceType serviceType;

    // --- CommunityAd fields ---
    private CommunityType communityType;
    private LostAndFound lostOrFound;

    // --- ResumeAd fields ---
    private Boolean availableMornings;
    private Boolean availableAfternoons;
    private Boolean availableEvenings;
    private Boolean availableOvernights;
    private Boolean availableWeekdays;
    private Boolean availableWeekends;
    private EducationCompleted educationCompleted;

    private LocalDateTime createdAt = LocalDateTime.now();
}