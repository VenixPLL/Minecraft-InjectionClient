package me.dickmeister.agent.attacher;

import com.sun.tools.attach.VirtualMachine;

import java.lang.management.ManagementFactory;

public class AgentAttacher {

    public static void attachDynamic(String jarFilePath, String pid) throws Exception {
        VirtualMachine vm = VirtualMachine.attach(pid);
        vm.loadAgent(jarFilePath, "");
        vm.detach();
    }

}
