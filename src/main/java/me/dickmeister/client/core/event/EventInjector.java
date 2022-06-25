package me.dickmeister.client.core.event;

import me.dickmeister.client.ClientMain;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.dynamic.scaffold.TypeValidation;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class EventInjector {

    public void initialize(){

        var loader = Thread.currentThread().getContextClassLoader();
        var mappingManager = ClientMain.getMappingManager();

        try {
            ByteBuddyAgent.install();
            var clazz = loader.loadClass(mappingManager.translateClassName("net.minecraft.client.player.LocalPlayer"));
            var method = mappingManager.translateMethodName("net.minecraft.client.player.LocalPlayer", "chat(Ljava/lang/String;)V");
            System.out.println("Class: " + clazz.getName());
            System.out.println("Method: " + method);

            new ByteBuddy().with(TypeValidation.DISABLED)
                    .redefine(clazz)
                    .visit(Advice.to(Class.forName("me.dickmeister.client.core.event.impl.PlayerChatEvent")).withExceptionPrinting().on(named(method)))
                    .make().load(loader, ClassReloadingStrategy.fromInstalledAgent());
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
