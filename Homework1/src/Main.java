import java.util.*;

public class Main {
    public static boolean checkVisited(List<Node> visited, Node node){
        for (Node value : visited) {
            if(Arrays.equals(value.state,node.state)) return true;
        }
        return false;
    }

    public static void IDA(Node initialState){
        int threshold = initialState.manhattan();
        while(true){
            List<Node> visited = new ArrayList<>();
            HashMap<Integer, List<String>> temp = search(initialState, threshold, visited);
            if((int)temp.keySet().toArray()[0] <= threshold){ // if solution with less cost than threshold is found
                System.out.println(temp.keySet().toArray()[0]);
                List<String> path = (List<String>) temp.values().toArray()[0];
                for(String direction: path){
                    System.out.println(direction);
                }
                break;
            }else{ // update threshold
                if((int)temp.keySet().toArray()[0] == Integer.MAX_VALUE){
                    System.out.println("UNSOLVED");
                }
                threshold = (int)temp.keySet().toArray()[0];
            }
        }
    }

    public static HashMap<Integer,List<String>> search(Node node, int threshold, List<Node> visited){
        visited.add(node);
        int f = node.manhattan() + node.distance;

        HashMap<Integer, List<String>> result = new HashMap<>();
        if(f > threshold){ // if distance to this node is more than threshold and puzzle is not solved
            result.put(f,null);
            return result;
        }
        if(node.manhattan() == 0){ // solution is found
            result.put(node.distance,node.path);
            return result;
        }

        HashMap<Integer,List<String>> min = new HashMap<>();
        min.put(Integer.MAX_VALUE,null);
        Node left = node.moveLeft();
        Node right = node.moveRight();
        Node up = node.moveUp();
        Node down = node.moveDown();
        List<Node> neighbours = new ArrayList<>();
        Collections.addAll(neighbours,left,right,up,down);

        for(Node neighbour: neighbours){
            if(neighbour != null && !checkVisited(visited,neighbour)){
                HashMap<Integer,List<String>> temp = search(neighbour,threshold,visited);
                if((int)temp.keySet().toArray()[0] <= threshold){
                    return temp;
                }
                if((int)temp.keySet().toArray()[0] < (int)min.keySet().toArray()[0]){
                    min = temp;
                }
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
        long start = System.currentTimeMillis();
        IDA(node);
        long end = System.currentTimeMillis();
        System.out.println((end-start)/1000.0);
    }
}
