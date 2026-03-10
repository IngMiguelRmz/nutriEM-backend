package com.nutriem.repository;

import com.nutriem.model.DietPlan;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DietPlanRepository extends JpaRepository<DietPlan, Long> {

    @Query("SELECT d FROM DietPlan d WHERE d.patient.id = :patientId ORDER BY d.createdAt DESC")
    List<DietPlan> findAllByPatientId(@Param("patientId") Long patientId);

    @Query("SELECT d FROM DietPlan d WHERE d.patient.id = :patientId AND d.status = :status")
    List<DietPlan> findAllByPatientIdAndStatus(@Param("patientId") Long patientId,
                                               @Param("status") DietPlan.Status status);

    @Query("SELECT d FROM DietPlan d WHERE d.id = :id AND d.patient.nutriologist.id = :nid")
    Optional<DietPlan> findByIdAndNutritionistId(@Param("id") Long id, @Param("nid") Long nutriologistId);

    @Query("SELECT COUNT(d) FROM DietPlan d WHERE d.createdBy.id = :uid")
    long countByCreatedById(@Param("uid") Long userId);

    @Query("SELECT COUNT(d) FROM DietPlan d WHERE d.createdBy.id = :uid AND d.status = 'ACTIVE'")
    long countActiveByCreatedById(@Param("uid") Long userId);

    @Query("SELECT COUNT(d) FROM DietPlan d WHERE d.createdBy.id = :uid AND d.generationSource = :source")
    long countByCreatedByIdAndGenerationSource(@Param("uid") Long userId,
                                               @Param("source") DietPlan.GenerationSource source);

    @Query("SELECT d FROM DietPlan d WHERE d.createdBy.id = :uid ORDER BY d.createdAt DESC")
    List<DietPlan> findTop5ByCreatedById(@Param("uid") Long userId, Pageable pageable);
}
