import day20.Module;
import day20.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

//followed assumptions from https://youtu.be/lxm6i21O83k?t=907&si=-9pugUnKlXWvOfcY
public class Day20Part2 {

    static String BROADCASTER = "broadcaster";
    static String BUTTON = "button";

    static Map<String,Module> moduleMap = new HashMap<>();

    public static void main(String[] args) {
        try {
            File myObj = new File("/Users/ratanmamdekar/IdeaProjects/AdventOfCode2023/src/main/java/day20.txt");
            System.out.println("Reading file: ");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] split = data.split(" -> ");
                if(data.charAt(0)=='b'){
                    List<String> next = Arrays.stream(split[1].split(", ")).toList();
                    Module module = new Broadcaster(split[0], next);
                    moduleMap.put(module.getName(),module);
                }else if (data.charAt(0)=='%'){
                    List<String> next = Arrays.stream(split[1].split(", ")).toList();
                    Module module = new FlipFlop(split[0].substring(1), next);
                    moduleMap.put(module.getName(),module);
                } else {
                    List<String> next = Arrays.stream(split[1].split(", ")).toList();
                    Module module = new Conjunction(split[0].substring(1), next);
                    moduleMap.put(module.getName(),module);
                }
            }

            addPrevForConjunction();
            Set<String> feederOfRx = getFeeder(Collections.singleton("rx"));
            Set<String> feederOfFeederOfRx = getFeeder(feederOfRx);
            List<Integer> cycleLength = processModules(feederOfRx.stream().findAny().orElse(null),feederOfFeederOfRx);
            System.out.println("cycleLength:"+cycleLength);
            System.out.println("ans is LCM of these cycle length:"+ Day8Part2.lcm_of_array_elements(cycleLength.toArray(new Integer[0])));
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static Set<String> getFeeder(Set<String> strings) {
        Set<String> feeders = new HashSet<>();
        for (Module module : moduleMap.values()){
            for (String next : module.getNext()){
                if (strings.contains(next)){
                    feeders.add(module.getName());
                }
            }
        }
        return feeders;
    }

    private static List<Integer> processModules(String feed, Set<String> feederOfFeederOfRx) {
        Map<String,Integer> cycles = new HashMap<>();
        for (int idx=1;;idx++){
            pressButton(feed,cycles,idx);
            if (cycles.size()==feederOfFeederOfRx.size()){
                break;
            }
        }
        return cycles.values().stream().toList();
    }

    private static void pressButton(String feed, Map<String,Integer> cycles, int idx) {
        Module broadcaster = moduleMap.get(BROADCASTER);
        List<ModulePulseWrapper> todo = new ArrayList<>();
        todo.add(new ModulePulseWrapper(broadcaster,BUTTON,Module.LOW));// button sends low pulse to the broadcaster;

        while (!todo.isEmpty()){
            List<ModulePulseWrapper> nextTodos = new ArrayList<>();
            for (ModulePulseWrapper wrapper :todo){
                Module module = wrapper.getModule();
                if (Objects.isNull(module)) {
                    continue;
                }
                if (module instanceof Conjunction){
                    ((Conjunction) module).updatePrev(wrapper.getPrevModule(), wrapper.getPulse());
                }

                if (module.getName().equals(feed) && wrapper.getPulse() == Module.HIGH){
                    if (!cycles.containsKey(wrapper.getPrevModule())){
                        cycles.put(wrapper.getPrevModule(), idx);
                    }
                }

                nextTodos.addAll(module.applyPulse(wrapper.getPulse(), moduleMap));
            }
            todo = nextTodos;
        }

    }

    private static void addPrevForConjunction() {
        for (Module module : moduleMap.values()){
            for (String next : module.getNext()){
                Module nextModule = moduleMap.get(next);
                if(nextModule instanceof Conjunction){
                    ((Conjunction) nextModule).addPrev(module.getName());
                }
            }
        }
    }
}