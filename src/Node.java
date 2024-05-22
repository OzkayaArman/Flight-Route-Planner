/*This class represents a node of the search tree and contains the following
    State: int distance, angle
    Depth: int depth
    Path Cost: double cost
    Pointer to predecessor: Node Parent
    F Cost: double overall cost
    Euclidian Cost: double euclidian cost
*/
import java.util.ArrayList;
import java.util.Objects;

public class Node {

    private PolarState state;
    private int depth;
    private double cost;
    private double euclidianCost;
    private double overallCost;
    private Node predecessorPointer;
    private ArrayList<Node> forgotten = new ArrayList<Node>();
    public boolean leaf;
    
    //Sets the state of the node
    public void setState(PolarState stateIn){
        state = stateIn;
    }

    //Set the depth of the node
    public void setDepth(int depthIn){
        depth = depthIn;
    }

    //Set the cost of the node
    public void setCost(double costIn){
        cost = costIn;
    }

    //Sets the f cost of the node
    public void setOverallCost(double overallCostIn){
        overallCost = overallCostIn;
    }

    //Sets the parent of the node 
    public void setPredecessorPointer(Node predIn){
        predecessorPointer = predIn;
    }

    //Sets the euclidian distance between the node to act as a heuristic
    public void setEuclidianCost(double euclidianCostIn){
        euclidianCost = euclidianCostIn;
    }

    //Adds a node to the forgotten arraylist to help implement smastar
    public void addForgotten(Node nodeIn){
        forgotten.add(nodeIn);
    }
    
    //Given an index, removes a node from the forgotten arraylist
    public void deleteForgotten(int i){
        forgotten.remove(i);
    }

    //Returns the forgotten arraylist to help implement smastar
    public ArrayList<Node> getForgotten(){
        return forgotten;
    }

    //Returns whether forgotten list is empty to help implement smastar
    public boolean isForgottenEmpty(){
        return forgotten.isEmpty();
    }

    //Returns the state of a node
    public PolarState getState(){
        return state;
    }

    //Returns the depth of a node
    public int getDepth(){
        return depth;
    }

    //Returns the path cost of a node
    public double getCost(){
        return cost;
    }

    //Returns the parent of a node
    public Node getPredecessorPointer(){
        return predecessorPointer;
    }

    //Returns the euclidian distance to goal from the node
    public double getEuclidianCost() {
        return euclidianCost;
    }

    //Returns the f cost of the node
    public double getOverallCost(){
        return overallCost;
    }

    //Updates the cost based on the cost of nodes in forgotten list to help implement smastar
    public void updateCost(){
        double smallestCost = 0;
        for(int i = 0 ; i < forgotten.size();i++){
            if(forgotten.get(i).getOverallCost() > smallestCost){
                smallestCost = forgotten.get(i).getOverallCost();
            }
        }
        overallCost = smallestCost;
    }

    //Override equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(state, node.state); 
    }

    @Override
    public int hashCode() {
        return Objects.hash(state); 
    }


  

    



}
