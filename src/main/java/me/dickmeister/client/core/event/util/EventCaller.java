package me.dickmeister.client.core.event.util;

import me.dickmeister.common.util.LogUtil;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class EventCaller {

    public static boolean callEvent(String methodName,Object... args){
        var classLoader = Thread.currentThread().getContextClassLoader();

        try {
            var clazz = classLoader.loadClass("me.dickmeister.client.ClientMain");
            var instance = clazz.getDeclaredMethod("getInstance").invoke(null);

            var eventDistributor = clazz.getDeclaredMethod("getEventDistributor").invoke(instance);

            var result = new AtomicBoolean(false);
            var methods = eventDistributor.getClass().getMethods();
            Arrays.stream(methods).forEach(m -> {
                if(m.getName().equals(methodName)){
                    try {
                        result.set((Boolean) m.invoke(eventDistributor, args));
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });


            return result.get();

        }catch(Exception e){
            LogUtil.err("Failed to invoke event " + methodName);
            e.printStackTrace();
        }

        return false;
    }

}
