import java.util.*;
import java.util.stream.Stream;

public class Day12Part2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter all tile strings: ");
        String str = sc.nextLine();

        double ans = 0;

        while (!str.equalsIgnoreCase("end")) {
            double arrangement = process(str);
            ans+=arrangement;
            str = sc.nextLine();
        }
        System.out.println("sum of valid arrangements: " + ans);
    }

    private static double process(String string) {
        String[] split = string.split(" ");
        List<Integer> lengths = getLengths(split[1]);
        String newStr = (split[0]+"?"+split[0]+"?"+split[0]+"?"+split[0]+"?"+split[0]);
        System.out.println(newStr+"+"+lengths);
        long[][] dp = new long[newStr.length()][lengths.size()];
        for(long[] row : dp){
            Arrays.fill(row,-1);
        }
        long arrangement = helper2withDp(0,0,newStr,lengths,dp);
        System.out.println("arrangement:"+arrangement);
        return arrangement;
    }


    public static long helper2withDp(int idx1, int idx2, String s, List<Integer> lengths,long[][] dp){
        if(idx2 >= lengths.size()){
            if (idx1>=s.length())
                return 1;
            return  s.substring(idx1).contains("#")?0:1;
        }
        if(idx1 >= s.length()){
            return 0;
        }

        if(dp[idx1][idx2]!=-1){
            return dp[idx1][idx2];
        }
        if (s.charAt(idx1)=='.') {
            long temp = helper2withDp(idx1 + 1, idx2, s, lengths,dp);

            return dp[idx1][idx2]=temp;
        } else if (s.charAt(idx1)=='#'){
            if (validHashes(s,idx1,lengths.get(idx2))){
                long temp = helper2withDp(idx1 + lengths.get(idx2) + 1, idx2 + 1, s, lengths,dp);

                return dp[idx1][idx2]=temp;
            }else {

                return dp[idx1][idx2]=0;
            }
        } else {
            long temp = helper2withDp(idx1 + 1, idx2, s, lengths,dp);
            if (validHashes(s,idx1,lengths.get(idx2))){
                temp+= helper2withDp(idx1+lengths.get(idx2)+1,idx2+1,s,lengths,dp);
            }

            return dp[idx1][idx2]=temp;
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

    // mathy solution works for ending with . or #
    // unable to find for ending with ? since overlapping answers are now possible
    private static double getArrangements(String charArray, List<Integer> lengths) {
        double arrangements = 0;
        if(charArray.charAt(charArray.length()-1)=='#'){
            long arrangement =helper2(0,0,charArray,lengths);
            arrangements= Math.pow(arrangement,5);
        }else if(charArray.charAt(charArray.length()-1)=='.'){
            long arrangement1 =helper2(0,0,charArray,lengths);
            long arrangement2 =helper2(0,0,getNewCharArray(charArray,0),lengths);
            arrangements= Math.pow(arrangement2,4)*arrangement1;
        }
        // not working;

//        else if(charArray[charArray.length-1]=='?'){
//            long arrangement1 =helper(0,charArray,lengths);
//            long arrangement2 = Math.max(helper(0,getNewCharArray(charArray,charArray.length),lengths),
//                    helper(0,getNewCharArray(charArray,0),lengths));
//            arrangements= Math.pow(arrangement2,4)*arrangement1;
//        }
        return arrangements;
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

    private static String getNewCharArray(String charArray,int idx) {
        return "?"+charArray;
//        char[] newCharArray = new char[charArray.length+1];
//        newCharArray[idx]='?';
//        System.arraycopy(charArray, 0, newCharArray, idx==0?1:0,charArray.length);
//        return newCharArray;
    }

    private static String getKey(int idx, char[] s) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0;i<=idx;i++){
            stringBuilder.append(s[i]);
        }
        return stringBuilder.toString();
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
        List<Integer> list = Arrays.stream(s.split(",")).map(Integer::parseInt).toList();
        return Stream.of(list,list,list,list,list).flatMap(Collection::stream).toList();
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

ans 525152
* */