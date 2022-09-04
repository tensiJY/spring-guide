package test.product;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.product.dto.ProductDTO;
import test.product.dto.ProductResponseDTO;

@Service
public class ProductServiceImpl implements ProductService{
	
//	private ProductDAO productDAO;
//	
//	@Autowired
//	private ProductServiceImpl(ProductDAO productDAO) {
//		this.productDAO = productDAO;
//	};
	
	private final ProductRepository productRepository;
	
	@Autowired
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

    
	
	@Override
	public ProductResponseDTO getProduct(Long number) {
		Product product = productRepository.findById(number).get();
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
		
		Product savedProduct = productRepository.save(product);
		
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
		Product foundProduct = productRepository.findById(number).get();
		foundProduct.setName(name);
		
		Product changeProduct = productRepository.save(foundProduct);
		
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
		productRepository.deleteById(number);
	}
	
}
