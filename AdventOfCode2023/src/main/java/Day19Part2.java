import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;


public class Day19Part2 {

    static int X=0;
    static int M=1;
    static int A=2;
    static int S=3;
    static String STARTING_POINT = "in";
    static Set<String> ENDING_POINT = Set.of("A","R");

    public static void main(String[] args) {
        try {
            File myObj = new File("/Users/ratanmamdekar/IdeaProjects/AdventOfCode2023/src/main/java/day19.txt");
            System.out.println("Reading file: ");
            Scanner myReader = new Scanner(myObj);
            List<String> workflow = new ArrayList<>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.isBlank()){
                    break;
                }
                workflow.add(data);

            }
            List<List<Integer>> ratings = new ArrayList<>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
//                do nothing with rating
//                ratings.add(getRatings(data));
            }
            Map<Character,int[]> ratingRanges= Map.of(
                    'x',new int[]{1,4000},
                    'm',new int[]{1,4000},
                    'a',new int[]{1,4000},
                    's',new int[]{1,4000}
            );
            myReader.close();
            Map<String, String> workflowMap = modify(workflow);
            workflowMap.put("A","A");
            workflowMap.put("R","R");
            long ans = process(workflowMap,"in",ratingRanges);
            System.out.println("Sum of rating numbers: " + ans);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static Map<String, String> modify(List<String> workflows) {
        Map<String,String> workflowMap = new HashMap<>();
        for (String workflow : workflows){
            int idx = workflow.indexOf("{");

            workflowMap.put(workflow.substring(0,idx),workflow.substring(idx+1,workflow.length()-1));
        }
        return workflowMap;
    }

    private static List<Integer> getRatings(String data) {
        String[] split = data.split(",");
        List<Integer> ratings = new ArrayList<>();
        ratings.add(Integer.parseInt(split[0].split("=")[1]));
        ratings.add(Integer.parseInt(split[1].split("=")[1]));
        ratings.add(Integer.parseInt(split[2].split("=")[1]));
        ratings.add(Integer.parseInt(split[3].substring(0,split[3].length()-1).split("=")[1]));

        return ratings;
    }

    private static long process(Map<String,String> workflows, String current, Map<Character,int[]> ratingRanges) {
        if(current.equals("A")){
            long ans =1;
            for (Map.Entry<Character,int[]> entry : ratingRanges.entrySet()){
                ans*= (entry.getValue()[1] - entry.getValue()[0] +1);
            }
            return ans;
        }else if (current.equals("R")){
            return 0;
        }

        long ans=0;
        String workflow = workflows.get(current);
        String[] rules = workflow.split(",");
        boolean notExecutedAny = false;
        for (int i=0;i<rules.length-1;i++){
            String rule = rules[i];
            char ch = rule.charAt(0);
            char op = rule.charAt(1);
            int colon = rule.indexOf(":");
            int n = Integer.parseInt(rule.substring(2,colon));
            String target = rule.substring(colon+1);

            int[] range = ratingRanges.get(ch);
            int[] T = new int[2];
            int[] F = new int[2];
            if(op =='<'){
                T[0]= range[0];
                T[1]= n-1;

                F[0] = n;
                F[1] = range[1];
            }else {
                T[0]= n+1;
                T[1]= range[1];

                F[0] = range[0];
                F[1] = n;
            }

            if (T[0]<=T[1]){
                Map<Character, int[]> copyOfRanges = getCopy(ratingRanges);
                copyOfRanges.put(ch,T);
                ans+= process(workflows,target,copyOfRanges);
                notExecutedAny=true; //??why is this working??
            }
            if (F[0]<=F[1]){
                ratingRanges = getCopy(ratingRanges);
                ratingRanges.put(ch,F);
            }else {
//                notExecutedAny=true;
                break;
            }

        }
        if(notExecutedAny){
            ans+= process(workflows,rules[rules.length-1],ratingRanges);
        }

        return ans;
    }

    private static Map<Character, int[]> getCopy(Map<Character,int[]> ratingRanges) {
        return ratingRanges.entrySet()
                .stream()
                .collect(
                        Collectors
                                .toMap(Map.Entry::getKey,
                                        Map.Entry::getValue));
    }

    private static boolean acceptedRating(List<Integer> rating, Map<String, String> workflows) {
        String currentWork = workflows.get(STARTING_POINT);
//        System.out.println("currentWork:"+currentWork);

        while (!ENDING_POINT.contains(currentWork)){
            String string = processCurrentWork(rating, currentWork);
            if(string.contains(":")){
                currentWork=string;
            }else {
                currentWork = workflows.get(string);
            }

//            System.out.println("currentWork:"+currentWork);
        }

        return currentWork.equals("A");
    }

    private static String processCurrentWork(List<Integer> rating, String currentWork) {
        int colon = currentWork.indexOf(":");
        String predicate = currentWork.substring(0,colon);
        String values = currentWork.substring(colon+1);
        int comma = values.indexOf(",");
        String left = values.substring(0,comma);
        String right = values.substring(comma+1);
        return evaluate(predicate,rating)?left:right;
    }

    private static boolean evaluate(String predicate, List<Integer> rating) {
//        int lessThan = predicate.indexOf("<");
//        if(lessThan!=-1){
//            String[] split = predicate.split("<");
//            return validate(split[0],Integer.parseInt(split[1]),rating,true);
//        }
//
//        String[] split = predicate.split(">");

        return validate(predicate.charAt(0),predicate.charAt(1),Integer.parseInt(predicate.substring(2)),rating);

    }
    
    static Map<Character, BiFunction<Integer,Integer,Boolean>> functionMap = Map.of(
            '<',(a,b)->a<b,'>',(a,b)->a>b);

    private static boolean validate(char str,char operator, int num, List<Integer> rating) {
        int idx = getIdx(str);

        return functionMap.get(operator).apply(rating.get(idx), num);
    }

    private static int getIdx(char ch) {
        if(ch=='x'){
            return X;
        }else if(ch=='m'){
            return M;
        }else if(ch=='a'){
            return A;
        }else {
            return S;
        }
    }
}