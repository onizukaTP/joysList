package com.tpdev.joysList.util;

import com.tpdev.joysList.entity.Category;
import com.tpdev.joysList.entity.Subcategory;
import com.tpdev.joysList.entity.UserEntity;
import com.tpdev.joysList.entity.enums.AdType;
import com.tpdev.joysList.entity.enums.Role;
import com.tpdev.joysList.repo.CategoryRepository;
import com.tpdev.joysList.repo.SubcategoryRepository;
import com.tpdev.joysList.repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CategoryDataSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;

    @Override
    public void run(String... args) {

        if (categoryRepository.count() > 0) {
            log.info("Seed data already exists. Skipping initialization.");
            return;
        }

        // ==========================
        // HOUSING
        // ==========================
        Category housing = createCategory(
                AdType.HOUSING,
                "Rentals, roommates, and real estate"
        );

        createSubcategory(housing, "Apartments", "Apartment rentals");
        createSubcategory(housing, "Houses", "House rentals");
        createSubcategory(housing, "Rooms", "Room and roommate listings");

        // ==========================
        // FOR SALE
        // ==========================
        Category forSale = createCategory(
                AdType.FOR_SALE,
                "Buy and sell goods"
        );

        createSubcategory(forSale, "Electronics", "Phones, laptops, and gadgets");
        createSubcategory(forSale, "Furniture", "Home and office furniture");
        createSubcategory(forSale, "Clothing", "Clothes and accessories");

        // ==========================
        // JOBS
        // ==========================
        Category jobs = createCategory(
                AdType.JOBS,
                "Full-time, part-time, and contract work"
        );

        createSubcategory(jobs, "Tech Jobs", "Software and IT positions");
        createSubcategory(jobs, "Retail Jobs", "Retail and customer service");
        createSubcategory(jobs, "Healthcare Jobs", "Medical and care roles");

        // ==========================
        // GIGS
        // ==========================
        Category gigs = createCategory(
                AdType.GIGS,
                "Short-term and one-off work"
        );

        createSubcategory(gigs, "Delivery Gigs", "Delivery and courier gigs");
        createSubcategory(gigs, "Creative Gigs", "Design, writing, and media gigs");

        // ==========================
        // EVENTS
        // ==========================
        Category events = createCategory(
                AdType.EVENTS,
                "Local events and activities"
        );

        createSubcategory(events, "Music Events", "Concerts and performances");
        createSubcategory(events, "Sports Events", "Games and outdoor activities");

        // ==========================
        // RESUMES
        // ==========================
        Category resumes = createCategory(
                AdType.RESUMES,
                "Job seekers and candidates"
        );

        createSubcategory(resumes, "Tech Resumes", "Software and IT candidates");
        createSubcategory(resumes, "Healthcare Resumes", "Medical candidates");

        // ==========================
        // SERVICES
        // ==========================
        Category services = createCategory(
                AdType.SERVICES,
                "Local services offered"
        );

        createSubcategory(services, "Cleaning", "Home and office cleaning");
        createSubcategory(services, "Moving", "Moving and hauling services");
        createSubcategory(services, "Tutoring", "Lessons and tutoring");

        // ==========================
        // COMMUNITY
        // ==========================
        Category community = createCategory(
                AdType.COMMUNITY,
                "Community posts and announcements"
        );

        createSubcategory(community, "Lost and Found", "Lost and found items");
        createSubcategory(community, "Volunteers", "Volunteer opportunities");

        log.info("Category and subcategory seed data loaded successfully.");
    }

    private Category createCategory(
            AdType adType,
            String description) {

        Category category = new Category();
        category.setName(adType);
        category.setDescription(description);

        return categoryRepository.save(category);
    }

    private void createSubcategory(
            Category category,
            String name,
            String description) {

        Subcategory subcategory = new Subcategory();
        subcategory.setCategory(category);
        subcategory.setName(name);
        subcategory.setDescription(description);

        subcategoryRepository.save(subcategory);
    }

}
