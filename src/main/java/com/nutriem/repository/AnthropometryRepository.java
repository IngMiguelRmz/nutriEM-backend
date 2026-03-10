package com.nutriem.repository;

import com.nutriem.model.Anthropometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnthropometryRepository extends JpaRepository<Anthropometry, Long> {

    @Query("SELECT a FROM Anthropometry a WHERE a.patient.id = :patientId ORDER BY a.assessmentDate DESC")
    List<Anthropometry> findAllByPatientId(@Param("patientId") Long patientId);

    @Query("SELECT a FROM Anthropometry a WHERE a.patient.id = :patientId ORDER BY a.assessmentDate DESC")
    List<Anthropometry> findLatestByPatientId(@Param("patientId") Long patientId,
                                               org.springframework.data.domain.Pageable pageable);
}
