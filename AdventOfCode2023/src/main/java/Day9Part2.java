import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day9Part2 {

    static class Node {

        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter all network strings: ");
            String str = sc.nextLine();
            int ans = 0;
            while (!str.equalsIgnoreCase("end")) {
                ans += process(str);
                str = sc.nextLine();
            }
            System.out.println("total sum of these extrapolated values : " + ans);
        }

        private static int process(String str) {
            List<Integer> collect = Arrays.stream(str.split(" ")).map(Integer::parseInt).collect(Collectors.toList());
            List<List<Integer>> diffs = new ArrayList<>();
            diffs.add(collect);

            while (!diffs.get(diffs.size() - 1).stream().allMatch(num -> num == 0)) {
                List<Integer> newList = new ArrayList<>();
                List<Integer> lastList = diffs.get(diffs.size() - 1);
                for (int i = 1; i < lastList.size(); i++) {
                    newList.add(lastList.get(i) - lastList.get(i - 1));
                }
                diffs.add(newList);
            }
            int prediction = 0;
            for (int idx = diffs.size() - 2; idx >= 0; idx--) {
                prediction = diffs.get(idx).get(0) - prediction;
            }


            System.out.println("prediction: " + prediction);
            return prediction;
        }

    }
}

/*\
sample input
0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45
end

ans - 2
* */