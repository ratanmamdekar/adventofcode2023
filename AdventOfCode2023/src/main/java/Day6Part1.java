import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day6Part1 {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in); //System.in is a standard input stream
        System.out.println("Enter strings: ");
        String str= sc.nextLine();
        List<List<Integer>> inputs = new ArrayList<>();
        while (!str.equals("end")){
            List<String> list = Arrays.stream(str.split(" ")).filter(s -> !s.isBlank()).toList();//.mapToInt(Integer::parseInt).collect()
            inputs.add(list.stream().filter(s->Character.isDigit(s.charAt(0))).map(Integer::parseInt).toList());
            str= sc.nextLine();
        }
        int ans =1;
        for(int i=0;i<inputs.get(0).size();i++){
            ans *= numberOfWays(inputs.get(0).get(i),inputs.get(1).get(i));
        }

        System.out.println("multiply of numbers : " + ans);
    }

    private static int numberOfWays(Integer time, Integer dist) {
        int numberOfWays =0;
        for(int t =0;t<=time;t++){
            if(t*(time-t)>dist) numberOfWays++;
        }
        System.out.println("time: "+time+", dist: "+dist+", numberOfWays: "+numberOfWays);
        return numberOfWays;
    }


}

/*\
sample input(enter with each prompt)
Time:      7  15   30
Distance:  9  40  200
end

time: 7, dist: 9, numberOfWays: 4
time: 15, dist: 40, numberOfWays: 8
time: 30, dist: 200, numberOfWays: 9
multiply of numbers : 288
* */