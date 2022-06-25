package me.dickmeister.agent.modules.impl;

import me.dickmeister.agent.modules.AgentModule;

import java.lang.instrument.Instrumentation;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;

public class MinecraftQueueFinderModule extends AgentModule {

    @Override
    public Object execute(Instrumentation instrumentation, Object arg) throws Exception {
        var mappings = (String) arg;
        var mcClass = findMinecraftClass(mappings);
        var mcInstance = getMinecraftInstance(mcClass,mappings,instrumentation);
        var taskQueue = getMinecraftTaskQueue(mcInstance,mappings);

        if(Objects.isNull(taskQueue)){
            System.out.println("Failed to find task queue");
            return null;
        }
        return taskQueue;
    }

    /**
     * Get minecraft task queue
     * @param minecraftObject minecraft instance
     * @param mappingData mapping data
     * @return minecraft task queue
     * @throws Exception if failed to get queue
     */
    private Queue<Runnable> getMinecraftTaskQueue(Object minecraftObject,String mappingData) throws Exception{
        String fieldName = "";
        var scanner = new Scanner(mappingData);
        while(scanner.hasNextLine()){
            var line = scanner.nextLine();
            if(line.contains("progressTasks") && line.contains("java.util.Queue progressTasks")){
                fieldName = line.split(" -> ")[1];
                break;
            }
        }

        var method = minecraftObject.getClass().getDeclaredField(fieldName);
        method.setAccessible(true);
        return (Queue<Runnable>) method.get(minecraftObject);
    }

    /**
     * Get minecraft instance
     * @param className minecraft class name
     * @param mappingData mapping data
     * @return minecraft instance
     * @throws Exception
     */
    private Object getMinecraftInstance(String className,String mappingData,Instrumentation instrumentation) throws Exception{

        String methodName = "";
        var scanner = new Scanner(mappingData);
        while(scanner.hasNextLine()){
            var line = scanner.nextLine();
            if(line.contains("getInstance()")){
                if(line.contains("net.minecraft.client.Minecraft") && line.contains(" -> ")){
                    methodName = line.split(" -> ")[1];
                    break;
                }
            }
        }

        if(methodName.isEmpty()){
            System.err.println("[PreHook] Failed to find getInstance() method name!");
            return null;
        }

        for(Class clazz : instrumentation.getAllLoadedClasses()){
            if(clazz.getSimpleName().equals(className)){
                var method = clazz.getDeclaredMethod(methodName);
                method.setAccessible(true);
                return method.invoke(null);
            }
        }

        return null;
    }

    /**
     * Get minecraft class name
     * @param mappingData mapping data
     * @return minecraft class name
     */
    private String findMinecraftClass(String mappingData){
        var scanner = new Scanner(mappingData);
        while(scanner.hasNextLine()){
            var line = scanner.nextLine();
            if(line.contains("net.minecraft.client.Minecraft")){
                if(line.contains(" -> ") && line.endsWith(":") && !line.contains("$")){
                    return line.split(" -> ")[1].split(":")[0];
                }
            }
        }
        return null;
    }

}
