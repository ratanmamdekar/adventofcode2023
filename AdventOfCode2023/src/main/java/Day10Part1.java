import java.util.*;

public class Day10Part1 {

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
        System.out.println("furthest tile number : " + ans);
    }

    private static int process(List<String> tiles) {
        int[] curr = findS(tiles);
        int[] nxt = findNextPipe(tiles,curr);
        int col = tiles.get(0).length();
        moves = getMoveMap();
        int loopSize = 1;
        Set<Integer> loopPos= new HashSet<>();
        loopPos.add(curr[0]*col+curr[1]);
        loopPos.add(nxt[0]*col+nxt[1]);
        while (!isNextS(nxt,tiles)){
            loopSize++;

            makeMove(curr,nxt,tiles);
            loopPos.add(nxt[0]*col+nxt[1]);
        }
        System.out.println("loopPos.size():"+loopPos.size());

        return loopSize/2;
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
.|..|.L7.|.
.L--J..L-J.
...........
end

ans - 23
* */
