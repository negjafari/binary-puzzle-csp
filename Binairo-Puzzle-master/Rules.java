import java.util.*;


 


public class Rules {

    public int n;

    public Rules(int n){
        this.n = n;
    }
    
    // rule 1
    public boolean checkNumberOfCircles(State state) {
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

    
    public boolean updateVariableDomainRule1(State state, int x, int y){
        //white 0 , black 1

        ArrayList<ArrayList<ArrayList<String>>> domainsCopy = state.copyCurrentDomain();
        ArrayList<String> currentNodeDomain = domainsCopy.get(x).get(y);
        ArrayList<ArrayList<String>> row = state.getRow(x);
        ArrayList<ArrayList<String>> column = state.getColumn(y);

        int whiteInRow = state.getValueNumberInSeries(row, "W");
        int blackInRow = state.getValueNumberInSeries(row, "B");
        int whiteInColumn = state.getValueNumberInSeries(column, "W");
        int blackInColumn = state.getValueNumberInSeries(column, "B");


        if (whiteInRow > (row.size()/2) || blackInRow > (row.size()/2) ||
            whiteInColumn > (column.size()/2) || blackInColumn > (column.size()/2)) {
                return false;
        }

        if (whiteInRow == (row.size()/2) && currentNodeDomain.contains("w")){
            domainsCopy.get(x).get(y).remove("w");
        }

        if (blackInRow == (row.size()/2) && currentNodeDomain.contains("b")){
            domainsCopy.get(x).get(y).remove("b");
        }

        if (whiteInColumn == (column.size()/2) && currentNodeDomain.contains("w")){
            domainsCopy.get(x).get(y).remove("w");
        }

        if (blackInColumn == (column.size()/2) && currentNodeDomain.contains("b")){
            domainsCopy.get(x).get(y).remove("b");
        }

        return true;

    }


    public boolean updateVariableDomainRule3(State state){
        int n = state.getBoardSize();
        ArrayList<ArrayList<ArrayList<String>>> domainsCopy = state.copyCurrentDomain();
        Set<String> rows = new HashSet<String>();
        Set<String> columns = new HashSet<String>();


        //check row
        for(int i=0 ; i<n ; i++){
            String stringRow = "";
            ArrayList<ArrayList<String>> row = state.getRow(i);
            int whiteInRow = state.getValueNumberInSeries(row, "W");
            int blackInRow = state.getValueNumberInSeries(row, "B");
            if (whiteInRow + blackInRow == n){
                stringRow = makeStringSeries(row);
                rows.add(stringRow);
            }
        }

        //check column
        for(int i=0 ; i<n ; i++){
            String stringColumn = "";
            ArrayList<ArrayList<String>> column = state.getColumn(i);
            int whiteInColumn = state.getValueNumberInSeries(column, "W");
            int blackInColumn = state.getValueNumberInSeries(column, "B");
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


    public boolean updateVariableDomainRule2(State state, int x, int y){
        ArrayList<ArrayList<ArrayList<String>>> domainsCopy = state.copyCurrentDomain();
        ArrayList<ArrayList<String>> currentRow = state.getRow(x);
        ArrayList<ArrayList<String>> currentColumn = state.getColumn(y);

        ArrayList<String> currentNodeDomain = domainsCopy.get(x).get(y);

        //check row
        checkDuplication(y, currentRow, currentNodeDomain);

        //check column
        checkDuplication(x, currentColumn, currentNodeDomain);


        return true;
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


    public void updateVariableDomain(State state, int x, int y) {
        boolean flag = true;

        flag = updateVariableDomainRule1(state, x, y);
    
    
        // # check rule 1 -> tedad barabar 0,1
        // flag, new_domain = check_variables_domain_with_rule1(variables_domain_copy, variable_index)
        // # print("after check rule 1 ", new_domain)
        // if not flag:
        //     print("!!! Breaking the rule 1 !!!")
        //     return False, []
    
        // variables_domain_copy[x][y] = new_domain

    }

    
    
}
