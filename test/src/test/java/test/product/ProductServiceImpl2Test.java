package test.product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import test.product.dto.ProductDTO;
import test.product.dto.ProductResponseDTO;

//	SpringExtension 클래스는 Junit 5의 Jupiter 테스트에 스프링 테스트 컨텍스트 프레임워크를 통합하는 역할을 수행
@ExtendWith(SpringExtension.class)
@Import({ProductServiceImpl.class})
public class ProductServiceImpl2Test {

	//	의존성 주입
	//	mock객체를 직접 생성하는 방식이 더 빠르
	@MockBean
	private ProductRepository productRepository;
	
	
	@Autowired
	private ProductServiceImpl productServiceImpl;
	
	@Test
	@DisplayName("상품조회")
	void getProductTest() {
		// given
        Product givenProduct = new Product();
        givenProduct.setNumber(123L);
        givenProduct.setName("펜");
        givenProduct.setPrice(1000);
        givenProduct.setStock(1234);

        Mockito.when(productRepository.findById(123L))
            .thenReturn(Optional.of(givenProduct));

        // when
        ProductResponseDTO productResponseDto = productServiceImpl.getProduct(123L);

        // then
        Assertions.assertEquals(productResponseDto.getNumber(), givenProduct.getNumber());
        Assertions.assertEquals(productResponseDto.getName(), givenProduct.getName());
        Assertions.assertEquals(productResponseDto.getPrice(), givenProduct.getPrice());
        Assertions.assertEquals(productResponseDto.getStock(), givenProduct.getStock());

        verify(productRepository).findById(123L);
	}
	
	@Test
	@DisplayName("상품등록")
	void saveProductTest() {
        // given
        Mockito.when(productRepository.save(any(Product.class)))
            .then(AdditionalAnswers.returnsFirstArg());

        // when
        ProductResponseDTO productResponseDto = productServiceImpl.saveProduct(
            new ProductDTO("펜", 1000, 1234));

        // then
        Assertions.assertEquals(productResponseDto.getName(), "펜");
        Assertions.assertEquals(productResponseDto.getPrice(), 1000);
        Assertions.assertEquals(productResponseDto.getStock(), 1234);

        verify(productRepository).save(any());
	}
	
}
