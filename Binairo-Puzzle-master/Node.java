public class Node {
    private State state;
    private Node parent;
    private String value;
    private int x;
    private int y;

    private boolean hasBoard;


    public Node(Node parent){
        this.parent = parent;
    }



    public Node(Node parent, State state){
        this.parent = parent;
        this.state = state;
    }

    public Node(int x, int y){
        this.x = x;
        this.y = y;
    }
    

    public Node getParent(){
        return this.parent;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPostiton(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }


    @Override
    public String toString() {
        if (this.state != null) {
            return "Node1{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
        return "Node0{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
