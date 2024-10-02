package ru.yudina.tutorbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.yudina.tutorbot.service.handler.CallbackQueryHandler;
import ru.yudina.tutorbot.service.handler.CommandHandler;
import ru.yudina.tutorbot.service.handler.MessageHandler;
import ru.yudina.tutorbot.telegram.Bot;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateDispatcher {
    private final MessageHandler messageHandler;
    private final CommandHandler commandHandler;
    private final CallbackQueryHandler callbackQueryHandler;

    public BotApiMethod<?> distribute(Update update, Bot bot) {
        if (update.hasCallbackQuery()) {
            return callbackQueryHandler.answer(update.getCallbackQuery(), bot);
        }
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText() && message.getText().charAt(0) == '/') {
                return commandHandler.answer(message, bot);
            }
            return messageHandler.answer(message, bot);
        }
        log.info("Unsupported update " + update);
        return null;
    }
}
