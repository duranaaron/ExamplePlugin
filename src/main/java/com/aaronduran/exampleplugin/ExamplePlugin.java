package com.aaronduran.exampleplugin;

import cloud.commandframework.CommandTree;
import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.arguments.parser.ParserParameters;
import cloud.commandframework.arguments.parser.StandardParameters;
import cloud.commandframework.bukkit.BukkitCommandManager;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.paper.PaperCommandManager;
import com.aaronduran.exampleplugin.commands.ExampleCommand;
import com.aaronduran.exampleplugin.listeners.ExampleListener;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Function;

public final class ExamplePlugin extends JavaPlugin {

    public BukkitCommandManager<CommandSender> manager;
    private AnnotationParser<CommandSender> annotationParser;

    @Override
    public void onEnable() {
        final Function<CommandTree<CommandSender>, CommandExecutionCoordinator<CommandSender>> executionCoordinatorFunction =
                CommandExecutionCoordinator.simpleCoordinator();
        final Function<CommandSender, CommandSender> senderMapper = Function.identity();

        try {
            this.manager = new PaperCommandManager<>(
                    this,
                    executionCoordinatorFunction,
                    senderMapper,
                    senderMapper
            );
        } catch (final Exception e) {
            this.getLogger().severe("Failed to initialize the command this.manager");
            this.getServer().getPluginManager().disablePlugin(this);
        }

        if (this.manager.queryCapability(CloudBukkitCapabilities.BRIGADIER)) {
            this.manager.registerBrigadier();
        }
        if (this.manager.queryCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
            ((PaperCommandManager<CommandSender>) this.manager).registerAsynchronousCompletions();
        }

        final Function<ParserParameters, CommandMeta> commandMetaFunction = p ->
                CommandMeta.simple()
                        .with(CommandMeta.DESCRIPTION, p.get(StandardParameters.DESCRIPTION, "Geen beschrijving voor dit commando"))
                        .build();

        this.annotationParser = new AnnotationParser<>(
                this.manager,
                CommandSender.class,
                commandMetaFunction
        );
        constructCommands();
        Bukkit.getPluginManager().registerEvents(new ExampleListener(), this);
    }

    private void constructCommands() {
        this.annotationParser.parse(new ExampleCommand(this));
    }
}
