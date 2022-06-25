package me.dickmeister.client.tasks;

import com.darkmagician6.eventapi.EventManager;
import me.dickmeister.client.core.event.impl.ClientInternalTickEvent;

public class TickingTask implements Runnable{
    @Override
    public void run() {
        EventManager.call(new ClientInternalTickEvent());
    }
}
