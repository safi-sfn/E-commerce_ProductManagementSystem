package in.inxod.pms.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import javax.swing.SortOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.inxod.pms.dto.ProductDto;
import in.inxod.pms.globalException.ProductNotFoundException;
import in.inxod.pms.service.ProductServiceImpl;

@RestController
//@RequestMapping("/api")
public class ProductController {

	private static final String UPLOAD_DIR = "uploads/";
	
	@Autowired
	private ProductServiceImpl service;
	
	@PostMapping("/add-product")
	public ResponseEntity<ProductDto> addProduct(
					@RequestParam("product") String product,
					@RequestParam("image") MultipartFile imageFile) throws  IOException {

		
		System.out.println("Received product JSON-------: " + product);
	    System.out.println("Received image file---------: " + imageFile.getOriginalFilename());
		// ObjectMApper Convert JSON to Java Object
		ObjectMapper mapper = new ObjectMapper();
		ProductDto productDto = mapper.readValue(product, ProductDto.class);
		
		 // Image file ko local folder me save karna
        String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        Path imagePath = Paths.get(UPLOAD_DIR + fileName);
        Files.createDirectories(imagePath.getParent());
        Files.write(imagePath, imageFile.getBytes());

        // DTO me sirf image URL set karna
        String imageUrl = "http://localhost:2426/inxod/pms/uploads/"+ fileName;
        productDto.setProductImageUrl(imageUrl);
        
		ProductDto dto = service.addProduct(productDto);
		return new ResponseEntity<>(dto, HttpStatus.CREATED);

/*		
		ObjectMapper mapper = new ObjectMapper();
		ProductDto productDto = mapper.readValue(product, ProductDto.class);
		productDto.setProductImage(imageFile.getBytes());

		ProductDto dto = service.addProduct(productDto);
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
*/
	}
	
	
	@GetMapping("/products")
	public ResponseEntity<Page<ProductDto>> getAllProducts(@PageableDefault(size=25, page=0) Pageable pageble){
		Page<ProductDto> allProduct = service.getAllProduct(pageble);
		return new ResponseEntity<>(allProduct,HttpStatus.OK);
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<ProductDto> getProductById(@PathVariable int id){
		ProductDto productDto= service.getProductById(id);
		if(productDto == null) {
			throw new ProductNotFoundException("Product with Id " + id + " does not exist");
		}
		return new ResponseEntity<>(productDto, HttpStatus.OK);
	}
	
	@PutMapping("update-product/{id}")
	public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Integer id, @RequestBody ProductDto productDto){
		ProductDto updateProduct = service.updateProduct(id, productDto);
		return new ResponseEntity<>(updateProduct,HttpStatus.OK);
	}
	
	@GetMapping("/getBy-brandName/{brandName}")
	public ResponseEntity<Page<ProductDto>> searchProductsByBrand(@PathVariable("brandName") String brandName,
			@PageableDefault(size = 10, page = 0) Pageable pageable) {
		Page<ProductDto> responsePageDto = service.searchProductsByBrand(brandName, pageable);
		if (responsePageDto == null || responsePageDto.getTotalElements() <= 0) {
			return new ResponseEntity<>(Page.empty(), HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(responsePageDto, HttpStatus.OK);
		}
	}

	@GetMapping("/products/{productName}")
	public ResponseEntity<List<ProductDto>> searchByProductName(@PathVariable("productName") String productName) {
		List<ProductDto> products = service.searchByProductName(productName);

		return new ResponseEntity<>(products, HttpStatus.OK);
	}
	
	@GetMapping("/products/{min}/{max}")
	public ResponseEntity<Page<ProductDto>> serchByPriceRange(@PathVariable("min") double min, 
					@PathVariable("max") double max,
					@PageableDefault(page = 0, size = 15, sort = "productName", direction = Sort.Direction.ASC ) Pageable pageble ){
		Page<ProductDto> product = service.searchByTheProductPriceRange(min, max, pageble);
		return new ResponseEntity<>(product,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") int id){
		String deleteStatus = service.deleteProduct(id);
		return new ResponseEntity<String>(deleteStatus, HttpStatus.OK);
	}
	
}




















