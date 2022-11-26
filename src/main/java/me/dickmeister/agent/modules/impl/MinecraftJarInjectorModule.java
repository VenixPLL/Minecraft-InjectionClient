package me.dickmeister.agent.modules.impl;

import me.dickmeister.agent.ClientAgent;
import me.dickmeister.agent.modules.AgentModule;
import me.dickmeister.common.util.LogUtil;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;
import java.util.Queue;

public class MinecraftJarInjectorModule extends AgentModule {
    @Override
    public Object execute(Instrumentation instrumentation, Object arg) throws Exception {
        if(Objects.isNull(arg)) return null;
        var queue = (Queue<Runnable>) arg;
        var filePath = ClientAgent.class.getProtectionDomain().getCodeSource().getLocation().getPath();;
        queue.add(() -> {
            try {
                var file = new File(filePath);
                var classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()}, Thread.currentThread().getContextClassLoader());

                var clazz = classLoader.loadClass("me.dickmeister.client.ClientMain");
                var instance = clazz.newInstance();
                clazz.getDeclaredMethod("invokeClient",Instrumentation.class).invoke(instance,instrumentation);

                Thread.currentThread().setContextClassLoader(classLoader);
            }catch(Exception e){
                e.printStackTrace();
            }
        });
        return null; //Last module
    }
}
