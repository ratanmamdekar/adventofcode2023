import java.util.*;

public class Day13Part1 {

    static Map<Character,int[]> moves;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter all tile strings: ");
        String str = sc.nextLine();
        List<String> lavaIslands = new ArrayList<>();

        while (!str.equalsIgnoreCase("end")) {
            lavaIslands.add(str);
            str = sc.nextLine();
        }

        int ans = process(lavaIslands);
        System.out.println("Summary all of your notes: " + ans);
    }

    private static int process(List<String> lavaIslands) {
        List<String> lavaIsland = new ArrayList<>();
        int ans=0;
        for (String string : lavaIslands){
            if(string.isBlank()){
                int temp = getSummary(lavaIsland);
                System.out.println("temp: "+temp);
                lavaIsland = new ArrayList<>();
                ans+=temp;
            }else {
                lavaIsland.add(string);
            }
        }

        int temp = getSummary(lavaIsland);
        System.out.println("temp: "+temp);
        ans+=temp;

        return ans;
    }

    private static int getSummary(List<String> lavaIsland) {
        //check if symmetry is along row
        int rowMirror = getRowMirror(lavaIsland);
        if(rowMirror!=-1){
            return rowMirror*100;
        }

        //check if symmetry is along col
        return getColMirror(lavaIsland);
    }

    private static int getRowMirror(List<String> lavaIsland) {
        for (int i=0;i<lavaIsland.size()-1;i++){
            if (symmetryAlongRow(lavaIsland,i)){
                return i+1;
            }
        }
        return -1;
    }

    private static boolean symmetryAlongRow(List<String> lavaIsland, int idx) {
        for (int i1=idx,i2=idx+1;i1>=0 && i2<lavaIsland.size();i1--, i2++){
            if (!lavaIsland.get(i1).equals(lavaIsland.get(i2)))
                return false;
        }
        return true;
    }

    private static int getColMirror(List<String> lavaIsland) {
        for (int j=0;j<lavaIsland.get(0).length();j++){
            if (symmetryAlongCol(lavaIsland,j)){
                return j+1;
            }
        }
        return -1;
    }

    private static boolean symmetryAlongCol(List<String> lavaIsland, int idx) {
        for (int j1=idx,j2=idx+1;j1>=0 && j2<lavaIsland.get(0).length();j1--, j2++){
            for (String string : lavaIsland) {
                if (string.charAt(j1) != string.charAt(j2))
                    return false;
            }
        }
        return true;
    }

}

/*\
sample input
#.##..##.
..#.##.#.
##......#
##......#
..#.##.#.
..##..##.
#.#.##.#.

#...##..#
#....#..#
..##..###
#####.##.
#####.##.
..##..###
#....#..#
end

ans - 405
* */