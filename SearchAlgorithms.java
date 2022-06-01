import java.util.*;

public class SearchAlgorithms {
    static int numOfNodes = 0;
    int cost = Integer.MAX_VALUE;
    LinkedHashMap<SmallBoard, SmallBoard> openList = new LinkedHashMap<>();
    boolean PrintOpen = false;
    /**
     * Prints openlist
     */
    public void PrintOpen(Hashtable<SmallBoard, SmallBoard> openList){
        System.out.println("OPEN LIST : ");
        for (Map.Entry<SmallBoard, SmallBoard> entry : openList.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
    /**
     * Breadth first search using openlist, closedlist
     * @param InitialState
     * @param GoalState
     * @param PrintOpen
     * @return
     */
    public String BFS(SmallBoard InitialState, SmallBoard GoalState, boolean PrintOpen){
        // Create our openlist, closedlist and queue.
        Hashtable<SmallBoard, SmallBoard> openList = new Hashtable<>();
        Hashtable<SmallBoard, SmallBoard> closedList = new Hashtable<>();
        LinkedHashMap <SmallBoard, SmallBoard> closedList2 = new LinkedHashMap<>();
        Queue<SmallBoard> queue = new LinkedList<>();
        numOfNodes = 0;

        queue.add(InitialState);
        openList.put(InitialState, InitialState);
        numOfNodes++;
        while(!queue.isEmpty()){
            //Printing openlist
            if(PrintOpen){
                PrintOpen(openList);
            }

            SmallBoard front = queue.poll();
            closedList.put(front, front);
            closedList2.put(front, front);

            for(SmallBoard operator: front.allowedOperators()){
                numOfNodes++;
                //Check that the node isn't in the openlist and the closedlist.
                if(openList.get(operator)==null && closedList.get(operator) == null) {
                    if (operator.equals(GoalState)) {
                        //System.out.println(closedList2.toString());
                        return path(operator, InitialState);
                    }
                    queue.add(operator);
                    openList.put(operator, operator);
                }
            }
        }
        return "no path\nNum: " + numOfNodes + "\nCost: inf\n";
    }

    /**
     * Deepening Iterative Depth First Search
     * @param InitialState
     * @param GoalState
     * @param PrintOpen
     * @return
     */
    public String DFID(SmallBoard InitialState, SmallBoard GoalState, boolean PrintOpen){
        //init Vars
        String result;
        numOfNodes = 0;
        this.PrintOpen = PrintOpen;

        //We start at depth one.
        for(int depth = 1; depth < Integer.MAX_VALUE; depth++){
            Hashtable<SmallBoard, SmallBoard> loopAvoidance = new Hashtable<>();
            result = Limited_DFS(InitialState, InitialState, GoalState, depth, loopAvoidance);
            if(result != "cutoff"){
                return result;
            }
        }
        return "no path\nNum: " + numOfNodes + "\nCost: inf\n";
    }

    /**
     *
     * @param CurrentState
     * @param GoalState
     * @param limit
     * @return
     */
    public String Limited_DFS(SmallBoard InitialState, SmallBoard CurrentState, SmallBoard GoalState,
                              int limit, Hashtable H){
        //if we reach target stop.
        if(CurrentState.equals(GoalState)) {
            return path(CurrentState, InitialState);
        }
        else if(limit == 0){
            return "cutoff";
        }
        else {
            //Printing openlist
            if(PrintOpen){
                PrintOpen(H);
            }
            H.put(CurrentState, CurrentState);
            boolean isCutoff = false;
            for(SmallBoard operator: CurrentState.allowedOperators()){
                numOfNodes++;
                // loop avoidance
                if(H.get(operator)==null){
                    String result = Limited_DFS(InitialState, operator, GoalState, limit-1, H);
                    if(result.equals("cutoff")) {
                        isCutoff = true;
                    }
                    else if(!result.equals("fail")){
                        return result;
                    }
                }
            }
            H.remove(CurrentState);
            if(isCutoff) {
                return "cutoff";
            }
            else {
                return "fail";
            }
        }


    }

    /**
     * An algorithm that uses a heuristic function to calculate the best node for expansion.
     * Here we choose the manhattan distance.
     * so f(n) = h(n) + g(n)
     * for each node we measure its distance from the starting node ,
     * we add that node manhattan distance to the goal.
     *
     * https://en.wikipedia.org/wiki/A*_search_algorithm
     *
     * @param InitialState
     * @param GoalState
     * @param PrintOpen
     * @return
     */
    public String aStar(SmallBoard InitialState, SmallBoard GoalState, boolean PrintOpen){
        numOfNodes = 0;
        // Create our openlist, closedlist and queue.
        Hashtable<SmallBoard, SmallBoard> openList = new Hashtable<>();
        LinkedHashMap<SmallBoard, SmallBoard> closedList = new LinkedHashMap<>();
        PriorityQueue<SmallBoard> priorityQueue =new PriorityQueue<>();

        //Init openlist and priority que.
        priorityQueue.add(InitialState);
        openList.put(InitialState, InitialState);
        numOfNodes++;
        while(!priorityQueue.isEmpty()){
            //Printing openlist
            if(PrintOpen){
                PrintOpen(openList);
            }
            SmallBoard current = priorityQueue.poll();
            if(current.equals(GoalState)){
                //System.out.println(openList.get(GoalState).PreviousState);
                return path(openList.get(GoalState), InitialState);
            }
            closedList.put(current, current);
            for(SmallBoard operator: current.allowedOperators()){
                numOfNodes++;
                //if we haven't calc manhattan distance yet.
                if(operator.Distance_from_goal == -1) {
                    operator.ManhattanDistance2(GoalState);
                }
                if(closedList.get(operator) == null && openList.get(operator) == null){
                    priorityQueue.add(operator);
                    openList.put(operator, operator);
                }
                else if(openList.get(operator).Distance_from_start > operator.Distance_from_start){
                    priorityQueue.remove(openList.get(operator));
                    openList.remove(openList.get(operator));

                    priorityQueue.add(operator);
                    openList.put(operator,operator);
                }
            }
        }
        return "no path\nNum: " + numOfNodes + "\nCost: inf\n";
    }

    /**
     * A star iterative deepening.
     */
    public String IdaStar(SmallBoard InitialState, SmallBoard GoalState , boolean printOpen){
        numOfNodes = 0;
        //Create our stack and hashtable.
        Stack<SmallBoard> stack = new Stack<>();
        Hashtable<SmallBoard, SmallBoard> h = new Hashtable<>();

        //h(t)
        int t = InitialState.ManhattanDistance2(GoalState);

        while (t != Integer.MAX_VALUE){

            int minF = Integer.MAX_VALUE;
            stack.push(InitialState);
            h.put(InitialState, InitialState);

            while(!stack.isEmpty()){
                //Printing openlist
                if(printOpen){
                    PrintOpen(h);
                }
                SmallBoard current = stack.pop();
                if(current.Mark.equals("out")){
                    h.remove(current);
                }
                else {
                    current.Mark = "out";
                    stack.push(current);
                    for(SmallBoard operator:current.allowedOperators()){
                        numOfNodes++;
                        if(operator.Distance_from_goal == -1) {
                            operator.ManhattanDistance2(GoalState);
                        }
                        // f(operator) > t
                        if(operator.DistanceAndHuristics() > t){
                            //System.out.println(t);
                            minF = Math.min(minF, operator.DistanceAndHuristics());
                            continue;
                        }
                        if(h.get(operator)!=null && h.get(operator).Mark.equals("out")){
                            continue;
                        }
                        if(h.get(operator)!=null && !h.get(operator).Mark.equals("out")){
                            if(h.get(operator).DistanceAndHuristics() > operator.DistanceAndHuristics()){
                                stack.remove(operator);
                                h.remove(operator);
                            }
                            else {
                                continue;
                            }
                        }
                        if(operator.equals(GoalState)){
                            return path(operator, InitialState);
                        }

                        stack.push(operator);
                        h.put(operator, operator);
                    }
                }
            }
            InitialState.Mark = "in";
            t = minF;

        }
        return "no path\nNum: " + numOfNodes + "\nCost: inf\n";
    }

    /**
     * DFBNB algorithm.
     * @param InitialState
     * @param GoalState
     */
    public String dfnBNB(SmallBoard InitialState, SmallBoard GoalState, boolean printOpen){
        numOfNodes = 0;

        //Used to sort boards.
        Comparator<SmallBoard> boardComparator = (sm1, sm2) -> {
            if (sm1.DistanceAndHuristics() < sm2.DistanceAndHuristics()) {
                return -1;
            } else if (sm1.DistanceAndHuristics() > sm2.DistanceAndHuristics()) {
                return 1;
            } else {
                return Integer.compare(sm1.ID, sm2.ID);
            }
        };

        //Create our stack and hashtable.
        Stack<SmallBoard> stack = new Stack<>();
        Hashtable<SmallBoard, SmallBoard> h = new Hashtable<>();
        //insert initial node.
        stack.push(InitialState);
        h.put(InitialState, InitialState);

        String result = "no path";
        int t = Integer.MAX_VALUE;

        while(!stack.isEmpty()){
            //print openlist
            if(printOpen){
                PrintOpen(h);
            }
            SmallBoard current = stack.pop();
            if(current.Mark.equals("out")){
                h.remove(current);
            }
            else {
                current.Mark = "out";
                stack.push(current);
                List<SmallBoard> allowedOperators = current.allowedOperators();
                for(SmallBoard sm:allowedOperators){
                    if(sm.Distance_from_goal == -1){
                        sm.ManhattanDistance2(GoalState);
                    }
                }
                allowedOperators.sort(boardComparator);

                for(int i=0; i < allowedOperators.size(); i++){
                    numOfNodes++;
                    SmallBoard operator = allowedOperators.get(i);

                    if(operator.Distance_from_goal == -1){
                        operator.ManhattanDistance(GoalState);
                    }
                    if(operator.DistanceAndHuristics() >= t){
                        //remove g and all the nodes after it from the list.
                        allowedOperators.subList(i, allowedOperators.size()).clear();
                    }
                    else if(h.get(operator)!=null && h.get(operator).Mark.equals("out")){
                        //remove operator from allowedOperators.
                        allowedOperators.remove(operator);
                    }
                    else if(h.get(operator)!=null && !h.get(operator).Mark.equals("out")){
                        if(h.get(operator).DistanceAndHuristics() <= operator.DistanceAndHuristics()){
                            //remove operator from allowedOperators.
                            allowedOperators.remove(operator);
                        }
                        else {
                            //remove operator from stack & h.
                            stack.remove(operator);
                            h.remove(operator);
                        }
                    }
                    else if(operator.equals(GoalState)){
                        t = operator.DistanceAndHuristics();
                        result = path(operator, InitialState);
                        //remove operator and all the nodes after it , from allowedOperators.
                        allowedOperators.subList(i, allowedOperators.size()).clear();
                    }
                }
                //Insert allowedOperators in reverse order to stack & h.
                Collections.reverse(allowedOperators);
                for (SmallBoard sm : allowedOperators) {
                    stack.push(sm);
                    h.put(sm, sm);
                }
            }
        }
        if(result.equals("no path")){
            result = "no path\nNum: " + numOfNodes + "\nCost: inf\n";
        }
        return result;
    }
    /**
     * Find the path between 2 given Board games.
     * We use a search algorithm to determine such a path exists,
     * then we can use this function to determine it.
     *
     * @param GoalState
     * @param InitialState
     * @return
     */
    public String path(SmallBoard GoalState, SmallBoard InitialState){
        List<SmallBoard> pathList = new ArrayList<>();
        cost = GoalState.Distance_from_start;

        pathList.add(GoalState);
        int cost = GoalState.CostToPreviousState;
        while(!GoalState.equals(InitialState)){
            GoalState = GoalState.PreviousState;
            pathList.add(GoalState);
            cost += GoalState.CostToPreviousState;
        }

        //Reverse the list in order to create the path.
        Collections.reverse(pathList);
        String ans="";
        for(int i=0; i<pathList.size();i++) {
            if (pathList.get(i).PreviousStateString != null) {
                ans += pathList.get(i).PreviousStateString;
                if (i < pathList.size() - 1) {
                    ans += "--";
                }
            }
        }
        //System.out.println("Num: " + numOfNodes);
        //System.out.println("Cost: " + cost);
        return ans + "\nNum: " + numOfNodes + "\n" + "Cost: " + cost + "\n";
    }


}
