package com.example.testbot.util;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class UpdateUtil {

    public long chatId(Update update) {
        return update.getMessage().getChatId();
    }
}
