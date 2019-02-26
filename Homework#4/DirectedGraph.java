package GraphPackage;

import QueuePackage.LinkedQueue;
import QueuePackage.QueueInterface;
import StackPackage.ArrayStack;
import StackPackage.StackInterface;

import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;

public class DirectedGraph<T> implements GraphInterface<T> {

    private HashMap<T, VertexInterface<T>> vertices;
    private int edgeCount;

    public DirectedGraph() {
        vertices = new HashMap<>();
        edgeCount = 0;
    }

    @Override
    public QueueInterface<T> getBreadthFirstTraversal(T origin) {
        resetVertices();
        QueueInterface<T> traversalOrder = new LinkedQueue<>();
        QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue<>();

        VertexInterface<T> originVertex = vertices.get(origin);
        originVertex.visit();
        traversalOrder.enqueue(origin);
        vertexQueue.enqueue(originVertex);

        while (!vertexQueue.isEmpty()) {
            VertexInterface<T> frontVertex = vertexQueue.dequeue();
            Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
            while (neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (!nextNeighbor.isVisited()) {
                    nextNeighbor.visit();
                    traversalOrder.enqueue(nextNeighbor.getLabel());
                    vertexQueue.enqueue(nextNeighbor);
                }
            }
        }
        return traversalOrder;
    }

    @Override
    public QueueInterface<T> getDepthFirstTraversal(T origin) {
        resetVertices();
        QueueInterface<T> traversalOrder = new LinkedQueue<>();
        StackInterface<VertexInterface<T>> vertexStack = new ArrayStack<>();

        VertexInterface<T> originVertex = vertices.get(origin);
        originVertex.visit();
        vertexStack.push(originVertex);
        traversalOrder.enqueue(originVertex.getLabel());
        while (!vertexStack.isEmpty()) {
            VertexInterface<T> topVertex = vertexStack.peek();
            VertexInterface<T> nextNeighbor = topVertex;
            Iterator<VertexInterface<T>> neighbors = topVertex.getNeighborIterator();

            boolean hasUnvisitedNeighbor = false;
            while (!hasUnvisitedNeighbor && neighbors.hasNext()) {
                nextNeighbor = neighbors.next();
                if (!nextNeighbor.isVisited()) {
                    hasUnvisitedNeighbor = true;
                }
            }
            if (hasUnvisitedNeighbor) {
                nextNeighbor.visit();
                traversalOrder.enqueue(nextNeighbor.getLabel());
                vertexStack.push(nextNeighbor);
            } else {
                vertexStack.pop();
            }
        }
        return traversalOrder;
    }

    @Override
    public StackInterface<T> getTopologicalOrder() {
        return null;
    }

    @Override
    public int getShortestPath(T begin, T end, StackInterface<T> path) {
        resetVertices();
        boolean done = false;
        QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue<>();
        VertexInterface<T> originVertex = vertices.get(begin);
        VertexInterface<T> endVertex = vertices.get(end);
        originVertex.visit();
        vertexQueue.enqueue(originVertex);
        while (!done && !vertexQueue.isEmpty()) {
            VertexInterface<T> frontVertex = vertexQueue.dequeue();
            Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
            while (!done && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (!nextNeighbor.isVisited()) {
                    nextNeighbor.visit();
                    nextNeighbor.setCost(1 + frontVertex.getCost());
                    nextNeighbor.setPredecessor(frontVertex);
                    vertexQueue.enqueue(nextNeighbor);
                }
                if (nextNeighbor.equals(endVertex)) {
                    done = true;
                }
            }
        }
        // Traversal ends; construct shortest path
        int pathLength = (int) endVertex.getCost();
        path.push(endVertex.getLabel());

        VertexInterface<T> vertex = endVertex;
        while (vertex.hasPredecessor()) {
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
        }
        return pathLength;
    }

    @Override
    public boolean addVertex(T vertexLabel) {
        VertexInterface<T> addOutcome = vertices.put(vertexLabel, new Vertex<>(vertexLabel));
        return addOutcome == null;
    }

    @Override
    public double getCheapestPath(T begin, T end, StackInterface<T> path) {
        resetVertices();
        boolean done = false;
        PriorityQueue<VertexInterface<T>> pq = new PriorityQueue<>();
        VertexInterface<T> originVertex = vertices.get(begin);
        VertexInterface<T> endVertex = vertices.get(end);
        pq.add(new EntryPQ(originVertex, 0, null).getVertex());
        while (!done && !pq.isEmpty()) {
            VertexInterface<T> frontVertex = pq.remove();
            T frontEntry = frontVertex.getLabel();
            if (!frontVertex.isVisited()) {
                frontVertex.visit();
                VertexInterface<T> frontv = vertices.get(frontEntry);
                frontVertex.setCost(frontv.getCost()); //Set the cost of the path to frontVertex to the cost recorded in frontEntry
                frontVertex.setPredecessor(frontv.getPredecessor()); //Set the predecessor of frontVertex to the predecessor recorded in frontEntry
                if (frontVertex == endVertex) {
                    done = true;
                } else {
                    Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
                    while (!done && neighbors.hasNext()) {
                        VertexInterface<T> nextNeighbor = neighbors.next();
                        Iterator<Double> weights = nextNeighbor.getWeightIterator();
                        double weightOfEdgeToNeighbor = weights.next(); //weightOfEdgeToNeighbor = weight of edge to nextNeighbor
                        if (!nextNeighbor.isVisited()) {
                            double nextCost = weightOfEdgeToNeighbor + frontVertex.getCost();//nextCost = weightOfEdgeToNeighbor + cost of path to frontVertex
                            pq.add(new EntryPQ(nextNeighbor, nextCost, frontVertex).getVertex());
                        }
                    }
                }
            }
        }
        double pathCost = endVertex.getCost();
        path.push(endVertex.getLabel());
        VertexInterface<T> vertex = endVertex;
        while (vertex.hasPredecessor()) {
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
        }
        return pathCost;
    }

    @Override
    public boolean addEdge(T begin, T end,
            double edgeWeight
    ) {
        boolean result = false;
        VertexInterface<T> beginVertex = vertices.get(begin);
        VertexInterface<T> endVertex = vertices.get(end);

        if ((beginVertex != null) && (endVertex != null)) {
            result = beginVertex.connect(endVertex, edgeWeight);
        }
        if (result) {
            edgeCount++;
        }
        return result;
    }

    @Override
    public boolean addEdge(T begin, T end
    ) {
        return addEdge(begin, end, 0);
    }

    @Override
    public boolean hasEdge(T begin, T end
    ) {
        boolean found = false;
        VertexInterface<T> beginVertex = vertices.get(begin);
        VertexInterface<T> endVertex = vertices.get(end);

        if ((beginVertex != null) && (endVertex != null)) {
            Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
            while (!found && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor)) {
                    found = true;
                }
            }
        }
        return found;
    }

    @Override
    public boolean isEmpty() {
        return vertices.isEmpty();
    }

    @Override
    public int getNumberOfVertices() {
        return vertices.size();
    }

    @Override
    public int getNumberOfEdges() {
        return edgeCount;
    }

    @Override
    public void clear() {
        vertices = new HashMap<>();
        edgeCount = 0;
    }

    protected void resetVertices() {
        Iterator<VertexInterface<T>> vertexIterator = vertices.values().iterator();
        while (vertexIterator.hasNext()) {
            VertexInterface<T> nextVertex = vertexIterator.next();
            nextVertex.unvisit();
            nextVertex.setCost(0);
            nextVertex.setPredecessor(null);

        }
    }

    // in getCheapestPath, add EntryPQ objects to the priority queue rather than Vertex objects,
    // since each Vertex might get added to the priority queue multiple times with different costs and predecessors
    private class EntryPQ implements Comparable<EntryPQ> {

        private VertexInterface<T> vertex;
        private VertexInterface<T> previousVertex;
        private double cost; // cost to nextVertex in current traversal

        private EntryPQ(VertexInterface<T> vertex, double cost, VertexInterface<T> previousVertex) {
            this.vertex = vertex;
            this.previousVertex = previousVertex;
            this.cost = cost;
        }

        public VertexInterface<T> getVertex() {
            return vertex;
        }

        public VertexInterface<T> getPredecessor() {
            return previousVertex;
        }

        public double getCost() {
            return cost;
        }

        public int compareTo(EntryPQ otherEntry) {
            if (otherEntry.cost == cost) {
                return 0;
            } else if (otherEntry.cost < cost) {
                return 1;
            }
            return -1;
        }

        public String toString() {
            return vertex.toString() + " " + cost;
        }
    }
}
