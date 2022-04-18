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
        return allAssigned(state) && checkNumberOfCircles(state) && checkIfUnique(state) && checkAdjacency2(state);
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

//        System.out.println("blackInColumn = " + blackInColumn + " " + "blackInRow = " + blackInRow + " " + "whiteInColumn = " + whiteInColumn + " " + "whiteInRow = " + whiteInRow);


        if (whiteInRow > (row.size()/2) || blackInRow > (row.size()/2) ||
            whiteInColumn > (column.size()/2) || blackInColumn > (column.size()/2)) {

                return new Pair(null, false);
        }

        if (whiteInRow == (row.size()/2) && contains(currentNodeDomain, "w")){
            currentNodeDomain.remove("w");
        }

        if (blackInRow == (row.size()/2) && contains(currentNodeDomain, "b")){
            currentNodeDomain.remove("b");
        }

        if (whiteInColumn == (column.size()/2) && contains(currentNodeDomain, "w")){
            currentNodeDomain.remove("w");
        }

        if (blackInColumn == (column.size()/2) && contains(currentNodeDomain, "b")){
            currentNodeDomain.remove("b");
        }

        return new Pair(currentNodeDomain, true);

    }

    public boolean contains(ArrayList<String> currentNodeDomain, String value) {

        int n = currentNodeDomain.size();
        for(int i = 0 ; i<n ; i++){
            if(currentNodeDomain.get(i).equals(value)){
                return true;
            }
        }
        return false;

    }


    public boolean updateVariableDomainRule3(ArrayList<ArrayList<ArrayList<String>>> domain){
        int n = domain.size();
        ArrayList<ArrayList<ArrayList<String>>> domainsCopy = copyDomain(domain);
        ArrayList<String> rows = new ArrayList<>();
        ArrayList<String> columns = new ArrayList<>();
//        Set<String> rows = new HashSet<String>();
//        Set<String> columns = new HashSet<String>();


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

        boolean check = duplicateSeries(rows);
        if(!check) {
            return false;
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

        check = duplicateSeries(columns);

        return check;

    }


    public boolean duplicateSeries(ArrayList<String> series){
        Set<String> list = new HashSet<String>();
        for (String name : series) {
            if (!list.add(name)) {
                return false;
            }
        }
        return true;
    }


//
//    # check columns
//    for i in range(len(board)):
//    column = get_column_from_array(board, i)
//        if not check_duplicate_digit(column):
//            return False
//
//    return True




    public boolean duplication(ArrayList<String> sequence){
        for(int i = 0 ; i < sequence.size()-2 ; i++){
            String ele1 = sequence.get(i);
            String ele2 = sequence.get(i+1);
            String ele3 = sequence.get(i+2);
            if(ele1.equals(ele2) && ele2.equals(ele3)) {
                if(!ele1.equals("E")){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkAdjacency2(State state){
        ArrayList<ArrayList<String>> board = state.getBoard();
        int n = board.size();


        //row
        for(int i=0 ; i<n ; i++){
            ArrayList<String> row = getBRow(board, i);
            if (!duplication(row)){
                return false;
            }
        }

        //column
        for(int i=0 ; i<n ; i++){
            ArrayList<String> row = getBColumn(board, i);
            if (!duplication(row)){
                return false;
            }
        }

        return true;
    }



    public ArrayList<String> updateVariableDomainRule2(ArrayList<ArrayList<ArrayList<String>>> domain, int x, int y){
        ArrayList<ArrayList<ArrayList<String>>> domainsCopy = copyDomain(domain);
        ArrayList<ArrayList<String>> currentRow = getRow(domain, x);
        ArrayList<ArrayList<String>> currentColumn = getColumn(domain, y);
        ArrayList<String> currentNodeDomain = domainsCopy.get(x).get(y);



        //check row

        ArrayList<String> checkedRow = checkDuplication(y, currentRow, currentNodeDomain);

        //check column
        if(!isEmpty(checkedRow)){
            ArrayList<String> checkedColumn = checkDuplication(x, currentColumn, checkedRow);
            return  checkedColumn;
        }


//        System.out.println(checkedColumn + " size : " + isEmpty(checkedColumn));
        return checkedRow;


    }

    public boolean isEmpty(ArrayList<String> list){
        for (String s : list) {
            if (s.equals("w") || s.equals("b") || s.equals("W") || s.equals("B")) {
                return false;
            }
        }
        return true;
    }


    public ArrayList<String> checkDuplication(int index, ArrayList<ArrayList<String>> series, ArrayList<String> currentNodeDomain){
        ArrayList<String> newDomain = currentNodeDomain;

        // 2 ta ghabl
        if (index >= 2) {
            ArrayList<String> list1 = series.get(index-1);
            String element1 = list1.get(0);
            ArrayList<String> list2 = series.get(index-2);
            String element2 = list2.get(0);

            if ((element1.equals(element2) && isFixed(element1)) && (currentNodeDomain.contains(element1.toLowerCase()))){
//                public void removeIndexDomain(int x, int y, String value) {
//                    domain.get(x).get(y).remove(value);
//                }
//                newDomain.remove(index-1);
//                System.out.println("before " + newDomain);
                int toRemove = removeFromDomain(newDomain, element1.toLowerCase());
                if(!isFixed(element1.toLowerCase())){
                    newDomain.remove(toRemove);
                }

//                System.out.println("after " + newDomain);
            }

        }

        if (index >= 1 && index <= series.size()-2){
            ArrayList<String> list1 = series.get(index-1);
            String element1 = list1.get(0);
            ArrayList<String> list2 = series.get(index+1);
            String element2 = list2.get(0);
            if ((element1.equals(element2) && isFixed(element1)) && (newDomain.contains(element1.toLowerCase()))) {
//                newDomain.remove(index-1);
                //newDomain = removeFromDomain(newDomain, element1);
                int toRemove = removeFromDomain(newDomain, element1.toLowerCase());
                if(!isFixed(element1.toLowerCase())){
                    newDomain.remove(toRemove);
                }

            }
        }


        if (index <= series.size() - 3){
            ArrayList<String> list1 = series.get(index+1);
            String element1 = list1.get(0);
            ArrayList<String> list2 = series.get(index+2);
            String element2 = list2.get(0);
            if ((element1.equals(element2) && isFixed(element1)) && (newDomain.contains(element1.toLowerCase()))) {
//                newDomain.remove(index+1);
               // newDomain = removeFromDomain(newDomain, element1);
                int toRemove = removeFromDomain(newDomain, element1.toLowerCase());
                if(!isFixed(element1.toLowerCase())){
                    newDomain.remove(toRemove);
                }

            }
        }

        return newDomain;
    }

    public int removeFromDomain(ArrayList<String> list, String value){
        int index = -1;
        for(int i = 0 ; i <list.size() ; i++){
            if (list.get(i).equals(value)){
                index = i;
            }
        }
        return index;
    }

    public boolean isFixed(String value) {
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
        ArrayList<String> nodeDomain = domainCopy.get(x).get(y);
        boolean isFixed = hasVariable(nodeDomain);

        if(!isFixed){
            Pair rule1 = updateVariableDomainRule1(domainCopy, x, y);
            if (!rule1.flag()){
                return new Pair(false, true);
//                return new Pair(false, null);
            }

            domainCopy.get(x).set(y, rule1.getVariableDomain());
        }


        if (!isFixed){
            boolean rule2 = updateVariableDomainRule3(domainCopy);
            if(!rule2) {
                return new Pair(false, true);
//                return new Pair(false, null);
            }
        }



        ArrayList<String> rule3 = updateVariableDomainRule2(domainCopy, x, y);
        if (isEmpty(rule3)) {
            return new Pair(false, true);
//            return new Pair(false, null);
        }

        domainCopy.get(x).set(y, rule3);


        return new Pair(true, domainCopy);
        

    }

    public boolean hasVariable(ArrayList<String> domain) {

        for (String s : domain) {
            if (s.equals("w") || s.equals("b")) {
                return true;
            }
        }
        return false;
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

    public ArrayList<String> getBRow(ArrayList<ArrayList<String>> board, int index) {
        return board.get(index);
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


    public ArrayList<String> getBColumn(ArrayList<ArrayList<String>> board, int index){
        ArrayList<String> column = new ArrayList<>();
        int n = board.size();
        for(int i=0 ; i<n ; i++){
            ArrayList<String> firstElement = getBRow(board ,i);
            column.add(firstElement.get(index));
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
