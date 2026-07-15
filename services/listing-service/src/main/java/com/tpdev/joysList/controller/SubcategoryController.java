package com.tpdev.joysList.controller;

import com.tpdev.joysList.constants.ApiConstants;
import com.tpdev.joysList.entity.Subcategory;
import com.tpdev.joysList.service.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.SUBCATEGORIES)
public class SubcategoryController {
    private final SubcategoryService service;

    @GetMapping
    public ResponseEntity<List<Subcategory>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(ApiConstants.CATEGORY_BY_ID)
    public ResponseEntity<List<Subcategory>> getSubcategories(@PathVariable Long categoryId) {
        List<Subcategory> result =  service.getSubcategoriesByCategory(categoryId);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<String> createSubcategory(@RequestBody Subcategory subcategory) {
        service.createSubcategory(subcategory);
        return new ResponseEntity<>("Subcategory created successfully.", HttpStatus.CREATED);
    }

    @PutMapping(ApiConstants.ID)
    public ResponseEntity<String> updateSubcategory (
            @PathVariable Long id,
            @RequestBody Subcategory subcategory
    ) {
        if (service.findById(id) == null)
            return new ResponseEntity<>("Not found with the id: " + id, HttpStatus.NOT_FOUND);
        Subcategory found = service.findById(id);
        found.setName(subcategory.getName());
        found.setDescription(subcategory.getDescription());
        return ResponseEntity.ok("Updated successfully.");
    }

    @DeleteMapping(ApiConstants.ID)
    public ResponseEntity<String> deleteSubcategory(@PathVariable Long id) {
        if (service.findById(id) == null)
            return new ResponseEntity<>("Not found with the id: " + id, HttpStatus.NOT_FOUND);
        service.deleteSubcategory(id);
        return ResponseEntity.ok("Deleted successfully.");
    }
}
