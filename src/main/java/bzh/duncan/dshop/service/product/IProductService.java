package bzh.duncan.dshop.service.product;

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
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);


}
