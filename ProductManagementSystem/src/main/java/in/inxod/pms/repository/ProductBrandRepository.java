package in.inxod.pms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.inxod.pms.model.Product;
import in.inxod.pms.model.ProductBrand;
@Repository
public interface ProductBrandRepository extends JpaRepository<ProductBrand, Integer> {

	// This method finds a ProductBrand by its name, ignoring case differences
	// Example: It will match "Nike", "NIKE", or "nike" as the same brand
	public ProductBrand findByBrandNameIgnoreCase(String brandName);
	
	
	// This method finds products by brand name containing a specific string (case-insensitive)
	// It uses JPQL (Java Persistence Query Language) to define a custom search query
	// Results are returned in a paginated format (Page) for efficient data handling
	@Query("SELECT p FROM Product p WHERE LOWER(p.productBrand.brandName) LIKE LOWER(CONCAT('%',:name,'%'))")
    Page<Product> findByBrandNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);
}
