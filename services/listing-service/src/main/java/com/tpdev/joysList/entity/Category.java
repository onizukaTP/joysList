package com.tpdev.joysList.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tpdev.joysList.entity.enums.AdType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private AdType name;

    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("category-subcategories")
    private List<Subcategory> subcategories;

}
