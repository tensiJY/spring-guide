package jpa.product;

import jpa.product.dto.ProductDTO;
import jpa.product.dto.ProductResponseDTO;

public interface ProductService {

	ProductResponseDTO getProduct(Long number);	
	
	ProductResponseDTO saveProduct(ProductDTO productDTO);
	
	ProductResponseDTO changeProuctName(Long number, String name) throws Exception;
	
	void deleteProduct(Long Number) throws Exception;
		
	
}
