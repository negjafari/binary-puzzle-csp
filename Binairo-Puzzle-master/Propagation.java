import java.util.ArrayList;

public class Propagation {
    

    public boolean forwardChecking(State state) {
        boolean check = true;
        int n = state.getBoardSize();

        ArrayList<ArrayList<ArrayList<String>>> domainCopy = state.copyCurrentDomain();
        for (int i=0 ; i<n ; i++) {
            for (int j=0 ; j<n ; j++) {
                int x;
                int y;
                ArrayList<String> nodeDomain = domainCopy.get(i).get(j);
                if(hasVariable(nodeDomain)){
                    x = i;
                    y = j;
                    
                }

                
            }
        }

        return false;

    }

    public boolean hasVariable(ArrayList<String> domain) {

        for(int i=0 ; i<domain.size() ; i++){
            if(domain.get(i).equals("w") || domain.get(i).equals("b")){
                return true;
            }
        }
        return false;
    }







}
