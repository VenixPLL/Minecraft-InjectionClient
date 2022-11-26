package me.dickmeister.client.core.module;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import me.dickmeister.client.core.event.impl.KeyPressEvent;
import me.dickmeister.client.core.event.util.EventInitializer;
import me.dickmeister.common.util.LogUtil;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ModuleManager {

    private final List<Module> moduleList = new ArrayList<>();

    public void initialize() throws InstantiationException, IllegalAccessException {
        Reflections reflections = new Reflections("me.dickmeister.client.core.module.impl", new SubTypesScanner());
        var modules = reflections.getSubTypesOf(Module.class);

        for(var module : modules){
            var instance = module.newInstance();
            LogUtil.log("Loaded module: %s", module.getSimpleName());
            moduleList.add(instance);
        }

        EventManager.register(this);
    }

    @EventTarget
    public void onKeyPress(KeyPressEvent event){

        if(event.getPressType() == 1){
            getModuleList().forEach(m -> {
                var keybind = m.getKeybind();

                if(keybind != 0 && keybind == event.getKeyCode())
                    m.toggle();

            });
        }
    }


    public void toggleModule(Module module){
        module.toggle();
    }

    public List<Module> getModuleList() {
        return moduleList;
    }

}
