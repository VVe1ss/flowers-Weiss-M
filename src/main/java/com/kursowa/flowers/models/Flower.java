package com.kursowa.flowers.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Flower {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private int pricePerFlower;
    private int length;

    private int posInBouquet;
    double levelOfFreshness;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private FlowerType type;

    @ManyToOne
    @JoinColumn(name = "bouquet_id")
    private Bouquet bouquet;

    @Override
    public String toString() {
        return String.format("""
                ---------------------------------
                id = %d
                ім'я = %s
                ціна = %d
                довжина стебла = %d
                рівень свіжості = %f
                тип квітки = %s
                """, id, name, pricePerFlower, length, levelOfFreshness, type.getName());
    }
}
