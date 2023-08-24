package net.just_s.nam.mixin;

import net.just_s.nam.NotAliveMod;
import net.just_s.nam.util.Config;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class PlayerTickMixin {
	@Inject(at = @At("TAIL"), method = "tickMovement")
	private void init(CallbackInfo info) {
		ClientPlayerEntity player = (ClientPlayerEntity)(Object)this;
		if (player.getWorld() != null && player.isAlive()) {
			if (isAffectedByDaylight(player) && NotAliveMod.isIgnitableInWorld()) {
				player.setOnFireFor(8);
			}
		}
	}

	private boolean isAffectedByDaylight(ClientPlayerEntity player) {
		player.getWorld().calculateAmbientDarkness();
		if (player.getWorld().isDay()) {
			BlockPos blockPos = new BlockPos(player.getX(), player.getEyeY(), player.getZ());
			boolean bl = player.hasStatusEffect(StatusEffects.FIRE_RESISTANCE) || player.isWet() || player.inPowderSnow || player.wasInPowderSnow;
			return !bl && player.getWorld().isSkyVisible(blockPos);
		}
		return false;
	}
}
