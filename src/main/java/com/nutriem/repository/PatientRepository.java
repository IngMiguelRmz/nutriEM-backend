package com.nutriem.repository;

import com.nutriem.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("SELECT p FROM Patient p WHERE p.nutriologist.id = :nid ORDER BY p.createdAt DESC")
    Page<Patient> findAllByNutritionistId(@Param("nid") Long nutriologistId, Pageable pageable);

    @Query("SELECT p FROM Patient p WHERE p.nutriologist.id = :nid AND p.id = :id")
    Optional<Patient> findByIdAndNutritionistId(@Param("id") Long id, @Param("nid") Long nutriologistId);

    @Query("SELECT p FROM Patient p WHERE p.nutriologist.id = :nid AND " +
           "(LOWER(p.firstName) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           " LOWER(p.lastName)  LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           " LOWER(p.email)     LIKE LOWER(CONCAT('%', :q, '%'))) " +
           "ORDER BY p.createdAt DESC")
    Page<Patient> searchByNutriologist(@Param("nid") Long nutriologistId,
                                       @Param("q") String query,
                                       Pageable pageable);

    @Query("SELECT COUNT(p) FROM Patient p WHERE p.nutriologist.id = :nid")
    long countByNutritionistId(@Param("nid") Long nutriologistId);

    @Query("SELECT COUNT(p) FROM Patient p WHERE p.nutriologist.id = :nid AND p.status = 'ACTIVE'")
    long countActiveByNutritionistId(@Param("nid") Long nutriologistId);

    @Query("SELECT COUNT(p) FROM Patient p WHERE p.nutriologist.id = :nid AND p.createdAt >= :since")
    long countByNutritionistIdAndCreatedAtAfter(@Param("nid") Long nutriologistId,
                                                @Param("since") LocalDateTime since);

    @Query("SELECT p FROM Patient p WHERE p.nutriologist.id = :nid ORDER BY p.createdAt DESC")
    List<Patient> findTop5ByNutritionistId(@Param("nid") Long nutriologistId, Pageable pageable);

    @Query("SELECT p.primaryGoal, COUNT(p) FROM Patient p WHERE p.nutriologist.id = :nid " +
           "AND p.primaryGoal IS NOT NULL GROUP BY p.primaryGoal")
    List<Object[]> countByGoalForNutritionist(@Param("nid") Long nutriologistId);
}
