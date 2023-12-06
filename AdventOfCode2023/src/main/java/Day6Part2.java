import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.time.temporal.ChronoField.MILLI_OF_SECOND;
import static java.time.temporal.ChronoField.NANO_OF_SECOND;

public class Day6Part2 {
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
        long start = Instant.now().getLong(MILLI_OF_SECOND);
        Long ans =numberOfWays(getNumFromList(inputs.get(0)), getNumFromList(inputs.get(1)));

        long end = Instant.now().getLong(MILLI_OF_SECOND);
        System.out.println("multiply of numbers : " + ans + ", time taken: " + (end-start));
    }

    private static Long getNumFromList(List<Integer> integers) {
        StringBuilder sb = new StringBuilder();
        for (int num : integers){
            sb.append(num);
        }
        return Long.parseLong(sb.toString());
    }

    private static Long numberOfWays(Long time, Long dist) {
        Long numberOfWays =0L;
        for(long t =0;t<=time;t++){
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

time: 49787980, dist: 298118510661181, numberOfWays: 35865985
multiply of numbers : 35865985, time taken: 270
* */