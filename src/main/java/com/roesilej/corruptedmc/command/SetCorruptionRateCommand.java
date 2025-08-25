package com.roesilej.corruptedmc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.roesilej.corruptedmc.config.CorruptionRateConfig;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class SetCorruptionRateCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // Register the main command literal "corruptionrate"
        dispatcher.register(Commands.literal("corruptionrate")
            // Require operator permission level 2 to use this command
            .requires(source -> source.hasPermission(2))

            // Sub-command: /corruptionrate set <multiplier>
            .then(Commands.literal("set")
                .then(Commands.argument("multiplier", FloatArgumentType.floatArg(0)) // Argument must be a float >= 0
                    .executes(context -> {
                        float multiplier = FloatArgumentType.getFloat(context, "multiplier");
                        CorruptionRateConfig.setRateMultiplier(multiplier);
                        // Send feedback to the player who ran the command
                        context.getSource().sendSuccess(() -> Component.literal("Corruption rate multiplier set to " + multiplier), true);
                        return 1; // Indicates command was successful
                    })
                )
            )

            // Sub-command: /corruptionrate get
            .then(Commands.literal("get")
                .executes(context -> {
                    float multiplier = CorruptionRateConfig.getRateMultiplier();
                    // Send feedback to the player who ran the command
                    context.getSource().sendSuccess(() -> Component.literal("Current corruption rate multiplier is " + multiplier), false);
                    return 1; // Indicates command was successful
                })
            )
        );
    }
}