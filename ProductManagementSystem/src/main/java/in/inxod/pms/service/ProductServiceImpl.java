package in.inxod.pms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import in.inxod.pms.dto.ProductDto;
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
			categoryRepo.save(newCategory);
		}
		
		Product product = ProductUtility.constructProductModel(productDto, newBrand, newCategory);
		product = productRepo.save(product);
		return ProductUtility.convertProductToProductDto(product);
	}

	@Override
	public Page<ProductDto> getAllProduct(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductDto getProductById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductDto updateProduct(Integer id, ProductDto productDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<ProductDto> searchProductsByBrand(String brandName, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductDto> searchByTheName(String productName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<ProductDto> searchByTheProductPriceRange(double min, double max, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
