package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class LabelStar extends Label implements Comparable<Label>{

    private final double coutEstime;

    public LabelStar(Node sommetCourant, boolean marque, double coutRealise, Arc arcEntrant, double coutEstime) {
        super(sommetCourant, marque, coutRealise, arcEntrant);
        this.coutEstime = coutEstime;
    }

    @Override
    public double getTotalCost() {
        return this.coutRealise + this.coutEstime;
    }

}
