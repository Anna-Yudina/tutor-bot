package ru.yudina.tutorbot.service.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.yudina.tutorbot.service.factory.KeyboardFactory;
import ru.yudina.tutorbot.telegram.Bot;

import java.util.List;

import static ru.yudina.tutorbot.service.data.Command.*;

@Service
@AllArgsConstructor
public class CommandHandler {

    private final KeyboardFactory keyboardFactory;

    public BotApiMethod<?> answer(Message message, Bot bot) {
        String command = message.getText();
        switch (command) {
            case START -> {
                return start(message);
            }
            case FEEDBACK -> {
                return feedback(message);
            }
            case HELP -> {
                return help(message);
            }
            default -> {
                return defaultAnswer(message);
            }
        }
    }

    private BotApiMethod<?> start(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text("""
                        Приветствую в Tutor-Bot
                        """)
                .replyMarkup(keyboardFactory.getInlineKeyboard(
                        List.of("Помощь", "Обратная связь"),
                        List.of(2),
                        List.of("help", "feedback")
                ))
                .build();
    }

    private BotApiMethod<?> feedback(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text("""
                        Ссылки для обратной связи
                        https://t.me/Anuta_Nuty
                        """)
                .build();
    }

    private BotApiMethod<?> help(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text("""
                        Доступные команды:
                        - start
                        - feedback
                        - help
                                                
                        Доступные функции:
                        - Расписание
                        - Домашнее задание
                        - Контроль успеваемости
                        """)
                .build();
    }

    private BotApiMethod<?> defaultAnswer(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text("Неподдерживаемая команда")
                .build();
    }
}
