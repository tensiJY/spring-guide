package jpa.product;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductDAOImpl implements ProductDAO {

	private final ProductRepository productRepository;
	
	@Autowired
	public ProductDAOImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@Override
	public Product insertProduct(Product product) {
		//	저
		Product savedProduct = productRepository.save(product);
		return savedProduct;
	}

	@Override
	public Product selectProduct(Long number) {
		//	getById deprecated... -> getReferenceById (버전 2.7)
		//	단일 조회 = 
		Product selectedProduct = productRepository.getReferenceById(number);
		//Product selectedProduct = productRepository.findById(number);
		return selectedProduct;
	}

	@Override
	public Product updateProductName(Long number, String name) throws Exception {
		Optional<Product> selectedProduct = productRepository.findById(number);
		Product updatedProduct;
		//	isEmpty는 null
		//	isPresent는 반
		if(selectedProduct.isPresent()) {
			
			//	업데이트라는 키워드를 사용하지 않는다
			Product product = selectedProduct.get();
			product.setName(name);
			product.setUpdatedAt(LocalDateTime.now());
			
			updatedProduct = productRepository.save(product);
		}else {
			throw new Exception();
		}
		return updatedProduct;
	}

	@Override
	public void deleteProduct(Long number) throws Exception {
		Optional<Product> selectedProduct = productRepository.findById(number);
		if(selectedProduct.isPresent()) {
			Product product = selectedProduct.get();
			productRepository.delete(product);
		}else {
			throw new Exception();
		}
	}

	
	
}
