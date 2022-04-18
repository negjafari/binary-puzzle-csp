import java.util.ArrayList;


public class Pair {
    private boolean flag;
    private boolean IsDomainNull;
    private ArrayList<ArrayList<ArrayList<String>>> domain;
    private ArrayList<String> variableDomain;



    public Pair(boolean flag, ArrayList<ArrayList<ArrayList<String>>> domain) {
        this.flag = flag;
        this.domain = domain;
    }

    public Pair(ArrayList<String> variableDomain, boolean flag) {
        this.flag = flag;
        this.variableDomain = variableDomain;
    }

    public Pair(boolean flag, boolean isDomainNull) {
        this.flag = flag;
        IsDomainNull = isDomainNull;
    }

    public Pair(boolean flag) {
        this.flag = flag;
    }

    public boolean isDomainNull() {
        return IsDomainNull;
    }

    public ArrayList<String> getVariableDomain() {
        return variableDomain;
    }

    public boolean flag() {
        return flag;
    }

    public ArrayList<ArrayList<ArrayList<String>>> domain() {
        return domain;
    }

    @Override
    public String toString() {
        if(domain!=null && variableDomain!=null) {
            return "Pair{" +
                    "flag=" + flag +
                    ", domain=" + domain +
                    ", variableDomain=" + variableDomain +
                    '}';
        }
        else if(domain==null){
            return "Pair{" +
                    "flag=" + flag +
                    ", domain=" + " " +
                    ", variableDomain=" + variableDomain +
                    '}';
        }
        else {
            return "Pair{" +
                    "flag=" + flag +
                    ", domain=" + domain +
                    ", variableDomain=" + " " +
                    '}';
        }

    }
}
