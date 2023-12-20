package day20;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FlipFlop implements Module{
    int state;
    String name;
    List<String> next = new ArrayList<>();

    public FlipFlop(String name, List<String> next) {
        this.next = next;
        this.name = name;
        state=LOW;
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
    public List<ModulePulseWrapper> applyPulse(int pulse, Map<String,Module> moduleMap){
        List<ModulePulseWrapper> todos = new ArrayList<>();
        if (pulse==HIGH){
            return todos; // high pulse will be ignored.
        }
        state=state^1;
        for (String moduleName : next){
            Module module = moduleMap.get(moduleName);
            todos.add(new ModulePulseWrapper(module,this.name,state));
        }
        return todos;
    }
}
