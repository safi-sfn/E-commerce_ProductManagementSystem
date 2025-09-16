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

	ProductBrand findByBrandNameIgnoreCase(String brandName);
	
	@Query("SELECT p FROM Product p WHERE LOWER(p.productBrand.brandName) LIKE LOWER(CONCAT('%',:name,'%'))")
    Page<Product> findByBrandNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);
}
