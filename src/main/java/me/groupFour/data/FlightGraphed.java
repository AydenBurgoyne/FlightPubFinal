package me.groupFour.data;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class FlightGraphed {
    private Graph<String, DefaultWeightedEdge> flightGraph;

    public FlightGraphed() {

    }

    public Graph<String, DefaultWeightedEdge> getFlightGraph() {
        return flightGraph;
    }

    public void setFlightGraph(Graph<String, DefaultWeightedEdge> graph) {
        this.flightGraph = graph;
    }

}
