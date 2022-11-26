package me.dickmeister.client.core.event;

import com.darkmagician6.eventapi.EventManager;
import me.dickmeister.client.core.event.impl.KeyPressEvent;
import me.dickmeister.client.core.event.impl.PlayerChatEvent;
import me.dickmeister.client.core.event.impl.Render2DEvent;

public class EventDistributor {

    public boolean onChat(String message){
        var event = new PlayerChatEvent(message);
        EventManager.call(event);
        return event.isCancelled();
    }

    public boolean onRender(Object poseStack,Float partialTicks){
        var event = new Render2DEvent(poseStack,partialTicks);
        EventManager.call(event);
        return false;
    }

    public boolean onKeyPress(long pressTime, int keyCode, int keyCode1, int pressType, int idkwhatthisis){
        var event = new KeyPressEvent(keyCode,pressType);
        EventManager.call(event);
        return false;
    }

}
