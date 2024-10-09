package ru.yudina.tutorbot.service.manager;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.yudina.tutorbot.service.factory.AnswerMethodFactory;
import ru.yudina.tutorbot.service.factory.KeyboardFactory;
import ru.yudina.tutorbot.telegram.Bot;

@Component
@AllArgsConstructor
public class FeedbackManager extends AbstractManager{

    private final AnswerMethodFactory answerMethodFactory;

    private final KeyboardFactory keyboardFactory;

    @Override
    public BotApiMethod<?> answerCommand(Message message, Bot bot) {
        return answerMethodFactory.getSendMessage(
                message.getChatId(),
                """
                        Ссылки для обратной связи
                        https://t.me/Anuta_Nuty
                        """,
                null
        );
    }


    @Override
    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery, Bot bot) {
        return answerMethodFactory.getEditMessageText(
                callbackQuery,
                """
                        Ссылки для обратной связи
                        https://t.me/Anuta_Nuty
                        """,
                null);

    }

    @Override
    public BotApiMethod<?> answerMessage(Message message, Bot bot) {
        return null;
    }
}
