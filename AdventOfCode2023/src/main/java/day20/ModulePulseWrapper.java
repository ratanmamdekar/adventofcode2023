package day20;

public class ModulePulseWrapper {
    Module module;
    String prevModule;
    Integer pulse;

    public ModulePulseWrapper(Module module, String prevModule, Integer pulse) {
        this.module = module;
        this.prevModule = prevModule;
        this.pulse = pulse;
    }

    public Module getModule() {
        return module;
    }

    public String getPrevModule() {
        return prevModule;
    }

    public Integer getPulse() {
        return pulse;
    }
}
