package ru.yudina.tutorbot.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.yudina.tutorbot.entity.user.User;
import ru.yudina.tutorbot.repository.UserRepository;
import ru.yudina.tutorbot.service.handler.CallbackQueryHandler;
import ru.yudina.tutorbot.service.handler.CommandHandler;
import ru.yudina.tutorbot.service.handler.MessageHandler;
import ru.yudina.tutorbot.telegram.Bot;

@Service
@Slf4j
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UpdateDispatcher {
    MessageHandler messageHandler;
    CommandHandler commandHandler;
    CallbackQueryHandler callbackQueryHandler;
    UserRepository userRepository;

    public BotApiMethod<?> distribute(Update update, Bot bot) {
        if (update.hasCallbackQuery()) {
            return callbackQueryHandler.answer(update.getCallbackQuery(), bot);
        }
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                userRepository.save(User.builder()
                        .chatId(message.getChatId())
                        .build());
                if (message.getText().charAt(0) == '/') {
                    return commandHandler.answer(message, bot);
                }
            }
            return messageHandler.answer(message, bot);
        }
        log.info("Unsupported update " + update);
        return null;
    }
}
