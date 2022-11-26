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

import static net.bytebuddy.matcher.ElementMatchers.*;

public class Render2DEvent implements Event, EventInitializer {

    private Object poseStack;
    private float partialTicks;

    public Render2DEvent(){}

    public Render2DEvent(Object poseStack,float partialTicks) {
        this.poseStack = poseStack;
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public Object getPoseStack() {
        return poseStack;
    }

    @RuntimeType
    @Advice.OnMethodEnter(skipOn = Boolean.class)
    public static Boolean onRender(@RuntimeType Object obj, float partialTicks) {
        if(EventCaller.callEvent("onRender", obj,partialTicks)) return true;
        return null;
    }

    @Override
    public void initialize(ByteBuddy byteBuddy, ClassLoader loader, MappingManager mappingManager) throws Exception {
        var clazz = loader.loadClass(mappingManager.translateClassName("net.minecraft.client.gui.Gui"));
        var method = mappingManager.translateMethodName("net.minecraft.client.gui.Gui", "render(Lcom/mojang/blaze3d/vertex/PoseStack;F)V");
        byteBuddy.with(TypeValidation.DISABLED)
                .redefine(clazz)
                .visit(Advice.to(Class.forName(this.getClass().getName())).withExceptionPrinting().on(named(method).and(takesArgument(1, float.class))))
                .make().load(loader, ClassReloadingStrategy.fromInstalledAgent());
    }
}
