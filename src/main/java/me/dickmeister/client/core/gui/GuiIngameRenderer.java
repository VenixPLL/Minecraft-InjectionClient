package me.dickmeister.client.core.gui;

import com.darkmagician6.eventapi.EventTarget;
import me.dickmeister.client.ClientMain;
import me.dickmeister.client.core.event.impl.Render2DEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class GuiIngameRenderer {

    @EventTarget
    public void onRender2D(Render2DEvent event) {
        try {
            ClientMain.getMinecraftUtil().drawFontString(event.getPoseStack(), "InjectClient v0", 5, 5, 0xFFFFFF);

            var modules = ClientMain.getInstance().getModuleManager();

            AtomicInteger yIndex = new AtomicInteger(15);

            modules.getModuleList().forEach(module -> {
                if(module.isEnabled()){
                    var yPos = yIndex.getAndAdd(10);
                    try {
                        ClientMain.getMinecraftUtil().drawFontString(event.getPoseStack(), module.getName(), 5, yPos, 0xFFFFFF);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
