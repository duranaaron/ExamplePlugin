package com.aaronduran.exampleplugin.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ExampleListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        final TextComponent hoverText = Component.text()
                .append(Component.text("Click me to suggest the help command for you.", NamedTextColor.GREEN))
                .build();
        final TextComponent joinText = Component.text()
                .append(Component.text("Hey! Click me :)", NamedTextColor.GREEN))
                .hoverEvent(HoverEvent.showText(hoverText))
                .clickEvent(ClickEvent.suggestCommand("/example help"))
                .build();
        player.sendMessage(joinText);
    }
}
