import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day1 {
    static Map<String,Integer> map;
    public static void main(String[] args) {

        map = new HashMap<>();
        map.put("zero",0);
        map.put("two",2);
        map.put("one",1);
        map.put("three",3);
        map.put("four",4);
        map.put("five",5);
        map.put("six",6);
        map.put("seven",7);
        map.put("eight",8);
        map.put("nine",9);

        Scanner sc= new Scanner(System.in); //System.in is a standard input stream
        System.out.println("Enter a string: ");
        String str= sc.nextLine();
        int ans = 0;
        while (!str.equalsIgnoreCase("end")){
            int num =getNum(str);
            ans += num;
            System.out.println(num);
            str= sc.nextLine();
        }

        System.out.println(ans);
    }

    // Part 1 - form number from numeric characters
    // Part 2 - form number from numeric characters and word string
    private static int getNum(String str) {
        int num =0;

        for(int i=0;i<str.length();i++){
            char c = str.charAt(i);

            if(Character.isDigit(c)){
                num += Integer.parseInt(c+"") * 10;
                break;
            }
            int word = getNumber(str,i);
            if(word!=-1){
                num += word *10;
                break;
            }
        }
        for(int i=str.length()-1;i>=0;i--){
            char c = str.charAt(i);
            if(Character.isDigit(c)){
                num += Integer.parseInt(c+"") ;
                break;
            }
            int word = getNumber(str,i);
            if(word!=-1){
                num += word;
                break;
            }
        }

        return num;
    }

    private static int getNumber(String str, int i) {
        String substring = str.substring(i, Math.min(i + 5, str.length()));
        for (Map.Entry<String,Integer> entry : map.entrySet()){
            if (substring.startsWith(entry.getKey())) return entry.getValue();
        }
        return -1;
    }
}

/*\
sample input
two1nine
eightwothree
abcone2threexyz
end
* */
