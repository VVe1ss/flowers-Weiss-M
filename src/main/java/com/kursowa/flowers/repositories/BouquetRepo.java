package com.kursowa.flowers.repositories;

import com.kursowa.flowers.models.Bouquet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BouquetRepo extends JpaRepository <Bouquet, Long> {
    Bouquet getBouquetById(Long id);
}
