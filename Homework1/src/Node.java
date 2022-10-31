import java.util.ArrayList;
import java.util.List;

public class Node {
    public int[] state;
    public int size;
    public int distance;
    public List<String> path;
    public int zeroIndex;
    public int ssize;
    
    public Node(int[] state, int size, int zeroIndex) {
        this.size = size;
        this.ssize = (int)Math.sqrt(size);
        this.state = new int[size];
        this.zeroIndex = zeroIndex;
        for(int i=0;i<size;i++){
            this.state[i] = state[i];
        }
        this.distance = 0;
        this.path = new ArrayList<String>();
    }

    public Node(Node node,int x,int y,String direction){
        this.size = node.size;
        this.ssize = node.ssize;
        this.state = new int[size];
        this.zeroIndex = y;
        for(int i=0;i<size;i++){
            if(i == y){
                this.state[i] = node.state[x];
                continue;
            }
            if(i == x){
                this.state[i] = node.state[y];
                continue;
            }
            this.state[i] = node.state[i];
        }
        this.distance = node.distance + 1;
        this.path = new ArrayList<String>(node.path);
        this.path.add(direction);
    }

    public void print(){
        int a = (int)Math.sqrt(size);
        for(int i=0;i<size;i++){
            if(i%a == 0) System.out.println();
            System.out.print(state[i] + " ");
        }
    }

    public int manhattan(){
        int sum = 0;
        for(int i=0;i<size;i++){
            if(state[i]!=0) sum+=Math.abs(state[i]-i-1);
        }
        return sum;
    }

    public Node moveLeft(){
        if(zeroIndex % ssize != ssize-1){
            return new Node(this,zeroIndex,zeroIndex+1,"left");
        }
        return null;
    }

    public Node moveRight(){
        if(zeroIndex % ssize != 0){
            return new Node(this,zeroIndex-1,zeroIndex,"right");
        }
        return null;
    }

    public Node moveUp(){
        if(zeroIndex > ssize){
            return new Node(this,zeroIndex,zeroIndex-ssize,"up");
        }
        return null;
    }

    public Node moveDown(){
        if(zeroIndex < size-ssize){
            return new Node(this,zeroIndex,zeroIndex+ssize,"down");
        }
        return null;
    }
}