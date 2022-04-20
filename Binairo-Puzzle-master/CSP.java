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
        Node start = new Node(new Node(null), this.state);
        backtracking(start, "start");

    }

    public void backtracking(Node node, String mode) {

        if (!IsSolvable(node)) {
            return;
        }


        boolean finished = rules.isFinished(node.getState());
        if (finished) {
            System.out.println("PUZZLE SOLVED");
            printB(node.getState().getBoard());
            System.out.println();
            return;
        }


        boolean empty = heuristic.MVR(node, mode);


        if (!empty) {
            System.out.println("BACKTRACKING ...");
            backtracking(node.getParent(), "continue");
        }

        else {
            ArrayList<ArrayList<ArrayList<String>>> domainCopy = rules.copyDomain(node.getState().getDomain());
            int x = node.getX();
            int y = node.getY();


            domainCopy.get(x).set(y, new ArrayList<>(List.of(node.getValue().toUpperCase())));

            ArrayList<ArrayList<String>> boardCopy = rules.copyBoard(node.getState().getBoard());
            boardCopy.get(x).set(y, node.getValue().toUpperCase());

            System.out.println("NODE : " + node + "SELECTED WITH VALUE : " + node.getValue());
            printB(node.getState().getBoard());
            System.out.println();


//            Pair pair = propagation.forwardChecking(domainCopy);
            Pair pair = propagation.AC3(domainCopy, new Node(x,y));


            if (pair.flag()) {
                System.out.println("CONTINUE SOLVING ");
                Node child = new Node(node, new State(boardCopy, pair.domain()));
                backtracking(child, "continue");
            }

            else if(!pair.flag() && (pair.isDomainNull())) {
                System.out.println("BACKTRACKING (EMPTY DOMAIN) ");
                backtracking(node.getParent(), "backtracking");
            }

            else {
                System.out.println("LAST VALUE ASSIGNED HAD CONFLICT");
                backtracking(node, "same");
            }

        }

       
    }

    public boolean IsSolvable(Node node){
        if(node.getState() == null) {
            System.out.println("unsolvable");
            return false;
        }
        return true;
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
