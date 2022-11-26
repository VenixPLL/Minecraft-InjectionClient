package me.dickmeister.client.core.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import me.dickmeister.client.ClientMain;
import me.dickmeister.client.core.event.impl.ClientInternalTickEvent;
import me.dickmeister.client.core.module.Module;
import me.dickmeister.client.core.module.ModuleCategory;

import java.awt.event.KeyEvent;

public class ESPModule extends Module {
    public ESPModule() {
        super("ESP", KeyEvent.VK_O, "ESP", ModuleCategory.RENDER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();

        try{
            var players = ClientMain.getMinecraftUtil().getAllPlayers();
            if(players == null){
                return;
            }

            for(var obj : players) {
                ClientMain.getMinecraftUtil().setSharedFlag(obj, 6, false);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @EventTarget
    public void tick(ClientInternalTickEvent event){
        try{
            var players = ClientMain.getMinecraftUtil().getAllPlayers();
            if(players == null){
                return;
            }

            for(var obj : players) {
                ClientMain.getMinecraftUtil().setSharedFlag(obj, 6, true);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
