package day20;

import java.util.*;

public class Conjunction implements Module{
    List<String> next = new ArrayList<>();
    Map<String,Integer> prev = new HashMap<>();
    String name;
    public Conjunction(String name, List<String> next) {
        this.next = next;
        this.name = name;
    }

    public void addPrev(String previous){
        prev.put(previous,LOW);
    }

    public void updatePrev(String previous,int pulse){
        prev.put(previous,pulse);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getNext() {
        return next;
    }

    @Override
    public List<ModulePulseWrapper> applyPulse(int pulse, Map<String,Module> moduleMap) {
        List<ModulePulseWrapper> todos = new ArrayList<>();
        int state = getConjunction();

        for (String moduleName : next){
            Module module = moduleMap.get(moduleName);
//            if(Objects.nonNull(module)){
//            }
                todos.add(new ModulePulseWrapper(module,this.name,state));
        }
        return todos;
    }

    private int getConjunction() {
        int allHigh = 1;
        for (int state : prev.values()){
            allHigh*=state;
        }
        return allHigh^1;
    }
}
