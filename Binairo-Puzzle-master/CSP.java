import java.util.ArrayList;


public class CSP {
    private State state;
    private Rules rules;
    private Heuristic heuristic;
    private Propagation propagation;


    public CSP(State state) {
        this.state = state;
        this.rules = new Rules();
        this.heuristic = new Heuristic(state.getBoard(), state.getDomain());
        this.propagation = new Propagation();
    }



    public void csp() {
        Node start = new Node(new Node(null), state);
        backtracking(start, "start");

    }

    public void backtracking(Node node, String mode) {


        boolean finished = rules.isFinished(state);
        if (finished) {
            System.out.println("puzzle solved");
            return;
        }

        
        boolean empty = heuristic.MVR(node, mode);

      
        if (!empty) {
            backtracking(node.getParent(), "continue");
        }
        else {
            ArrayList<ArrayList<ArrayList<String>>> domainCopy = rules.copyDomain(node.getState().getDomain());
            int x = node.getX();
            int y = node.getY();


            ArrayList<String> newDomain = new ArrayList<>();
            newDomain.add(node.getValue());

            domainCopy.get(x).set(y, newDomain);

            ArrayList<ArrayList<String>> boardCopy = rules.copyBoard(node.getState().getBoard());
            boardCopy.get(x).set(y, node.getValue());
            // System.out.print(boardCopy);


            Pair pair = propagation.forwardChecking(domainCopy);

            ArrayList<String> varDomain = node.getState().getDomain().get(node.getX()).get(node.getY());


            if (pair.flag()) {
                // ArrayList<ArrayList<String>> boardCopy = rules.copyBoard(node.getState().getBoard());
                Node child = new Node(node, new State(boardCopy, pair.domain()));
                backtracking(child, "continue");
            }



            else if(!pair.flag() && (varDomain.size() == 0)) {
                backtracking(node.getParent(), "backtracking");   
            }

            else {
                backtracking(node, "samevar");
            } 

        }

       
    }


    public void printD(ArrayList<ArrayList<ArrayList<String>>> domainCopy){
        int n = domainCopy.size();
        for(int i=0 ; i<n ; i++) {
            for(int j=0; j<n ; j++) {
                ArrayList<String> domain = domainCopy.get(i).get(j);
                System.out.print(domain);
            }
            System.out.println();
        }

    }

}
