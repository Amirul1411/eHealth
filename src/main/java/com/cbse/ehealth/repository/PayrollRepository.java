package com.cbse.ehealth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cbse.ehealth.model.Payroll;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {
	List<Payroll> findByStaffId(Long staffId);
}
