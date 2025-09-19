package in.inxod.pms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.inxod.pms.model.Product;

//extends JpaRepository to get built-in CRUD operations for Product entity
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	// This method searches for products by name using a custom query
	  @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	    List<Product> searchByName(@Param("keyword") String keyword);

	// This method searches for products within a specific price range
	    @Query("select  p from Product p where p.productPrice between :min and :max")
	    Page<Product> searchByPriceRange(@Param("min") double min, @Param("max") double max, Pageable pageable);
}
