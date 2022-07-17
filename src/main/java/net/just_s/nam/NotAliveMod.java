package net.just_s.nam;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.just_s.nam.util.Config;
import net.just_s.nam.util.IgniteCommand;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotAliveMod implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("nam");
	public static final MinecraftClient MC = MinecraftClient.getInstance();

	@Override
	public void onInitializeClient() {
		Config.load();
		ClientCommandRegistrationCallback.EVENT.register(IgniteCommand::register);
	}

	public static boolean isIgnitableInWorld() {
		return !MC.isInSingleplayer() && Config.ignitingWorlds.contains(NotAliveMod.getWorldAddress());
	}

	public static String getWorldAddress() {
		String x = MC.player.networkHandler.getConnection().getAddress().toString();
		return x.substring(0, x.indexOf("/"));
	}
}
