import java.util.ArrayList;

public class Propagation {

    private Rules rules;

    public Propagation(){
        rules = new Rules();
    }
    

    public Pair forwardChecking(ArrayList<ArrayList<ArrayList<String>>> domain) {
        Pair pair = new Pair(true, null);
        int n = domain.size();
        ArrayList<ArrayList<ArrayList<String>>> domainCopy = rules.copyDomain(domain);


        for (int i=0 ; i<n ; i++) {
            for (int j=0 ; j<n ; j++) {

                int x;
                int y;
                ArrayList<String> nodeDomain = domainCopy.get(i).get(j);
                if(!hasVariable(nodeDomain)){
                    x = i;
                    y = j;
                    pair = rules.updateVariableDomain(domainCopy, x, y);
                    if(pair.flag()) {
                        domainCopy = pair.domain();
                    }
                    else{
                        break;
                    }
                }
            }
            if (!pair.flag()) break;
        }

        if (pair.flag()) {
            return new Pair(true, domainCopy);
        }
        else{
            return new Pair(false, null);
        }

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
