package com.cbse.ehealth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cbse.ehealth.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
