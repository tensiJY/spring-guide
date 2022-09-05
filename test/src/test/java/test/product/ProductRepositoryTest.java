package test.product;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

//	기존에 사용하고 있는 마리아디비에서 테스트하기 위해서는 별도의 설정이 필요
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
//	Replace.NONE으로 변경하면 애플리케이션에서 실제로 사용하는 데이터베이스로 테스트가 가능
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository productRepository;	
	
	@Test
	@DisplayName("상품저장")
	void save() {
		Product product = Product.builder()
				.name("pen")
				.price(1000)
				.stock(1000)
				.createdAt(LocalDateTime.now())
				.build();
		
		Product savedProduct = productRepository.save(product);
		
		assertEquals(product.getName(), savedProduct.getName());
	}
	

}
