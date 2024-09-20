package bzh.duncan.dshop.mapper;

import bzh.duncan.dshop.dto.CategoryDto;
import bzh.duncan.dshop.dto.ProductDto;
import bzh.duncan.dshop.model.Product;

public class ProductMapper {

    public static ProductDto productToProductDto(Product product, ProductDto productDto){
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setBrand(product.getBrand());
        productDto.setDescription(product.getDescription());
        productDto.setInventory(product.getInventory());
        productDto.setPrice(product.getPrice());
        productDto.setCategory(CategoryMapper.mapToCategoryDto(product.getCategory(), new CategoryDto()));
        return productDto;
    }

}
