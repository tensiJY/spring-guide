package test.product;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import test.product.dto.ProductDTO;
import test.product.dto.ProductResponseDTO;

//	테스트 대상 클래스.class
//	WebMvcTest는 slice 테스트라고 부름
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
	//	ProductController에서 잡고 있는 Bean 객체에 대해 Mock 형태의 객체를 생성해줌
	@Autowired
	private MockMvc mockMvc;
	
	//	실제 빈 객체가 아닌 Mock(가짜) 객체를 생성해서 주입하는 역할 수행
	//	실제 객체가 아니므로 실제 행위를 수행하지 않음
	//	Mockito의 given()메서드를 통해 동작을 정의
	@MockBean
	private ProductServiceImpl productServiceImpl;
	
	//	DisplayName : 테스트 메서드의 이름이 복잡해서 가독성이 떨어질 경우 이 어노테이션을 통해 테스트에 대한 표현 정의
	@Test
	@DisplayName("상품 상세 조회")
	void getProductTest() throws Exception{
		
		long productId = 123;
		//	given : Mock 객체가 특정 상황에서 해야하는 행위를 정의하는 메소드
		given(productServiceImpl.getProduct(productId)).willReturn(
				ProductResponseDTO.builder()
				.number(productId)
				.name("pen")
				.price(5000)
				.stock(2000)
				.build()
				);
		
		//	perform() 메서드를 이용하면 서버로 URL 요청을 보내는 것처럼 통신 테스트 코드를 작성해서 컨트롤러 테스트를 할 수 있음
		//	andExpect : 기대하는 값이 나왔는지 체크해볼 수 있는 메소드
		mockMvc.perform(
					get("/api/product?number="+productId))
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("$.number").exists())
			   .andExpect(jsonPath("$.name").exists())
			   .andExpect(jsonPath("$.price").exists())
			   .andExpect(jsonPath("$.stock").exists())
			   .andDo(print());
				
        //	verify : 해당 객체의 메소드가 실행되었는지 체크해줌
        verify(productServiceImpl).getProduct(productId);	
	}
	
	
	@Test
	@DisplayName("상품 등록")
	void createProductTest() throws Exception{
		
		long productId = 12315;
		
		//	Mock객체에서 특정 메서드가 실행되는 경우 실제 Return을 줄 수 없기 때문에 아래와 같이 가정 사항을 만들어줌
		given(productServiceImpl.saveProduct(ProductDTO.builder()
				.name("pen")
				.price(5000)
				.stock(2000)
				.build()
				))
			.willReturn(ProductResponseDTO.builder()
					.number(productId)
					.name("pen")
					.price(5000)
					.stock(2000)
					.build()
					);
		//	1. given 메서드를 통해 동작 규칙을 설명
		
		//	2. 테스트에 필요한 객체를 생
		ProductDTO productDTO = ProductDTO.builder()
				.name("pen")
				.price(5000)
				.stock(2000)
				.build();
		
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(productDTO);
		
		//System.out.println("content : " + content);
		
		//	실제 테스트를 수행하는 코드
		//	Post요청을 통해 도출된 결과값에 대해 각 항목이 존재하는지 jsonPath().exists()를 통해 검증
		mockMvc.perform(
					post("/api/product")
						.content(content)
						.contentType(MediaType.APPLICATION_JSON))
				   .andExpect(status().isOk())
				   .andExpect(jsonPath("$.number").exists())
				   .andExpect(jsonPath("$.name").exists())
				   .andExpect(jsonPath("$.price").exists())
				   .andExpect(jsonPath("$.stock").exists())
				   .andDo(print());
		
		verify(productServiceImpl).saveProduct(ProductDTO.builder()
				.name("pen")
				.price(5000)
				.stock(2000)
				.build());
	}
	
	
}
