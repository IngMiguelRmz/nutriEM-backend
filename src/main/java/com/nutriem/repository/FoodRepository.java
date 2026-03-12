package com.nutriem.repository;

import com.nutriem.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    @Query("SELECT f FROM Food f WHERE LOWER(f.name) LIKE LOWER(CONCAT('%', :q, '%')) ORDER BY f.isVerified DESC, f.name ASC")
    List<Food> searchByName(@Param("q") String query);

    @Query("SELECT f FROM Food f WHERE f.category = :cat ORDER BY f.name ASC")
    List<Food> findByCategory(@Param("cat") Food.Category category);

    boolean existsByNameIgnoreCase(String name);
}
