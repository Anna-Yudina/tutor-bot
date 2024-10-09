package ru.yudina.tutorbot.service.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.yudina.tutorbot.service.manager.FeedbackManager;
import ru.yudina.tutorbot.service.manager.HelpManager;
import ru.yudina.tutorbot.telegram.Bot;

import static ru.yudina.tutorbot.service.data.CallbackData.*;

@Service
@AllArgsConstructor
public class CallbackQueryHandler {

    private final HelpManager helpManager;
    private final FeedbackManager feedbackManager;

    public BotApiMethod<?> answer(CallbackQuery callbackQuery, Bot bot) {
        String callbackData = callbackQuery.getData();

        switch (callbackData) {
            case FEEDBACK -> {
                return feedbackManager.answerCallbackQuery(callbackQuery);
            }
            case HELP -> {
                return helpManager.answerCallbackQuery(callbackQuery);
            }
        }
        return null;
    }
}
