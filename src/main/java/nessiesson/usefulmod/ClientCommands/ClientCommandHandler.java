package nessiesson.usefulmod.ClientCommands;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import java.util.List;
import java.util.stream.IntStream;

import static net.minecraft.util.text.TextFormatting.GRAY;
import static net.minecraft.util.text.TextFormatting.RESET;

public class ClientCommandHandler extends CommandHandler {
	public static final ClientCommandHandler instance = new ClientCommandHandler();

	public String[] latestAutoComplete = null;

	@Override
	public int executeCommand(ICommandSender sender, String message) {
		message = message.trim();

		if (message.startsWith("/")) {
			message = message.substring(1);
		}

		final String[] temp = message.split(" ");
		final String[] args = new String[temp.length - 1];
		final String commandName = temp[0];
		System.arraycopy(temp, 1, args, 0, args.length);
		final ICommand command = getCommands().get(commandName);
		if (command == null) {
			return 0;
		}

		try {
			this.tryExecute(sender, args, command, message);
		} catch (Throwable t) {
			final TextComponentTranslation error = new TextComponentTranslation("commands.generic.exception");
			error.getStyle().setColor(TextFormatting.RED);
			sender.sendMessage(error);
		}

		return -1;
	}

	public void autoComplete(String leftOfCursor) {
		this.latestAutoComplete = null;

		if (leftOfCursor.charAt(0) == '/') {
			leftOfCursor = leftOfCursor.substring(1);

			final Minecraft mc = Minecraft.getMinecraft();
			if (mc.currentScreen instanceof GuiChat) {
				final List<String> commands = this.getTabCompletions(mc.player, leftOfCursor, mc.player.getPosition());
				if (!commands.isEmpty()) {
					if (leftOfCursor.indexOf(' ') == -1) {
						IntStream.range(0, commands.size()).forEach(s -> commands.set(s, GRAY + "/" + commands.get(s) + RESET));
					} else {
						IntStream.range(0, commands.size()).forEach(s -> commands.set(s, GRAY + commands.get(s) + RESET));
					}

					this.latestAutoComplete = commands.toArray(new String[0]);
				}
			}
		}
	}

	@Override
	protected MinecraftServer getServer() {
		return Minecraft.getMinecraft().getIntegratedServer();
	}
}
