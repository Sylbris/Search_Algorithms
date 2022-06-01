import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Ex1 {
    public static void main(String[] args) throws IOException {
//        //////////////////////5x5
//        Marble [][] Board =
//                {{new Marble('R'), new Marble('R'), new Marble('R'),  new Marble('R'),  new Marble('_')},
//                {new Marble('B'), new Marble('B'), new Marble('B'), new Marble('_'), new Marble('_')},
//                        {new Marble('G'), new Marble('G'), new Marble('G'), new Marble('G'), new Marble('_')},
//                        {new Marble('_'), new Marble('_'), new Marble('_'), new Marble('_'), new Marble('_')},
//                        {new Marble('_'), new Marble('Y'), new Marble('Y'), new Marble('Y'), new Marble('Y')}};
//
//        Marble [][] Board_Goal =
//                {{new Marble('R'), new Marble('R'), new Marble('R'),  new Marble('R'),  new Marble('_')},
//                        {new Marble('B'), new Marble('B'), new Marble('B'), new Marble('_'), new Marble('_')},
//                        {new Marble('G'), new Marble('G'), new Marble('G'), new Marble('G'), new Marble('_')},
//                        {new Marble('_'), new Marble('_'), new Marble('_'), new Marble('_'), new Marble('_')},
//                        {new Marble('Y'), new Marble('Y'), new Marble('Y'), new Marble('Y'), new Marble('_')}};
//        ////////////////////3x3
////        Marble [][] Board = {{new Marble('R'), new Marble('R'), new Marble('_')},
////                {new Marble('B'), new Marble('B'), new Marble('_')}, {new Marble('G'),
////                new Marble('G'), new Marble('_')}};
////        Marble [][] Board_Goal = {{new Marble('R'), new Marble('R'), new Marble('B')},
////                {new Marble('B'), new Marble('G'), new Marble('_')}, {new Marble('G'),
////                new Marble('_'), new Marble('_')}};
////        Marble [][] Board_Goal = {{new Marble('R'), new Marble('B'), new Marble('B')},
////                {new Marble('_'), new Marble('G'), new Marble('G')}, {new Marble('_'),
////                new Marble('R'), new Marble('_')}};
//        SmallBoard smallBoard = new SmallBoard(Board, null, 0);
//        SmallBoard goalBoard = new SmallBoard(Board_Goal, null, 0);
////        System.out.println(smallBoard.ManhattanDistance2(goalBoard));
//        long start2 = System.currentTimeMillis();
////        Location l1 = new Location(1,1);
////        Location l2 = new Location(1,2);
////
////        Location k1 = new Location(1,2);
////        Location k2 = new Location(2,2);
////        List<Location> first = new ArrayList<>();
////        first.add(l1);
////        first.add(l2);
////
////        List<Location> second = new ArrayList<>();
////        second.add(k1);
////        second.add(k2);
////        LocationAlgo locationAlgo = new LocationAlgo();
////        locationAlgo.createDistMatrix(first, second, 1);
//        SearchAlgorithms searchAlgorithms = new SearchAlgorithms();
//        //System.out.println(searchAlgorithms.BFS(smallBoard, goalBoard, false));
//        //System.out.println(searchAlgorithms.DFID(smallBoard, goalBoard, false));
//        //System.out.println(searchAlgorithms.aStar(smallBoard, goalBoard, false));
//        //System.out.println(searchAlgorithms.IdaStar(smallBoard, goalBoard, false));
//        System.out.println(searchAlgorithms.dfnBNB(smallBoard, goalBoard, false));
//        long end2 = System.currentTimeMillis();
//        System.out.println((end2-start2)*0.001 + " seconds");
//        //System.out.println(searchAlgorithms.BFS(smallBoard, goalBoard, false));
//        //System.out.println(searchAlgorithms.DFID(smallBoard, goalBoard, false));
////        Marble [][] Board_test = {{new Marble('R'), new Marble('R'), new Marble('_')},
////                {new Marble('B'), new Marble('_'), new Marble('B')}, {new Marble('G'),
////                new Marble('G'), new Marble('_')}};
//        //SmallBoard smallBoard_test = new SmallBoard(Board_test, null, 0);
        InputParser inputParser = new InputParser("input.txt");
    }
}
