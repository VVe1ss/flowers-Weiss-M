package com.kursowa.flowers.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Bouquet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique=true)
    private String name;
    private int nextFlowerPos = 0;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bouquet", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Flower> flowers = new LinkedHashSet<>();
}
