package ru.yudina.tutorbot.service.manager;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.yudina.tutorbot.entity.user.User;
import ru.yudina.tutorbot.entity.user.UserDetails;
import ru.yudina.tutorbot.repository.UserRepo;
import ru.yudina.tutorbot.service.factory.AnswerMethodFactory;
import ru.yudina.tutorbot.telegram.Bot;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class ProfileManager extends AbstractManager {

    AnswerMethodFactory answerMethodFactory;

    UserRepo userRepo;

    @Override
    public BotApiMethod<?> answerCommand(Message message, Bot bot) {
        return showProfile(message);
    }

    @Override
    public BotApiMethod<?> answerMessage(Message message, Bot bot) {
        return null;
    }

    @Override
    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery, Bot bot) {
        return null;
    }

    private BotApiMethod<?> showProfile(Message message) {
        Long chatId = message.getChatId();
        StringBuilder text = new StringBuilder("\uD83D\uDC64 Профиль\n");

        User user = userRepo.findById(chatId).get();
        UserDetails userDetails = user.getUserDetails();

        if (userDetails.getName() != null) {
            text.append("\uFE0FИмя пользователя - ").append(userDetails.getName());
        } else {
            text.append("\uFE0FИмя пользователя - ").append(userDetails.getFirstName());
        }

        text.append("\uFE0F\nРоль - ").append(user.getRole().name());
        text.append("\uFE0F\nВаш уникальный токен - ").append(user.getToken());
        text.append("\n\n Токен нужен для того, чтобы ученик или учитель могли установить между собой связь");

        return answerMethodFactory.getSendMessage(
                chatId,
                text.toString(),
                null
        );
    }
}
