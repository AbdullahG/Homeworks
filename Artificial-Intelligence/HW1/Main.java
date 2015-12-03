
import java.util.ArrayList;

/**
 *
 * @author MuhammedAbdullah
 */
public class Main {

    public static void main(String[] args) {
        
        long timeRange=0;
        int[] lengthOfRoads = new int[11];
        String truckStartPoint = "";
        ArrayList<String> shipments = new ArrayList<>();
        ArrayList<String> destinations = new ArrayList<>();
        
        if (args.length == 14) {
            truckStartPoint=args[0];
            if(args[1].length()<=3)
                timeRange=1000;
            else if(args[1].length()<=5)
                timeRange=2000;
            else if(args[1].length()==6){
                timeRange=20000;
            }else if(args[1].length()==7){
                timeRange= 65000;
            }else if(args[1].length()==8){
                timeRange = 115000;
            }
            for (int i = 0; i < args[1].length(); i++) {
                shipments.add(args[1].substring(i, i+1));
            }
            for (int i = 0; i < args[2].length(); i++) {
                destinations.add(args[2].substring(i, i+1));
            }
            for (int i = 0; i < lengthOfRoads.length; i++) {
                lengthOfRoads[i] = Integer.parseInt(args[3+i]);
            }
            
        }
        else
        {
            System.out.println("Run the program with 14 arguments.. Argument Number: "+args.length);
            System.out.println("Program will end.");
            return;
        }

        ArrayList<Graph> graphList = createGraphs(lengthOfRoads);
        makeBreadthFirst(graphList);

        Problem problem = new Problem();

        problem.setDestinations(destinations);
        problem.setShipments(shipments);
        problem.setGraphList(graphList);
        problem.setTruckStartPoint(truckStartPoint);
        problem.setTimeRange(timeRange);
        problem.createSolutions();
        problem.fillRootIndex();
        problem.efficientSolution();

    }

    public static ArrayList<Graph> createGraphs(int[] array) {
        ArrayList<Graph> graphList = new ArrayList<>();
        Graph graph = null;

        for (int i = 0; i < 8; i++) {
            graph = new Graph("A");
            graph.addAdjacent("A", "B", array[0]);
            graph.addAdjacent("B", "C", array[1]);
            graph.addAdjacent("B", "E", array[2]);
            graph.addAdjacent("B", "G", array[3]);
            graph.addAdjacent("C", "D", array[4]);
            graph.addAdjacent("C", "G", array[5]);
            graph.addAdjacent("D", "G", array[6]);
            graph.addAdjacent("E", "F", array[7]);
            graph.addAdjacent("E", "G", array[8]);
            graph.addAdjacent("F", "G", array[9]);
            graph.addAdjacent("G", "H", array[10]);
            graphList.add(graph);
        }
        return graphList;
    }

    public static void makeBreadthFirst(ArrayList<Graph> graphList) {
        Graph tmp;
        String cities = "ABCDEFGH";
        for (int i = 0; i < graphList.size(); i++) {
            tmp = graphList.get(i);
            tmp.breadthFirst(tmp.getCity(cities.substring(i, i + 1)));
        }
    }

}
