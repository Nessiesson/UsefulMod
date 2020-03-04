package nessiesson.usefulmod.ClientCommands.mixins;

import com.google.common.collect.ObjectArrays;
import nessiesson.usefulmod.ClientCommands.ClientCommandHandler;
import net.minecraft.util.TabCompleter;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TabCompleter.class)
public abstract class MixinTabCompleter {
	@ModifyArg(method = "complete", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiTextField;writeText(Ljava/lang/String;)V"))
	private String stripFormattingCodes(String text) {
		return TextFormatting.getTextWithoutFormattingCodes(text);
	}

	@Inject(method = "requestCompletions", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/NetHandlerPlayClient;sendPacket(Lnet/minecraft/network/Packet;)V"))
	private void addAutoComplete(String prefix, CallbackInfo ci) {
		ClientCommandHandler.instance.autoComplete(prefix);
	}

	@ModifyVariable(method = "setCompletions", at = @At(value = "INVOKE", target = "Ljava/util/List;clear()V", remap = false))
	private String[] setLatestAutoComplete(String... completions) {
		final String[] complete = ClientCommandHandler.instance.latestAutoComplete;
		if (complete != null) {
			return ObjectArrays.concat(complete, completions, String.class);
		}

		return completions;
	}

	@Redirect(method = "setCompletions", at = @At(value = "INVOKE", target = "Lorg/apache/commons/lang3/StringUtils;getCommonPrefix([Ljava/lang/String;)Ljava/lang/String;", remap = false))
	private String adjustedGetCommonPrefix(String... strs) {
		final String string = StringUtils.getCommonPrefix(strs);
		return TextFormatting.getTextWithoutFormattingCodes(string);
	}
}
