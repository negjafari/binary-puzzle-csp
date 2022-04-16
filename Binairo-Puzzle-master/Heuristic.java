import java.util.ArrayList;


public class Heuristic {

    private ArrayList<ArrayList<String>> board;
    private ArrayList<ArrayList<ArrayList<String>>> domain;


    public Heuristic(ArrayList<ArrayList<String>> board, ArrayList<ArrayList<ArrayList<String>>> domain) {
        this.board = board;
        this.domain = domain;
    }

    public Node minDomain(Node node) {
        int min = 2;
        int x = -1;
        int y = -1;
        int n = this.board.size();
        for (int i=0 ; i<n ; i++){
            for (int j=0 ; j<n ; j++) {
                int size = node.getState().getDomain().get(i).get(j).size();
                if (node.getState().getBoard().get(i).get(j).equals("E") && size <= min) {
                    min = size;
                    x = i;
                    y = j;
                }
            }
        }
        
        return new Node(x,y);
    }


    public String assignValue(Node node){
        int x = node.getX();
        int y = node.getY();


        if(node.getState().getDomain().get(x).get(y).size() == 0){
            return "0";
        }
        else {
            return node.getState().getDomain().get(x).get(y).remove(0);
        }

    }


    public boolean MVR(Node node, String mode) {
        if (mode.equals("samevar")) {
        }
        else {
            Node minNode = minDomain(node);
            node.setPostiton(minNode.getX(), minNode.getY());
            if (node.getX() == -1) {
                return false;
            }
        }


        String value = assignValue(node);
        node.setValue(value);



        if (!value.equals("0")){
            node.setValue(value);
            return true;
        }
        else {
            return false;
        }



    }






    
}

