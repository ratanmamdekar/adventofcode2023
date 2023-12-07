import java.util.*;

public class Day7Part2 {

    static class CamelCard{
        String hand;
        Integer bid;

        CamelCard(String hand, String bid){
            this.hand=hand;
            this.bid=Integer.parseInt(bid);
        }
    }

    static Map<Character,Integer> map = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in); //System.in is a standard input stream
        System.out.println("Enter all strings: ");
        List<CamelCard> camelCards = new ArrayList<>();
        String str= sc.nextLine();
        while (!str.equalsIgnoreCase("end")){
            camelCards.add(getCamelCard(str));
            str= sc.nextLine();
        }
        int ans = process(camelCards);
        System.out.println("total winnings : " + ans);
    }

    private static CamelCard getCamelCard(String str) {
        String[] split = str.split(" ");
        return new CamelCard(split[0],split[1]);
    }

    private static int process(List<CamelCard> camelCards) {
        List<List<CamelCard>> camelCardsOrderedByStrength = getCamelCardsOrderedByStrength(camelCards);

        sortCamelCards(camelCardsOrderedByStrength);

        List<CamelCard> camelCardStream = camelCardsOrderedByStrength.stream().flatMap(Collection::stream).toList();
        int rank =1,ans =0;
        for(CamelCard camelCard : camelCardStream) {
            ans += camelCard.bid * rank++;
        }
        return ans;
    }

    private static void sortCamelCards(List<List<CamelCard>> camelCardsOrderedByStrength) {
        map.put('A',14);
        map.put('K',13);
        map.put('Q',12);
        map.put('T',10);
        map.put('9',9);
        map.put('8',8);
        map.put('7',7);
        map.put('6',6);
        map.put('5',5);
        map.put('4',4);
        map.put('3',3);
        map.put('2',2);
        map.put('J',1);
        camelCardsOrderedByStrength.forEach(Day7Part2::sortForSameStrength);
    }

    private static void sortForSameStrength(List<CamelCard> camelCards) {
        camelCards.sort((c1,c2)->compare(c1.hand,c2.hand));
    }

    private static int compare(String c1, String c2) {
        for(int i=0;i<5;i++){
            if(c1.charAt(i)==c2.charAt(i)){
                continue;
            }
            return Integer.compare(map.get(c1.charAt(i)),map.get(c2.charAt(i)));
        }
        return 0;
    }

    private static List<List<CamelCard>> getCamelCardsOrderedByStrength(List<CamelCard> camelCards) {
        List<List<CamelCard>> camelCardsOrderedByStrength = new ArrayList<>();
        for(int i=0;i<7;i++) camelCardsOrderedByStrength.add(new ArrayList<>());

        camelCards.forEach(camelCard -> camelCardsOrderedByStrength.get(getStrength(camelCard)).add(camelCard));

        return camelCardsOrderedByStrength;
    }

    private static int getStrength(CamelCard camelCard) {
        Map<Character,Integer> map = new HashMap<>();
        for (Character ch : camelCard.hand.toCharArray()){
            map.merge(ch,1, Integer::sum);
        }

        if(isFiveOfAKind(map)){
            return 6;
        }
        if(isFourOfAKind(map)){
            return 5;
        }
        if(isFullHouse(map)){
            return 4;
        }
        if(isThreeOfAKind(map)){
            return 3;
        }
        if(isTwoPair(map)){
            return 2;
        }
        if(isOnePair(map)){
            return 1;
        }
        return 0;
    }

    private static boolean isOnePair(Map<Character,Integer> map) {
        int countOfPairs=0;
        for(Integer count : map.values()){
            if(count==2){
                countOfPairs++;
            }
        }
        return countOfPairs==1 || map.containsKey('J');
    }

    //there is no point of checking twoPair with Js. It will automatically become something of higher strength
    private static boolean isTwoPair(Map<Character,Integer> map) {
        int countOfPairs=0;
        for(Integer count : map.values()){
            if(count==2){
                countOfPairs++;
            }
        }
        return countOfPairs==2 ;
    }

    private static boolean isThreeOfAKind(Map<Character,Integer> map) {
        for(Map.Entry<Character,Integer> entry : map.entrySet()){
            if(entry.getKey()!='J' && entry.getValue()+map.getOrDefault('J',0) == 3){
                return true;
            }
            if (entry.getKey()=='J' && entry.getValue()==3){
                return true; // won't ever be executed as this makes five/four of a kind
            }
        }
        return false;
    }

    private static boolean isFullHouse(Map<Character,Integer> map) {
        return map.size()==2 || (map.containsKey('J') && map.size()==3);
    }

    private static boolean isFourOfAKind(Map<Character,Integer> map) {
        for(Map.Entry<Character,Integer> entry : map.entrySet()){
            if(entry.getKey()!='J' && entry.getValue()+map.getOrDefault('J',0) == 4){
                return true;
            }
            if (entry.getKey()=='J' && entry.getValue()==4){
                return true; // won't ever be executed as this makes five of a kind
            }
        }
        return false;
    }

    private static boolean isFiveOfAKind(Map<Character,Integer> map) {
        return map.size()==1 || (map.containsKey('J') && map.size()==2);
    }

}

/*\
sample input
32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483
end

ans - 5905
* */
