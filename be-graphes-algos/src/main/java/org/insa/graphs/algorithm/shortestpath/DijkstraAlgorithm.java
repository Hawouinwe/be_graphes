package org.insa.graphs.algorithm.shortestpath;


import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Path;


public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {

        // retrieve data from the input problem (getInputData() is inherited from the
        // parent class ShortestPathAlgorithm)
        final ShortestPathData data = getInputData();

        // variable that will contain the solution of the shortest path problem
        ShortestPathSolution solution = null;

        // TODO: implement the Dijkstra algorithm ---------------------------------------
        Graph graph = data.getGraph();
        final int nbNodes = graph.size();

        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());

        // Initilaize Priority Queue, Binary Heap
        BinaryHeap<Label> filePrio = new BinaryHeap<Label>();
        

        // Initialize Labels
        Label[] listeLabel = new Label[nbNodes];

        for (int i = 0 ; i < nbNodes ; i++) {
            listeLabel[i] = new Label(graph.get(i), false, Double.POSITIVE_INFINITY, null);
        }

        filePrio.insert(listeLabel[data.getOrigin().getId()]);

        listeLabel[data.getOrigin().getId()].setCost(0);

        // Algorithm
        while(!filePrio.isEmpty()) {
            Label labCour = filePrio.findMin();
            
            //System.out.println("Marque ? " + labCour.getMarque());

            if(labCour.checkMarque()) {
                filePrio.remove(labCour);
                continue;
            }

            //System.out.println("iciA");

            if(labCour.sommetCourant.hasSuccessors()) {

                //System.out.println("iciB");

                for (Arc arc : labCour.sommetCourant.getSuccessors()) {

                    //System.out.println("iciC");

                    if (!data.isAllowed(arc)) {
                        continue;
                    }

                    Label dest = listeLabel[arc.getDestination().getId()];
                    //System.out.println("Son cout " + dest.getCost());
                    //System.out.println("Contre cout "+ labCour.getCost() + data.getCost(arc));

                    if(dest.getCost() > labCour.getCost() + data.getCost(arc)) {
                        //System.out.println("Oui if");
                        dest.setCost(labCour.getCost() + data.getCost(arc));
                        //System.out.println("On a set Cost");
                        dest.setArcEntrant(arc);
                        //System.out.println("On a set Arc");
                        //System.out.println("Arc :"+arc+"\n");
                        if(!dest.checkMarque()) {
                            //System.out.println("Il est pas marque");
                            filePrio.insert(dest);
                            //System.out.println("On l'a mis dans la file prio");
                        }
                    }
                }
            }
            labCour.setMarque();
            filePrio.remove(labCour);
        }

        //Constructing the solution
        ArrayList<Arc> arcs = new ArrayList<>();

        Label l = listeLabel[data.getDestination().getId()];

        //System.out.println("Label " + l);
        //System.out.println("ID dest "+l.getSommet().getId());

        //System.out.println("Arc entrant" + l.getArcEntrant());
        //System.out.println("ID dest bis" + l.getArcEntrant().getDestination().getId());
        
        
        
        while (l.getArcEntrant().getOrigin().getId() != data.getOrigin().getId() ) {
            arcs.add(l.getArcEntrant());
            //System.out.println("On ajoute l'arc " + l.getSommet().getId());
            l = listeLabel[l.getArcEntrant().getOrigin().getId()];
        }


        arcs.add(l.getArcEntrant());

        Collections.reverse(arcs);

        solution = new ShortestPathSolution(data, Status.OPTIMAL,
                    new Path(graph, arcs));

        // when the algorithm terminates, return the solution that has been found
        return solution;
    }

}
