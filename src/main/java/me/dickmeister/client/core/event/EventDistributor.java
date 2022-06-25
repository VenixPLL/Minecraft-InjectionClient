package me.dickmeister.client.core.event;

import com.darkmagician6.eventapi.EventManager;
import me.dickmeister.client.core.event.impl.PlayerChatEvent;

public class EventDistributor {

    public boolean onChat(String message){
        var event = new PlayerChatEvent(message);
        EventManager.call(event);
        return event.isCancelled();
    }

}
