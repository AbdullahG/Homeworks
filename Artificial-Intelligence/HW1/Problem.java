
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MuhammedAbdullah
 */
public class Problem {

    private ArrayList<Graph> graphList;
    private ArrayList<String> shipments;
    private ArrayList<String> destinations;
    private ArrayList<Shipment> remainingShipments;
    private ArrayList<Shipment> loadedShipments;
    private String truckStartPoint;
    private HashMap<String, Integer> rootIndex;
    private ArrayList<String> solutions = new ArrayList<>();
    private ArrayList<Shipment> allPairs = new ArrayList<>();
    private ArrayList<String> timeCosts = new ArrayList<>();

    private HashMap<String, Integer> timeCostValues = new HashMap<>();

    int ifr = 0;
    long begin;
    long timeRange = 0;
    String timeCost;
    double currentMemory;

    public Problem() {

    }

    public void initializeShipments() {
        Shipment sh = null;
        this.loadedShipments = new ArrayList<>();
        this.remainingShipments = new ArrayList<>();
        for (int i = 0; i < this.destinations.size(); i++) {
            sh = new Shipment();
            sh.setDestination(this.destinations.get(i));
            sh.setShipmentLoc(this.shipments.get(i));
            this.remainingShipments.add(sh);
            this.allPairs.add(sh);
        }
    }

    public void createSolutions() {
        this.begin = System.currentTimeMillis();
        String solution;
        ArrayList<String> ship;
        ArrayList<String> dest;
        Random rnd = new Random();
        while (true) {
            solution = this.truckStartPoint;
            timeCost = "";

            this.initializeShipments();
            while (loadedShipments.size() > 0 || remainingShipments.size() > 0) {
                if (loadedShipments.size() == 0) {
                    ifr = rnd.nextInt(remainingShipments.size());
                    solution += remainingShipments.get(ifr).getShipmentLoc();
                    loadedShipments.add(remainingShipments.get(ifr));
                    Shipment tmp = remainingShipments.remove(ifr);
                    timeCost += "X";
                    if (remainingShipments.size() > 0) {
                        Shipment tmpSh = isThereAnySameShipment(tmp);
                        if (tmpSh != null) {
                            loadedShipments.add(tmpSh);
                            remainingShipments.remove(tmpSh);
                            timeCost += "Y";
                        }
                    }
                } else if (loadedShipments.size() == 1) {
                    ifr = rnd.nextInt(2);
                    timeCost += "Y";
                    if (ifr == 0 && remainingShipments.size() > 0) {
                        ifr = rnd.nextInt(remainingShipments.size());
                        solution += remainingShipments.get(ifr).getShipmentLoc();
                        loadedShipments.add(remainingShipments.get(ifr));
                        remainingShipments.remove(ifr);
                    } else {
                        solution += loadedShipments.get(0).getDestination();
                        loadedShipments.remove(0);
                    }
                } else if (loadedShipments.size() == 2) {
                    ifr = rnd.nextInt(2);
                    solution += loadedShipments.get(ifr).getDestination();
                    loadedShipments.remove(ifr);
                    timeCost += "Z";
                }
            }
            if (!existsInSolutions(solution)) {
                this.solutions.add(solution);
                this.timeCosts.add(timeCost);
            }
            if ((System.currentTimeMillis() - begin) > timeRange) {
                currentMemory = ((double) ((double) (Runtime.getRuntime().totalMemory() / 1024) / 1024)) - ((double) ((double) (Runtime.getRuntime().freeMemory() / 1024) / 1024));
                break;
            }
        }
    }

    public Shipment isThereAnySameShipment(Shipment sh) {
        for (int i = 0; i < remainingShipments.size(); i++) {
            if (remainingShipments.get(i).getShipmentLoc().equals(sh.getShipmentLoc())) {
                return remainingShipments.remove(i);
            }
        }
        return null;
    }

    public boolean existsInSolutions(String solution) {
        for (int i = 0; i < this.solutions.size(); i++) {
            if (solutions.get(i).equals(solution)) {
                return true;
            }
        }
        return false;
    }

    public void efficientSolution() {

        Graph tt = this.graphList.get(rootIndex.get("C"));
        String str;
        double travelTime = 0;
        double bestTravelTime = 0;
        int efficient = 0;
        String efficientSol = "";
        String efficientTime = "";
        int sum;
        for (int i = 0; i < solutions.size(); i++) {
            sum = 0;
            travelTime = 0;
            str = solutions.get(i);
            for (int j = 0; j < str.length() - 1; j++) {
                Graph gr = this.graphList.get(rootIndex.get(str.substring(j, j + 1)));

                Graph.Node n = gr.getCity(str.substring(j + 1, j + 2));
                travelTime += ((double) gr.getTotalCost(n)) / timeCostValues.get(timeCosts.get(i).substring(j, j + 1));

                sum += gr.getTotalCost(n);
            }
            if (i == 0 || travelTime < bestTravelTime) {
                efficient = sum;
                efficientSol = str;
                efficientTime = timeCosts.get(i);
                bestTravelTime = travelTime;
            }
        }
        
        ArrayList<String> output = new ArrayList<>();
        output.add("Solution : " + efficientSol + ", Total Distance: " + efficient + " km, Travel Time: " + bestTravelTime * 60 + " minutes.");
        output.add("Details:");
        str = efficientSol;
        int gggg = 0;
        for (int j = 0; j < str.length() - 1; j++) {
            Graph gr = this.graphList.get(rootIndex.get(str.substring(j, j + 1)));

            Graph.Node n = gr.getCity(str.substring(j + 1, j + 2));
            gggg += gr.getTotalCost(n);
            output.add("From : " + str.substring(j, j + 1) + " to " + str.substring(j + 1, j + 2) + " " + gr.getTotalCost(n) + " km, Speed: " + timeCostValues.get(efficientTime.substring(j, j + 1)) + " km/h");

        }
        output.add("");
        output.add("(I used the shortest paths here which I obtained by BFS.)");
        output.add("");
        output.add("Memory Usage: " + currentMemory + " megabytes. (According to JVM, may be wrong)");
        this.createOutputFile(output);
    }

    public boolean isCreated(String str) {
        return !(this.solutions.stream().noneMatch((solution) -> (str.equals(solution))));
    }

    public void fillRootIndex() {
        this.rootIndex = new HashMap<>();
        rootIndex.put("A", 0);
        rootIndex.put("B", 1);
        rootIndex.put("C", 2);
        rootIndex.put("D", 3);
        rootIndex.put("E", 4);
        rootIndex.put("F", 5);
        rootIndex.put("G", 6);
        rootIndex.put("H", 7);

        timeCostValues.put("X", 90);
        timeCostValues.put("Y", 72);
        timeCostValues.put("Z", 60);
    }

    public ArrayList<Shipment> getLoadedShipments() {
        return loadedShipments;
    }

    public void setLoadedShipments(ArrayList<Shipment> loadedShipments) {
        this.loadedShipments = loadedShipments;
    }

    public long getBegin() {
        return begin;
    }

    public void setBegin(long begin) {
        this.begin = begin;
    }

    public long getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(long timeRange) {
        this.timeRange = timeRange;
    }

    public ArrayList<Shipment> getRemainingShipments() {
        return remainingShipments;
    }

    public void setRemainingShipments(ArrayList<Shipment> remainingShipments) {
        this.remainingShipments = remainingShipments;
    }

    public HashMap<String, Integer> getRootIndex() {
        return rootIndex;
    }

    public void setRootIndex(HashMap<String, Integer> rootIndex) {
        this.rootIndex = rootIndex;
    }

    public ArrayList<String> getSolutions() {
        return solutions;
    }

    public void setSolutions(ArrayList<String> solutions) {
        this.solutions = solutions;
    }

    public int getIfr() {
        return ifr;
    }

    public void setIfr(int ifr) {
        this.ifr = ifr;
    }

    public ArrayList<Graph> getGraphList() {
        return graphList;
    }

    public void setGraphList(ArrayList<Graph> graphList) {
        this.graphList = graphList;
    }

    public ArrayList<String> getShipments() {
        return shipments;
    }

    public void setShipments(ArrayList<String> shipments) {
        this.shipments = shipments;
    }

    public ArrayList<String> getDestinations() {
        return destinations;
    }

    public void setDestinations(ArrayList<String> destinations) {
        this.destinations = destinations;
    }

    public String getTruckStartPoint() {
        return truckStartPoint;
    }

    public void setTruckStartPoint(String truckStartPoint) {
        this.truckStartPoint = truckStartPoint;
    }
    
    public void createOutputFile(ArrayList<String> output){
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File("output.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Problem.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (int i = 0; i < output.size(); i++) {
            pw.println(output.get(i));
        }
        pw.close();
    }
    
    public class Solution {

        private String path;
        private int time;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }
}
