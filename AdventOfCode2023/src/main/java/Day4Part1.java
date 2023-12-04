import java.util.*;
import java.util.stream.Collectors;

public class Day4Part1 {

    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in); //System.in is a standard input stream
        System.out.println("Enter all strings: ");
        List<String> scratchcards = new ArrayList<>();
        String str= sc.nextLine();
        while (!str.equalsIgnoreCase("end")){
            scratchcards.add(str);
            str= sc.nextLine();
        }
        int ans = processAllCards(scratchcards);
        System.out.println("total points : " + ans);
    }

    private static int processAllCards(List<String> scratchcards) {
        int ans =0;

        for( String scratchcard:scratchcards){
            int matches = getMatches(scratchcard);
            if(matches>0){
                double points = Math.pow(2, matches - 1);
                System.out.println("points: "+points);
                ans+=points;
            }
        }

        return ans;
    }

    private static int getMatches(String scratchcard) {
        int matches=0;
        String numbersStr = scratchcard.split(":")[1];
        String[] numbersSplit = numbersStr.split(" & ");
        Set<String> winningNumbers = Arrays.stream(numbersSplit[0].trim().split(" ")).map(String::trim).filter(str->!str.isBlank()).collect(Collectors.toSet());
        return Arrays.stream(numbersSplit[1].trim().split(" ")).map(String::trim).filter(str->!str.isBlank()).filter(winningNumbers::contains)
                .toList().size();
    }
}

/*\
sample input
Card 1: 41 48 83 86 17 & 83 86  6 31 17  9 48 53
Card 2: 13 32 20 16 61 & 61 30 68 82 17 32 24 19
Card 3:  1 21 53 59 44 & 69 82 63 72 16 21 14  1
Card 4: 41 92 73 84 69 & 59 84 76 51 58  5 54 83
Card 5: 87 83 26 28 32 & 88 30 70 12 93 22 82 36
Card 6: 31 18 13 56 72 & 74 77 10 23 35 67 36 11
end


ans - 4361
* */