package bzh.duncan.dshop.service.product;

import bzh.duncan.dshop.dto.ProductDto;
import bzh.duncan.dshop.model.Product;
import bzh.duncan.dshop.request.AddProductRequest;
import bzh.duncan.dshop.request.UpdateProductRequest;

import java.util.List;

public interface IProductService {
    //Methods to add a product to the database
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(UpdateProductRequest newProduct, Long productToUpdateId);
    List<ProductDto> getAllProducts();
    List<ProductDto> getProductsByCategory(String category);
    List<ProductDto> getProductsByBrand(String brand);
    List<ProductDto> getProductsByCategoryAndBrand(String category, String brand);
    List<ProductDto> getProductsByName(String name);
    List<ProductDto> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);


}
