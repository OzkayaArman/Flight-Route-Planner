import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

//A first in first out queue data structure implementation
public class Stack {
    LinkedList <Node> coordinates = new LinkedList<Node>();

    public void push(Node newNode){
        coordinates.addFirst(newNode);
    }
    public Node pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return coordinates.remove(0); 
    }

    public void printStack(){
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

    public boolean isEmpty(){
        return coordinates.isEmpty();
    }

    public boolean contains(PolarState polarstateIn){
        for(int i = 0 ; i < coordinates.size(); i++){
            if(coordinates.get(i).getState().equals(polarstateIn)){
                return true;
            }
        }
        return false;
    }

     //Sort based on fcost then based on basic tie breaking strategy
     public void sortBestFirst(){
		coordinates.sort(
            Comparator.comparingDouble(Node::getEuclidianCost) 
            .thenComparing(node -> node.getState().getDistance()) 
            .thenComparing(node -> node.getState().getAngle()) 
        );
    }

    public void sortTieBreak(){
        coordinates.sort(
            Comparator.comparingInt((Node node) -> node.getState().getDistance())
                      .thenComparingInt(node -> node.getState().getAngle())
        );
    }


}
