package com.kursowa.flowers.comparators;


import com.kursowa.flowers.models.Flower;

import java.util.Comparator;

public class FlowersComparator implements Comparator<Flower> {

    @Override
    public int compare(Flower o1, Flower o2) {
        if (o1.getLevelOfFreshness() == (o2.getLevelOfFreshness()))
            return o1.getName().compareTo(o2.getName());
        else
            return  Double.compare(o1.getLevelOfFreshness(), o2.getLevelOfFreshness());
    }
}
