package in.inxod.pms.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.inxod.pms.dto.ProductDto;
import in.inxod.pms.globalException.ProductNotFoundException;
import in.inxod.pms.service.ProductServiceImpl;

@RestController
@RequestMapping("/api")
public class ProductController {

	private static final String UPLOAD_DIR = "uploadsImage/";
	
	@Autowired
	private ProductServiceImpl service;
	
	@PostMapping("/add-product")
	public ResponseEntity<ProductDto> addProduct(
					@RequestParam("product") String product,
					@RequestParam("image") MultipartFile imageFile) throws  IOException {
		// ObjectMApper Convert JSON to Java Object
		ObjectMapper mapper = new ObjectMapper();
		ProductDto productDto = mapper.readValue(product, ProductDto.class);
		
		 // Image file ko local folder me save karna
        String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        Path imagePath = Paths.get(UPLOAD_DIR + fileName);
        Files.createDirectories(imagePath.getParent());
        Files.write(imagePath, imageFile.getBytes());

        // DTO me sirf image URL set karna
        String imageUrl = "http://localhost:2426/inxod/pms/api/" + UPLOAD_DIR + fileName;
        productDto.setProductImageUrl(imageUrl);
        
		ProductDto dto = service.addProduct(productDto);
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}
	
	
	@GetMapping("/products")
	public ResponseEntity<Page<ProductDto>> getAllProducts(@PageableDefault(size=10, page=0) Pageable pageble){
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
	
	
	
	
}




















