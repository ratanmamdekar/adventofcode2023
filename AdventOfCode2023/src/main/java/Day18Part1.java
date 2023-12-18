import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Day18Part1 {

    enum Direction{
        R(0,1),
        L(0,-1),
        U(-1,0),
        D(1,0);

        final int di,dj;

        Direction(int i, int j) {
            di=i;
            dj=j;
        }
    }

    static Map<String,Character> leftRightMap = new HashMap<>();

    public static void main(String[] args) {
        try {
            File myObj = new File("/Users/ratanmamdekar/IdeaProjects/AdventOfCode2023/src/main/java/day18.txt");
            System.out.println("Reading file: ");
            Scanner myReader = new Scanner(myObj);
            List<String> digPlan = new ArrayList<>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                digPlan.add(data);
            }
            myReader.close();
            int ans = process(digPlan);
            System.out.println("cubic meters of lava: " + ans);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Enter all tile strings: ");
//        String str = sc.nextLine();
//        List<String> tiles = new ArrayList<>();
//
//        while (!str.equalsIgnoreCase("end")) {
//            tiles.add(str);
//            str = sc.nextLine();
//        }
//
//        int ans = process(tiles);
//        System.out.println("Total energized tiles: " + ans);
    }

    private static int process(List<String> digPlan) {
        int maxI=0,maxJ=0,minI=0,minJ=0,i=0,j=0;
        for (String dig : digPlan){
            String[] split = dig.split(" ");
            int magnitude = Integer.parseInt(split[1]);
            Direction direction = Direction.valueOf(split[0]);
            i+=direction.di*magnitude;
            j+=direction.dj*magnitude;

            maxI=Math.max(maxI,i);
            maxJ=Math.max(maxJ ,j);
            minI=Math.min(minI,i);
            minJ=Math.min(minJ,j);
        }

        List<char[]> lavaLand = extracted(maxJ-minJ, maxI-minI);
        int cubicMetersOfLava = 0;
        fillMap();
        i=-minI;j=-minJ;
        Set<Integer> exteriorPoints= new HashSet<>();
        Direction prevDirection = Direction.U;
        for (String dig : digPlan){
            System.out.println(dig);
            String[] split = dig.split(" ");
            int magnitude = Integer.parseInt(split[1]);
            cubicMetersOfLava+=magnitude;
            Direction direction = Direction.valueOf(split[0]);
            fillLavaLand(lavaLand,i,j,leftRightMap.get(prevDirection.name()+direction.name()),direction,magnitude,exteriorPoints);
            i+=direction.di*magnitude;
            j+=direction.dj*magnitude;
            prevDirection=direction;
        }

        for(char[] row:lavaLand){
            System.out.println(Arrays.toString(row));
        }
        cubicMetersOfLava+=interiorArea(lavaLand,exteriorPoints);
        return cubicMetersOfLava;
    }

    private static int interiorArea(List<char[]> lavaLand, Set<Integer> exteriorPoints) {
        Set<Character> charactersThatInterestTheRay = Set.of('|', '7', 'F');
//        Set<Character> charactersThatInterestTheRay = Set.of('|', 'L', 'J');

        int row = lavaLand.size();
        int col = lavaLand.get(0).length;
        int[][] grid = new int[row][col];

        for (int i=0;i<row;i++){
            int counter=0;
            for (int j=col-1;j>=0;j--){
                if (exteriorPoints.contains(i*col+j) && charactersThatInterestTheRay.contains(lavaLand.get(i)[j])) counter++;
                grid[i][j]=counter;
            }
        }

        int ans =0;
        for (int i=0;i<row;i++){
            for (int j=col-1;j>=0;j--){
                if (!exteriorPoints.contains(i*col+j) && grid[i][j]%2==1){
                    ans++;
//                    grid[i][j]=-1; //just to mark it if you want to see the enclosed ones
                }

            }
        }

//        for (int[] rows : grid){
//            System.out.println(Arrays.toString(rows));
//        }
        return ans;
    }

    private static void fillMap() {
        leftRightMap.put("UR",'F');
        leftRightMap.put("LD",'F');
        leftRightMap.put("RD",'7');
        leftRightMap.put("UL",'7');
        leftRightMap.put("DL",'J');
        leftRightMap.put("RU",'J');
        leftRightMap.put("LU",'L');
        leftRightMap.put("DR",'L');
    }

    private static void fillLavaLand(List<char[]> lavaLand, int i, int j, Character starting, Direction direction,
                                     int magnitude, Set<Integer> exteriorPoints) {
        int col = lavaLand.get(0).length;
        lavaLand.get(i)[j] = starting;
        exteriorPoints.add(i*col+j);
        char ch ='-';
        if(direction.equals(Direction.D) || direction.equals(Direction.U)){
            ch='|';
        }
        for (int idx =1;idx<magnitude;idx++){
            i+=direction.di;
            j+=direction.dj;
            lavaLand.get(i)[j] = ch;
            exteriorPoints.add(i*col+j);
        }

    }

    private static List<char []>  extracted(int maxJ, int maxI) {
        List<char []> lavaLand = new ArrayList<>();
        char[] row = new char[maxJ+1];
        Arrays.fill(row,'.');
        for (int i=0; i< maxI+1; i++){
            lavaLand.add(Arrays.copyOf(row,row.length));
        }
        return lavaLand;
    }


}