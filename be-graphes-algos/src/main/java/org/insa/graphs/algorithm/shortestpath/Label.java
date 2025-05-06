package org.insa.graphs.algorithm.shortestpath;
import  org.insa.graphs.model.Arc;
import  org.insa.graphs.model.Node;

public class Label implements Comparable<Label>{

    protected Node sommetCourant;
    protected boolean marque;
    protected double coutRealise;
    protected Arc arcEntrant;

    public Label(Node sommetCourant, boolean marque, double coutRealise, Arc arcEntrant) {
        this.sommetCourant = sommetCourant;
        this.marque = marque;
        this.coutRealise = coutRealise;
        this.arcEntrant = arcEntrant;
    }

    public Node getSommet() {
        return this.sommetCourant;
    }

    public boolean getMarque() {
        return this.marque;
    }

    public double getCost() {
        return this.coutRealise;
    }

    public void setCost(double nvCout) {
        this.coutRealise = nvCout;
    }

    public Arc getArcEntrant() {
        return this.arcEntrant;
    }

    public void setArcEntrant(Arc nvArc) {
        this.arcEntrant = nvArc;
    }

    public boolean checkMarque() {
        return this.marque == true;
    }

    public void setMarque() {
        this.marque = true;
    }

    @Override
    public int compareTo(Label autre) {
        if (this.getCost() == autre.getCost()) {
            return 0;
        }
        else if (this.getCost() > autre.getCost()) {
            return 1;
        }
        else {
            return -1;
        }
    }

}
