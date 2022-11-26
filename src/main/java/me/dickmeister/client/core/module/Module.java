package me.dickmeister.client.core.module;

import com.darkmagician6.eventapi.EventManager;

public abstract class Module {

    private final String name;
    private final int keybind;
    private final String description;
    private final ModuleCategory moduleCategory;

    private boolean enabled;

    public Module(String name, int keybind, String description, ModuleCategory moduleCategory) {
        this.name = name;
        this.keybind = keybind;
        this.description = description;
        this.moduleCategory = moduleCategory;
    }

    public void onEnable(){
        EventManager.register(this);
    }

    public void onDisable(){
        EventManager.unregister(this);
    }

    public void toggle(){
        this.enabled = !this.enabled;
        if(this.enabled){
            onEnable();
            return;
        }

        onDisable();
    }

    public String getDescription() {
        return description;
    }

    public int getKeybind() {
        return keybind;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
