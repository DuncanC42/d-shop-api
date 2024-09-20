package bzh.duncan.dshop.service.category;

import bzh.duncan.dshop.dto.CategoryDto;
import bzh.duncan.dshop.model.Category;

import java.util.List;

public interface ICategoryService {
    CategoryDto getCategoryById(Long id);
    CategoryDto getCategoryByName(String name);
    List<CategoryDto> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategory(Long id);
}
