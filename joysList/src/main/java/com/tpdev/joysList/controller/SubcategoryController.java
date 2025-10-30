package com.tpdev.joysList.controller;

import com.tpdev.joysList.entity.Subcategory;
import com.tpdev.joysList.service.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subcategories")
public class SubcategoryController {
    private final SubcategoryService service;

    @GetMapping("/category/{categoryId}")
    public List<Subcategory> getSubcategories(@PathVariable Long categoryId) {
        return service.getSubcategoriesByCategory(categoryId);
    }

    @PostMapping
    public Subcategory createSubcategory(@RequestBody Subcategory subcategory) {
        return service.createSubcategory(subcategory);
    }

    @PutMapping("/{id}")
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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubcategory(@PathVariable Long id) {
        if (service.findById(id) == null)
            return new ResponseEntity<>("Not found with the id: " + id, HttpStatus.NOT_FOUND);
        service.deleteSubcategory(id);
        return ResponseEntity.ok("Deleted successfully.");
    }
}
