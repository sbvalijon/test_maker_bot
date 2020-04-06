package com.example.testbot.bot;

import com.example.testbot.student.StudentService;
import com.example.testbot.util.UserContext;
import com.example.testbot.util.UserContextUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.example.testbot.bot.Keyboards.main;
import static com.example.testbot.bot.Keyboards.shareNumberBtn;
import static com.example.testbot.bot.Keyboards.variants;
import static com.example.testbot.constants.MessageTexts.BEGIN_EXAM;
import static com.example.testbot.constants.MessageTexts.WELCOME_BACK;
import static com.example.testbot.util.UserState.NOT_IN_DB;
import static com.example.testbot.util.UserState.READY;
import static com.example.testbot.util.UserState.REGISTER_STEP_1;

@Component
@AllArgsConstructor
public class TextHandler {
    private TestBot bot;
    private UserContextUtil userContextUtil;
    private StudentService studentService;

    public void handle(Update update) {
        String text = update.getMessage().getText();
        BotApiMethod response = defaultMessage();

        if (text.equals("/start")) {
            handleStart();
            return;
        }

        //TODO check state
        if (userContextUtil.getState().equals(REGISTER_STEP_1)) {
            studentService.completeRegistration(text);
            return;

        } else if (userContextUtil.getState().equals(READY)) {
            if (text.equals(BEGIN_EXAM)) {
                response = new SendMessage()
                        .setReplyMarkup(variants("A", "B", "C", "D"))
                        .setText("Test matni bu yerda");

                bot.sendResponse(response);
            } else {
                bot.sendResponse(response);
            }
        }


    }



    private void handleStart() {
        BotApiMethod response = null;
        if (userContextUtil.getState().equals(NOT_IN_DB)) {
            response = new SendMessage()
                    .setReplyMarkup(shareNumberBtn())
                    .setText("Ro`yxatdan o`tish uchun raqamni ulashing");
        } else {
            response = new SendMessage()
                    .setReplyMarkup(main())
                    .setText(WELCOME_BACK + "\n" + userContextUtil.getCurrentUser().getStudent().getFullName());
        }

        bot.sendResponse(response);
    }

    private BotApiMethod defaultMessage() {
        UserContext userDetails = (UserContext) SecurityContextHolder.getContext();

        return new SendMessage()
                .setChatId(userDetails.getChatId())
                .setText("Komanda noaniq!");
    }
}
