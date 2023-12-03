import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day2 {

    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in); //System.in is a standard input stream
        System.out.println("Enter a string: ");
        String str= sc.nextLine();
        int ans = 0;
        while (!str.equalsIgnoreCase("end")){
            int num =extracted(str);
            ans += num;
            System.out.println("gameNumber:"+num);
            str= sc.nextLine();
        }
        System.out.println("sum of games:" + ans);
    }

    private static int extracted(String str) {
//        String str = "Game 1: 2 red, 2 green; 6 red, 3 green; 2 red, 1 green, 2 blue; 1 red";
        String[] game = str.split(":");
        Integer gameNumber = Integer.parseInt(game[0].split(" ")[1]);
        System.out.println(gameNumber);
        String[] shows = game[1].split(";");
        Map<String,Integer> map = new HashMap<>();
        for (String show : shows){
            String[] split = show.trim().split(", ");
            for (String s : split){
                int count = Integer.parseInt(s.trim().split(" ")[0]);
                String colour = s.trim().split(" ")[1];
                System.out.println(colour+"+"+count);
                Integer existingCount = map.getOrDefault(colour, 0);
                map.put(colour,Math.max(existingCount,count));
            }
        }
        System.out.println(map);
        //part 1 - return game number if its valid
//        if (map.getOrDefault("red",0)>12){
//            return 0;
//        }
//        if (map.getOrDefault("blue",0)>14){
//            return 0;
//        }
//        if (map.getOrDefault("green",0)>13){
//            return 0;
//        }

        //part 2 - return product of the three
        return map.getOrDefault("red",0)*map.getOrDefault("blue",0)*map.getOrDefault("green",0);
    }
}

/*\
sample input
Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
end

* */