package in.inxod.pms.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import in.inxod.pms.dto.ProductDto;

public interface ProductService {
	
		// Adds a new product 
		// Takes a ProductDto as input and returns the created product as DTO
		ProductDto addProduct(ProductDto productDto);
		
		// Retrieves all products with pagination support
		Page<ProductDto> getAllProduct(Pageable pageable);
		
		// Retrieves a specific product by product id ID 
		ProductDto getProductById(int id);
		
		// Updates an existing product with the given ID
		// Takes the product ID and updated ProductDto as input, returns the updated product as DTO
		ProductDto updateProduct(Integer id, ProductDto productDto);
		
		// Searches for products by brand name with pagination support
		Page<ProductDto> searchProductsByBrand(String brandName, Pageable pageable);
		
		// Searches for products by name
		// Returns a List of ProductDto objects matching the product name 
		List<ProductDto> searchByProductName(String productName);
		
		// Searches for products within a specific price range with pagination support
		// Returns a Page of ProductDto objects where price is between min and max values
		Page<ProductDto> searchByTheProductPriceRange(double min, double max, Pageable pageable);
		
		// Delete The Product
		String deleteProduct(int id);
	}

