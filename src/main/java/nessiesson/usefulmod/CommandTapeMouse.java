package nessiesson.usefulmod;

import nessiesson.usefulmod.ClientCommands.mixins.IKeybinding;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CommandTapeMouse extends CommandBase {
	private static final Map<String, KeyBinding> KEYBIND_ARRAY = IKeybinding.getKeyBindArray();

	@Override
	public String getName() {
		return "tm";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return this.getName() + " <sync|keybinding name> [delay|off]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
		if (args.length == 1) {
			final List<String> list = new ArrayList<>(Collections.singletonList("off"));
			for (KeyBinding keyBinding : KEYBIND_ARRAY.values()) {
				if (keyBinding == null || keyBinding.getKeyCode() == Keyboard.KEY_NONE) {
					continue;
				}

				final String name = keyBinding.getKeyDescription().replaceFirst("^key\\.", "");
				list.add(name);
			}

			return getListOfStringsMatchingLastWord(args, list);
		}

		return super.getTabCompletions(server, sender, args, pos);
	}
}
