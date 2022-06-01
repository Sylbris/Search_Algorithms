import java.util.List;

public class LocationAlgo {
    /**
     * Create our initial matrix , to check best pairing between Manhattan Distances.
     * @param l1
     * @param l2
     * @param cost
     * @return
     */
   public int createDistMatrix(List<Location> l1, List<Location> l2, int cost){
       int [][] matrix = new int[l1.size()][l2.size()];

       for(int i=0; i< matrix.length; i++){
           for(int j=0; j< matrix[0].length;j++){
               matrix[i][j] = l1.get(i).ManhattanDistance(l2.get(j), cost);
           }
       }
       HungarianAlgorithm hungarianAlgorithm = new HungarianAlgorithm(matrix);
       return hungarianAlgorithm.findMinDistance();
   }
}
