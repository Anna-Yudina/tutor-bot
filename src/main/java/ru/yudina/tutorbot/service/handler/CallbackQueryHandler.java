package ru.yudina.tutorbot.service.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.yudina.tutorbot.service.manager.*;
import ru.yudina.tutorbot.telegram.Bot;

import static ru.yudina.tutorbot.service.data.CallbackData.*;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CallbackQueryHandler {

    HelpManager helpManager;
    FeedbackManager feedbackManager;

    TimeTableManager timeTableManager;

    TaskManager taskManager;

    ProgressControlManager progressControlManager;

    public BotApiMethod<?> answer(CallbackQuery callbackQuery, Bot bot) {
        String callbackData = callbackQuery.getData();
        String keyWord = callbackData.split("_")[0];
        if (TIMETABLE.equals(keyWord)) {
            timeTableManager.answerCallbackQuery(callbackQuery, bot);
        }
        if(TASK.equals(keyWord)){
            return taskManager.answerCallbackQuery(callbackQuery, bot);
        }
        if(PROGRESS.equals(keyWord)){
            return progressControlManager.answerCallbackQuery(callbackQuery, bot);
        }
        switch (callbackData) {
            case FEEDBACK -> {
                return feedbackManager.answerCallbackQuery(callbackQuery, bot);
            }
            case HELP -> {
                return helpManager.answerCallbackQuery(callbackQuery, bot);
            }
        }
        return null;
    }
}
