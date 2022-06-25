package me.dickmeister.client.core.event.util;

import me.dickmeister.common.util.LogUtil;

import java.util.Objects;

public class EventCaller {

    public static boolean callEvent(String methodName,Object... args){
        var classLoader = Thread.currentThread().getContextClassLoader();

        try {
            var clazz = classLoader.loadClass("me.dickmeister.client.ClientMain");
            var instance = clazz.getDeclaredMethod("getInstance").invoke(null);

            var eventDistributor = clazz.getDeclaredMethod("getEventDistributor").invoke(instance);

            var methodArguments = new Class[args.length];
            for(int i = 0; i < args.length; i++){
                methodArguments[i] = args[i].getClass();
            }

            var eventMethod = eventDistributor.getClass().getDeclaredMethod(methodName,methodArguments);
            return (boolean) eventMethod.invoke(eventDistributor, args);

        }catch(Exception e){
            LogUtil.err("Failed to invoke event " + methodName);
            e.printStackTrace();
        }

        return false;
    }

}
