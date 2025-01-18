package com.cbse.ehealth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cbse.ehealth.model.Shift;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {
	List<Shift> findByStaffId(Long staffId);
}
