package test.product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;

import test.product.dto.ProductDTO;
import test.product.dto.ProductResponseDTO;

public class ProductServiceImplTest {

	private ProductRepository productRepository = Mockito.mock(ProductRepository.class);
	private ProductServiceImpl productServiceImpl;
	
	@BeforeEach
	public void setUpTest() {
		productServiceImpl = new ProductServiceImpl(productRepository);
		
		System.out.println("시작 : " + LocalDateTime.now());
		
	}
	
	@AfterEach
	public void endTest() {
		System.out.println("종료 : " + LocalDateTime.now());
	}
	
	@Test
	@DisplayName("상품조회")
	void getProductTest() {
		//	Given-When-Then 패턴
		//	프로덕트 엔티티 객체를 생성하고 프로트레포지토리의 동작에 대한 결과 리턴을 설정
		Product givenProduct = Product.builder()
				.number(100L)
				.name("pen")
				.price(10000)
				.stock(1234)
				.build();
		
		//	테스트의 목적을 보여줌 > 상품 아이디로 상품을 조
		Mockito.when(productRepository.findById(100L))
				.thenReturn(Optional.of(givenProduct));
			
		
		//	테스트의 결과를 검증하는 단계
		ProductResponseDTO productResponseDTO = productServiceImpl.getProduct(100L);
		Assertions.assertEquals(productResponseDTO.getNumber(), givenProduct.getNumber());
		Assertions.assertEquals(productResponseDTO.getName(), givenProduct.getName());
		Assertions.assertEquals(productResponseDTO.getPrice(), givenProduct.getPrice());
		Assertions.assertEquals(productResponseDTO.getStock(), givenProduct.getStock());
		
		//	값을 검증
		verify(productRepository).findById(100L);
	}
	
	@Test
	@DisplayName("상품등록")
	void saveProductTest() {
		//	any() Mockito의 ArgumentMatchers에서 제공하는 메서드로서 Mock 객체의 동작을 정의하거나
		//	검증하는 단계에서 조건으로 특정 매개변수의 전달을 설정하지 않고 
		//	메서드의 실행만을 확인하거나 좀 더 큰 범위의 클래스 객체를 매개변수로 전달받는 등의 상황에 사용 
		Mockito.when(productRepository.save(any(Product.class)))
				.then(AdditionalAnswers.returnsFirstArg());
		
		ProductResponseDTO productResponseDTO = productServiceImpl.saveProduct(ProductDTO.builder()
				.name("pen")
				.price(1000)
				.stock(1234)
				.build());
		
		Assertions.assertEquals(productResponseDTO.getName(), "pen");
		Assertions.assertEquals(productResponseDTO.getPrice(), 1000);
		Assertions.assertEquals(productResponseDTO.getStock(), 1234);
		
		verify(productRepository).save(any());
	}
	
}
