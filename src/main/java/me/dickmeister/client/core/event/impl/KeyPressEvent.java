package me.dickmeister.client.core.event.impl;

import com.darkmagician6.eventapi.events.Event;
import me.dickmeister.client.core.event.util.EventCaller;
import me.dickmeister.client.core.event.util.EventInitializer;
import me.dickmeister.client.mappings.MappingManager;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.takesArgument;

public class KeyPressEvent implements Event, EventInitializer {

    private int keyCode;
    private int pressType; // 1 = pressed, 0 = released, 2 = pressed still

    public KeyPressEvent(int keyCode,int pressType) {
        this.keyCode = keyCode;
        this.pressType = pressType;
    }

    public KeyPressEvent(){}

    public int getKeyCode() {
        return keyCode;
    }

    public int getPressType() {
        return pressType;
    }

    @RuntimeType
    @Advice.OnMethodEnter(skipOn = Boolean.class)
    public static Boolean chat(@RuntimeType long p_90894_, int p_90895_, int p_90896_, int p_90897_, int p_90898_) {
        if(EventCaller.callEvent("onKeyPress", p_90894_, p_90895_, p_90896_, p_90897_, p_90898_)) return true;
        return null;
    }

    @Override
    public void initialize(ByteBuddy byteBuddy, ClassLoader loader, MappingManager mappingManager) throws Exception {
        // public void keyPress(long p_90894_, int p_90895_, int p_90896_, int p_90897_, int p_90898_) {

        //net.minecraft.client.KeyboardHandler
        var className = mappingManager.translateClassName("net.minecraft.client.KeyboardHandler");
        var methodName = mappingManager.translateMethodName("net.minecraft.client.KeyboardHandler","keyPress(JIIII)V");

        var clazz = loader.loadClass(className);

        byteBuddy.with(TypeValidation.DISABLED)
                .redefine(clazz)
                .visit(Advice.to(Class.forName("me.dickmeister.client.core.event.impl.KeyPressEvent")).withExceptionPrinting()
                        .on(named(methodName).and(takesArgument(0, long.class).and(takesArgument(1, int.class).and(takesArgument(2, int.class).and(takesArgument(3, int.class).and(takesArgument(4, int.class))))))))
                .make().load(loader, ClassReloadingStrategy.fromInstalledAgent());
    }
}
