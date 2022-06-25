package me.dickmeister.client.core.utils;

import me.dickmeister.client.ClientMain;

import java.util.UUID;

public class MinecraftUtils {

    public static void sendSystemMessage(String message) throws Exception{
        var player = getLocalPlayer();
        var sendSystemMessageMethodName = ClientMain.getMappingManager()
                .translateMethodName("net.minecraft.client.player.LocalPlayer",
                        "sendMessage(Lnet/minecraft/network/chat/Component;Ljava/util/UUID;)V");

        var componentClass = Class.forName(ClientMain.getMappingManager()
                .translateClassName("net.minecraft.network.chat.Component"));
        var textComponentClass = Class.forName(ClientMain.getMappingManager()
                .translateClassName("net.minecraft.network.chat.TextComponent"));

        var sendMessageMethod = player.getClass().getDeclaredMethod(sendSystemMessageMethodName, componentClass, UUID.class);

        var textComponentInstance = textComponentClass.getConstructor(String.class).newInstance(message);
        sendMessageMethod.invoke(player,textComponentInstance,UUID.randomUUID());
    }

    public static Object getLocalPlayer() throws Exception {
        var instance = getMinecraftInstance();
        var playerFieldName = ClientMain.getMappingManager().translateFieldName("net.minecraft.client.Minecraft", "player");
        var playerField = instance.getClass().getDeclaredField(playerFieldName);

        return playerField.get(instance);
    }

    public static Object getMinecraftInstance() throws Exception{
        var classLoader = Thread.currentThread().getContextClassLoader();
        var mcClassName = ClientMain.getMappingManager().translateClassName("net.minecraft.client.Minecraft");
        var mcGetInstanceMethodName = ClientMain.getMappingManager()
                .translateMethodName("net.minecraft.client.Minecraft",
                "getInstance()Lnet/minecraft/client/Minecraft;");

        var mcClass = classLoader.loadClass(mcClassName);
        var mcInstance = mcClass.getMethod(mcGetInstanceMethodName).invoke(null);

        return mcInstance;
    }

}
