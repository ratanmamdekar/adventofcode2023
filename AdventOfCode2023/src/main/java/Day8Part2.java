import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day8Part2 {

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
        long ans = process(network,directions);
        System.out.println("total steps needed : " + ans);
    }

    private static Node getCamelCard(String str) {
//        AAA = (BBB, CCC)
        return new Node(str.substring(0,3),str.substring(7,10),str.substring(12,15));
    }

    private static long process(List<Node> network,String directions) {
        Map<String, Node> map = network.stream().collect(Collectors.toMap(Node::getElement, Function.identity()));
        List<Node> nodes = getStartingNodes(network);
        List<Integer> steps = new ArrayList<>();
        for (Node node: nodes){
            steps.add(getSteps(node,map,directions));
        }

        return lcm_of_array_elements(steps.toArray(new Integer[0]));
    }

    private static Integer getSteps(Node node, Map<String, Node> map, String directions) {
        int steps =0;
        int size = directions.length();
        while (!(node.getElement().charAt(2)=='Z')){
            char move = directions.charAt(steps%size);
            node = map.get(move=='L'?node.leftElement:node.rightElement);
            steps++;
        }

        return steps;
    }

    // if you try to simulate all starting nodes then it'll take forever(ans is range of 10^13).
    // Instead, get all steps individually and then take LCM of all of those
    private static boolean allNodesAtZ(List<Node> nodes) {
        return nodes.stream().allMatch(node -> node.element.charAt(2)=='Z');
    }

    private static List<Node> getStartingNodes(List<Node> network) {
        return network.stream().filter(node -> node.element.charAt(2)=='A').collect(Collectors.toList());
    }

    // https://www.geeksforgeeks.org/lcm-of-given-array-elements/
    public static long lcm_of_array_elements(Integer[] element_array)
    {
        long lcm_of_array_elements = 1;
        int divisor = 2;

        while (true) {
            int counter = 0;
            boolean divisible = false;

            for (int i = 0; i < element_array.length; i++) {

                // lcm_of_array_elements (n1, n2, ... 0) = 0.
                // For negative number we convert into
                // positive and calculate lcm_of_array_elements.

                if (element_array[i] == 0) {
                    return 0;
                }
                else if (element_array[i] < 0) {
                    element_array[i] = element_array[i] * (-1);
                }
                if (element_array[i] == 1) {
                    counter++;
                }

                // Divide element_array by devisor if complete
                // division i.e. without remainder then replace
                // number with quotient; used for find next factor
                if (element_array[i] % divisor == 0) {
                    divisible = true;
                    element_array[i] = element_array[i] / divisor;
                }
            }

            // If divisor able to completely divide any number
            // from array multiply with lcm_of_array_elements
            // and store into lcm_of_array_elements and continue
            // to same divisor for next factor finding.
            // else increment divisor
            if (divisible) {
                lcm_of_array_elements = lcm_of_array_elements * divisor;
            }
            else {
                divisor++;
            }

            // Check if all element_array is 1 indicate
            // we found all factors and terminate while loop.
            if (counter == element_array.length) {
                return lcm_of_array_elements;
            }
        }
    }
}

/*\
sample input
LR

11A = (11B, XXX)
11B = (XXX, 11Z)
11Z = (11B, XXX)
22A = (22B, XXX)
22B = (22C, 22C)
22C = (22Z, 22Z)
22Z = (22B, 22B)
XXX = (XXX, XXX)
end

ans - 6
* */