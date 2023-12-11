import java.util.*;
import java.util.stream.Collectors;

public class Day11Part1And2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter all tile strings: ");
        String str = sc.nextLine();
        List<List<String>> image = new ArrayList<>();
        long ans = 0;
        while (!str.equalsIgnoreCase("end")) {
            image.add(Arrays.stream(str.split("")).collect(Collectors.toList()));
            str = sc.nextLine();
        }

        ans += process(image);
        System.out.println("sum of these Manhattan lengths : " + ans);
    }

    private static long process(List<List<String>> image) {
        Set<Integer> emptyRow = new HashSet<>();
        for (int i=0;i<image.size();i++){
            if(!containsGalaxy(image,i,i,0,image.get(0).size()-1)){
                emptyRow.add(i);
            }
        }

        Set<Integer> emptyCol = new HashSet<>();
        for (int j=0;j<image.get(0).size();j++){
            if(!containsGalaxy(image,0, image.size()-1, j,j)){
                emptyCol.add(j);
            }
        }

        List<Integer> indI = new ArrayList<>();
        List<Integer> indJ = new ArrayList<>();

        fill(image,indI,indJ,emptyRow,emptyCol);

        long ans = 0;
        ans+=sumPairs(indI);
        ans+=sumPairs(indJ);
        return ans;
    }

    private static void fill(List<List<String>> image, List<Integer> indI, List<Integer> indJ,Set<Integer> emptyRow ,Set<Integer> emptyCol ) {

        for (int i=0;i<image.size();i++){
            int colOffset =0;
            for (int j=0;j<image.get(0).size();j++){
                if (emptyCol.contains(j)){
//                    colOffset+=1; // part 1
                    colOffset+=1000000-1; // part 2 -- 1 row has become 1000000, therefore new rows = 1000000-1
                }
                if (image.get(i).get(j).equals("#")){
                    indJ.add(j+colOffset);
                }
            }
        }

        for (int j=0;j<image.get(0).size();j++){
            int rowOffset =0;
            for (int i=0;i<image.size();i++){
                if (emptyRow.contains(i)){
//                    rowOffset+=1; // part 1
                    rowOffset+=1000000-1; // part 2 -- 1 row has become 1000000, therefore new rows = 1000000-1
                }
                if (image.get(i).get(j).equals("#")){
                    indI.add(i+rowOffset);
                }
            }
        }


    }

    private static boolean containsGalaxy(List<List<String>> image, int is, int ie, int js, int je) {
        for (int i=is;i<=ie;i++){
            for (int j=js;j<=je;j++){
                if (image.get(i).get(j).equals("#")){
                    return true;
                }
            }
        }
        return false;
    }

//    https://www.geeksforgeeks.org/sum-absolute-differences-pairs-given-array/
    static long sumPairs(List<Integer> arr) {
        // sorting the array
        Collections.sort(arr);

        int n = arr.size();
        // Initialising the variable
        // to store the sum
        long sum = 0;
        // Iterating through each element
        // and adding it i times and
        // subtracting it (n-1-i) times
        for(long i = 0; i < n; i++) {
            sum += i*arr.get((int)i) - (n-1-i)*arr.get((int)i);
        }

        return sum;
    }
}
/*\
sample input
...#......
.......#..
#.........
..........
......#...
.#........
.........#
..........
.......#..
#...#.....
end

ans part1 - 374
part2
10x 1030
100x 8410
* */