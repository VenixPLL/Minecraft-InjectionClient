package me.dickmeister.injector;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import me.dickmeister.agent.ClientAgent;
import me.dickmeister.agent.attacher.AgentAttacher;

import java.util.List;
import java.util.Scanner;

public class ClientInjector {

    public static void main(String[] args) {
        List<VirtualMachineDescriptor> descriptors = VirtualMachine.list();

        System.out.println("[Injector] List of running VMs:");
        var index = 0;
        for (VirtualMachineDescriptor descriptor : descriptors) {
            var printer = System.out;
            if(descriptor.displayName().contains("net.minecraft.client.main.Main") || descriptor.displayName().contains("net.fabricmc")) printer = System.err;

            printer.println("     #" + ++index + " > " + descriptor.id() + " " + descriptor.displayName().split(" ")[0]);
        }

        System.out.print("[Injector] Select one of the above VMs to attach to: ");
        var scanner = new Scanner(System.in);
        var input = scanner.nextLine();

        var vmIndex = Integer.parseInt(input);
        var vm = descriptors.get(vmIndex - 1);
        System.out.println("[Injector] Selected VM: " + vm.id());

        var agentPath = ClientInjector.class.getProtectionDomain().getCodeSource().getLocation().getPath()
                .replace("/","\\")
                .substring(1);

        System.out.println("[Injector] Attaching from: " + agentPath);

        try {
            AgentAttacher.attachDynamic(agentPath, vm.id());
            System.out.println("[Injector] Attached!");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
