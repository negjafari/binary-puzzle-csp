import java.util.*;


 


public class Rules {

    // public int n;

    public Rules(){
        
    }
    
    // rule 1
    public boolean checkNumberOfCircles(State state) {
        int n = state.getBoardSize();
        ArrayList<ArrayList<String>> cBoard = state.getBoard();
        //row
        for (int i = 0; i < n; i++) {
            int numberOfWhites = 0;
            int numberOfBlacks = 0;
            for (int j = 0; j < n; j++) {
                String a = cBoard.get(i).get(j);
                switch (a) {
                    case "w", "W" -> numberOfWhites++;
                    case "b", "B" -> numberOfBlacks++;
                }
            }
            if (numberOfBlacks > n/2 || numberOfWhites > n/2) {
                return false;
            }
        }
        //column
        for (int i = 0; i < n; i++) {
            int numberOfWhites = 0;
            int numberOfBlacks = 0;
            for (int j = 0; j < n; j++) {
                String a = cBoard.get(j).get(i);
                switch (a) {
                    case "w", "W" -> numberOfWhites++;
                    case "b", "B" -> numberOfBlacks++;
                }
            }
            if (numberOfBlacks > n/2 || numberOfWhites > n/2) {
                return false;
            }
        }
        return true;
    }
    
    // rule 2
    public boolean checkAdjacency(State state) {
        int n = state.getBoardSize();
        ArrayList<ArrayList<String>> cBoard = state.getBoard();

        //Horizontal
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n-2; j++) {
                ArrayList<String> row = cBoard.get(i);
                String c1 = row.get(j).toUpperCase();
                String c2 = row.get(j+1).toUpperCase();
                String c3 = row.get(j+2).toUpperCase();
                if (c1.equals(c2) && c2.equals(c3) && !c1.equals("E")) {
                    return false;
                }
            }
        }
        //column
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n-2; i++) {
                String c1 = cBoard.get(i).get(j).toUpperCase();
                String c2 = cBoard.get(i+1).get(j).toUpperCase();
                String c3 = cBoard.get(i+2).get(j).toUpperCase();
                if (c1.equals(c2) && c2.equals(c3) && !c1.equals("E")) {
                    return false;
                }
            }
        }

        return true;
    }
    
    // rule 3
    public boolean checkIfUnique (State state) {
        ArrayList<ArrayList<String>> cBoard = state.getBoard();
        int n = state.getBoardSize();

        //check if two rows are duplicated
        for (int i = 0; i < n-1; i++) {
            for (int j = i+1; j < n; j++) {
                int count = 0;
                for (int k = 0; k < n; k++) {
                    String a = cBoard.get(i).get(k);
                    if (a.equals(cBoard.get(j).get(k)) && !a.equals("E")) {
                        count++;
                    }
                }
                if (count == n) {
                    return false;
                }
            }
        }

        //check if two columns are duplicated
        for (int j = 0; j < n-1; j++) {
            for (int k = j+1; k < n; k++) {
                int count = 0;
                for (int i = 0; i < n; i++) {
                    if (cBoard.get(i).get(j).equals(cBoard.get(i).get(k))) {
                        count++;
                    }
                }
                if (count == n) {
                    return false;
                }
            }
        }

        return true;
    }
    
    public boolean allAssigned(State state) {
        ArrayList<ArrayList<String>> cBoard = state.getBoard();
        int n = state.getBoardSize();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                String s = cBoard.get(i).get(j);
                if (s.equals("E"))
                    return false;
            }
        }
        return true;
    }
        

    public boolean isFinished(State state) {
        return allAssigned(state) && checkAdjacency(state) && checkNumberOfCircles(state) && checkIfUnique(state);
    }

    public boolean isConsistent(State state) {
        return checkNumberOfCircles(state) && checkAdjacency(state) && checkIfUnique(state);
    }

    
    public Pair updateVariableDomainRule1(ArrayList<ArrayList<ArrayList<String>>> domain, int x, int y){
        //white 0 , black 1

        // ArrayList<ArrayList<ArrayList<String>>> domainsCopy = copyDomain(domain);
        ArrayList<String> currentNodeDomain = domain.get(x).get(y);
        ArrayList<ArrayList<String>> row = getRow(domain, x);
        ArrayList<ArrayList<String>> column = getColumn(domain, y);

        int whiteInRow = getValueNumberInSeries(row, "W");
        int blackInRow = getValueNumberInSeries(row, "B");
        int whiteInColumn = getValueNumberInSeries(column, "W");
        int blackInColumn = getValueNumberInSeries(column, "B");


        if (whiteInRow > (row.size()/2) || blackInRow > (row.size()/2) ||
            whiteInColumn > (column.size()/2) || blackInColumn > (column.size()/2)) {
                return new Pair(null, false);
        }

        if (whiteInRow == (row.size()/2) && currentNodeDomain.contains("w")){
            currentNodeDomain.remove("w");
        }

        if (blackInRow == (row.size()/2) && currentNodeDomain.contains("b")){
            currentNodeDomain.remove("b");
        }

        if (whiteInColumn == (column.size()/2) && currentNodeDomain.contains("w")){
            currentNodeDomain.remove("w");
        }

        if (blackInColumn == (column.size()/2) && currentNodeDomain.contains("b")){
            currentNodeDomain.remove("b");
        }

        return new Pair(currentNodeDomain, true);

    }


    public boolean updateVariableDomainRule3(ArrayList<ArrayList<ArrayList<String>>> domain){
        int n = domain.size();
        ArrayList<ArrayList<ArrayList<String>>> domainsCopy = copyDomain(domain);
        Set<String> rows = new HashSet<String>();
        Set<String> columns = new HashSet<String>();


        //check row
        for(int i=0 ; i<n ; i++){
            String stringRow = "";
            ArrayList<ArrayList<String>> row = getRow(domain, i);
            int whiteInRow = getValueNumberInSeries(row, "W");
            int blackInRow = getValueNumberInSeries(row, "B");
            if (whiteInRow + blackInRow == n){
                stringRow = makeStringSeries(row);
                rows.add(stringRow);
            }
        }

        //check column
        for(int i=0 ; i<n ; i++){
            String stringColumn = "";
            ArrayList<ArrayList<String>> column = getColumn(domain, i);
            int whiteInColumn = getValueNumberInSeries(column, "W");
            int blackInColumn = getValueNumberInSeries(column, "B");
            if (whiteInColumn + blackInColumn == n){
                stringColumn = makeStringSeries(column);
                columns.add(stringColumn);
            }
        }
        
        if (rows.size()!= n || columns.size()!=n){
            return false;
        }

        return true;
        
    }


    public ArrayList<String> updateVariableDomainRule2(ArrayList<ArrayList<ArrayList<String>>> domain, int x, int y){
        ArrayList<ArrayList<ArrayList<String>>> domainsCopy = copyDomain(domain);
        ArrayList<ArrayList<String>> currentRow = getRow(domain, x);
        ArrayList<ArrayList<String>> currentColumn = getColumn(domain, y);

        ArrayList<String> currentNodeDomain = domainsCopy.get(x).get(y);

        //check row
        checkDuplication(y, currentRow, currentNodeDomain);

        //check column
        checkDuplication(x, currentColumn, currentNodeDomain);


        return currentNodeDomain;


    }


    public void checkDuplication(int index, ArrayList<ArrayList<String>> series, ArrayList<String> currentNodeDomain){
        if (index >= 2) {
            ArrayList<String> list1 = series.get(index-1);
            String element1 = list1.get(0);
            ArrayList<String> list2 = series.get(index-2);
            String element2 = list2.get(0);
            if ((element1.equals(element2) && isValidToChange(element1)) && (currentNodeDomain.contains(element1.toLowerCase()))){
                //remove from domain
                //domainsCopy.get(x).get(y).remove(element);
            }

        }

        if (index >= 1 && index <= series.size()-2){
            ArrayList<String> list1 = series.get(index-1);
            String element1 = list1.get(0);
            ArrayList<String> list2 = series.get(index+1);
            String element2 = list2.get(0);
            if ((element1.equals(element2) && isValidToChange(element1)) && (currentNodeDomain.contains(element1.toLowerCase()))) {
                // remove from domain
            }
        }


        if (index <= series.size() - 3){
            ArrayList<String> list1 = series.get(index+1);
            String element1 = list1.get(0);
            ArrayList<String> list2 = series.get(index+2);
            String element2 = list2.get(0);
            if ((element1.equals(element2) && isValidToChange(element1)) && (currentNodeDomain.contains(element1.toLowerCase()))) {
                // remove from domain
            }
        }
    }


    public boolean isValidToChange(String value) {
        if (value.equals("W") || value.equals("B")){
            return true;
        }
        return false;
    }


    public String makeStringSeries(ArrayList<ArrayList<String>> series) {
        int n = series.size();
        String stringSeries = "";
        for(int i=0 ; i<n ; i++) {
            ArrayList<String> domain = series.get(i);
            for(int j=0 ; j<domain.size() ; j++){
                stringSeries += domain.get(j);
            }
        }
        return stringSeries;
    }


    public Pair updateVariableDomain(ArrayList<ArrayList<ArrayList<String>>> domain, int x, int y) {
        
        ArrayList<ArrayList<ArrayList<String>>> domainCopy = copyDomain(domain);
        boolean flag;

        Pair rule1 = updateVariableDomainRule1(domainCopy, x, y);
        if (!rule1.flag()){
            return new Pair(false, null);
        }

        domainCopy.get(x).set(y, rule1.getVariableDomain());
        

        flag = updateVariableDomainRule3(domainCopy);
        if(!flag) {
            return new Pair(false, null);
        }

        ArrayList<String> rule3 = updateVariableDomainRule2(domainCopy, x, y);
        if (rule3.size() == 0) {
            return new Pair(flag, null);
        }
        domainCopy.get(x).set(y, rule3);
        

        return new Pair(true, domainCopy);
        

    }


    public ArrayList<ArrayList<String>> copyBoard(ArrayList<ArrayList<String>> cBoard) {
        ArrayList<ArrayList<String>> res = new ArrayList<>();
        int n = cBoard.size();
        for (int i = 0; i < n; i++) {
            res.add(new ArrayList<>(Arrays.asList(new String[n])));
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res.get(i).set(j, cBoard.get(i).get(j));
            }
        }

        return res;
    }

    public ArrayList<ArrayList<ArrayList<String>>> copyDomain(ArrayList<ArrayList<ArrayList<String>>> currentDomain) {
        ArrayList<ArrayList<ArrayList<String>>> res = new ArrayList<>();
        int n = currentDomain.size();
        for (int i = 0; i < n; i++) {
            ArrayList<ArrayList<String>> row = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                row.add(new ArrayList<>(Arrays.asList("", "")));
            }
            res.add(row);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < currentDomain.get(i).get(j).size(); k++) {
                    res.get(i).get(j).set(k, currentDomain.get(i).get(j).get(k));
                }
            }
        }

        return res;
    }


    public ArrayList<ArrayList<String>> getRow(ArrayList<ArrayList<ArrayList<String>>> domain, int index){
        ArrayList<ArrayList<String>> row = domain.get(index);
        return row;
    }

    public ArrayList<ArrayList<String>> getColumn(ArrayList<ArrayList<ArrayList<String>>> domain, int index) {
        ArrayList<ArrayList<String>> column = new ArrayList<>();
        int n = domain.size();
        for(int i=0 ; i<n ; i++){
            ArrayList<String> firstElement = getRow(domain ,i).get(index);
            column.add(firstElement);
        }
        return column;
    }

    public int getValueNumberInSeries(ArrayList<ArrayList<String>> series, String value) {
        int n = series.size();
        int count = 0;
        for(int i=0 ; i<n ; i++){
            ArrayList<String> domain = series.get(i);
            for(int j=0; j<domain.size() ; j++){
                if (domain.get(j).equals(value)){
                    count++;
                }
            }
        }
        return count;

    }

    
    
}
