package org.telegram.telegrambots.api.commands;

import org.telegram.telegrambots.BotLogger;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;

/**
 * standard help command, which gets registered by default, to supply a list of all available commands
 *
 * @author tschulz
 */
public class HelpBotCommand extends BotCommand {

    private static final String LOGTAG = "HELPCOMMAND";
    private final ICommandRegistry commandRegistry;

    public HelpBotCommand(ICommandRegistry commandRegistry, String botToken) {
        super("help", "Gives an overview over all Commands registered for this bot", botToken);
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void execute(String[] arguments, Chat chat) {

        for (BotCommand registeredBotCommand : commandRegistry.getRegisteredCommands()) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chat.getId());
            sendMessage.enableHtml(true);
            sendMessage.setText("<b>" + COMMAND_INIT_CHARACTER + registeredBotCommand.getCommandIdentifier() + "</b>\n" + registeredBotCommand.getDescription());

            try {
                sendMessage(sendMessage);
            } catch (TelegramApiException e) {
                BotLogger.error("Failed to send HelpMessage", LOGTAG, e);
            }
        }
    }
}
