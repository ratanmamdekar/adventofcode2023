import java.util.*;

public class Day10Part2 {

//  logic inspired from https://tildes.net/~comp.advent_of_code/1cs0/day_10
    static Map<Character,int[]> moves;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter all tile strings: ");
        String str = sc.nextLine();
        List<String> tiles = new ArrayList<>();
        int ans = 0;
        while (!str.equalsIgnoreCase("end")) {
            tiles.add(str);
            str = sc.nextLine();
        }

        ans += process(tiles);
        System.out.println("Tile enclosed : " + ans);
    }

    private static int process(List<String> tiles) {
        int[] curr = findS(tiles);
        int[] nxt = findNextPipe(tiles,curr);
        int row = tiles.size();
        int col = tiles.get(0).length();
        moves = getMoveMap();
        Set<Integer> loopPos= new HashSet<>();
        loopPos.add(curr[0]*col+curr[1]);
        loopPos.add(nxt[0]*col+nxt[1]);

        while (!isNextS(nxt,tiles)){
            makeMove(curr,nxt,tiles);
            loopPos.add(nxt[0]*col+nxt[1]);
        }
        System.out.println("loopPos.size():"+loopPos.size());

        int[][] grid = new int[row][col];

        fillSWithPipe(tiles);
        // count only pipes that cross one direction(up or down), and for this to work we need to replace S with correct pipe
        // if you choose '7', 'F' then it is like your ray runs just beneath of middle,
        // that's why it will not intersect with 'L', 'J'

        Set<Character> charactersThatInterestTheRay = Set.of('|', '7', 'F');
//        Set<Character> charactersThatInterestTheRay = Set.of('|', 'L', 'J');
        for (int i=0;i<row;i++){
            int counter=0;
            for (int j=col-1;j>=0;j--){
                if (loopPos.contains(i*col+j) && charactersThatInterestTheRay.contains(tiles.get(i).charAt(j))) counter++;
                grid[i][j]=counter;
            }
        }

        int ans =0;
        for (int i=0;i<row;i++){
            for (int j=col-1;j>=0;j--){
                if (!loopPos.contains(i*col+j) && grid[i][j]%2==1){
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

    private static void fillSWithPipe(List<String> tiles) {
        int[] s = findS(tiles);
        int i=s[0];
        int j=s[1];
        Set<Character> right = Set.of('-', '7', 'J');
        Set<Character> left = Set.of('-', 'L', 'F');
        Set<Character> up = Set.of('|', '7', 'F');
        Set<Character> down = Set.of('|', 'L', 'J');
        if (up.contains(tiles.get(i-1).charAt(j)) && down.contains(tiles.get(i+1).charAt(j))){
            // '|'
            updateSTile(i,j,tiles,'|');
        }
        if (left.contains(tiles.get(i).charAt(j-1)) && right.contains(tiles.get(i).charAt(j+1))){
            // '-'
            updateSTile(i,j,tiles,'-');
        }
        if (up.contains(tiles.get(i-1).charAt(j)) && right.contains(tiles.get(i).charAt(j+1))){
            // 'L'
            updateSTile(i,j,tiles,'L');
        }
        if (up.contains(tiles.get(i-1).charAt(j)) && left.contains(tiles.get(i).charAt(j-1))){
            // 'J'
            updateSTile(i,j,tiles,'J');
        }
        if (left.contains(tiles.get(i).charAt(j-1)) && down.contains(tiles.get(i+1).charAt(j))){
            // '7'
            updateSTile(i,j,tiles,'7');
        }
        if (right.contains(tiles.get(i).charAt(j+1)) && down.contains(tiles.get(i+1).charAt(j))){
            // 'F'
            updateSTile(i,j,tiles,'F');
        }
    }

    private static void updateSTile(int i, int j, List<String> tiles, char c) {
        char[] charArray = tiles.get(i).toCharArray();
        charArray[j]=c;
        tiles.set(i,String.valueOf(charArray));
    }


    private static void makeMove(int[] curr, int[] nxt, List<String> tiles) {
        int[] move = moves.get(tiles.get(nxt[0]).charAt(nxt[1]));

        for(int i=0;i<3;i+=2){
            int prevX = move[i];
            int prevY = move[i+1];
            int nextX = move[(i+2)%4];
            int nextY = move[(i+3)%4];

            if (nxt[0]+prevX == curr[0] && nxt[1]+prevY == curr[1]){
                curr[0]= nxt[0];
                curr[1]= nxt[1];
                nxt[0]+=nextX;
                nxt[1]+=nextY;
            }
        }
    }

    private static boolean isNextS(int[] nxt,List<String> tiles) {
        int[] sTile = findS(tiles);
        return sTile[0]==nxt[0] && sTile[1]==nxt[1];
    }

    private static Map<Character,int[]> getMoveMap() {
        Map<Character,int[]> move = new HashMap<>();

        move.put('|',new int[]{-1,0,1,0});
        move.put('-',new int[]{0,-1,0,1});
        move.put('F',new int[]{1,0,0,1});
        move.put('J',new int[]{-1,0,0,-1});
        move.put('7',new int[]{0,-1,1,0});
        move.put('L',new int[]{-1,0,0,1});

        return move;
    }

    private static int[] findNextPipe(List<String> tiles, int[] curr) {
        int i= curr[0];
        int j= curr[1];

        //up
        if(i-1>=0){
            char ch = tiles.get(i-1).charAt(j);
            if (ch=='|' || ch=='F' || ch=='7')
                return new int[]{i-1,j};
        }
        //down
        if(i+1<tiles.size()){
            char ch = tiles.get(i+1).charAt(j);
            if (ch=='|' || ch=='J' || ch=='L')
                return new int[]{i+1,j};
        }
        //left
        if(j-1>=0){
            char ch = tiles.get(i).charAt(j-1);
            if (ch=='-' || ch=='F' || ch=='L')
                return new int[]{i,j-1};
        }
        //right
        if(j+1<tiles.get(0).length()){
            char ch = tiles.get(i).charAt(j+1);
            if (ch=='|' || ch=='J'|| ch=='7')
                return new int[]{i,j+1};
        }

        return new int[2];
    }

    private static int[] findS(List<String> tiles) {
        for(int i=0;i<tiles.size();i++){
            for (int j=0;j<tiles.get(0).length();j++){
                if (tiles.get(i).charAt(j)=='S'){
                    return new int[]{i,j};
                }
            }
        }

        return new int[2];
    }

}

/*\
sample input
...........
.S-------7.
.|F-----7|.
.||.....||.
.||.....||.
.|L-7.F-J|.
.|..|.|..|.
.L--J.L--J.
...........
end
...........
.F-------7.
.|F-----7|.
.||.....||.
.||.....||.
.|L-7.F-J|.
.|..S.L7.|.
.L--J..L-J.
...........
end


ans - 4,3


--------------------
FF7FSF7F7F7F7F7F---7
L|LJ||||||||||||F--J
FL-7LJLJ||||||LJL-77
F--JF--7||LJLJ7F7FJ-
L---JF-JLJ.||-FJLJJ7
|F|F-JF---7F7-L7L|7|
|FFJF7L7F-JF7|JL---7
7-L-JL7||F7|L7F-7F7|
L.L7LFJ|||||FJL7||LJ
L7JLJL-JLJLJL--JLJ.L
end

ans - 10
* */