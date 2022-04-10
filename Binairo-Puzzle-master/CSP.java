import java.util.ArrayList;

    // E E W E E W E E
    // E B E E B W E B
    // E E E W E E E B
    // E E E E E W E E 
    // E E W E W E E B
    // E E W E E B E B
    // E E E W E E E E
    // E E E E E E W E


public class CSP {
    Rules rules;
    Heuristic heuristic;


    public CSP(Rules gamRules) {
        this.rules = gamRules;
        this.heuristic = new Heuristic();
    }

    public void csp(State state) {
        Node base = new Node(null);
        Node start = new Node(base);
        backtracking(state, start);

    }

    public void backtracking(State state, Node node) {

        boolean finished = this.rules.isFinished(state);
        if (finished) {
            System.out.println("puzzle solved");
            return;
        }

        //first implemention no heuristic 

        boolean h_result = heuristic.MVR(state, "");
        if(!h_result) {
            //here we should go to parent node
            System.out.println("backtracking");
            backtracking(state, node.getParent());
        }
        else {
            //create a copy of domain and board
            State newState = state.copy();
            int x = node.getX();
            int y = node.getY();
            newState.getDomain().get(x).get(y).add(node.getValue());
            newState.setIndexBoard(x, y, node.getValue());
            System.out.println("assign variable " + x + "," + y + " to " + node.getValue());
            newState.printBoard();
            System.out.println("----------------------------------------------");
            
        }








        
    }

}
