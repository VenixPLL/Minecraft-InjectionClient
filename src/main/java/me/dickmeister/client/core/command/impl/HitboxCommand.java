package me.dickmeister.client.core.command.impl;

import me.dickmeister.client.core.command.Command;
import me.dickmeister.client.core.module.impl.combat.HitboxModule;

public class HitboxCommand extends Command {

    public HitboxCommand() {
        super("hitbox", "[width] [height]", "Expands player hitboxes");
    }

    @Override
    public void execute(String[] args) throws Exception {
        var width = Double.parseDouble(args[1]);
        var height = Double.parseDouble(args[2]);

        HitboxModule.hitboxWidth = (float) width;
        HitboxModule.hitboxHeight = (float) height;
    }
}
