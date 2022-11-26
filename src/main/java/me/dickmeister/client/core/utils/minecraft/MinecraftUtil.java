package me.dickmeister.client.core.utils.minecraft;

import me.dickmeister.client.core.utils.minecraft.impl.MinecraftUtil_118x;

import java.util.List;

public abstract class MinecraftUtil {

    public static MinecraftUtil getFromVersion(String versionString){
        if(versionString.startsWith("1.18")){
            return new MinecraftUtil_118x();
        }

        return null;
    }

    public abstract void setPlayerDimensions(Object player, float width, float height) throws Exception;
    public abstract List<Object> getAllPlayers() throws Exception;
    public abstract void setSharedFlag(Object entity,int flag, boolean value) throws Exception;
    public abstract void drawFontString(Object poseStack,String message,float x,float y,int color) throws Exception;
    public abstract Object getFont() throws Exception;
    public abstract void sendSystemMessage(String message) throws Exception;

    public abstract Object getLevel() throws Exception;
    public abstract Object getLocalPlayer() throws Exception;
    public abstract Object getMinecraftInstance() throws Exception;

}
