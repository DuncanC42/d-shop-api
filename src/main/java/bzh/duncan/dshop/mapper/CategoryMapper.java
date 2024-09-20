package bzh.duncan.dshop.mapper;

import bzh.duncan.dshop.dto.CategoryDto;
import bzh.duncan.dshop.model.Category;

public class CategoryMapper {

    public static CategoryDto mapToCategoryDto(Category category, CategoryDto categoryDto){
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    public static Category mapToCategory(Category category, CategoryDto categoryDto){
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        return category;
    }


}
