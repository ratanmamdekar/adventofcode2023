import day20.*;
import day20.Module;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Day20Part1 {

    static String BROADCASTER = "broadcaster";
    static String BUTTON = "button";
    static long[] pulseCount = new long[2];

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
            long[] pulses = processModules();
            System.out.println("low:"+pulses[0]);
            System.out.println("high:"+pulses[1]);
            System.out.println("product of the total number of low pulses sent by the total number " +
                    "of high pulses sent: "+pulses[0]*pulses[1]);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static long[] processModules() {
//        long[] result = new long[3];
        for (int idx=0;idx<1000;idx++){
            pressButton();
        }
        return pulseCount;
    }

    private static void pressButton() {
        Module broadcaster = moduleMap.get(BROADCASTER);
        List<ModulePulseWrapper> todo = new ArrayList<>();
        todo.add(new ModulePulseWrapper(broadcaster,BUTTON,Module.LOW));// button sends low pulse to the broadcaster;

        while (!todo.isEmpty()){
            List<ModulePulseWrapper> nextTodos = new ArrayList<>();
            for (ModulePulseWrapper wrapper :todo){
                pulseCount[wrapper.getPulse()]++;
                Module module = wrapper.getModule();
                if (module instanceof Conjunction){
                    ((Conjunction) module).updatePrev(wrapper.getPrevModule(), wrapper.getPulse());
                }
                if (Objects.nonNull(module))
                    nextTodos.addAll(module.applyPulse(wrapper.getPulse(),moduleMap));
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