package com.nutriem.repository;

import com.nutriem.model.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    @Query("SELECT f FROM Food f WHERE LOWER(f.name) LIKE LOWER(CONCAT('%',:q,'%')) OR LOWER(f.nameEs) LIKE LOWER(CONCAT('%',:q,'%'))")
    Page<Food> searchByName(@Param("q") String query, Pageable pageable);

    List<Food> findByCategory(Food.Category category);
}
