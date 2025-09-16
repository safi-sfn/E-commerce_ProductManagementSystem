package in.inxod.pms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.inxod.pms.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	  @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	    List<Product> searchByName(@Param("keyword") String keyword);

	    @Query("select  p from Product p where p.productPrice between :min and :max")
	    Page<Product> searchByPriceRange(@Param("min") double min, @Param("max") double max, Pageable pageable);
}
