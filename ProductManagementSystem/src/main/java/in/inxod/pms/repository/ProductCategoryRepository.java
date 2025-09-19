package in.inxod.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.inxod.pms.model.ProductCategory;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
	
	// This method finds a ProductCategory by its name, ignoring case differences
	// Example: It will match "Electronics", "electronics", or "ELECTRONICS" as the same
		public ProductCategory findByCategoryNameIgnoreCase(String productCategory);
}
