package me.dickmeister.client.core.command.impl;

import me.dickmeister.client.ClientMain;
import me.dickmeister.client.core.command.Command;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help","","Shows list of commands and their usage");
    }

    @Override
    public void execute(String[] args) throws Exception {
        var cmdManager = ClientMain.getInstance().getCommandManager();
        var spacing = " ".repeat(4);
        ClientMain.getMinecraftUtil().sendSystemMessage("&aAvailable commands&7:");
        cmdManager.getCommandList().forEach(cmd -> {
            try {
                ClientMain.getMinecraftUtil().sendSystemMessage(spacing + "&a," + cmd.getPrefix() + " &7" + cmd.getUsage() + " &8- &7" + cmd.getDescription());
            }catch(Exception e){}
        });
    }
}
