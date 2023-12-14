import java.util.*;


public class Day14Part2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter all tile strings: ");
        String str = sc.nextLine();
        List<char[]> lavaIslands = new ArrayList<>();

        while (!str.equalsIgnoreCase("end")) {
            lavaIslands.add(str.toCharArray());
            str = sc.nextLine();
        }

        int ans = process(lavaIslands);
        System.out.println("Total load: " + ans);
    }

    private static int process(List<char[]> lavaIsland) {
        int ans=0;
        Map<String,Integer> seen = new HashMap<>();
        int idx =0;
        seen.put(getStr(lavaIsland),idx);
        while (true){
            cycle(lavaIsland);
            idx++;
            String str = getStr(lavaIsland);
            if (seen.containsKey(str)){
                break;
            }
            seen.put(str,idx);
        }
        Integer start = seen.get(getStr(lavaIsland));
        Integer length = idx- start;

        int end = (1_000_000_000 -start) % length + start;
        List<String> land = getLavaLand(seen,end,lavaIsland.size());

        return calculateLoad(land);
    }

    private static int calculateLoad(List<String> lavaIsland) {
        int load =0;
        int n = lavaIsland.size();
        for (int j=0;j<lavaIsland.get(0).length();j++){
            for (int i = 0; i < n; i++) {
                if (lavaIsland.get(i).charAt(j) == 'O') {
                    load += (n - i);
                }
            }
        }
        return load;
    }

    private static List<String> getLavaLand(Map<String, Integer> seen, int end, int size) {
        String str = null;
        for (Map.Entry<String,Integer> entry : seen.entrySet()){
            if(entry.getValue()==end){
                str = entry.getKey();
                break;
            }
        }
        List<String> lavaLand = new ArrayList<>();
        for (int i=0;i+size<=str.length();i=i+size){
            lavaLand.add(str.substring(i,i+size));
        }
        return lavaLand;
    }

    private static void cycle(List<char[]> lavaIsland) {

        processAllCol(lavaIsland);
        rotateClockwise(lavaIsland);
        processAllCol(lavaIsland);
        rotateClockwise(lavaIsland);
        processAllCol(lavaIsland);
        rotateClockwise(lavaIsland);
        processAllCol(lavaIsland);
        rotateClockwise(lavaIsland);
    }
/*
12
43

transpose
14
23
flip horizontal
41
32
 */
    private static void rotateClockwise(List<char[]> lavaIsland) {
//        transpose
        for (int i=0;i<lavaIsland.size();i++){
            for (int j=0;j<i;j++){
                char temp = lavaIsland.get(i)[j];
                lavaIsland.get(i)[j] = lavaIsland.get(j)[i];
                lavaIsland.get(j)[i] = temp;
            }
        }
//        flip horizontal
        for (int i=0;i<lavaIsland.size();i++){
            int l = 0,r=lavaIsland.get(0).length-1;
            while (l<r){
                char temp = lavaIsland.get(i)[l];
                lavaIsland.get(i)[l] = lavaIsland.get(i)[r];
                lavaIsland.get(i)[r] = temp;
                l++;
                r--;
            }
        }
    }

    private static String getStr(List<char[]> lavaIsland) {
        StringBuilder sb = new StringBuilder();
        for (char[] str : lavaIsland){
            sb.append(str);
        }
        return sb.toString();
    }

    private static void processAllCol(List<char[]> lavaIsland) {
//        int load =0;
        int n = lavaIsland.size();
        for (int j=0;j<lavaIsland.get(0).length;j++){
            int open =0;
            for (int i = 0; i < n; i++) {
                if (lavaIsland.get(i)[j] == '.') {
                    continue;
                } else if (lavaIsland.get(i)[j] == 'O') {
//                    load += (n - open);
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

ans - 64
* */