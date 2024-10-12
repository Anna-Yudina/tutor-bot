package ru.yudina.tutorbot.service.manager;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.yudina.tutorbot.service.factory.AnswerMethodFactory;
import ru.yudina.tutorbot.service.factory.KeyboardFactory;
import ru.yudina.tutorbot.telegram.Bot;

import java.util.List;

import static ru.yudina.tutorbot.service.data.CallbackData.FEEDBACK;
import static ru.yudina.tutorbot.service.data.CallbackData.HELP;

@Component
@AllArgsConstructor
@Slf4j
public class StartManager  extends AbstractManager{

    private final AnswerMethodFactory answerMethodFactory;

    private final KeyboardFactory keyboardFactory;

    @Override
    public BotApiMethod<?> answerCommand(Message message, Bot bot) {
        log.info("Зашли в метод StartManager.answerCommand");
        return answerMethodFactory.getSendMessage(
                message.getChatId(),
                        """
                        Приветствую в Tutor-Bot
                        """,
                keyboardFactory.getInlineKeyboard(
                        List.of("Помощь", "Обратная связь"),
                        List.of(2),
                        List.of(HELP, FEEDBACK)
                ));
    }

    @Override
    public BotApiMethod<?> answerMessage(Message message, Bot bot) {
        return null;
    }

    @Override
    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery, Bot bot) {
        return null;
    }
}
