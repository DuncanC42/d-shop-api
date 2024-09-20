package bzh.duncan.dshop.service.category;

import bzh.duncan.dshop.dto.CategoryDto;
import bzh.duncan.dshop.exceptions.AlreadyExistsException;
import bzh.duncan.dshop.exceptions.CategoryNotFoundException;
import bzh.duncan.dshop.mapper.CategoryMapper;
import bzh.duncan.dshop.model.Category;
import bzh.duncan.dshop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;


    @Override
    public CategoryDto getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(category -> CategoryMapper.mapToCategoryDto(category, new CategoryDto()))
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

    @Override
    public CategoryDto getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .map(category -> CategoryMapper.mapToCategoryDto(category, new CategoryDto()))
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }


    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> CategoryMapper.mapToCategoryDto(category, new CategoryDto()))
                .collect(Collectors.toList());
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(cat -> !categoryRepository.existsByName(cat.getName()))
                .map(categoryRepository :: save).orElseThrow(() -> new AlreadyExistsException(category.getName() + "already exists"));
    }

@Override
public Category updateCategory(Category newCategory, Long oldCategoryId) {
    return categoryRepository.findById(oldCategoryId)
        .map(oldCategory -> {
            oldCategory.setName(newCategory.getName());
            return categoryRepository.save(oldCategory);
        }) .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
}

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id).ifPresentOrElse(
                categoryRepository :: delete, // if present
                () -> { // else
                    throw new CategoryNotFoundException("This category doesn't exist");
                });
    }
}
