package me.dickmeister.agent;

import me.dickmeister.agent.modules.AgentModule;
import me.dickmeister.agent.modules.impl.MinecraftJarInjectorModule;
import me.dickmeister.agent.modules.impl.MinecraftQueueFinderModule;
import me.dickmeister.agent.modules.impl.VersionFinderModule;
import me.dickmeister.agent.modules.impl.VersionMappingDownloaderModule;
import me.dickmeister.common.util.LogUtil;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;

public class ClientAgent {

    private static final List<AgentModule> moduleList = new ArrayList<>();

    public static void agentmain(String agentArgs, Instrumentation inst) {
        LogUtil.log("Loading modules...");
        moduleList.add(new VersionFinderModule());
        moduleList.add(new VersionMappingDownloaderModule());
        moduleList.add(new MinecraftQueueFinderModule());
        moduleList.add(new MinecraftJarInjectorModule());

        LogUtil.log("Executing modules...");
        Object returnCache = new Object();
        for(AgentModule m : moduleList) {
            try{
                returnCache = m.execute(inst,returnCache);
                LogUtil.log("Executed module: " + m.getClass().getName());
            }catch(Exception e){
                e.printStackTrace();
                LogUtil.log("Failed to execute module " + m.getClass().getName());
            }
        }
    }
}
