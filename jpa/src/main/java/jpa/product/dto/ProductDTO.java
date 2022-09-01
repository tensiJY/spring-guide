package jpa.product.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {

	private String name;
	private int price;
	private int stock;
	
	
}
