package com.FastKart.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.FastKart.entities.Category;
import com.FastKart.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	// to show product by its category (one category multiple Product)
	List<Product> findProductByCategory(Category category);

	// Custom query to find products by multiple category IDs
	/*
	 * @Query("SELECT p FROM Product p JOIN p.category c WHERE c.id IN :categoryIds"
	 * ) List<Product> findProductsByCategoryIds(@Param("categoryIds") List<Integer>
	 * categoryIds,Pageable pageable);
	 */

	@Query("SELECT p FROM Product p JOIN p.category c WHERE c.id IN :categoryIds")
	Page<Product> findProductsByCategoryIds(@Param("categoryIds") List<Integer> categoryIds, Pageable pageable);

	int countByCategory(Category category);

	@Query("SELECT p FROM Product p WHERE price Between :minPrice AND :maxPrice")
	Page<Product> findByPriceBetween(double minPrice, double maxPrice, Pageable pageable);
	
	  @Query("SELECT p FROM Product p JOIN p.category c WHERE c.id IN :categoryIds AND p.price BETWEEN :minPrice AND :maxPrice")
	    Page<Product> findByCategoryIdsAndPriceBetween(
	        @Param("categoryIds") List<Integer> categoryIds,
	        @Param("minPrice") double minPrice,
	        @Param("maxPrice") double maxPrice,
	        Pageable pageable
	    );

	// Count products within the price range 0 to 1000
	@Query("SELECT COUNT(p) FROM Product p WHERE p.price BETWEEN 0 AND 1000")
	int countProductsInRange0To1000();

	// Count products within the price range 1000 to 10000
	@Query("SELECT COUNT(p) FROM Product p WHERE p.price BETWEEN 1000 AND 10000")
	int countProductsInRange1000To10000();

	// Count products within the price range 10000 to 100000
	@Query("SELECT COUNT(p) FROM Product p WHERE p.price BETWEEN 10000 AND 100000")
	int countProductsInRange10000To100000();

	// count all product
	@Query("SELECT COUNT(p) FROM Product p")
	int countAllProducts();


	// int countAllBy(); // Spring Data JPA can automatically implement a counting
	// method without needing a custom query if you follow the naming conventions.
 
//======================================= TO CHECK THAT PRODUCT IS ALREADY EXIST  OR NOT ======================================================
	   boolean existsByPname(String pname);
	
//===================================== TO IMPLEMENT SEARCH FINCTIONSLITY ====================================================================
	   Page<Product> findByPnameContaining(String keyword, Pageable pageable);
	   
	   /*SELECT * FROM Product WHERE name LIKE '%keyword%';
*/
	   
}
