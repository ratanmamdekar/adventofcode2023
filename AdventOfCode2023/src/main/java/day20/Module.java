package day20;

import java.util.List;
import java.util.Map;

public interface Module {

    int LOW =0;
    int HIGH =1;
    String getName();
    List<String> getNext();
    List<ModulePulseWrapper> applyPulse(int pulse, Map<String,Module> moduleMap);
}
