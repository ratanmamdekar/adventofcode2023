import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Day16Part1 {

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
            int ans = process(tiles);
            System.out.println("Total energized tiles: " + ans);
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

    private static int process(List<String> lavaIsland) {
        Set<String> visitedTiles = new HashSet<>();

        System.out.println("starting");
        helper(0,0,Direction.RIGHT,lavaIsland,visitedTiles);

        visitedTiles = new HashSet<>();
        System.out.println("starting");
        helper(0,0,Direction.RIGHT,lavaIsland,visitedTiles);
//        printEnergizedTiles(lavaIsland, visitedTiles);
        return findUniqueVisitedTiles(visitedTiles, lavaIsland.get(0).length());
    }

    private static void printEnergizedTiles(List<char[]> lavaIsland, Set<String> visitedTiles) {
        for (String tile: visitedTiles){
            String[] split = tile.split("#");
            lavaIsland.get(Integer.parseInt(split[0]))[Integer.parseInt(split[1])]='#';
        }
        for (char[] row : lavaIsland){
            System.out.println(Arrays.toString(row));
        }
    }

    private static int findUniqueVisitedTiles(Set<String> visitedTiles, int length) {
        Set<Integer> uniqueVisitedTiles = new HashSet<>();
        for (String tile : visitedTiles){
            String[] split = tile.split("#");
            uniqueVisitedTiles.add(Integer.parseInt(split[0])*length + Integer.parseInt(split[1]));
        }
        return uniqueVisitedTiles.size();
    }

    private static void helper(int i, int j, Direction direction, List<String> lavaIsland, Set<String> visitedTiles) {
        String key = i+"#"+j+"#"+direction;
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

ans - 46
* */