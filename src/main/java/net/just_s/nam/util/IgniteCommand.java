package net.just_s.nam.util;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.just_s.nam.NotAliveMod;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

public class IgniteCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> fabricClientCommandSourceCommandDispatcher, CommandRegistryAccess commandRegistryAccess) {
        fabricClientCommandSourceCommandDispatcher.register(
                literal("ignite").then(argument("value", BoolArgumentType.bool()).executes(IgniteCommand::run))
        );
    }

    private static int run(CommandContext<FabricClientCommandSource> fabricClientCommandSourceCommandContext) {
        if (NotAliveMod.MC.isInSingleplayer()) {
            NotAliveMod.MC.inGameHud.getChatHud().addMessage(Text.of("Мне было лень делать сохранения для одиночки, поэтому в сингле мод не работает)))))"));
            return 0;
        }
        if (fabricClientCommandSourceCommandContext.getArgument("value", boolean.class)) {
            if (!Config.ignitingWorlds.contains(NotAliveMod.getWorldAddress())) {
                Config.ignitingWorlds.add(NotAliveMod.getWorldAddress());
                Config.save();
            }
        } else {
            if (Config.ignitingWorlds.contains(NotAliveMod.getWorldAddress())) {
                Config.ignitingWorlds.remove(NotAliveMod.getWorldAddress());
                Config.save();
            }
        }
        NotAliveMod.MC.inGameHud.getChatHud().addMessage(Text.of("Происходит ли возгорание в этом мире: " + NotAliveMod.isIgnitableInWorld()));
        return 1;
    }
}
