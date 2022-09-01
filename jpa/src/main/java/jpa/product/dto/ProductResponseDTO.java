package jpa.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {

	private Long number;
	private String name;
	private int price;
	private int stock;
	
	
	
	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ProductResponseDTOBuilder {
		private Long number;
		private String name;
		private int price;
		private int stock;
	}
	
}
