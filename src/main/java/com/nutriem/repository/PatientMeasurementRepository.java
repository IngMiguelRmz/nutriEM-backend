package com.nutriem.repository;

import com.nutriem.model.PatientMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientMeasurementRepository extends JpaRepository<PatientMeasurement, Long> {

    @Query("SELECT m FROM PatientMeasurement m WHERE m.patient.id = :id ORDER BY m.measuredAt ASC")
    List<PatientMeasurement> findByPatientIdOrderByMeasuredAtAsc(@Param("id") Long patientId);
}
