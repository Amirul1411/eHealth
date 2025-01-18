package com.cbse.ehealth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cbse.ehealth.model.SupplyRequest;

@Repository
public interface SupplyRequestRepository extends JpaRepository<SupplyRequest, Long> {

}
