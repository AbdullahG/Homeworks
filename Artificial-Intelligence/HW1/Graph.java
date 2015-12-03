
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author MuhammedAbdullah
 */
public class Graph {

    Node root;
    ArrayList<Node> visitedList;
    
    public Graph(String rootName) {
        this.root = new Graph.Node(rootName);
    }
    
    public void breadthFirst(Node rootNode) {
        int totalCost = 0;
        Node tmp;

        ArrayList<Node> fringe = new ArrayList<>();
        fringe.add(rootNode);
        Iterator<Node> iterator;

        rootNode.shortestPaths.put(rootNode, 0);
        while (fringe.size() > 0) {
            iterator = fringe.get(0).adjacents.keySet().iterator();
            while (iterator.hasNext()) {
                tmp = iterator.next();
                if (tmp.name.equals(rootNode.name)) {
                    continue;
                }
                if (containsKey(rootNode.shortestPaths, tmp) == null) {
                    tmp.setParent(fringe.get(0));
                    rootNode.shortestPaths.put(tmp, getTotalCost(tmp));
                    fringe.add(tmp);
                } else if (rootNode.shortestPaths.get(tmp) > getTotalCost(tmp)) {
                    tmp.setParent(fringe.get(0));
                    rootNode.shortestPaths.remove(tmp);
                    rootNode.shortestPaths.put(tmp, getTotalCost(tmp));
                    fringe.add(tmp);
                }
            }
            fringe.remove(0);
        }

    }

    public Node containsKey(HashMap<Node, Integer> map, Node key) {
        for (Node city : map.keySet()) {
            if (city.name.equals(key.name)) {
                return city;
            }
        }
        return null;
    }

    public int getTotalCost(Node node) {
        if (node == null) {
            return -1;
        }
        if (node.getParent() == null) {
            return 0;
        }
       // System.out.println("return " + node.getParent().adjacents.get(node) + " + Kendisi: " + node.getName() + " Parenti: " + node.parent.getName());
        return node.getParent().adjacents.get(node) + getTotalCost(node.parent);
    }

    public boolean addAdjacent(String first, String second, int cost) {
        Node f = getCity(first);
        Node s = getCity(second);
        if (f != null && s != null) {
            for (Node adj : f.getAdjacents().keySet()) {
                if (adj.name.equals(s.name)) {
                    return false;
                }
            }
            f.adjacents.put(s, cost);
            s.adjacents.put(f, cost);
            return true;
        } else if (f != null && s == null) {
            s = new Node(second);
            f.adjacents.put(s, cost);
            s.adjacents.put(f, cost);
            return true;
        } else {
            return false;
        }
    }

    public boolean isVisited(Node node) {
        for (Node city : visitedList) {
            if (city.sameWith(node)) {
                return true;
            }
        }
        return false;
    }

    public void makeVisited(Node node) {
        this.visitedList.add(node);
    }

    public Node getCity(String name) {
        this.visitedList = new ArrayList<>();
        if (root.getName().equals(name)) {
            return root;
        }

        ArrayList<Node> myStack = new ArrayList<>();
        myStack.add(this.root);

        while (myStack.size() > 0) {
            for (Node adjacent : myStack.get(0).getAdjacents().keySet()) {
                if (adjacent.getName().equals(name)) {
                    return adjacent;
                } else if (!isVisited(adjacent)) {
                    makeVisited(adjacent);
                    myStack.add(adjacent);
                }
            }
            myStack.remove(0);
        }
        return null;
    }

    public class Node {

        private String name;
        private boolean visited;
        private Node parent;
        private HashMap<Node, Integer> adjacents = new HashMap<>();
        private HashMap<Node, Integer> shortestPaths = new HashMap<>();

        public Node(String name) {
            setName(name);
            visited = false;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public HashMap<Node, Integer> getShortestPaths() {
            return shortestPaths;
        }

        public void setShortestPaths(HashMap<Node, Integer> shortestPaths) {
            this.shortestPaths = shortestPaths;
        }

        public boolean sameWith(Node node) {
            if (this.getName().equals(node.getName())) {
                return true;
            } else {
                return false;
            }
        }

        public boolean isVisited() {
            return this.visited;
        }

        public void makeVisited() {
            this.visited = true;
        }

        public HashMap<Node, Integer> getAdjacents() {
            return adjacents;
        }

        public void setAdjacents(HashMap<Node, Integer> adjacents) {
            this.adjacents = adjacents;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
