package com.kursowa.flowers.repositories;

import com.kursowa.flowers.models.FlowerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowerTypeRepo extends JpaRepository <FlowerType, Long> {
}
