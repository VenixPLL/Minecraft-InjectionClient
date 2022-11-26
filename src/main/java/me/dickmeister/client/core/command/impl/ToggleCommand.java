package me.dickmeister.client.core.command.impl;

import me.dickmeister.client.ClientMain;
import me.dickmeister.client.core.command.Command;

import java.util.Objects;

public class ToggleCommand extends Command {

    public ToggleCommand() {
        super("toggle", "[modulename]","Toggle cheat module");
    }

    @Override
    public void execute(String[] args) throws Exception {
        var name = args[1];
        var module = ClientMain.getInstance().getModuleManager().getModuleList().stream().filter(m -> m.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        if(Objects.nonNull(module)){
            module.toggle();
            ClientMain.getMinecraftUtil().sendSystemMessage("&aModule " + module.getName() + " is now " + (module.isEnabled() ? "&aenabled" : "&cdisabled"));
            return;
        }

        ClientMain.getMinecraftUtil().sendSystemMessage("&cModule not found");
    }
}
