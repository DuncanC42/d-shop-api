package bzh.duncan.dshop.service.product;

import bzh.duncan.dshop.dto.ProductDto;
import bzh.duncan.dshop.exceptions.ProductNotFoundException;
import bzh.duncan.dshop.mapper.ProductMapper;
import bzh.duncan.dshop.model.Category;
import bzh.duncan.dshop.model.Product;
import bzh.duncan.dshop.repository.CategoryRepository;
import bzh.duncan.dshop.repository.ProductRepository;
import bzh.duncan.dshop.request.AddProductRequest;
import bzh.duncan.dshop.request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
        //Check if the category is found in db
        //If yes -> set as the new product category
        //Else -> create the category and then save it as new Product category

        Category category = categoryRepository.findByName(request.getCategory().getName())
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        // Because you are not sure to find the product, it means the product is optional, so you need to throw an exception
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete, () -> {
            throw new ProductNotFoundException("Product not found");
        });
    }

    @Override
    public Product updateProduct(UpdateProductRequest newProduct, Long productToUpdateId) {
        return productRepository.findById(productToUpdateId)
                .map(existingProduct -> updateExistingProduct(existingProduct, newProduct))
                .map(productRepository::save)
                .orElseThrow(() -> new ProductNotFoundException("Product not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName())
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        existingProduct.setCategory(category);

        return existingProduct;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> ProductMapper.productToProductDto(product, new ProductDto()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategoryName(category);
        return products.stream()
                .map(product -> ProductMapper.productToProductDto(product, new ProductDto()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByBrand(String brand) {
        List<Product> products = productRepository.findByBrand(brand);
        return products.stream()
                .map(product -> ProductMapper.productToProductDto(product, new ProductDto()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategoryAndBrand(String category, String brand) {
        List<Product> products = productRepository.findByCategoryNameAndBrand(category, brand);
        return products.stream()
                .map(product -> ProductMapper.productToProductDto(product, new ProductDto()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByName(String name) {
        List<Product> products = productRepository.findByName(name);
        return products.stream()
                .map(product -> ProductMapper.productToProductDto(product, new ProductDto()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByBrandAndName(String brand, String name) {
        List<Product> products = productRepository.findByBrandAndName(brand, name);
        return products.stream()
                .map(product -> ProductMapper.productToProductDto(product, new ProductDto()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
