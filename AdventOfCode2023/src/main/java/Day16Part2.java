import java.io.File;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static java.time.temporal.ChronoField.MILLI_OF_SECOND;


public class Day16Part2 {

    enum Direction{
        RIGHT(0,1),
        LEFT(0,-1),
        UP(-1,0),
        DOWN(1,0);

        final int di,dj;

        Direction(int i, int j) {
            di=i;
            dj=j;
        }
    }

    public static void main(String[] args) {
        try {
            File myObj = new File("/Users/ratanmamdekar/IdeaProjects/AdventOfCode2023/src/main/java/day16.txt");
            System.out.println("Reading file: ");
            Scanner myReader = new Scanner(myObj);
            List<String> tiles = new ArrayList<>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                tiles.add(data);
            }
            myReader.close();
            long start = System.currentTimeMillis();
            int ans = process(tiles);
            long end = System.currentTimeMillis();
            System.out.println("Total energized tiles: " + ans + ", time taken: " + (end-start));
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
//        sc.close();
//        int ans = process(tiles);
//        System.out.println("Total max energized tiles: " + ans);
    }

    private static int process(List<String> lavaIsland) {
        int maxEnergizedTiles =0;
        int row = lavaIsland.size();
        int col = lavaIsland.get(0).length();
        maxEnergizedTiles = Math.max(maxEnergizedTiles,processAllStarterTiles(0,row-1,0,0,
                lavaIsland,Direction.RIGHT));
        maxEnergizedTiles = Math.max(maxEnergizedTiles,processAllStarterTiles(0,row-1,col-1,col-1,
                lavaIsland,Direction.LEFT));
        maxEnergizedTiles = Math.max(maxEnergizedTiles,processAllStarterTiles(0,0,0,col-1,
                lavaIsland,Direction.DOWN));
        maxEnergizedTiles = Math.max(maxEnergizedTiles,processAllStarterTiles(row-1,row-1,0,col-1,
                lavaIsland,Direction.UP));

//        printEnergizedTiles(lavaIsland, visitedTiles);
        return maxEnergizedTiles;
    }

    private static int processAllStarterTiles(int is, int ie, int js, int je,List<String> lavaIsland, Direction direction) {
        int max =0;
        List<Integer> ans = new ArrayList<>();
        List<CompletableFuture<?>> completableFutures = new ArrayList<>();

//        Set<String> visitedTiles = new HashSet<>();
        for (int i=is;i<=ie;i++){
            for (int j=js;j<=je;j++){
//                int finalI = i;
//                int finalJ = j;
//                CompletableFuture<Void> integerCompletableFuture = CompletableFuture.runAsync(() -> {
//                    Set<String> visitedTiles = new HashSet<>();
////                    System.out.println("Starting at " + finalI + "+" + finalJ);
//                    helper(finalI, finalJ, direction, lavaIsland, visitedTiles);
////                    System.out.println("Finished " + finalI + "+" + finalJ);
//                    ans.add(findUniqueVisitedTiles(visitedTiles, lavaIsland.get(0).length()));
//                });
//                completableFutures.add(integerCompletableFuture);
                Set<String> visitedTiles = new HashSet<>();
//                System.out.println("Starting at " + i + "+" + j);
                helper(i, j, direction, lavaIsland, visitedTiles);
//                    System.out.println("Finished " + i + "+" + j);
                max = Math.max(max,findUniqueVisitedTiles(visitedTiles, lavaIsland.get(0).length()));
            }
        }
        CompletableFuture.allOf(completableFutures.toArray(CompletableFuture[]::new)).join();
        return ans.stream().max(Integer::compareTo).orElse(1);
//        return max;
    }

//    private static void printEnergizedTiles(List<char[]> lavaIsland, Set<String> visitedTiles) {
//        for (String tile: visitedTiles){
//            String[] split = tile.split("#");
//            lavaIsland.get(Integer.parseInt(split[0]))[Integer.parseInt(split[1])]='#';
//        }
//        for (char[] row : lavaIsland){
//            System.out.println(Arrays.toString(row));
//        }
//    }

    private static int findUniqueVisitedTiles(Set<String> visitedTiles, int length) {
        Set<Integer> uniqueVisitedTiles = new HashSet<>();
        for (String tile : visitedTiles){
            String[] split = tile.split("#");
            uniqueVisitedTiles.add(Integer.parseInt(split[0])*length + Integer.parseInt(split[1]));
        }
        return uniqueVisitedTiles.size();
    }

    private static void helper(Integer i, Integer j, Direction direction, List<String> lavaIsland, Set<String> visitedTiles) {
        String key = String.join("#",i.toString(),j.toString(),direction.name());//new String(i+"#"+j+"#"+direction);
//        String key = i+"#"+j+"#"+direction;
//        StringBuilder sb = new StringBuilder();
//        sb.append(i);
//        sb.append("#");
//        sb.append(j);
//        sb.append("#");
//        sb.append(direction.name());
//        System.out.println(key);
        if(i<0 || j<0 || i>=lavaIsland.size() || j>=lavaIsland.get(0).length() || visitedTiles.contains(key)){
            return;
        }
        visitedTiles.add(key);

        List<Direction> directions = getNextDirections(lavaIsland.get(i).charAt(j),direction);
        for (Direction nextDirection : directions){
            helper(i+nextDirection.di,j+nextDirection.dj,nextDirection,lavaIsland,visitedTiles);
        }
    }

    private static List<Direction> getNextDirections(char c, Direction currentDirection) {
        List<Direction> directions = new ArrayList<>();

        if (c=='.'){
            directions.add(currentDirection);
        }else if(c=='|'){
            if(Direction.RIGHT.equals(currentDirection) || Direction.LEFT.equals(currentDirection)){
                directions.add(Direction.UP);
                directions.add(Direction.DOWN);
            }else {
                directions.add(currentDirection);
            }
        }else if(c=='-'){
            if(Direction.RIGHT.equals(currentDirection) || Direction.LEFT.equals(currentDirection)){
                directions.add(currentDirection);
            }else {
                directions.add(Direction.LEFT);
                directions.add(Direction.RIGHT);
            }
        }else if(c=='\\'){
            if (Direction.RIGHT.equals(currentDirection)){
                directions.add(Direction.DOWN);
            }else if(Direction.LEFT.equals(currentDirection)){
                directions.add(Direction.UP);
            }else if(Direction.UP.equals(currentDirection)){
                directions.add(Direction.LEFT);
            }else {
                directions.add(Direction.RIGHT);
            }
        }else {             // right slanted mirror
            if (Direction.RIGHT.equals(currentDirection)){
                directions.add(Direction.UP);
            }else if(Direction.LEFT.equals(currentDirection)){
                directions.add(Direction.DOWN);
            }else if(Direction.UP.equals(currentDirection)){
                directions.add(Direction.RIGHT);
            }else {
                directions.add(Direction.LEFT);
            }
        }

        return directions;
    }

}

/*\
sample input
.|...\....
|.-.\.....
.....|-...
........|.
..........
.........\
..../.\\..
.-.-/..|..
.|....-|.\
..//.|....
end

ans - 51
* */