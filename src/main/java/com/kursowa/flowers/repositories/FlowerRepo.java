package com.kursowa.flowers.repositories;

import com.kursowa.flowers.models.Bouquet;
import com.kursowa.flowers.models.Flower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FlowerRepo extends JpaRepository <Flower, Long> {
    Flower getFlowerById(Long id);
    List<Flower> findAllByBouquetIsNull();
    List<Flower> getFlowerByBouquetAndLengthBetweenOrderByPosInBouquet(Bouquet bouquet, int length1, int length2);

    List<Flower> getFLowersByBouquet(Bouquet bouquet);
}
