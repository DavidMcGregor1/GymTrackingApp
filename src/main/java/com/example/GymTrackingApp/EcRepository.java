package com.example.GymTrackingApp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EcRepository extends JpaRepository<ECs, Integer> {
}
