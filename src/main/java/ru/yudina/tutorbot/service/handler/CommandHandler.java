package ru.yudina.tutorbot.service.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.yudina.tutorbot.service.manager.FeedbackManager;
import ru.yudina.tutorbot.service.manager.HelpManager;
import ru.yudina.tutorbot.service.manager.StartManager;
import ru.yudina.tutorbot.telegram.Bot;

import java.util.List;

import static ru.yudina.tutorbot.service.data.Command.*;

@Service
@AllArgsConstructor
public class CommandHandler {

    private final HelpManager helpManager;

    private final FeedbackManager feedbackManager;

    private final StartManager startManager;

    public BotApiMethod<?> answer(Message message, Bot bot) {
        String command = message.getText();
        switch (command) {
            case START_COMMAND -> {
                return startManager.answerCommand(message);
            }
            case FEEDBACK_COMMAND -> {
                return feedbackManager.answerCommand(message);
            }
            case HELP_COMMAND -> {
                return helpManager.answerCommand(message);
            }
            default -> {
                return defaultAnswer(message);
            }
        }
    }

    private BotApiMethod<?> defaultAnswer(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text("Неподдерживаемая команда")
                .build();
    }
}
