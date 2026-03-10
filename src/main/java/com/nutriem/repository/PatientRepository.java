package com.nutriem.repository;

import com.nutriem.model.Patient;
import com.nutriem.model.Patient.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("SELECT p FROM Patient p WHERE p.nutriologist.id = :id")
    Page<Patient> findAllByNutritionistId(@Param("id") Long nutriologistId, Pageable pageable);

    @Query("SELECT p FROM Patient p WHERE p.nutriologist.id = :id AND p.status = :status")
    List<Patient> findAllByNutritionistIdAndStatus(@Param("id") Long nutriologistId, @Param("status") Status status);

    @Query("""
        SELECT p FROM Patient p
        WHERE p.nutriologist.id = :id
          AND (LOWER(p.firstName) LIKE LOWER(CONCAT('%',:q,'%'))
            OR LOWER(p.lastName)  LIKE LOWER(CONCAT('%',:q,'%'))
            OR LOWER(p.email)     LIKE LOWER(CONCAT('%',:q,'%')))
    """)
    Page<Patient> searchByNutriologist(@Param("id") Long nutriologistId, @Param("q") String query, Pageable pageable);

    @Query("SELECT p FROM Patient p WHERE p.id = :id AND p.nutriologist.id = :nid")
    Optional<Patient> findByIdAndNutritionistId(@Param("id") Long id, @Param("nid") Long nutriologistId);

    @Query("SELECT COUNT(p) FROM Patient p WHERE p.nutriologist.id = :id")
    long countByNutritionistId(@Param("id") Long nutriologistId);

    @Query("SELECT COUNT(p) FROM Patient p WHERE p.nutriologist.id = :id AND p.status = :status")
    long countByNutritionistIdAndStatus(@Param("id") Long nutriologistId, @Param("status") Status status);
}
