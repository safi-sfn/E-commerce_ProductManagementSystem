package in.inxod.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.inxod.pms.model.ProductCategory;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
	
	ProductCategory findByCategoryNameIgnoreCase(String productCategory);
}
