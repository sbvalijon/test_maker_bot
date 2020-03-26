package com.example.testbot.bot;

import com.example.testbot.util.UserContext;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.example.testbot.bot.Keyboards.shareNumberBtn;
import static com.example.testbot.bot.Keyboards.variants;
import static com.example.testbot.constants.MessageTexts.BEGIN_EXAM;

@Component
@AllArgsConstructor
public class TextHandler {
    private TestBot bot;

    public void handle(Update update)  {
        String text = update.getMessage().getText();
        BotApiMethod response = defaultMessage();

        if (text.equals("/start")) {
            handlStart();
        } else if (text.equals(BEGIN_EXAM)) {
            response = new SendMessage()
                                .setReplyMarkup(variants("A", "B", "C", "D"))
                                .setText("Test matni bu yerda");

            bot.sendResponse(response);
        } else {
            bot.sendResponse(response);
        }
    }

    private void handlStart() {
        BotApiMethod response = new SendMessage()
                            .setReplyMarkup(shareNumberBtn())
                            .setText("Ro`yxatdan o`tish uchun raqamni ulashing");

        bot.sendResponse(response);
    }

    private BotApiMethod defaultMessage() {
        UserContext userDetails = (UserContext) SecurityContextHolder.getContext();

        return new SendMessage()
                        .setChatId(userDetails.getChatId())
                        .setText("Komanda noaniq!");
    }
}
