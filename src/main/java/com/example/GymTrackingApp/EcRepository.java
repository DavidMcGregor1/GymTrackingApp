package com.example.GymTrackingApp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EcRepository extends JpaRepository<ECs, Integer> {

    List<ECs> findByCategoryId(Integer categoryId);
}
