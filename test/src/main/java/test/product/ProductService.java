package test.product;

import test.product.dto.ProductDTO;
import test.product.dto.ProductResponseDTO;

public interface ProductService {

	ProductResponseDTO getProduct(Long number);	
	
	ProductResponseDTO saveProduct(ProductDTO productDTO);
	
	ProductResponseDTO changeProuctName(Long number, String name) throws Exception;
	
	void deleteProduct(Long number) throws Exception;
		
	
}
