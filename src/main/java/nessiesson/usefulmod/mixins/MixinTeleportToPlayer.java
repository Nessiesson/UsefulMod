package nessiesson.usefulmod.mixins;

import com.google.common.collect.Ordering;
import net.minecraft.client.gui.spectator.PlayerMenuObject;
import net.minecraft.client.network.NetworkPlayerInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.categories.TeleportToPlayer;

import java.util.Collection;
import java.util.List;

@Mixin(TeleportToPlayer.class)
public abstract class MixinTeleportToPlayer implements ISpectatorMenuView, ISpectatorMenuObject {

	@Shadow @Final private List<ISpectatorMenuObject> items;
	@Shadow @Final private static Ordering<NetworkPlayerInfo> PROFILE_ORDER;

	//Hackfix to allow spectated players to teleport to other specators.
    @Inject(method = "Lnet/minecraft/client/gui/spectator/categories/TeleportToPlayer;<init>(Ljava/util/Collection;)V",
            at = @At(value = "RETURN"))
    private void onConstructSpectatorList(Collection<NetworkPlayerInfo> profiles, CallbackInfo ci) {
    	this.items.clear();
	    for (NetworkPlayerInfo networkplayerinfo : PROFILE_ORDER.sortedCopy(profiles)) {
		    this.items.add(new PlayerMenuObject(networkplayerinfo.getGameProfile()));
	    }
    }
}
