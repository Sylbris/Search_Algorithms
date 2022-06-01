import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputParser {
    List<String> inputList = new ArrayList<>();
    String algo;
    boolean printOpen;

    Marble [][] initialStateMatrix;
    Marble [][] goalStateMatrix;

    SmallBoard initialBoard;
    SmallBoard goalBoard;

    /**
     * Run our entire process from here.
     * We read - parse - use algo - write result to file.
     * @param path
     * @throws IOException
     */
    public InputParser(String path) throws IOException {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while (line != null) {
                inputList.add(line);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Get algorithm.
        algo = inputList.get(0);
        // to print open list
        String printOpen = inputList.get(1);
        if(printOpen.equals("no open")){
            this.printOpen = false;
        }
        else {
            this.printOpen = true;
        }
        String boardType = inputList.get(2);
        //build our gaming boards.
        if(boardType.equals("small")){
            //initial board + goal board built 3x3
            buildInitialBoard(3, 6, 3, 0);
            buildInitialBoard(7, 10, 3, 1);
        }
        else {
            //initial board + goal board built 5x5
            buildInitialBoard(3, 8, 5, 0);
            buildInitialBoard(9, 14, 5, 1);
        }
        //Build our boards.
        initialBoard = new SmallBoard(initialStateMatrix, null, 0);
        goalBoard = new SmallBoard(goalStateMatrix, null, 0);
        //Run algorithm.
        long start = System.currentTimeMillis();
        String output = runAlgo(initialBoard, goalBoard);
        long end = System.currentTimeMillis();
        DecimalFormat df = new DecimalFormat("#.###");
        String time = df.format((end - start)*0.001) + " seconds";

        //Write output to file.
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
        writer.write(output);
        writer.write(time);

        writer.close();
    }

    /**
     *
     * @param initialState
     * @param goalState
     */
    public String runAlgo(SmallBoard initialState, SmallBoard goalState){
        SearchAlgorithms searchAlgorithms = new SearchAlgorithms();
        String ans = "";
        switch (algo) {
            case "BFS":
                ans = searchAlgorithms.BFS(initialState, goalState, printOpen);
                break;
            case "DFID":
                ans = searchAlgorithms.DFID(initialState, goalState, printOpen);
                break;
            case "A*":
                ans = searchAlgorithms.aStar(initialState, goalState, printOpen);
                break;
            case "IDA*":
                ans = searchAlgorithms.IdaStar(initialState, goalState, printOpen);
                break;
            case "DFBnB":
                ans = searchAlgorithms.dfnBNB(initialState, goalState, printOpen);
                break;
        }
        return ans;
    }

    /**
     * build gaming board.
     * @param startIndex
     * @param endIndex
     * @param boardSize
     * @param bit -indicates if were creating an initial board or goal board.
     */
    public void buildInitialBoard(int startIndex, int endIndex, int boardSize, int bit){
        if(bit == 0){
            initialStateMatrix = new Marble[boardSize][boardSize];
        }
        else {
            goalStateMatrix = new Marble[boardSize][boardSize];
        }
        //////////////////GET board indexes /////////////////////////////////////////////////

        for(int i = startIndex; i < endIndex;i++){
            List<String> lineSplit = Arrays.asList(inputList.get(i).split(","));

                for(int col=0; col<initialStateMatrix.length; col++) {
                    if(bit == 0) {
                        initialStateMatrix[i-startIndex][col] = new Marble(lineSplit.get(col).charAt(0));
                    }
                    else {
                        goalStateMatrix[i-startIndex][col] = new Marble(lineSplit.get(col).charAt(0));
                    }
                }

        }

    }



}
