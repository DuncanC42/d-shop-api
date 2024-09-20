package bzh.duncan.dshop.controller;

import bzh.duncan.dshop.dto.CategoryDto;
import bzh.duncan.dshop.exceptions.AlreadyExistsException;
import bzh.duncan.dshop.exceptions.CategoryNotFoundException;
import bzh.duncan.dshop.model.Category;
import bzh.duncan.dshop.response.ApiResponse;
import bzh.duncan.dshop.service.category.ICategoryService;
import bzh.duncan.dshop.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final ICategoryService categoryService;
    private final ImageService imageService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<CategoryDto> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Found !", categories));
        } catch ( Exception e ) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error:", INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name) {
        try {
            Category category = categoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("Success", category));
        } catch ( AlreadyExistsException e ) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId) {
        try {
            CategoryDto category = categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("Found", category));
        } catch ( CategoryNotFoundException e ) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    //FIXME: Mehdi il a dit fait pas ça "?id=XXX", ça est à utiliser principalement pour les filtres ou multi critères

    @GetMapping("/category/name/{categoryName}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String categoryName) {
        try {
            CategoryDto category = categoryService.getCategoryByName(categoryName);
            return ResponseEntity.ok(new ApiResponse("Found", category));
        } catch ( CategoryNotFoundException e ) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update/category/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody Category category, @PathVariable Long id) {
        try {
            categoryService.updateCategory(category, id);
            return ResponseEntity.ok(new ApiResponse("Updated successfully", null));
        } catch (
                CategoryNotFoundException e ) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/category/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok(new ApiResponse("Deleted successfully", null));

        } catch ( CategoryNotFoundException e ) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


}
