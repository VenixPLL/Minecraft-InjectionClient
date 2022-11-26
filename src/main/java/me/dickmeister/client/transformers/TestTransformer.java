package me.dickmeister.client.transformers;

import javassist.*;
import me.dickmeister.client.ClientMain;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class TestTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        var clazz =  ClientMain.getMappingManager().translateClassName("net.minecraft.world.entity.player.Player") + "$1";

        System.out.println("PreReformatting: " + className + " looking for " + clazz);
        if(!className.equals(clazz))
            return classfileBuffer;

        try {
            System.out.println("Reformatting: " + className);
            ClassPool classPool = ClassPool.getDefault();
            classPool.appendClassPath(new LoaderClassPath(loader));

            CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
            // modyfikujesz sobie cos w tej klasie
            var methodName = ClientMain.getMappingManager()
                    .translateMethodName("net.minecraft.world.entity.player.Player",
                            "getDimensions(Lnet/minecraft/world/entity/Pose;)Lnet/minecraft/world/entity/EntityDimensions;");
            CtMethod m = ctClass.getDeclaredMethod(methodName);
            m.insertBefore("{ System.out.println($1); }");

            classfileBuffer = ctClass.toBytecode();
            ctClass.detach();

            return classfileBuffer;
        } catch (IOException | CannotCompileException ex) {
            ex.printStackTrace();
            System.exit(-1);
            return classfileBuffer;
        } catch (NotFoundException e) {
            e.printStackTrace();
            return classfileBuffer;
        }
    }
}
