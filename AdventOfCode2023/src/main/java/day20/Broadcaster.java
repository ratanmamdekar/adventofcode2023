package day20;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Broadcaster implements Module{
    List<String> next = new ArrayList<>();
    String name;
    public Broadcaster(String name, List<String> next) {
        this.next = next;
        this.name = name;
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
        for (String moduleName : next){
            Module module = moduleMap.get(moduleName);
            todos.add(new ModulePulseWrapper(module,this.name,LOW));
        }
        return todos;
    }
}
