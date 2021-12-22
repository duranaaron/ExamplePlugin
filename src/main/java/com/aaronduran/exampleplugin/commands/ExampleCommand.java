package com.aaronduran.exampleplugin.commands;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.specifier.Greedy;
import cloud.commandframework.minecraft.extras.MinecraftHelp;
import com.aaronduran.exampleplugin.ExamplePlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExampleCommand {

    private final ExamplePlugin main;

    public ExampleCommand(ExamplePlugin main) {
        this.main = main;
    }

    @CommandMethod("example")
    @CommandDescription("Prints the executor's name")
    private void exampleCommand(Player player) {
        player.sendMessage(player.getName());
    }

    @CommandMethod("example help [query]")
    @CommandDescription("Prints a help message")
    private void exampleCommand(CommandSender commandSender, @Argument("query") @Greedy String query) {
        MinecraftHelp<CommandSender> exampleHelp = new MinecraftHelp<>(
                "/example help",
                sender -> sender,
                main.manager
        );
        exampleHelp.queryCommands(query == null ? "" : query, commandSender);
    }

    @CommandMethod("example heal <target>")
    @CommandDescription("Heal a player")
    private void exampleCommand(Player player, @Argument("target") Player target) {
        TextComponent message = Component.text()
                .append(Component.text("Succesfully healed ", NamedTextColor.DARK_GREEN))
                .append(Component.text(target.getName(), NamedTextColor.GREEN))
                .build();
        player.setHealth(20.0);
        player.sendMessage(message);
    }
}
