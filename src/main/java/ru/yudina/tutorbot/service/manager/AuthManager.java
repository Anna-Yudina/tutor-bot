package ru.yudina.tutorbot.service.manager;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.yudina.tutorbot.entity.user.Action;
import ru.yudina.tutorbot.entity.user.Role;
import ru.yudina.tutorbot.entity.user.User;
import ru.yudina.tutorbot.repository.UserRepo;
import ru.yudina.tutorbot.service.factory.AnswerMethodFactory;
import ru.yudina.tutorbot.service.factory.KeyboardFactory;
import ru.yudina.tutorbot.telegram.Bot;

import java.util.List;

import static ru.yudina.tutorbot.service.data.CallbackData.*;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class AuthManager extends AbstractManager {

    UserRepo userRepo;

    AnswerMethodFactory answerMethodFactory;

    KeyboardFactory keyboardFactory;

    @Override
    public BotApiMethod<?> answerCommand(Message message, Bot bot) {
        return null;
    }

    @Override
    public BotApiMethod<?> answerMessage(Message message, Bot bot) {
        Long chatId = message.getChatId();
        User user = userRepo.findById(chatId).get();
        user.setAction(Action.AUTH);
        userRepo.save(user);
        return answerMethodFactory.getSendMessage(
                chatId,
                """
                        Выберите свою роль
                        """,
                keyboardFactory.getInlineKeyboard(
                        List.of("Ученик", "Учитель"),
                        List.of(2),
                        List.of(AUTH_STUDENT, AUTH_TEACHER)
                )
        );
    }

    @Override
    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery, Bot bot) {
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        User user = userRepo.findById(callbackQuery.getMessage().getChatId()).get();

        if (AUTH_TEACHER.equals(callbackQuery.getData())) {
            user.setRole(Role.TEACHER);
        } else {
            user.setRole(Role.STUDENT);
        }
        user.setAction(Action.FREE);
        userRepo.save(user);

        try {
            bot.execute(answerMethodFactory.getAnswerCallbackQuery(
                    callbackQuery.getId(),
                    """
                            Авторизация прошла успешно, повторите предыдущий запрос!
                            """
            ));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }

        return answerMethodFactory.getDeleteMessage(chatId, messageId);
    }
}
