package in.inxod.pms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import in.inxod.pms.dto.ProductDto;
import in.inxod.pms.globalException.BrandNameNotValidException;
import in.inxod.pms.globalException.ProductNotFoundException;
import in.inxod.pms.model.Product;
import in.inxod.pms.model.ProductBrand;
import in.inxod.pms.model.ProductCategory;
import in.inxod.pms.repository.ProductBrandRepository;
import in.inxod.pms.repository.ProductCategoryRepository;
import in.inxod.pms.repository.ProductRepository;
import in.inxod.pms.utils.ProductUtility;

/*
  This class implements the ProductService interface and provides the actual business logic
   for product-related operations. It's annotated as a Spring Service component.
*/
@Service
public class ProductServiceImpl implements ProductService {

	// Automatically injects the ProductRepository dependency for database
	// operations
	@Autowired
	private ProductRepository productRepo;

	// Automatically injects the ProductCategoryRepository for category-related
	// operations
	@Autowired
	private ProductCategoryRepository categoryRepo;

	// Automatically injects the ProductBrandRepository for brand-related operations
	@Autowired
	private ProductBrandRepository brandRepo;

	// Implementation of the add a new product
	@Override
	public ProductDto addProduct(ProductDto productDto) {

		// Check if the brand already exists in the database (case-insensitive search)
		ProductBrand newBrand = brandRepo.findByBrandNameIgnoreCase(productDto.getProductBrand().getBrandName());

		// If the brand doesn't exist, create a new brand entity
		if (newBrand == null) {
			newBrand = new ProductBrand();
			newBrand.setBrandName(productDto.getProductBrand().getBrandName());
			newBrand = brandRepo.save(newBrand); // Save the new brand to database
		}

		// Check if the category already exists in the database (case-insensitive
		// search)
		ProductCategory newCategory = categoryRepo
				.findByCategoryNameIgnoreCase(productDto.getProductCategory().getCategoryName());
		// If the category doesn't exist, create a new category entity
		if (newCategory == null) {
			newCategory = new ProductCategory();
			newCategory.setCategoryName(productDto.getProductCategory().getCategoryName());
			newCategory.setOccassion(productDto.getProductCategory().getOccassion());
			newCategory.setConsumerType(productDto.getProductCategory().getConsumerType());
			newCategory = categoryRepo.save(newCategory); // Save the new category to database
		}

		// Convert the ProductDto to a Product entity using a utility method
		Product product = ProductUtility.constructProductModel(productDto, newBrand, newCategory);
		// Save the product to the database
		product = productRepo.save(product);

		// Convert the saved Product entity back to ProductDto and return it
		return ProductUtility.convertProductToProductDto(product);
	}

	// Implementation of the method to get all products with pagination support
	@Override
	public Page<ProductDto> getAllProduct(Pageable pageable) {

		// Step 1: Fetch a page of Product entities from the database using the
		// repository
		// Step 2: Convert each Product entity in the page to a ProductDto using map()
		Page<ProductDto> products = productRepo.findAll(pageable)
				.map(product -> ProductUtility.convertProductToProductDto(product));
		// Step 3: Return the page of ProductDto objects
		return products;
	}

	// Implementation of the method to get a product by its ID
	@Override
	public ProductDto getProductById(int id) {
		// find the product in the database by its ID
		// findById() method returns an Optional<Product> which may or may not contain a
		// product
		Optional<Product> productExist = productRepo.findById(id);

		// Step 2: Initialize a variable to hold the result (will be null if product not
		// found)
		ProductDto prodResDto = null;

		// Step 3: Check if the product was found in the database
		if (productExist.isPresent()) {
			// Step 3a: If product exists, extract it from the Optional using get()
			// Step 3b: Convert the Product entity to ProductDto using the utility method
			prodResDto = ProductUtility.convertProductToProductDto(productExist.get());
		}
		// Step 4: Return the ProductDto (will be null if product wasn't found)
		return prodResDto;
	}

	// Implementation of the method to update an existing product
	@Override
	public ProductDto updateProduct(Integer id, ProductDto productDto) {

		// Step 1: find the existing product by ID
		Optional<Product> optional = productRepo.findById(id);

		// Step 2: Check if the product exists - if not, throw an exception
		if (optional.isEmpty()) {
			throw new ProductNotFoundException("Product Not Found with this id : " + id);
		}

		// Step 3: Extract the product from the Optional container
		Product existingProduct = optional.get();

		// Step 4: Handle the product brand - get existing or create new
		ProductBrand newBrand = brandRepo.findByBrandNameIgnoreCase(productDto.getProductBrand().getBrandName());
		if (newBrand == null) {
			// Create a new brand if it doesn't exist
			newBrand = new ProductBrand();
			newBrand.setBrandName(productDto.getProductBrand().getBrandName());
			// Save the new brand to database
			newBrand = brandRepo.save(newBrand);
		}

		// Step 5: Handle the product category - get existing or create new
		ProductCategory newCategory = categoryRepo
				.findByCategoryNameIgnoreCase(productDto.getProductCategory().getCategoryName());
		if (newCategory == null) {
			newCategory = new ProductCategory();
			newCategory.setCategoryName(productDto.getProductCategory().getCategoryName());
			newCategory.setOccassion(productDto.getProductCategory().getOccassion());
			newCategory.setConsumerType(productDto.getProductCategory().getConsumerType());
			// Save the new category to database
			newCategory = categoryRepo.save(newCategory);
		}

		// Step 6: Update the basic product information from the DTO
		existingProduct.setProductName(productDto.getProductName());
		existingProduct.setProductPrice(productDto.getProductPrice());
		existingProduct.setQuantityAvailable(productDto.getQuantityAvailable());
		existingProduct.setProductRating(productDto.getProductRating());
		// Step 7: Only update the image URL if a new one was provided
		// This prevents overwriting existing image with null if no new image was sent
		if (productDto.getProductImageUrl() != null) {
			existingProduct.setProductImageUrl(productDto.getProductImageUrl());
		}

		// Step 8: Associate the updated brand and category with the product
		existingProduct.setProductBrand(newBrand);
		existingProduct.setProductCategory(newCategory);

		// Step 9: Save the updated product to the database
		Product savedProduct = productRepo.save(existingProduct);

		// Step 10: Convert the saved product back to DTO and return it
		return ProductUtility.convertProductToProductDto(savedProduct);
	}

	// Implementation of the method to search for products by brand name with
	// pagination
	@Override
	public Page<ProductDto> searchProductsByBrand(String brandName, Pageable pageable) {

		// Step 1: Validate the input brand name
		// Check if the brandName is null, empty, or contains only whitespace
		// This prevents unnecessary database queries with invalid input
		if (brandName.equals("null") || brandName == null || brandName.trim().isEmpty()) {
			throw new BrandNameNotValidException("Brand Cannot be Empty");
		}

		// Step 2: Search for products by brand name using the repository
		Page<Product> products = brandRepo.findByBrandNameContainingIgnoreCase(brandName, pageable);

		// Step 3: Check if any products were found
		// products.getContent() returns the list of products in the current page
		if (products != null && !products.getContent().isEmpty()) {
			// Step 3a: Convert each Product entity to ProductDto using the utility method
			// The map() function transforms the Page<Product> to Page<ProductDto>
			Page<ProductDto> responseProducts = products
					.map(product -> ProductUtility.convertProductToProductDto(product));
			// Step 3b: Return the page of converted ProductDto objects
			return responseProducts;
		}
		// Step 4: If no products were found, throw an exception
		throw new BrandNameNotValidException("Brand does not exist or Not Valid Brand");
	}

	// Implementation of the method to search for products by name
	@Override
	public List<ProductDto> searchByProductName(String productName) {

		// Step 1: Search for products in the database using the repository method
		List<Product> productList = productRepo.searchByName(productName);

		// Step 2: Create an empty list to store the converted ProductDto objects
		// We use ArrayList as it's efficient for adding elements sequentially
		List<ProductDto> productDtoList = new ArrayList<>();

		// Step 3: Convert each Product entity to ProductDto
		// Loop through each product in the result list
		for (Product product : productList) {
			// Convert the Product entity to ProductDto using the utility method
			ProductDto dto = ProductUtility.convertProductToProductDto(product);
			// Add the converted DTO to our result list
			productDtoList.add(dto);
		}
		// Step 4: Return the list of ProductDto objects
		// This could be an empty list if no products were found
		return productDtoList;
	}

	// Implementation of the method to search for products within a price range with
	// pagination
	@Override
	public Page<ProductDto> searchByTheProductPriceRange(double min, double max, Pageable pageable) {
		// Step 1: Search for products in the database within the specified price range
		Page<Product> products = productRepo.searchByPriceRange(min, max, pageable);

		// Step 2: Convert the Page<Product> to Page<ProductDto> using map()
		return products.map(product -> ProductUtility.convertProductToProductDto(product));

	}

	// Implementation of the method to delete a product by its ID
	@Override
	public String deleteProduct(int id) {

		// Step 1: Check if the product exists before attempting to delete it
		productRepo.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("Product Not Found with this id : " + id));

		// Step 2: Delete the product from the database using the ID
		productRepo.deleteById(id);
		return "Product Deleted";
	}

}
