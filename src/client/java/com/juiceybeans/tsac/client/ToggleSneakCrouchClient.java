package com.juiceybeans.tsac.client;

import com.juiceybeans.tsac.ToggleSneakCrouch;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class ToggleSneakCrouchClient implements ClientModInitializer {
    KeyMapping.Category TOGGLE_KEYBINDS_CATEGORY = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath(ToggleSneakCrouch.MOD_ID, "toggle_keybinds")
    );

    KeyMapping toggleSneakKey = KeyMappingHelper.registerKeyMapping(
            new KeyMapping(
                    "key.tsac.toggle_sneak",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_J,
                    this.TOGGLE_KEYBINDS_CATEGORY
            ));
    KeyMapping toggleSprintKey = KeyMappingHelper.registerKeyMapping(
            new KeyMapping(
                    "key.tsac.toggle_sprint",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_K,
                    this.TOGGLE_KEYBINDS_CATEGORY
            ));


    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (this.toggleSneakKey.consumeClick()) {
                var isCrouchToggled = client.options.toggleCrouch().get();
                client.options.toggleCrouch().set(!isCrouchToggled);

                if (client.player != null) {
                    var msg = isCrouchToggled ? "msg.tsac.crouch_on_hold" : "msg.tsac.crouch_on_toggle";
                    client.player.sendSystemMessage(Component.translatable(msg));
                }
            }

            if (this.toggleSprintKey.consumeClick()) {
                var isSprintToggled = client.options.toggleSprint().get();
                client.options.toggleSprint().set(!isSprintToggled);

                if (client.player != null) {
                    var msg = isSprintToggled ? "msg.tsac.sprint_on_hold" : "msg.tsac.sprint_on_toggle";
                    client.player.sendSystemMessage(Component.translatable(msg));
                }
            }
        });
    }
}