package me.dickmeister.client.core.event.impl;

import com.darkmagician6.eventapi.events.Event;
import me.dickmeister.client.core.event.util.EventInitializer;
import me.dickmeister.client.mappings.MappingManager;
import net.bytebuddy.ByteBuddy;

public class ClientInternalTickEvent implements Event, EventInitializer {
    @Override
    public void initialize(ByteBuddy byteBuddy, ClassLoader loader, MappingManager mappingManager) throws Exception {
        //Ignore this, called internally by the client
    }
}
