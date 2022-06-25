package me.dickmeister.client.core.event.impl;

import com.darkmagician6.eventapi.events.callables.EventCancellable;
import me.dickmeister.client.core.event.util.EventCaller;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

public class PlayerChatEvent extends EventCancellable {

    private String message;

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

}
