package in.inxod.pms.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import in.inxod.pms.dto.ProductDto;

public interface ProductService {

	ProductDto addProduct(ProductDto productDto);
	
	Page<ProductDto> getAllProduct(Pageable pageable);
	
	ProductDto getProductById(int id);
	
	ProductDto updateProduct(Integer id, ProductDto productDto);
	
	Page<ProductDto> searchProductsByBrand(String brandName,Pageable pageable);
	
	List<ProductDto> searchByTheName(String productName);
	
	Page<ProductDto> searchByTheProductPriceRange(double min,double max , Pageable pageable);
}
