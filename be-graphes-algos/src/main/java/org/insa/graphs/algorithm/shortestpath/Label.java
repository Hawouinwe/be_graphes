package org.insa.graphs.algorithm.shortestpath;
import  org.insa.graphs.model.Node;

public class Label{

    protected Node sommetCourant;
    protected boolean marque;
    protected int coutRealise;
    protected Node pere;

    public Label(Node sommetCourant, boolean marque, int coutRealise, Node pere) {
        this.sommetCourant = sommetCourant;
        this.marque = marque;
        this.coutRealise = coutRealise;
        this.pere = pere;
    }

    public Node getSommet() {
        return this.sommetCourant;
    }

    public boolean getMarque() {
        return this.marque;
    }

    public int getCost() {
        return this.coutRealise;
    }

    public Node getPere() {
        return this.pere;
    }
}