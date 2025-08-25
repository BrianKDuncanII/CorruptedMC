package com.roesilej.corruptedmc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.roesilej.corruptedmc.event.CorruptionEvents;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.network.chat.Component;

import java.util.concurrent.CompletableFuture;

public class TriggerEventCommand {

    // An array of your event names. Easy to add to later!
    private static final String[] CORRUPTION_EVENTS = {
            "spread",
            "explosion"
            // "another_event", "a_third_event"
    };

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("corruptevent")
                .requires(source -> source.hasPermission(2)) // Operator level 2
                .then(Commands.literal("trigger")
                        .then(Commands.argument("eventName", StringArgumentType.string())
                                .suggests(TriggerEventCommand::getEventSuggestions) // Adds tab-completion
                                .executes(TriggerEventCommand::runEvent))));
    }

    private static int runEvent(CommandContext<CommandSourceStack> context) {
        String eventName = StringArgumentType.getString(context, "eventName");
        CommandSourceStack source = context.getSource();
        boolean success = false;

        switch (eventName.toLowerCase()) {
            case "spread":
                success = CorruptionEvents.triggerCorruptionSpreadEvent(source.getLevel());
                break;
            case "explosion":
                success = CorruptionEvents.triggerExplosionEvent(source.getLevel());
                break;
            // Add more cases here for future events
            // case "another_event":
            //     success = CorruptionEvents.triggerAnotherEvent(source.getLevel());
            //     break;
            default:
                source.sendFailure(Component.literal("Unknown event name: '" + eventName + "'"));
                return 0; // Command failed
        }

        if (success) {
            source.sendSuccess(() -> Component.literal("Successfully triggered event: '" + eventName + "'"), true);
            return 1; // Command succeeded
        } else {
            source.sendFailure(Component.literal("Failed to trigger event: '" + eventName + "'. Conditions may not have been met (e.g., no players online or no valid location found)."));
            return 0; // Command failed
        }
    }

    private static CompletableFuture<Suggestions> getEventSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggest(CORRUPTION_EVENTS, builder);
    }
}