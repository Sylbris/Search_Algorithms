import java.util.*;

/**
 * will contain our board.
 */
public class SmallBoard implements Comparable<SmallBoard>{

    Marble [][] CurrentState;
    SmallBoard PreviousState;
    int CostToPreviousState;
    String PreviousStateString;
    HashMap<Marble, ArrayList<Location>> MarbleMap;
    int Distance_from_goal = -1;
    int Distance_from_start;
    boolean ManhattanDistCalc =false;
    String Mark="";
    final int ID;
    static int Key = 0;
    /**
     * Gets an input of the gaming board as a string, and converts it to our gaming board.
     * @param inputMatrix
     */
    public SmallBoard(Marble [][] inputMatrix, SmallBoard previousState, int costToPreviousState){
        CurrentState = inputMatrix;
        PreviousState = previousState;
        CostToPreviousState = costToPreviousState;

        MarbleMap = new HashMap<>();
        CreateMarbleMap();
        if(previousState != null)
            Distance_from_start = costToPreviousState + previousState.Distance_from_start;
        else {
            Distance_from_start = costToPreviousState;
        }
        ID = Key++;
    }

    /**
     * Create marble map
     */
    public void CreateMarbleMap(){
        for(int i=0;i< CurrentState.length;i++){
            for(int j=0;j<CurrentState[0].length;j++){
                if(CurrentState[i][j].color != '_') {
                    Marble m = new Marble(CurrentState[i][j]);
                    Location l = new Location(i, j);
                    if (MarbleMap.get(m) == null) {
                        MarbleMap.put(m, new ArrayList<>());
                    }
                    MarbleMap.get(m).add(l);
                }
            }
        }
    }
    /**
     * return a list of all operators allowed on a specific board.
     * each time we see _ in our board we have between 2 and 4 moves.
     *
     * @return
     */
    public List<SmallBoard> allowedOperators(){
        List<SmallBoard> OperatorList = new ArrayList<>();
        for(int i=0; i < CurrentState.length; i++){
            for(int j=0; j < CurrentState[0].length; j++){
                // We found an empty slot.
                if(CurrentState[i][j].color == '_'){
                    if(i-1>=0 && !(CurrentState[i - 1][j].color == '_')) {
                        // Create our new board, and swap the positions.
                        Marble [][] downMatrix = deepCopyMatrix(CurrentState);
                        downMatrix[i][j] = CurrentState[i - 1][j];
                        downMatrix[i - 1][j] = CurrentState[i][j];

                        // Create string move.
                        String move = CreateMoveString(i-1, j, CurrentState[i - 1][j], i, j);

                        // Check that action is allowed (Can't perform action and then its inverse)
                        if(PreviousState == null || !Arrays.deepEquals(downMatrix, PreviousState.CurrentState)) {
                            SmallBoard downMove = new SmallBoard(downMatrix, this, downMatrix[i][j].cost);
                            downMove.PreviousStateString = move;
                            OperatorList.add(downMove);
                        }

                    }
                    if(i+1 < CurrentState.length && !(CurrentState[i + 1][j].color == '_')) {
                        // Create our new board, and swap the positions.
                        Marble [][] upMatrix = deepCopyMatrix(CurrentState);
                        upMatrix[i][j] = CurrentState[i + 1][j];
                        upMatrix[i + 1][j] = CurrentState[i][j];

                        // Create string move.
                        String move = CreateMoveString(i+1, j, CurrentState[i + 1][j], i, j);

                        // Check that action is allowed (Can't perform action and then its inverse)
                        if(PreviousState == null || !Arrays.deepEquals(upMatrix, PreviousState.CurrentState)) {
                            SmallBoard upMove = new SmallBoard(upMatrix, this, upMatrix[i][j].cost);
                            upMove.PreviousStateString = move;
                            OperatorList.add(upMove);
                        }

                    }
                    if(j+1 < CurrentState[0].length && !(CurrentState[i][j + 1].color == '_')) {
                        // Create our new board, and swap the positions.
                        Marble [][] rightMatrix = deepCopyMatrix(CurrentState);
                        rightMatrix[i][j] = CurrentState[i][j + 1];
                        rightMatrix[i][j + 1] = CurrentState[i][j];

                        // Create string move.
                        String move = CreateMoveString(i, j+1, CurrentState[i][j + 1], i, j);

                        // Check that action is allowed (Can't perform action and then its inverse)
                        if(PreviousState == null || !Arrays.deepEquals(rightMatrix, PreviousState.CurrentState)) {
                            SmallBoard rightMove = new SmallBoard(rightMatrix, this, rightMatrix[i][j].cost);
                            rightMove.PreviousStateString = move;
                            OperatorList.add(rightMove);
                        }

                    }
                    if(j-1 >= 0 && !(CurrentState[i][j - 1].color == '_')) {
                        // Create our new board, and swap the positions.
                        Marble [][] leftMatrix = deepCopyMatrix(CurrentState);
                        leftMatrix[i][j] = CurrentState[i][j - 1];
                        leftMatrix[i][j - 1] = CurrentState[i][j];

                        // Create string move.
                        String move = CreateMoveString(i, j-1, CurrentState[i][j-1], i, j);

                        // Check that action is allowed (Can't perform action and then its inverse)
                        if(PreviousState == null || !Arrays.deepEquals(leftMatrix, PreviousState.CurrentState)) {
                            SmallBoard leftMove = new SmallBoard(leftMatrix, this, leftMatrix[i][j].cost);
                            leftMove.PreviousStateString = move;
                            OperatorList.add(leftMove);
                        }
                    }
                    //System.out.println(" Size is: "+ OperatorList);
                }
            }
        }
        return OperatorList;
    }

    /**
     * Perform a deep copy for a matrix.
     * @param matrix
     * @return
     */
    public Marble[][] deepCopyMatrix(Marble [][] matrix){
        int rows = matrix.length;
        int cols = matrix[0].length;
        Marble [][] matrixCopy = new Marble[rows][cols];

        for(int i =0 ;i< rows; i++){
            for(int j=0 ; j<cols; j++){
                matrixCopy[i][j] = new Marble(matrix[i][j]);
            }
        }
        return matrixCopy;
    }

    @Override
    public String toString() {
        return "SmallBoard : " +
                Arrays.deepToString(CurrentState);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmallBoard that = (SmallBoard) o;
        return Arrays.deepEquals(CurrentState, that.CurrentState);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(CurrentState);
    }

    /**
     * Create a simple string for a move.
     * @param from_i
     * @param from_j
     * @param m
     * @param to_i
     * @param to_j
     * @return
     */
    public String CreateMoveString(int from_i,int from_j, Marble m, int to_i, int to_j){
        String move = "("+(from_i+1)+","+(from_j+1)+"):"+m.color+":("+(to_i+1)+","+(to_j+1)+")";
        return move;
    }

    /**
     * Get manhattan distance between boards.
     * @param otherSM
     * @return
     */
    public int ManhattanDistance2(SmallBoard otherSM){
        int min_sum = 0;
        //Go over each entry in board.
        for (Map.Entry<Marble, ArrayList<Location>> entry : MarbleMap.entrySet()) {
            Marble m = entry.getKey();
            ArrayList<Location> value = entry.getValue();
            LocationAlgo locationAlgo = new LocationAlgo();
            min_sum += locationAlgo.createDistMatrix(value, otherSM.MarbleMap.get(m), m.cost);

        }
        Distance_from_goal = min_sum;
        return min_sum;
    }
    /**
     * Get manhattan distance between boards.
     * @param otherSM
     * @return
     */
    public int ManhattanDistance(SmallBoard otherSM){
        int min_sum = 0;
        //Go over each entry in board.
        for (Map.Entry<Marble, ArrayList<Location>> entry : MarbleMap.entrySet()) {
            Marble m = entry.getKey();
            ArrayList<Location> value = entry.getValue();
            int min = Integer.MAX_VALUE;
            for(int i=0; i< value.size(); i++){

                min_sum+= value.get(i).ManhattanDistance(otherSM.MarbleMap.get(m).get(i), m.cost);
                //System.out.println(min_sum);
            }
        }
        Distance_from_goal = min_sum;
        return min_sum;
    }
    public int DistanceAndHuristics(){
        return Distance_from_start + Distance_from_goal;
    }

    @Override
    public int compareTo(SmallBoard o) {
        if(DistanceAndHuristics()  < o.DistanceAndHuristics()){
            return -1;
        }
        else if(DistanceAndHuristics() > DistanceAndHuristics()){
            return 1;
        }
        else{
            if(ID < o.ID){
                return -1;
            }
            else{
                return 1;
            }
        }
    }

    public int MinDistanceToGoal(List<SmallBoard> sm){
        int minimum = Integer.MAX_VALUE;
        for (int i = 1; i < sm.size(); i++) {
            if (minimum > sm.get(i).DistanceAndHuristics())
                minimum = sm.get(i).DistanceAndHuristics();
        }
        return minimum;
    }
}
