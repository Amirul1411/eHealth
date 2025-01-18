package com.cbse.ehealth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cbse.ehealth.model.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
	Optional<Staff> findByUsername(String username);
}
