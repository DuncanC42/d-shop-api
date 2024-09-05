package bzh.duncan.dshop.request;

import bzh.duncan.dshop.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data //Implement Getters and Setters
public class UpdateProductRequest {
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
