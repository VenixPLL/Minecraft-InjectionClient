package me.dickmeister.client.core.event.impl;

import com.darkmagician6.eventapi.events.callables.EventCancellable;
import me.dickmeister.client.core.event.util.EventCaller;
import me.dickmeister.client.core.event.util.EventInitializer;
import me.dickmeister.client.mappings.MappingManager;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class PlayerChatEvent extends EventCancellable implements EventInitializer {

    private String message;

    public PlayerChatEvent(){}

    public PlayerChatEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @RuntimeType
    @Advice.OnMethodEnter(skipOn = Boolean.class)
    public static Boolean chat(@RuntimeType String p_108740_) {
        if(EventCaller.callEvent("onChat", p_108740_)) return true;
        return null;
    }

    @Override
    public void initialize(ByteBuddy byteBuddy, ClassLoader loader, MappingManager mappingManager) throws Exception{
        var clazz = loader.loadClass(mappingManager.translateClassName("net.minecraft.client.player.LocalPlayer"));
        var method = mappingManager.translateMethodName("net.minecraft.client.player.LocalPlayer", "chat(Ljava/lang/String;)V");
        byteBuddy.with(TypeValidation.DISABLED)
                .redefine(clazz)
                .visit(Advice.to(Class.forName("me.dickmeister.client.core.event.impl.PlayerChatEvent")).withExceptionPrinting().on(named(method)))
                .make().load(loader, ClassReloadingStrategy.fromInstalledAgent());
    }
}
