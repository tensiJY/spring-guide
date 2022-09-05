package test.product;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;


//	jpa 관련된 설정만 로드해서 테스트를 진행
//	기본적으로 트랜잭셔널 어노테이션을 포함하고 있어 테스트 코드가 종료되면 자동으로 데이터베이스의 롤백이 진행
//	기본값으로 임베디드 데이터베이스를 사용. 다른 데이터베이스를 사용하려면 별도의 설정을 거쳐 사용 가능
@TestPropertySource("classpath:application-test.properties")
@DataJpaTest
//	리포지토리를 정상적으로 주입 받음
public class ProductRepositoryTestByH2 {

	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String flag;
	
	@BeforeEach
	private void doInit() {
		//	application-test.propertie 가 설정되었는지 확인
		System.out.println("doInit : " + this.flag);
	}
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	@DisplayName("상품저장")
	void saveTest() {
		//	given : 테스트에서 사용할 엔티티를 만
		Product product = Product.builder()
				.name("pen")
				.price(1000)
				.stock(1000)
				.createdAt(LocalDateTime.now())
				.build();
		
		//	when : 엔티티를 기반으로 저장 메서드를 호출해서 테스트를 진
		Product savedProduct = productRepository.save(product);
		
		//	then : 저장 메서드와 리턴객체와 Given에서 생성한 엔티티 객체의 값이 일치하는지 검
		assertEquals(product.getName(), savedProduct.getName());
		assertEquals(product.getPrice(), savedProduct.getPrice());
		assertEquals(product.getStock(), savedProduct.getStock());
	}
	
	@Test
	@DisplayName("상품조회")
	void selectTest() {
		//	given : 상품조회 테스트를 하려면 먼저 데이터베이스에 테스트 데이터를 추가 해야
		Product product = Product.builder()
				.name("pen")
				.price(1000)
				.stock(1000)
				.build();
		
		Product savedProduct = productRepository.saveAndFlush(product);
		System.out.println("2. 상품조회 > 저장 :" + savedProduct);
		
		//	when : 저장된 값의 id로 저장된 데이터를 조
		Product foundProduct = productRepository.findById(savedProduct.getNumber()).get();
		System.out.println("2. 상품조회 > 조회 : "+ foundProduct);
		//	then
		assertEquals(product.getName(), foundProduct.getName());
		assertEquals(product.getPrice(), foundProduct.getPrice());
		assertEquals(product.getStock(), foundProduct.getStock());
		
	}
}
