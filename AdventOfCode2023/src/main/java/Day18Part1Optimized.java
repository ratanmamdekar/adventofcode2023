import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

//https://www.youtube.com/watch?v=bGWK76_e-LM
//https://en.wikipedia.org/wiki/Shoelace_formula
//https://en.wikipedia.org/wiki/Pick%27s_theorem
public class Day18Part1Optimized {

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
            long ans = process(digPlan);
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

    private static long process(List<String> digPlan) {
        List<int[]> points = new ArrayList<>();
        int i=0,j=0,boundaryPoints=0;
        points.add(new int[]{i,j});
        for (String dig : digPlan){
            String[] split = dig.split(" ");
            int magnitude = Integer.parseInt(split[1]);
            boundaryPoints+=magnitude;
            Direction direction = Direction.valueOf(split[0]);
            i+=direction.di*magnitude;
            j+=direction.dj*magnitude;

            points.add(new int[]{i,j});
        }
        
        long shoeLaceArea = getShoeLaceArea(points);
        System.out.println("shoeLaceArea:"+shoeLaceArea);
        System.out.println("boundaryPoints:"+boundaryPoints);
        long interiorPoints = shoeLaceArea - boundaryPoints/2 + 1;
        System.out.println("interiorPoints:"+interiorPoints);
        return boundaryPoints+interiorPoints;
    }

    private static long getShoeLaceArea(List<int[]> points) {
        long area =0;
        int size = points.size();
        for (int i=0;i<size;i++){
            area += (long) points.get(i)[0] * (points.get((i+1)%size)[1]-points.get((i-1+size)%size)[1]);
        }

        return Math.abs(area)/2;
    }
}