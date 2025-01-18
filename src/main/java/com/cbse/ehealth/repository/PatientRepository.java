package com.cbse.ehealth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cbse.ehealth.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

}
