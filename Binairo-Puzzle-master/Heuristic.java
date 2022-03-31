import java.util.ArrayList;


public class Heuristic {

    public boolean MVR(State state, String mode) {
        if(mode.equals("same")){

        }
        else {
            ArrayList<ArrayList<String>> board = state.getBoard();
            ArrayList<ArrayList<ArrayList<String>>> domain = state.getDomain();
            int n = state.getBoardSize();
            int minimum = 2;
            int x = -1;
            int y = -1;
    
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    ArrayList<String> row = board.get(i);
                    if (row.get(j).equals("E") && domain.get(i).get(j).size() <= minimum) {
                        minimum = domain.get(i).get(j).size();
                        x = i;
                        y = j;
                    }
                }
            }

            //when backtrack and node parent variables is empty
            if (x==-1) {
                return false;
            }


        }
        return false;

    }





    
}

