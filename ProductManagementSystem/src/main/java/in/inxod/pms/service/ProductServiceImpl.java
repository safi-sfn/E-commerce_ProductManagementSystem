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

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private ProductCategoryRepository categoryRepo;
	@Autowired
	private ProductBrandRepository brandRepo;
	
	@Override
	public ProductDto addProduct(ProductDto productDto) {
		
		ProductBrand newBrand = brandRepo.findByBrandNameIgnoreCase(productDto.getProductBrand().getBrandName());
		if(newBrand == null) {
			 newBrand = new ProductBrand();
			newBrand.setBrandName(productDto.getProductBrand().getBrandName());
			newBrand = brandRepo.save(newBrand);
		}
		
		
		ProductCategory newCategory = categoryRepo.findByCategoryNameIgnoreCase(productDto.getProductCategory().getCategoryName());
		if(newCategory ==null) {
			newCategory = new ProductCategory();
			newCategory.setCategoryName(productDto.getProductCategory().getCategoryName());
			newCategory.setOccassion(productDto.getProductCategory().getOccassion());
		    newCategory.setConsumerType(productDto.getProductCategory().getConsumerType());
			newCategory = categoryRepo.save(newCategory);
		}
		
		Product product = ProductUtility.constructProductModel(productDto, newBrand, newCategory);
		product = productRepo.save(product);
		return ProductUtility.convertProductToProductDto(product);
	}

	@Override
	public Page<ProductDto> getAllProduct(Pageable pageable) {
		Page<ProductDto> products =  productRepo.findAll(pageable).map(product -> ProductUtility.convertProductToProductDto(product));
		return products;
	}

	@Override
	public ProductDto getProductById(int id) {
		Optional<Product> productExist = productRepo.findById(id);
		ProductDto prodResDto = null;
		if(productExist.isPresent()) {
			prodResDto = ProductUtility.convertProductToProductDto(productExist.get());
		}
		return prodResDto;
	}

	@Override
	public ProductDto updateProduct(Integer id, ProductDto productDto) {
		Optional<Product> optional = productRepo.findById(id);
		if(optional.isEmpty()) {
			throw new ProductNotFoundException("Product Not Found with this id : "+id);
		}
		Product existingProduct = optional.get();
		
		// Get or Create Brand
		ProductBrand newBrand = brandRepo.findByBrandNameIgnoreCase(productDto.getProductBrand().getBrandName());
		if(newBrand == null) {
			 newBrand = new ProductBrand();
			newBrand.setBrandName(productDto.getProductBrand().getBrandName());
			newBrand = brandRepo.save(newBrand);
		}
		
		// Get or Create Category
		ProductCategory newCategory = categoryRepo.findByCategoryNameIgnoreCase(productDto.getProductCategory().getCategoryName());
		if(newCategory ==null) {
			newCategory = new ProductCategory();
			newCategory.setCategoryName(productDto.getProductCategory().getCategoryName());
			newCategory.setOccassion(productDto.getProductCategory().getOccassion());
		    newCategory.setConsumerType(productDto.getProductCategory().getConsumerType());
			newCategory = categoryRepo.save(newCategory);
		}
		
		// Update fields on the existing product
				existingProduct.setProductName(productDto.getProductName());
				existingProduct.setProductPrice(productDto.getProductPrice());
				existingProduct.setQuantityAvailable(productDto.getQuantityAvailable());
				existingProduct.setProductRating(productDto.getProductRating());
		if(productDto.getProductImageUrl() != null) {
			existingProduct.setProductImageUrl(productDto.getProductImageUrl());
		}
		
		existingProduct.setProductBrand(newBrand);
		existingProduct.setProductCategory(newCategory);
		Product savedProduct = productRepo.save(existingProduct);
		return ProductUtility.convertProductToProductDto(savedProduct);
	}

	@Override
	public Page<ProductDto> searchProductsByBrand(String brandName, Pageable pageable) {
		
		if(brandName.equals(null) || brandName == null || brandName.isEmpty()) {
			throw new BrandNameNotValidException("Brand Cannot be  Empty");
		}
		
		Page<Product> products= brandRepo.findByBrandNameContainingIgnoreCase(brandName, pageable);
		if (products != null && !products.getContent().isEmpty()) {
			Page<ProductDto> responseProducts = products.map(product -> ProductUtility.convertProductToProductDto(product));
			return responseProducts;
		}
		throw new BrandNameNotValidException("Brand does not exist or Not Valid Brand");
	}

	@Override
	public List<ProductDto> searchByProductName(String productName) {
		List<Product> productList = productRepo.searchByName(productName);
		
		List<ProductDto> productDtoList = new ArrayList<>();
		for(Product product : productList) {
			ProductDto dto = ProductUtility.convertProductToProductDto(product);
			productDtoList.add(dto);
		}
		return productDtoList;
	}

	@Override
	public Page<ProductDto> searchByTheProductPriceRange(double min, double max, Pageable pageable) {
		Page<Product> products = productRepo.searchByPriceRange(min, max, pageable);
		return products.map(product->ProductUtility.convertProductToProductDto(product));
		
	}

	@Override
	public String deleteProduct(int id) {
		productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException("Product Not Found with this id : " + id));
	    productRepo.deleteById(id);
	    return "Product Deleted";	
	}

}
