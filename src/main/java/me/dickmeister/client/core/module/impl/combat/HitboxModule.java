package me.dickmeister.client.core.module.impl.combat;

import me.dickmeister.client.ClientMain;
import me.dickmeister.client.core.module.Module;
import me.dickmeister.client.core.module.ModuleCategory;
import me.dickmeister.client.core.utils.minecraft.MinecraftUtil;

import java.awt.event.KeyEvent;

public class HitboxModule extends Module {

    public HitboxModule() {
        super("Hitbox", KeyEvent.VK_K ,"Expands player hitboxes",ModuleCategory.COMBAT);
    }

    public static float hitboxWidth = 0.65f;
    public static float hitboxHeight = 1.9f;

    //Origin box 0.6F, 1.8F
    @Override
    public void onEnable() {
        super.onEnable();

        try {
            var player = ClientMain.getMinecraftUtil().getLocalPlayer();
            ClientMain.getMinecraftUtil().setPlayerDimensions(player, hitboxWidth, hitboxHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {
        super.onDisable();
        try {
            var player = ClientMain.getMinecraftUtil().getLocalPlayer();
            ClientMain.getMinecraftUtil().setPlayerDimensions(player, 0.6F, 1.8F);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
