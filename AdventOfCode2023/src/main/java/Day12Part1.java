import java.util.*;
import java.util.stream.Collectors;

public class Day12Part1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter all tile strings: ");
        String str = sc.nextLine();

        long ans = 0;

        while (!str.equalsIgnoreCase("end")) {
            ans += process(str);
            str = sc.nextLine();
        }
        System.out.println("sum of valid arrangements: " + ans);
    }

    private static long process(String string) {
        String[] split = string.split(" ");
        List<Integer> lengths = getLengths(split[1]);
        long arrangement =helper2(0,0,split[0],lengths);
        System.out.println("arrangement:"+arrangement);
        return arrangement;
    }

    public static long helper2(int idx1,int idx2, String s, List<Integer> lengths){
        if(idx2 >= lengths.size()){
            if (idx1>=s.length())
                return 1;
            return  s.substring(idx1).contains("#")?0:1;
        }
        if(idx1 >= s.length()){
            return 0;
        }

        if (s.charAt(idx1)=='.') {
            return helper2(idx1 + 1, idx2, s, lengths);
        } else if (s.charAt(idx1)=='#'){
            if (validHashes(s,idx1,lengths.get(idx2))){
                return helper2(idx1+lengths.get(idx2)+1,idx2+1,s,lengths);
            }else {
                return 0;
            }
        } else {
            long temp = helper2(idx1 + 1, idx2, s, lengths);
            if (validHashes(s,idx1,lengths.get(idx2))){
                temp+= helper2(idx1+lengths.get(idx2)+1,idx2+1,s,lengths);
            }

            return temp;
        }

    }

    private static boolean validHashes(String s, int idx1, Integer len) {
        try {
            String substring = s.substring(idx1, idx1 + len);
            boolean next = idx1 + len >= s.length() || s.charAt(idx1 + len) != '#';
            return !substring.contains(".") && next;
        }catch (Exception e){
            //index out of bound
            return false;
        }
    }

    private static long helper(int idx, char[] s, List<Integer> lengths) {
        if(idx == s.length){
            if (validArrangements(s,lengths)){
                return 1;
            }else {
                return 0;
            }
        }else {
            if (s[idx]=='?'){
                s[idx] = '.';
                long ans = helper(idx + 1, s, lengths);

                s[idx] = '#';
                ans += helper(idx + 1, s, lengths);

                s[idx] = '?';
                return ans;
            }
            else {
                return helper(idx + 1, s, lengths);
            }
        }
    }

    private static boolean validArrangements(char[] s, List<Integer> lengths) {
        int count =0;
        int idx =0;
        List<Integer> counts = new ArrayList<>();
        while (idx<s.length){
            if(s[idx]=='#'){
                count++;
                idx++;
            }else {
                if(count!=0) counts.add(count);
                count=0;
                idx++;
            }
        }
        if(count!=0) counts.add(count);

        return lengths.equals(counts);
    }

    private static List<Integer> getLengths(String s) {
        return Arrays.stream(s.split(",")).map(Integer::parseInt).toList();
    }

}
/*\
sample input
???.### 1,1,3
.??..??...?##. 1,1,3
?#?#?#?#?#?#?#? 1,3,1,6
????.#...#... 4,1,1
????.######..#####. 1,6,5
?###???????? 3,2,1
end

arrangement:1
arrangement:4
arrangement:1
arrangement:1
arrangement:4
arrangement:10
sum of valid arrangements: 21




?###????????? 3,2,1
?###???????? 3,2,1
end

* */