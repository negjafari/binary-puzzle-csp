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
                int size = getSize(node.getState().getDomain().get(i).get(j));
                if (node.getState().getBoard().get(i).get(j).equals("E") && size <= min) {
                    min = size;
                    x = i;
                    y = j;
                }
            }
        }
        
        return new Node(x,y);
    }


    public int getSize(ArrayList<String> list){
        int size = 0;
        for (String s : list) {
            if (s.equals("w") || s.equals("b")) {
                size++;
            }
        }
        return size;
    }


    public String assignValue(Node node){
        int x = node.getX();
        int y = node.getY();


        if(isEmpty(node.getState().getDomain().get(x).get(y))){
            return "0";
        }
        else {
            String value = getValue(node.getState().getDomain().get(x).get(y));
            node.getState().getDomain().get(node.getX()).get(node.getY()).remove(value);
            return value;
        }

    }


    public boolean isEmpty(ArrayList<String> list){
        for (String s : list) {
            if (s.equals("W") || s.equals("B") || s.equals("w") || s.equals("b")) {
                return false;
            }
        }
        return true;
    }

    public String getValue(ArrayList<String> list){
        for (String s : list) {
            if (s.equals("w") || s.equals("b")) {
                return s;
            }
        }
        return "0";
    }

    public String hasVariable(String value, ArrayList<String> list){
        for (String s : list) {
            if (s.equals("w") || s.equals("b")) {
                if (!s.equals(value)) {
                    return s;
                }
            }
        }
        return "0";
    }


    public boolean MVR(Node node, String mode) {
        if (mode.equals("same")) {
            String value = hasVariable(node.getValue(), node.getState().getDomain().get(node.getX()).get(node.getY()));
            if(value.equals("0")) { //domain is empty
                return false;
            }
            else {
                node.getState().getDomain().get(node.getX()).get(node.getY()).remove(value);
                node.setValue(value);
                return true;
            }

        }
        else {
            Node minNode = minDomain(node);
            //??
            node.setPostiton(minNode.getX(), minNode.getY());
            if (node.getX() == -1) {
                return false;
            }
        }


        String value = assignValue(node);
        node.setValue(value);
//        node.setValue(value);


        if (!value.equals("0")){
//            node.setValue(value);
            return true;
        }
        else {
            return false;
        }



    }






    
}

