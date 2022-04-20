import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


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
//        Node base = new Node(false);
        Node start = new Node(new Node(null), this.state);
        backtracking(start, "start");

    }

    public void backtracking(Node node, String mode) {

        if (!IsSolvable(node)) {
            return;
        }


        boolean finished = rules.isFinished(node.getState());
        if (finished) {
            System.out.println("puzzle solved");
            printB(node.getState().getBoard());
            System.out.println();
            return;
        }


        boolean empty = heuristic.MVR(node, mode);


        if (!empty) {
            System.out.println("backtracking ...");
            backtracking(node.getParent(), "continue");
        }

        else {
            ArrayList<ArrayList<ArrayList<String>>> domainCopy = rules.copyDomain(node.getState().getDomain());
            int x = node.getX();
            int y = node.getY();


            domainCopy.get(x).set(y, new ArrayList<>(List.of(node.getValue().toUpperCase())));

            ArrayList<ArrayList<String>> boardCopy = rules.copyBoard(node.getState().getBoard());
            boardCopy.get(x).set(y, node.getValue().toUpperCase());

            System.out.println("mvr selected " + node + " with value " + node.getValue());
            printB(node.getState().getBoard());
            System.out.println();


//            Pair pair = propagation.forwardChecking(domainCopy);
            Pair pair = propagation.AC3(domainCopy, new Node(x,y));


            if (pair.flag()) {
                System.out.println("continue solving puzzle ");
                Node child = new Node(node, new State(boardCopy, pair.domain()));
                backtracking(child, "continue");
            }

            else if(!pair.flag() && (pair.isDomainNull())) {
                System.out.println("empty domain , backtracking ");
                backtracking(node.getParent(), "backtracking");
            }

            else {
                System.out.println("Change last assigned variable value");
                backtracking(node, "samevar");
            }

        }

       
    }

    public boolean IsSolvable(Node node){
//        if(node.hasBoard()){
//            System.out.println("unsolvable");
//            return false;
//        }

        if(node.getState().getBoard() == null) {
            System.out.println("unsolvable");
            return false;
        }
        return true;
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

    public void printB(ArrayList<ArrayList<String>> board) {
        int n = board.size();
        for(int i = 0 ; i<n ; i++){
            for(int j = 0 ; j<n ;j++){
                System.out.print(board.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

}
