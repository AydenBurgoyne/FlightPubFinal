package me.groupFour.data;

import me.groupFour.dao.IDestinationEntityDAO;
import me.groupFour.dao.IFlightEntityDAO;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

//Flight map is used to store all the flight destination combinations in a graph where flight time is used as a weight for the edge connections between vertexs, it can then be used to find the shortest path.
//This allows for generate journeys to find stitch together Journeys based on the edge list produced.
public class FlightMap {

    //private ICountryEntityDAO countryEntityDAO;
    private final IDestinationEntityDAO destinationEntityDAO;
    private final IFlightEntityDAO flightEntityDAO;

    private Graph<String, DefaultWeightedEdge> flightGraph;
    private AllDirectedPaths<String, DefaultWeightedEdge> allDirectedPaths;
    private FlightGraphed graph;

    //sourceVertex, targetVertex, simplePaths boolean, maxDepth


    public FlightMap(IDestinationEntityDAO iDestinationEntityDAO, IFlightEntityDAO iFlightEntityDAO, FlightGraphed graph) {
        this.destinationEntityDAO = iDestinationEntityDAO;
        this.flightEntityDAO = iFlightEntityDAO;
        this.graph = graph;
        flightGraph = graph.getFlightGraph();
        if (flightGraph == null) {
            loadVertexes();
        }
        allDirectedPaths = new AllDirectedPaths<>(flightGraph);

    }

    public void loadVertexes() {
        List<FlightEntity> flights = flightEntityDAO.getAllDistinct();

        this.flightGraph = new DirectedWeightedMultigraph<>(String::new, DefaultWeightedEdge::new);

        for (FlightEntity f : flights
        ) {
            //set local variables for each method to make it readable
            String a = f.getDepartureCode().getDestinationCode();
            String b = f.getDestinationCode().getDestinationCode();
            Integer dur = f.getDuration();
            Integer dur2 = f.getDurationSecondLeg();

            //need to have both vertices created, cannot add an edge between 2 vertices that don't exist
            if (!flightGraph.containsVertex(a) || !flightGraph.containsVertex(b)) {
                if (!flightGraph.containsVertex(a) && !flightGraph.containsVertex(b)) {
                    //condition where it does not contain either vertices
                    flightGraph.addVertex(a);
                    flightGraph.addVertex(b);
                } else if (!flightGraph.containsVertex(a)) {
                    //does not contain the destination code
                    flightGraph.addVertex(a);
                } else {
                    //contains the departure code code but not the destination
                    flightGraph.addVertex(b);
                }
            }
            //condition where both vertices are already in the graph
            flightGraph.addEdge(a, b);
            if (dur2 != null) {
                //check if the flightGraph has a second duration
                flightGraph.setEdgeWeight(a, b, dur + dur2);
            } else {
                //if the flight does not have a second duration
                flightGraph.setEdgeWeight(a, b, dur);
            }

        }
        graph.setFlightGraph(flightGraph);
    }


    public LinkedList<LinkedList<DestinationEntity>> getPaths(String dep, String des) {
        //might need input validation
        //where the bean is defined, where the code is generated to be used within the jsp
        //takes ~150ms to complete
        //Graph<String, DefaultWeightedEdge> flightGraph = new DirectedWeightedMultigraph<>(String::new, DefaultWeightedEdge::new);
        //flightGraphBuilder


        //alldirectedPaths uses dijkstras but presents all paths given a certain depth
        //created linkedlist of linkedlist of destination obj

        LinkedList<DestinationEntity> destinationEntities;
        LinkedList<LinkedList<DestinationEntity>> destinationEntityList = new LinkedList<>();

        //sourceVertex, targetVertex, simplePaths boolean, maxDepth
        List<GraphPath<String, DefaultWeightedEdge>> allDirectSubPaths = allDirectedPaths.getAllPaths(dep, des, true, 6);

        //may need to be improved and optimised by decreasing the use of the DAO
        //may change return to JSON instead of llist llist
        //may use queryAll instead

        for (GraphPath<String, DefaultWeightedEdge> gp : allDirectSubPaths
        ) {
            List llist2 = gp.getVertexList();

            destinationEntities = new LinkedList<>();

            for (Object item : llist2
            ) {
                destinationEntities.add(destinationEntityDAO.findById(item.toString()));
            }
            destinationEntityList.add(destinationEntities);
        }

        //System.out.println(destinationEntityList);

        return destinationEntityList;
    }


    //method to combine  all of the different possibilities from a -> b, then b -> c
    public LinkedList<LinkedList<DestinationEntity>> viaPath(String ONE, String VIA, String SECOND) {

        FlightMap obj1 = new FlightMap(destinationEntityDAO, flightEntityDAO, graph);
        FlightMap obj2 = new FlightMap(destinationEntityDAO, flightEntityDAO, graph);

        LinkedList<LinkedList<DestinationEntity>> firstSection = obj1.getPaths(ONE, VIA);
        LinkedList<LinkedList<DestinationEntity>> secondSection = obj2.getPaths(VIA, SECOND);
        Iterator<LinkedList<DestinationEntity>> it = firstSection.iterator();
        Iterator<LinkedList<DestinationEntity>> it2 = secondSection.iterator();
        LinkedList<LinkedList<DestinationEntity>> finallist = new LinkedList<>();
        while (it.hasNext()) {
            LinkedList<DestinationEntity> temp = it.next();
            while (it2.hasNext()) {
                LinkedList<DestinationEntity> cloned = new LinkedList<>(temp);
                LinkedList<DestinationEntity> temp2 = it2.next();
                temp2.remove(0);
                cloned.addAll(temp2);
                finallist.add(cloned);
            }
        }
        return finallist;
    }
}