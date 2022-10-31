import java.util.*;

import static java.util.Objects.isNull;

public class Main {

    public static boolean checkVisited(List<Node> visited, Node node){
        boolean found = false;
        for (Node value : visited) {
            int[] state = value.state;
            boolean same = true;
            for (int j = 0; j < state.length; j++) {
                if (state[j] != node.state[j]) {
                    same = false;
                    break;
                }
            }
            if (same) {
                found = true;
                break;
            }
        }
        return found;
    }

    public static int IDA(Node initialState){
        int threshold = initialState.manhattan() + initialState.distance;
        while(true){
            System.out.println("Threshold:"+ threshold);
            List<Node> visited = new ArrayList<Node>();
            int temp = search(initialState, threshold, visited);
            if(temp <= threshold){ // if solution with less cost than threshold is found
                return temp;
            }else{ // update threshold
                if(temp == Integer.MAX_VALUE){
                    System.out.println("UNSOLVED");
                    return -1;
                }
                threshold = temp;
            }
        }
    }

    public static int search(Node node, int threshold, List<Node> visited){
//        node.print();
//        System.out.println();
//        System.out.println("Distance:"+ node.distance);
//        System.out.println("Path:"+ node.path);
//        System.out.println("Manhatan:"+ node.manhattan());
//        System.out.println("Visited size:" + visited.size());
//        System.out.println("------------------------------------");
        visited.add(node);
        int f = node.manhattan() + node.distance;
        if(f > threshold){ // if distance to this node is more than threshold and puzzle is not solved
            return f;
        }
        if(node.manhattan() == 0){ // solution is found
            System.out.println(node.path);
            return node.distance;
        }
        int min = Integer.MAX_VALUE;
        Node left = node.moveLeft();
        Node right = node.moveRight();
        Node up = node.moveUp();
        Node down = node.moveDown();

        if(!isNull(left) && !checkVisited(visited,left)){
            int temp = search(left,threshold,visited);
            if(temp <= threshold){
                return temp;
            }
            if(temp < min){
                min = temp;
            }
        }
        if(!isNull(right) && !checkVisited(visited,right)){
            int temp = search(right,threshold,visited);

            if(temp <= threshold){
                return temp;
            }
            if(temp < min){
                min = temp;
            }
        }

        if(!isNull(up) && !checkVisited(visited,up)){
            int temp = search(up,threshold,visited);
            if(temp <= threshold){
                return temp;
            }
            if(temp < min){
                min = temp;
            }
        }

        if(!isNull(down) && !checkVisited(visited,down)){
            int temp = search(down,threshold,visited);
            if(temp <= threshold){
                return temp;
            }
            if(temp < min){
                min = temp;
            }
        }
        visited.remove(visited.size()-1);
        return min;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt() + 1;
        int zeroIndex = scanner.nextInt();
        int[] numbers = new int[size];
        if(zeroIndex == -1){
            zeroIndex = size-(int)Math.sqrt(size);
        }
        for(int i=0;i<size;i++){
            if(i == zeroIndex){
                numbers[i]=0;
                continue;
            }
            numbers[i] = scanner.nextInt();
        }

        Node node = new Node(numbers,size,zeroIndex);
        System.out.println(IDA(node));
    }
}
