package me.dickmeister.client.core.command;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import me.dickmeister.client.ClientMain;
import me.dickmeister.client.core.command.impl.HelpCommand;
import me.dickmeister.client.core.command.impl.HitboxCommand;
import me.dickmeister.client.core.command.impl.PanicCommand;
import me.dickmeister.client.core.command.impl.ToggleCommand;
import me.dickmeister.client.core.event.impl.PlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private final List<Command> commandList = new ArrayList<>();

    public void initialize(){
        commandList.add(new HelpCommand());
        commandList.add(new PanicCommand());
        commandList.add(new ToggleCommand());
        commandList.add(new HitboxCommand());
        EventManager.register(this);
    }

    @EventTarget
    public void onChat(PlayerChatEvent event){
        var message = event.getMessage();
        if(message.startsWith(",")){

            var args = message.split(" ");
            var command = args[0].substring(1);

            var commandObject = commandList.stream().filter(cmd -> cmd.getPrefix().equalsIgnoreCase(command)).findFirst();
            commandObject.ifPresentOrElse(cmd -> {
                try {
                    cmd.execute(args);
                } catch (Exception e) {
                    try {
                        ClientMain.getMinecraftUtil().sendSystemMessage("&cFailed to execute command!");
                        e.printStackTrace();
                    }catch(Exception e1){
                        e1.printStackTrace();
                    }
                }
            }, () -> {
                try {
                    ClientMain.getMinecraftUtil().sendSystemMessage("&cCommand not found!");
                }catch(Exception e){
                    e.printStackTrace();
                }
            });


            event.setCancelled(true);
        }
    }

    public List<Command> getCommandList() {
        return commandList;
    }
}
