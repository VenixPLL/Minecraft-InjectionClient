package me.dickmeister.client.core.event.util;

import me.dickmeister.client.mappings.MappingManager;
import net.bytebuddy.ByteBuddy;

public interface EventInitializer {

    void initialize(ByteBuddy byteBuddy, ClassLoader loader, MappingManager mappingManager) throws Exception;

}
