package jpa.product;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jpa.product.dto.ProductDTO;
import jpa.product.dto.ProductResponseDTO;
import jpa.product.dto.ProductResponseDTO.ProductResponseDTOBuilder;

@Service
public class ProductServiceImpl implements ProductService{
	
	private ProductDAO productDAO;
	
	@Autowired
	private ProductServiceImpl(ProductDAO productDAO) {
		this.productDAO = productDAO;
	};

	@Override
	public ProductResponseDTO getProduct(Long number) {
		Product product = productDAO.selectProduct(number);
		ProductResponseDTO productResponseDTO = ProductResponseDTO
				.builder()
				.name(product.getName())
				.number(product.getNumber())
				.price(product.getPrice())
				.stock(product.getStock())
				.build()
				;
		
		return productResponseDTO;
	}

	@Override
	public ProductResponseDTO saveProduct(ProductDTO productDTO) {
		Product product = new Product();
		product.setName(productDTO.getName());
		product.setPrice(productDTO.getPrice());
		product.setStock(productDTO.getStock());
		product.setCreatedAt(LocalDateTime.now());
		product.setUpdatedAt(LocalDateTime.now());
		
		Product savedProduct = productDAO.insertProduct(product);
		
		ProductResponseDTO productResponseDTO = ProductResponseDTO
				.builder()
				.name(savedProduct.getName())
				.number(savedProduct.getNumber())
				.price(savedProduct.getPrice())
				.stock(savedProduct.getStock())
				.build()
				;
		
		return productResponseDTO;
	}

	@Override
	public ProductResponseDTO changeProuctName(Long number, String name) throws Exception {
		Product changeProduct = productDAO.updateProductName(number, name);
		ProductResponseDTO productResponseDTO = ProductResponseDTO
				.builder()
				.number(changeProduct.getNumber())
				.name(changeProduct.getName())
				.number(changeProduct.getNumber())
				.price(changeProduct.getPrice())
				.stock(changeProduct.getStock())
				.build()
				;
		
		return productResponseDTO;
	}

	@Override
	public void deleteProduct(Long number) throws Exception {
		productDAO.deleteProduct(number);
	}
	
}
