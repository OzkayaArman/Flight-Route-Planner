/*This class represents a the state of a node
*/
import java.util.Objects;

public class PolarState {
    private int distance;
    private int angle;
    private int worldSize;
    
    public PolarState(int distanceIn, int angleIn, int sizeIn){
        distance = distanceIn;
        angle = angleIn;
        worldSize = sizeIn;
    }
    public PolarState(){
        
    }

    public void setDistance(int distanceIn){
        distance = distanceIn;
    }

    public void setAngle(int angleIn){
        angle = angleIn;
    }

    public void setSize(int sizeIn){
        worldSize = sizeIn;
    }

    public int getSize (){
        return worldSize;
    }
    
    public int getDistance(){
        return distance;
    }

    public int getAngle(){
        return angle;
    }
     
    //GAP Reference: https://www.baeldung.com/java-equals-hashcode-contracts
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PolarState that = (PolarState) o;
        return distance == that.distance && angle == that.angle; 
    }

    @Override
    public int hashCode() {
        return Objects.hash(distance, angle); 
    }


}
