package ru.yudina.tutorbot.service.factory;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeyboardFactory {

    public InlineKeyboardMarkup getInlineKeyboard(
            List<String> text,
            List<Integer> configuration,
            List<String> data
    ) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        int index = 0;
        for (Integer rowNumber : configuration) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (int i = 0; i < rowNumber; i++) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(text.get(index));
                button.setCallbackData(data.get(index));
                row.add(button);
                index++;
            }
            keyboard.add(row);
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getReplyKeyboard(List<String> text,
                                                List<Integer> configuration) {
        List<KeyboardRow> keyboard = new ArrayList<>();
        int index = 0;
        for (Integer rowNumber : configuration) {
            KeyboardRow row = new KeyboardRow();
            for (int i = 0; i < rowNumber; i++) {
                KeyboardButton button = new KeyboardButton();
                button.setText(text.get(index));
                row.add(button);
                index++;
            }
            keyboard.add(row);
        }
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
}
