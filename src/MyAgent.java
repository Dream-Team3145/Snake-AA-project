import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;

import za.ac.wits.snake.DevelopmentAgent;

public class MyAgent extends DevelopmentAgent {

    private static int HEIGHT, WIDTH;
    private static int[][] GRID;
    private static Node APPLE, MY_HEAD;
    private static Node[] neighbourCoord = new Node[]{new Node(0, -1), new Node(0, 1), new Node(-1, 0), new Node(1, 0)};

    public static void main(String args[]) throws IOException {
        MyAgent agent = new MyAgent();
        MyAgent.start(agent, args);
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String initString = br.readLine();
            String[] temp = initString.split(" ");
            int nSnakes = Integer.parseInt(temp[0]);
            HEIGHT = Integer.parseInt(temp[2]);
            WIDTH = Integer.parseInt(temp[1]);

            while (true) {
                String line = br.readLine();
                if (line.contains("Game Over")) {
                    break;
                }
                GRID = new int[HEIGHT][WIDTH];
                String[] appleCoord = line.split(" ");
                APPLE = new Node(Integer.parseInt(appleCoord[0]), Integer.parseInt(appleCoord[1]));

                //do stuff with apple
                for (int i = 0; i < 3; i++) {
                    String zombieLine = br.readLine();
                    String[] zombieDesc = zombieLine.split(" ");
                    for (int k = 0; k < zombieDesc.length - 1; k++){
                        String[] firstKnick = zombieDesc[k].split(",");
                        String[] secondKnick = zombieDesc[k + 1].split(",");
                        Node A = new Node(Integer.parseInt(firstKnick[0]), Integer.parseInt(firstKnick[1]));
                        Node B = new Node(Integer.parseInt(secondKnick[0]), Integer.parseInt(secondKnick[1]));
                        drawLine(A, B);
                    }
                } 
                
                int mySnakeNum = Integer.parseInt(br.readLine());
                for (int i = 0; i < nSnakes; i++) {
                    String snakeLine = br.readLine();
                    String[] snakeDesc = snakeLine.split(" ");
                    if (i == mySnakeNum) {
                        //hey! That's me :)
                        String[] firstKnick = snakeDesc[3].split(",");
                        MY_HEAD = new Node(Integer.parseInt(firstKnick[0]), Integer.parseInt(firstKnick[1]));
                    }
                    for (int k = 3; k < snakeDesc.length - 1; k++){
                        String[] firstKnick = snakeDesc[k].split(",");
                        String[] secondKnick = snakeDesc[k + 1].split(",");
                        Node A = new Node(Integer.parseInt(firstKnick[0]), Integer.parseInt(firstKnick[1]));
                        Node B = new Node(Integer.parseInt(secondKnick[0]), Integer.parseInt(secondKnick[1]));
                        drawLine(A, B);
                    }
                    //do stuff with snakes
                } 
                // finished reading, calculate move:
                //calculate path
                //get node to go to
                //pass to generateMove
                int move = generateMove();
                System.out.println("log calculating...");
                //int move = new Random().nextInt(4);
                System.out.println(move);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int generateMove(Node p) {
        /*ArrayList<Node> neighbours = new ArrayList<>();

        for (Node n : neighbourCoord){
            if (areaSafe(MY_HEAD.getX() + n.getX(), MY_HEAD.getY() + n.getY())){
                neighbours.add(new Node(MY_HEAD.getX() + n.getX(), MY_HEAD.getY() + n.getY()));
            }
        }

        neighbours.sort(Comparator.comparingInt(o -> getManhattanDistance(o, APPLE)));

        if (neighbours.isEmpty()) {
            System.out.println("log something");
            return 5; //straight
        }

        Node p = neighbours.get(0);*/
        Node h = MY_HEAD;

        if (h.getX() == p.getX()){
            if (p.getY() > h.getY()) return 1;//down
            return 0;//up
        }
        else{
            if (p.getX() > h.getX()) return 3;//right
            return 2; //left
        }
    }

    private boolean inBounds(int x, int y){
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
    }

    private boolean areaSafe(int x, int y){
        if (inBounds(x, y)){
            /*for (Node n : neighbourCoord){
                    if (inBounds(x + n.getX(), y + n.getY()) && GRID[y + n.getY()][x + n.getX()] == 1 && !MY_HEAD.equals(new Node(x + n.getX(), y + n.getY()))){
                        return false;
                    }
                }*/
            return GRID[y][x] == 0;
        }
        else return false;
    }
    private void drawLine(Node A, Node B) {
        int currX = A.getX();
        int currY = A.getY();
        int nextX = B.getX();
        int nextY = B.getY();

        if(currX == nextX){
            if(currY > nextY) for( ; nextY <= currY; nextY++) GRID[nextY][currX] = 1;
            else for( ; currY <= nextY; currY++) GRID[currY][currX] = 1;
        }
        else{
            if(currX > nextX) for( ; nextX <= currX; nextX++) GRID[currY][nextX] = 1;
            else for( ; currX <= nextX; currX++) GRID[currY][currX] = 1;
        }

    }

    public static int getManhattanDistance(Node p1, Node p2){
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
    }
}