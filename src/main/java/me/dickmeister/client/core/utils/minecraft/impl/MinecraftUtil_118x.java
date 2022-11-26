package me.dickmeister.client.core.utils.minecraft.impl;

import me.dickmeister.client.ClientMain;
import me.dickmeister.client.core.utils.minecraft.MinecraftUtil;

import java.util.List;
import java.util.UUID;

public class MinecraftUtil_118x extends MinecraftUtil {

    @Override
    public void setPlayerDimensions(Object player, float width, float height) throws Exception{
        var dimensionFieldName = ClientMain.getMappingManager()
                .translateFieldName("net.minecraft.world.entity.player.Player","STANDING_DIMENSIONS");

        var classLoader = Thread.currentThread().getContextClassLoader();
        var className = ClientMain.getMappingManager()
                .translateClassName("net.minecraft.world.entity.player.Player");

        var clazz = classLoader.loadClass(className);

        var dimensionField = clazz.getDeclaredField(dimensionFieldName);
        var dimensionObject = dimensionField.get(player);

        var widthFieldName = ClientMain.getMappingManager()
                .translateFieldName("net.minecraft.world.entity.EntityDimensions","width");
        var heightFieldName = ClientMain.getMappingManager()
                .translateFieldName("net.minecraft.world.entity.EntityDimensions","height");

        var widthField = dimensionObject.getClass().getDeclaredField(widthFieldName);
        var heightField = dimensionObject.getClass().getDeclaredField(heightFieldName);

        widthField.setAccessible(true);
        heightField.setAccessible(true);

        widthField.setFloat(dimensionObject, width);
        heightField.setFloat(dimensionObject, height);
    }

    @Override
    public List<Object> getAllPlayers() throws Exception {
        var level = getLevel();
        //public List<AbstractClientPlayer> players() {
        //var players = level.players();

        var playersMethodName = ClientMain.getMappingManager()
                .translateMethodName("net.minecraft.client.multiplayer.ClientLevel","players()Ljava/util/List;");
        var playersMethod = level.getClass().getDeclaredMethod(playersMethodName);

        return (List<Object>) playersMethod.invoke(level);
    }

    @Override
    public void setSharedFlag(Object entity, int flag, boolean value) throws Exception {
        //protected void setSharedFlag(int p_20116_, boolean p_20117_) {
        var classLoader = Thread.currentThread().getContextClassLoader();

        var entityClassName = ClientMain.getMappingManager().translateClassName("net.minecraft.world.entity.Entity");
        var entityClass = classLoader.loadClass(entityClassName);

        var setSharedFlagMethodName = ClientMain.getMappingManager()
                .translateMethodName("net.minecraft.world.entity.Entity", "setSharedFlag(IZ)V");

        var method = entityClass.getDeclaredMethod(setSharedFlagMethodName, int.class, boolean.class);
        method.setAccessible(true);
        method.invoke(entity, flag, value);
    }

    @Override
    public void drawFontString(Object poseStack, String message, float x, float y, int color) throws Exception {
        //public int draw(PoseStack p_92884_, String p_92885_, float p_92886_, float p_92887_, int p_92888_) {

        var methodMapping = ClientMain.getMappingManager().translateMethodName(
                "net.minecraft.client.gui.Font","draw(Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/lang/String;FFI)I");

        var fontInstance = getFont();
        var drawMethod = fontInstance.getClass().getMethod(methodMapping,
                poseStack.getClass(), String.class, float.class, float.class, int.class);

        drawMethod.invoke(fontInstance, poseStack, message, x, y, color);
    }

    @Override
    public Object getFont() throws Exception {
        var mcInstance = getMinecraftInstance();
        //public final Font font;
        var font = mcInstance.getClass().getDeclaredField(
                ClientMain.getMappingManager().translateFieldName("net.minecraft.client.Minecraft", "font"));

        return font.get(mcInstance);
    }

    @Override
    public void sendSystemMessage(String message) throws Exception {
        message = message.replace("&","§");

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

    @Override
    public Object getLevel() throws Exception {
        var mcInstance = getMinecraftInstance();

        var levelFieldName = ClientMain.getMappingManager().translateFieldName("net.minecraft.client.Minecraft", "level");
        return mcInstance.getClass().getDeclaredField(levelFieldName).get(mcInstance);
    }

    @Override
    public Object getLocalPlayer() throws Exception {
        var instance = getMinecraftInstance();
        var playerFieldName = ClientMain.getMappingManager().translateFieldName("net.minecraft.client.Minecraft", "player");
        var playerField = instance.getClass().getDeclaredField(playerFieldName);

        return playerField.get(instance);
    }

    @Override
    public Object getMinecraftInstance() throws Exception {
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
