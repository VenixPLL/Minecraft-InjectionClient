package me.dickmeister.client.core.event;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import me.dickmeister.client.ClientMain;
import me.dickmeister.client.core.event.util.EventInitializer;
import me.dickmeister.client.mappings.MappingManager;
import me.dickmeister.common.util.LogUtil;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class EventInjector {

    public void initialize(){

        var loader = Thread.currentThread().getContextClassLoader();
        var mappingManager = ClientMain.getMappingManager();

        try {
            ByteBuddyAgent.install();
            var classes = findAllClassesUsingReflectionsLibrary("me.dickmeister.client.core.event.impl");
            for(var clazz : classes){
                var instance = clazz.newInstance();
                if(instance instanceof EventInitializer){
                    ((EventInitializer) instance).initialize(new ByteBuddy(), loader, mappingManager);
                    LogUtil.log("Injected event: " + clazz.getName());
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public Set<Class> findAllClassesUsingReflectionsLibrary(String packageName) {
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        return new HashSet<>(reflections.getSubTypesOf(EventInitializer.class));
    }

}
