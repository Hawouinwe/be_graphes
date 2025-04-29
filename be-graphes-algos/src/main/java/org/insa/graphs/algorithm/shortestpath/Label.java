public class Label{

    private Node sommetCourant;
    private boolean marque;
    private int coutRealise;
    private Node pere;

    public Label(Node sommetCourant, boolean marque, int coutRealise, Node pere) {
        this.sommetCourant = sommetCourant;
        this.marque = marque;
        this.coutRealise = coutRealise;
        this. pere = pere;
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

    public getPere() {
        return this.pere;
    }
}