package me.dickmeister.client.core.command.impl;

import com.darkmagician6.eventapi.EventManager;
import me.dickmeister.client.ClientMain;
import me.dickmeister.client.core.command.Command;

public class PanicCommand extends Command {

    public PanicCommand() {
        super("panic", "","Panic command");
    }

    @Override
    public void execute(String[] args) throws Exception {
        EventManager.getRegistryMap().clear();
        ClientMain.getInstance().getModuleManager().getModuleList().forEach(m -> {
            if(m.isEnabled()) m.toggle();
        });
    }
}
