import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

//A first in first out queue data structure implementation
public class Queue {
    ArrayList<Node> coordinates = new ArrayList<Node>();

    public void enqueue(Node newNode) {
        coordinates.add(newNode);
    }

    public Node dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return coordinates.remove(0);
    }

    public void printQueue() {
        for (int i = 0; i < coordinates.size(); i++) {
            // Print initial bracket
            if (i == 0) {
                System.out.print("[");
            }

            System.out.print(
                    "(" + coordinates.get(i).getState().getDistance() + ":" + coordinates.get(i).getState().getAngle());

            if (i < coordinates.size() - 1) {
                System.out.print(")" + ",");
            } else {
                System.out.print(")" + "]");
            }
        }
        System.out.println();
    }


    public void printQueue(boolean flag) {
        if (flag){
            for (int i = 0; i < coordinates.size(); i++) {
                // Print initial bracket
                if (i == 0) {
                    System.out.print("[");
                }
    
                System.out.print(
                        "(" + coordinates.get(i).getState().getDistance() + ":" + coordinates.get(i).getState().getAngle()+")");
    
                if (i < coordinates.size() - 1) {
                    System.out.printf("%.3f", coordinates.get(i).getOverallCost());
                    System.out.print(",");
                } else {
                    System.out.printf("%.3f", coordinates.get(i).getOverallCost());
                    System.out.print("]");
                }
            }
        }else{
            for (int i = 0; i < coordinates.size(); i++) {
                // Print initial bracket
                if (i == 0) {
                    System.out.print("[");
                }
    
                System.out.print(
                        "(" + coordinates.get(i).getState().getDistance() + ":" + coordinates.get(i).getState().getAngle()+")");
    
                if (i < coordinates.size() - 1) {
                    System.out.printf("%.3f", coordinates.get(i).getCost());
                    System.out.print(",");
                } else {
                    System.out.printf("%.3f", coordinates.get(i).getCost());
                    System.out.print("]");
                }
            }
        }
       
        System.out.println();
    }

    public boolean isEmpty() {
        return coordinates.isEmpty();
    }

    public boolean contains(PolarState polarstateIn) {
        for (int i = 0; i < coordinates.size(); i++) {
            if (coordinates.get(i).getState().equals(polarstateIn)) {
                return true;
            }
        }
        return false;
    }

    public int getSize() {
        return coordinates.size();
    }

    public Node containsA(PolarState polarstateIn) {
        for (int i = 0; i < coordinates.size(); i++) {
            if (coordinates.get(i).getState().equals(polarstateIn)) {
                return coordinates.get(i);
            }
        }
        return null;
    }

    // Remove the node inputted
    public void remove(Node nodeToBeRemoved) {
        for (int i = 0; i < coordinates.size(); i++) {
            if (coordinates.get(i).equals(nodeToBeRemoved)) {
                coordinates.remove(i);
            }
        }
    }

    // Remove the node inputted
    public Node remove(int index) {
        return coordinates.remove(index);
    }

    // Remove the node inputted
    public Node get(int index) {
        return coordinates.get(index);
    }

    // Sort based on fcost then based on basic tie breaking strategy
    public void sortBestFirst() {
        coordinates.sort(
                Comparator.comparingDouble(Node::getEuclidianCost).thenComparing(node -> node.getState().getDistance())
                        .thenComparing(node -> node.getState().getAngle()));
    }

    // Sort based on fcost then based on basic tie breaking strategy
    public void sortAStar() {
        coordinates.sort(
                Comparator.comparingDouble(Node::getOverallCost).thenComparing(node -> node.getState().getDistance())
                        .thenComparing(node -> node.getState().getAngle()));
    }


}
