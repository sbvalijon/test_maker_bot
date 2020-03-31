package com.example.testbot.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static com.example.testbot.constants.MessageTexts.BEGIN_EXAM;
import static com.example.testbot.constants.MessageTexts.CONTACT_ADMIN;
import static com.example.testbot.constants.MessageTexts.OTHERS;
import static com.example.testbot.constants.MessageTexts.PREVIOUS_RESULTS;
import static com.example.testbot.constants.MessageTexts.TRAFFIC_RULES;

public class Keyboards {

    public static ReplyKeyboardMarkup shareNumberBtn() {
        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("Raqamni ulashish")
                .setRequestContact(true));

        return new ReplyKeyboardMarkup()
                .setResizeKeyboard(true)
                .setKeyboard(List.of(row));
    }

    public static ReplyKeyboardMarkup variants(String... abc) {
        List<KeyboardRow> rows = new ArrayList<>();
        for (String variant : abc) {
            KeyboardRow row = new KeyboardRow();
            row.add(new KeyboardButton(variant));

            rows.add(row);
        }

        return new ReplyKeyboardMarkup()
                .setKeyboard(rows);
    }

    public static ReplyKeyboardMarkup main() {
        List<KeyboardRow> rows = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(BEGIN_EXAM));
        row1.add(new KeyboardButton(PREVIOUS_RESULTS));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton(TRAFFIC_RULES));
        row2.add(new KeyboardButton(OTHERS));

        KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton(CONTACT_ADMIN));

        rows.add(row1);
        rows.add(row2);
        rows.add(row3);

        return new ReplyKeyboardMarkup()
                .setResizeKeyboard(true)
                .setKeyboard(rows);
    }
}
