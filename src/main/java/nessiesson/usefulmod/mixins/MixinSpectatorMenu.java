package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuRecipient;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpectatorMenu.class)
public abstract class MixinSpectatorMenu {
	@Shadow
	public abstract ISpectatorMenuObject getItem(int index);

	@Inject(method = "<init>", at = @At("RETURN"))
	private void onlyShowPlayers(ISpectatorMenuRecipient menu, CallbackInfo ci) {
		if (!LiteModUsefulMod.config.showTeamMenu) {
			this.getItem(0).selectItem((SpectatorMenu) (Object) this);
		}
	}
}
