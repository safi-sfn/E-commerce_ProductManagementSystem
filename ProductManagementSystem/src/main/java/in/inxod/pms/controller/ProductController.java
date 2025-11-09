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

//This is a Controller class that handles HTTP requests for product operations
@RestController
//@RequestMapping("/api")
public class ProductController {

	private static final String UPLOAD_DIR = "uploads/";

	// Automatically injects the ProductService implementation
	@Autowired
	private ProductServiceImpl service;

	// Handles POST requests to add a new product with an image
	@PostMapping("/add-product")
	public ResponseEntity<ProductDto> addProduct(@RequestParam("product") String product,
			@RequestParam("image") MultipartFile imageFile) throws IOException {

		System.out.println("Received product JSON-------: " + product); // For Debug
		System.out.println("Received image file---------: " + imageFile.getOriginalFilename()); // for debug

		// Step 1: Convert JSON string to ProductDto object using ObjectMapper
		// ObjectMapper is a Jackson library class that converts JSON to Java objects
		// and vice versa
		ObjectMapper mapper = new ObjectMapper();
		ProductDto productDto = mapper.readValue(product, ProductDto.class);

		// Step 2: Save the image file to local storage
		String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
		Path imagePath = Paths.get(UPLOAD_DIR + fileName);
		Files.createDirectories(imagePath.getParent());
		Files.write(imagePath, imageFile.getBytes());

		// Step 3: Set the image URL in the ProductDto
		// This URL will be stored in the database and used to display the image
		String imageUrl = "http://localhost:2426/inxod/pms/uploads/" + fileName;
		productDto.setProductImageUrl(imageUrl);

		// Step 4: Call the service layer to add the product to the database
		ProductDto dto = service.addProduct(productDto);
		return new ResponseEntity<>(dto, HttpStatus.CREATED);

		/*
		 * ObjectMapper mapper = new ObjectMapper(); ProductDto productDto =
		 * mapper.readValue(product, ProductDto.class);
		 * productDto.setProductImage(imageFile.getBytes());
		 * 
		 * ProductDto dto = service.addProduct(productDto); return new
		 * ResponseEntity<>(dto, HttpStatus.CREATED);
		 */
	}

	// This method handles GET requests to retrieve all products with pagination
	@GetMapping("/products")
	public ResponseEntity<Page<ProductDto>> getAllProducts(@PageableDefault(size = 25, page = 0) Pageable pageble) {
		// @PageableDefault sets default pagination values if not provided in the
		// request
		// size=25: Default page size (number of products per page)
		// page=0: Default page number (first page)

		// Step 1: Call the service layer to get a page of products
		// The service returns a Page<ProductDto> containing:
		// - The products for the requested page
		// - Pagination information (total pages, total elements, etc.)
		Page<ProductDto> allProduct = service.getAllProduct(pageble);
		return new ResponseEntity<>(allProduct, HttpStatus.OK);
	}

	// This method handles GET requests to retrieve a specific product by its ID
	@GetMapping("/product/{id}")
	public ResponseEntity<ProductDto> getProductById(@PathVariable int id) {

		// Step 1: Call the service layer to find the product by ID
		ProductDto productDto = service.getProductById(id);

		// Step 2: Check if the product was found
		if (productDto == null) {
			// If product not found, throw a custom exception
			throw new ProductNotFoundException("Product with Id " + id + " does not exist");
		}
		return new ResponseEntity<>(productDto, HttpStatus.OK);
	}

	// This method handles PUT requests to update an existing product
	@PutMapping("update-product/{id}")
	public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Integer id,
			@RequestBody ProductDto productDto) {

		// Step 1: Call the service layer to update the product
		// The service will:
		// - Find the existing product by ID
		// - Update its fields with the new values from productDto
		// - Save the updated product back to the database
		ProductDto updateProduct = service.updateProduct(id, productDto);
		return new ResponseEntity<>(updateProduct, HttpStatus.OK);
	}

	// This method handles GET requests to search for products by brand name with
	// pagination
	@GetMapping("/getBy-brandName/{brandName}")
	public ResponseEntity<Page<ProductDto>> searchProductsByBrand(@PathVariable("brandName") String brandName,
			@PageableDefault(size = 10, page = 0) Pageable pageable) {

		// Step 1: Call the service layer to search for products by brand name
		Page<ProductDto> responsePageDto = service.searchProductsByBrand(brandName, pageable);

		// Step 2: Check if any products were found
		if (responsePageDto == null || responsePageDto.getTotalElements() <= 0) {
			return new ResponseEntity<>(Page.empty(), HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(responsePageDto, HttpStatus.OK);
		}
	}

	// This method handles GET requests to search for products by name
	@GetMapping("/products/{productName}")
	public ResponseEntity<List<ProductDto>> searchByProductName(@PathVariable("productName") String productName) {

		// Step 1: Call the service layer to search for products by name
		// The service returns a List<ProductDto> containing all matching products
		List<ProductDto> products = service.searchByProductName(productName);

		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	// This method handles GET requests to search for products within a specific
	// price range with pagination
	@GetMapping("/products/{min}/{max}")
	public ResponseEntity<Page<ProductDto>> serchByPriceRange(@PathVariable("min") double min,
			@PathVariable("max") double max,
			@PageableDefault(page = 0, size = 15, sort = "productName", direction = Sort.Direction.ASC) Pageable pageble) {

		// Step 1: Call the service layer to search for products in the price range
		Page<ProductDto> product = service.searchByTheProductPriceRange(min, max, pageble);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	// This method handles DELETE requests to remove a product from the system
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") int id) {

		// Step 1: Call the service layer to delete the product
		// - Check if the product exists
		// - Delete it from the database
		// - Return a status message
		String deleteStatus = service.deleteProduct(id);
		return new ResponseEntity<String>(deleteStatus, HttpStatus.OK);
	}

}
