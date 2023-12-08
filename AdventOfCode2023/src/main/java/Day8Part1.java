import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day8Part1 {

    static class Node{

        String element;
        String leftElement;
        String rightElement;

        Node(String e, String le, String re){
            element=e;
            leftElement=le;
            rightElement=re;
        }

        public String getElement() {
            return element;
        }
    }

    static Map<Character,Integer> map = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        System.out.println("Enter directions: ");
        String directions = sc.nextLine();
        System.out.println("Enter all network strings: ");
        List<Node> network = new ArrayList<>();
        String str= sc.nextLine();
        while (!str.equalsIgnoreCase("end")){
            network.add(getCamelCard(str));
            str= sc.nextLine();
        }
        int ans = process(network,directions);
        System.out.println("total steps needed : " + ans);
    }

    private static Node getCamelCard(String str) {
//        AAA = (BBB, CCC)
        return new Node(str.substring(0,3),str.substring(7,10),str.substring(12,15));
    }

    private static int process(List<Node> network,String directions) {
        int steps =0;
        int size = directions.length();
        Map<String, Node> map = network.stream().collect(Collectors.toMap(Node::getElement, Function.identity()));
        Node node = map.get("AAA");

        while (!node.getElement().equals("ZZZ")){
            char move = directions.charAt(steps%size);
            node = map.get(move=='L'?node.leftElement:node.rightElement);
            steps++;
        }

        return steps;
    }

}

/*\
sample input
RL

AAA = (BBB, CCC)
BBB = (DDD, EEE)
CCC = (ZZZ, GGG)
DDD = (DDD, DDD)
EEE = (EEE, EEE)
GGG = (GGG, GGG)
ZZZ = (ZZZ, ZZZ)
end

ans - 2
* */