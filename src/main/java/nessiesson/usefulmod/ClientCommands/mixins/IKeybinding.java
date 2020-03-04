package nessiesson.usefulmod.ClientCommands.mixins;

import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(KeyBinding.class)
public interface IKeybinding {
	@Accessor("KEYBIND_ARRAY")
	static Map<String, KeyBinding> getKeyBindArray() {
		throw new UnsupportedClassVersionError("Requires Java 8");
	}
}
