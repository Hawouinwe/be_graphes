package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.Point;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }



    @Override
    protected ShortestPathSolution doRun() {

        // Retrieve data from the input problem (getInputData() is inherited from the
        // Parent class ShortestPathAlgorithm)
        final ShortestPathData data = getInputData();


        // Variable that will contain the solution of the shortest path problem
        ShortestPathSolution solution = null;

        Graph graph = data.getGraph();
        final int nbNodes = graph.size();

        // Notify observers about the first event (origin processed).
        this.notifyOriginProcessed(data.getOrigin());

        // Initilaize Priority Queue, Binary Heap
        BinaryHeap<Label> filePrio = new BinaryHeap<Label>();

        // Initialize Labels
        LabelStar[] listeLabel = new LabelStar[nbNodes];

        // Filling the labels according to the different modes
        if(data.getMode().equals(AbstractInputData.Mode.LENGTH)) {
            for (int i = 0 ; i < nbNodes ; i++) {
                listeLabel[i] = new LabelStar(graph.get(i), false, Double.POSITIVE_INFINITY, null, Point.distance(graph.get(i).getPoint(), data.getDestination().getPoint()));
            }
        }
        else if (data.getMode().equals(AbstractInputData.Mode.TIME)){
            for (int i = 0 ; i < nbNodes ; i++) {
                listeLabel[i] = new LabelStar(graph.get(i), false, Double.POSITIVE_INFINITY, null, Point.distance(graph.get(i).getPoint(), data.getDestination().getPoint()) / (data.getMaximumSpeed()) * 3.6 );
            }
        }

        System.out.println("mode : " + data.getMode());

        filePrio.insert(listeLabel[data.getOrigin().getId()]);
        listeLabel[data.getOrigin().getId()].setCost(0);

        // A* core algorithm
        while(!filePrio.isEmpty()) {

            Label labCour = filePrio.deleteMin();

            // Visits successors of the current node
            if(labCour.sommetCourant.hasSuccessors()) {

                for (Arc arc : labCour.sommetCourant.getSuccessors()) {

                    // checks the validity of the arc
                    if (!data.isAllowed(arc)) {
                        continue;
                    }                    

                    Label dest = listeLabel[arc.getDestination().getId()];

                    // If the path to visited node is shorter, updates it
                    if(dest.getCost() > labCour.getCost() + data.getCost(arc)) {
                        this.notifyNodeReached(dest.getSommet());
                        dest.setCost(labCour.getCost() + data.getCost(arc));
                        dest.setArcEntrant(arc);
                        if(!dest.checkMarque()) {
                            filePrio.insert(dest);
                        }
                    }
                }
            }
            
            // Marks the current node, so as not to visit it again
            labCour.setMarque();
            this.notifyNodeMarked(labCour.getSommet());

            // Checks if the destination has been reached, stopping the algorithm then
            if(listeLabel[data.getDestination().getId()].checkMarque()) {
                break;
            }
        }

        // Constructing the solution
        ArrayList<Arc> arcs = new ArrayList<>();

        // If the destination was not reached, returns as an infeasible solution
        Label l = listeLabel[data.getDestination().getId()];
        if(!l.checkMarque()) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
            return solution;
        }
        else {
            this.notifyDestinationReached(l.getSommet());
        }

        // Constructs the path returned by the function
        while (l.getArcEntrant().getOrigin().getId() != data.getOrigin().getId() ) {
            arcs.add(l.getArcEntrant());
            l = listeLabel[l.getArcEntrant().getOrigin().getId()];
        }


        arcs.add(l.getArcEntrant());

        Collections.reverse(arcs);

        solution = new ShortestPathSolution(data, Status.OPTIMAL,
                    new Path(graph, arcs));

        // When the algorithm terminates, returns the solution that has been found
        return solution;
    }
}
