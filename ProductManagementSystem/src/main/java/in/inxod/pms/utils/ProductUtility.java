package in.inxod.pms.utils;

import org.springframework.beans.BeanUtils;

import in.inxod.pms.dto.ProductBrandDto;
import in.inxod.pms.dto.ProductCategoryDto;
import in.inxod.pms.dto.ProductDto;
import in.inxod.pms.model.Product;
import in.inxod.pms.model.ProductBrand;
import in.inxod.pms.model.ProductCategory;

public class ProductUtility {


	/**
	 * Converts Product entity -> ProductDto
	 * This is used when sending data from backend to API response.
	 */
	
	public static ProductDto convertProductToProductDto(Product product) {

		 // Create empty DTO objects
		ProductDto productDto = new ProductDto();
		ProductBrandDto brandDto = new ProductBrandDto();
		ProductCategoryDto categoryDto = new ProductCategoryDto();

		// Copy properties from ProductBrand entity -> ProductBrandDto
		BeanUtils.copyProperties(product.getProductBrand(), brandDto);
		
		 // Copy properties from ProductCategory entity -> ProductCategoryDto
		BeanUtils.copyProperties(product.getProductCategory(), categoryDto);
		
		// Copy properties from Product -> ProductDto, 
	    // but ignore "productCategory" and "productBrand" (handled separately)
		BeanUtils.copyProperties(product, productDto, "productCategory", "productBrand");

		 // Manually set brand and category DTOs inside productDto
		productDto.setProductBrand(brandDto);
		productDto.setProductCategory(categoryDto);

		return productDto;
	}
	


		/**
		 * Converts ProductDto -> Product entity
		 * This is used when saving/updating data in the database.
		 */
	public static Product constructProductModel(ProductDto dto, ProductBrand brand, ProductCategory category) {
		
		 // Create empty Product entity
		Product product = new Product();
		
		 // Copy all simple properties from ProductDto -> Product,
	    // but ignore brand and category (set them separately)
		BeanUtils.copyProperties(dto, product, "productCategory", "productBrand");
		
		// Set brand and category entities manually
		product.setProductBrand(brand);
		product.setProductCategory(category);
		
		return product;
	}

}
