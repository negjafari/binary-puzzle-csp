public class Node {
    private Node parent;
    private String value;
    private int x;
    private int y;

    public Node(Node parent){
        this.parent = parent;
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

    


    
}
