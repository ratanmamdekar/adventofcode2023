import java.util.*;


public class Day14Part1 {

    static Map<Character,int[]> moves;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter all tile strings: ");
        String str = sc.nextLine();
        List<char[]> lavaIslands = new ArrayList<>();

        while (!str.equalsIgnoreCase("end")) {
            lavaIslands.add(str.toCharArray());
            str = sc.nextLine();
        }

        int ans = processAllCol(lavaIslands);
        System.out.println("Total load: " + ans);
    }

    private static int processAllCol(List<char[]> lavaIsland) {
        int load =0;
        int n = lavaIsland.size();
        for (int j=0;j<lavaIsland.get(0).length;j++){
            int open =0;
            for (int i = 0; i < n; i++) {
                if (lavaIsland.get(i)[j] == '.') {
                    continue;
                } else if (lavaIsland.get(i)[j] == 'O') {
                    load += (n - open);
                    if (i != open) {
                        lavaIsland.get(i)[j] = '.';
                        lavaIsland.get(open)[j] = 'O';
                    }
                    open++;
                } else if (lavaIsland.get(i)[j] == '#') {
                    open = i + 1;

                }
            }
        }
        return load;
    }

}

/*\
sample input
O....#....
O.OO#....#
.....##...
OO.#O....O
.O.....O#.
O.#..O.#.#
..O..#O..O
.......O..
#....###..
#OO..#....
end

ans - 136
* */