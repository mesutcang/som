
package topology;

public class Coords {
    public int x;
    public int y;
    
    public Coords() {
        this(0,0);
    }
    
    public Coords(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public boolean equals(Object obj){
        if(obj instanceof Coords){
            Coords coords = (Coords) obj; 
            return (x == coords.x) && (y == coords.y);
        }
        return false;
    }
    
    public String toString(){
        return "[ x= " + x + ",y= " + y + " ]";
    }
}


