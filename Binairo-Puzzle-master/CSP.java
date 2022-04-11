import java.util.ArrayList;


public class CSP {
    private Rules rules;
    private Heuristic heuristic;
    private Propagation propagation;


    public CSP() {
        this.rules = new Rules();
        this.heuristic = new Heuristic();
        this.propagation = new Propagation();
    }



    public void csp(State state) {
        Node start = new Node(new Node(null));
        backtracking(state, start);

    }

    public void backtracking(State state, Node node) {


        boolean finished = rules.isFinished(state);
        if (finished) {
            System.out.println("puzzle solved");
            return;
        }

        Pair pair = propagation.forwardChecking(state);

        if (pair.flag()) {
            Node child = new Node(node);
            backtracking(state, child);
        }

        // + variable domain checking
        else if(!pair.flag()) {
            backtracking(state, node.getParent());   
        }

        else {
            backtracking(state, node);
        }        
    }

}
