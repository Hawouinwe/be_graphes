package org.insa.graphs.gui.simple;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
// import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.gui.drawing.Drawing;
import org.insa.graphs.gui.drawing.components.BasicDrawing;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;

public class Launch {

    /**
     * Create a new Drawing inside a JFrame an return it.
     *
     * @return The created drawing.
     * @throws Exception if something wrong happens when creating the graph.
     */
    public static Drawing createDrawing() throws Exception {
        BasicDrawing basicDrawing = new BasicDrawing();
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("BE Graphes - Launch");
                frame.setLayout(new BorderLayout());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.setSize(new Dimension(800, 600));
                frame.setContentPane(basicDrawing);
                frame.validate();
            }
        });
        return basicDrawing;
    }

    private void matabiauTourEiffelVoiture() throws Exception {
        // visit these directory to see the list of available files on commetud.
        final String mapName = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/france.mapgr";

        final Graph graph;
        // create a graph reader
        try (final GraphReader reader = new BinaryGraphReader(new DataInputStream(
                new BufferedInputStream(new FileInputStream(mapName))))) {
            graph = reader.read();
        }

        List<ArcInspector> filtres = ArcInspectorFactory.getAllFilters();
        ShortestPathData data = new ShortestPathData(graph, graph.get(36535), graph.get(154554), filtres.get(1));
        
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(data);

        ShortestPathSolution solutionPath = dijkstra.run();
        
        if (solutionPath.getStatus().equals(Status.INFEASIBLE)) {
            System.out.println("Il n'a pas trouvé de chemin !");
            return;
        }

        // create the drawing
        final Drawing drawing = createDrawing();
        // draw the graph on the drawing
        drawing.drawGraph(graph);
        // draw the path on the drawing
        drawing.drawPath(solutionPath.getPath());
    }

    private void hippodromeToulousePlagePieton() throws Exception {
        // visit these directory to see the list of available files on commetud.
        final String mapName = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/toulouse.mapgr";

        final Graph graph;
        // create a graph reader
        try (final GraphReader reader = new BinaryGraphReader(new DataInputStream(
                new BufferedInputStream(new FileInputStream(mapName))))) {
            graph = reader.read();
        }

        List<ArcInspector> filtres = ArcInspectorFactory.getAllFilters();
        ShortestPathData data = new ShortestPathData(graph, graph.get(6471), graph.get(18274), filtres.get(3));
        
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(data);

        ShortestPathSolution solutionPath = dijkstra.run();
        
        if (solutionPath.getStatus().equals(Status.INFEASIBLE)) {
            System.out.println("Il n'a pas trouvé de chemin !");
            return;
        }

        // create the drawing
        final Drawing drawing = createDrawing();
        // draw the graph on the drawing
        drawing.drawGraph(graph);
        // draw the path on the drawing
        drawing.drawPath(solutionPath.getPath());
    }

    // private void testPathValue() throws Exception {
    //     // visit these directory to see the list of available files on commetud.
    //     final String mapName = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/toulouse.mapgr";

    //     final Graph graph;
    //     // create a graph reader
    //     try (final GraphReader reader = new BinaryGraphReader(new DataInputStream(
    //             new BufferedInputStream(new FileInputStream(mapName))))) {
    //         graph = reader.read();
    //     }

    //     // Nofilter by length
    //     List<ArcInspector> filtres = ArcInspectorFactory.getAllFilters();
    //     ShortestPathData data = new ShortestPathData(graph, graph.get(6471), graph.get(18274), filtres.get(0));
    //     DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(data);
    //     ShortestPathSolution solutionPath = dijkstra.run();
    // }

    private void runAlgo(String algoName, String mapName, ArcInspector filter, int idOrigin, int idDestination) throws Exception {
        final Graph graph;
        // create a graph reader
        try (final GraphReader reader = new BinaryGraphReader(new DataInputStream(
                new BufferedInputStream(new FileInputStream(mapName))))) {
            graph = reader.read();
        }

        ShortestPathData data = new ShortestPathData(graph, graph.get(idOrigin), graph.get(idDestination), filter);
        ShortestPathSolution solutionPath = null;

        if (algoName.toLowerCase().equals("d")) {
            DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(data);
            solutionPath = dijkstra.run();
        } else if (algoName.toLowerCase().equals("a")) {
            AStarAlgorithm astar = new AStarAlgorithm(data);
            solutionPath = astar.run();
        } else {
            System.out.println("Tu n'as pas choisis un algo disponible, il faut choisir entre d (Dijskra) et a (Astar)");
            return;
        }

        
        
        System.out.println(solutionPath);
        if (solutionPath.getStatus().equals(Status.INFEASIBLE)) {
            // System.out.println("L'algorithme n'a pas trouvé de chemin !");
            return;
        }
        // create the drawing
        final Drawing drawing = createDrawing();
        // draw the graph on the drawing
        drawing.drawGraph(graph);
        // draw the path on the drawing
        drawing.drawPath(solutionPath.getPath());
    }

    public static void main(String[] args) throws Exception {
        Launch launch = new Launch();
        List<ArcInspector> filtres = ArcInspectorFactory.getAllFilters();
        try {
            // Dijkstra
            // Carre sans filtres
            launch.runAlgo("D", "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/carre.mapgr", filtres.get(0), 7, 0);
            // Matabiau jusqu'à la Tour Eiffel en voiture
            // launch.runAlgo("D", "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/france.mapgr", filtres.get(1), 36535, 154554);
            // Hippodrome vers Toulouse Plage à pied
            launch.runAlgo("D", "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/toulouse.mapgr", filtres.get(3), 6471, 18274);
            // launch.testPathValue();
            
            // Astar
            // Carre sans filtres
            launch.runAlgo("A", "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/carre.mapgr", filtres.get(0), 7, 0);
            // Matabiau jusqu'à la Tour Eiffel en voiture
            // launch.runAlgo("A", "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/france.mapgr", filtres.get(1), 36535, 154554);
            // Hippodrome vers Toulouse Plage à pied
            launch.runAlgo("A", "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/toulouse.mapgr", filtres.get(3), 6471, 18274);
        } catch (Exception e) {
            System.out.println("Il y a eu une erreur lors des différents tests: " + e);
            e.printStackTrace();
        }
    }

}
