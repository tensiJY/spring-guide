package test.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import test.product.dto.ChangeProductNameDTO;
import test.product.dto.ProductDTO;
import test.product.dto.ProductResponseDTO;

@RestController
@RequestMapping("/api/product")
@Tag(name = "product", description = "상품")
@Slf4j
public class ProductController {

	private final ProductService productService;
	
	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@Operation(tags = { "product" }, summary = "상품 조회", description = "상품 번호로 상품을 상세 조회 한다"
			, responses = {
					@ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
					@ApiResponse(responseCode = "400", description = "에러 응답"),
				}
			)		
	@GetMapping()
	public ResponseEntity<ProductResponseDTO> getProduct(Long number){
		log.info("call getProduct : {}", number);
		ProductResponseDTO productResponseDTO = productService.getProduct(number);
		
		return ResponseEntity.status(HttpStatus.OK).body(productResponseDTO);
	}
	
	@Operation(tags = { "product" }, summary = "상품 등록", description = "새로운 상품을 등록한다"
			, responses = {
					@ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
					@ApiResponse(responseCode = "400", description = "에러 응답"),
				}
			)	
	@PostMapping()
	public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductDTO productDTO) throws Exception{
		log.info("call createProduct : {}", productDTO);
		ProductResponseDTO productResponseDTO = productService.saveProduct(productDTO);
		
		return ResponseEntity.status(HttpStatus.OK).body(productResponseDTO);
	}
	
	@Operation(tags = { "product" }, summary = "상품 이름 수정", description = "상품번호로 상품 이름을 수정한다"
			, responses = {
					@ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
					@ApiResponse(responseCode = "400", description = "에러 응답"),
				}
			)	
	@PutMapping()
	public ResponseEntity<ProductResponseDTO> changeProductName(@RequestBody ChangeProductNameDTO changeProductNameDTO) throws Exception{
		log.info("call changeProductName : {}", changeProductNameDTO);
		ProductResponseDTO productResponseDTO = productService.changeProuctName(changeProductNameDTO.getNumber(), changeProductNameDTO.getName());
		return ResponseEntity.status(HttpStatus.OK).body(productResponseDTO);
	}
	
	@Operation(tags = { "product" }, summary = "상품 삭제", description = "상품번호로 상품을 삭제한다"
			, responses = {
					@ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
					@ApiResponse(responseCode = "400", description = "에러 응답"),
				}
			)	
	@DeleteMapping()
	public ResponseEntity<String> deleteProduct(Long number) throws Exception{
		log.info("call deleteProduct : {}", number);
		productService.deleteProduct(number);
		
		return ResponseEntity.status(HttpStatus.OK).body("정상적으로  삭제되었습니");
	}
	
}
