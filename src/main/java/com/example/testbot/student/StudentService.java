package com.example.testbot.student;

import com.example.testbot.bot.TestBot;
import com.example.testbot.util.UserContextUtil;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.example.testbot.constants.MessageTexts.INPUT_FULL_NAME;
import static com.example.testbot.constants.MessageTexts.MAIN_WINDOW;
import static com.example.testbot.constants.MessageTexts.NUMBER_ALREADY_EXISTS;
import static com.example.testbot.util.UserState.NOT_IN_DB;
import static com.example.testbot.util.UserState.READY;
import static com.example.testbot.util.UserState.REGISTER_STEP_1;

import static com.example.testbot.bot.Keyboards.main;

@Service
@AllArgsConstructor
public class StudentService {
    private StudentRepository repository;
    private UserContextUtil userContextUtil;
    private final TestBot bot;

    public void registerUser() {
        var student = userContextUtil
                .getCurrentUser()
                .getStudent();

        if (student.getState().equals(NOT_IN_DB.name())) {

            var phoneNumber = userContextUtil
                                        .getCurrentMessage()
                                        .getContact()
                                        .getPhoneNumber();

            var phoneAlreadyExists = repository.findByPhone(phoneNumber).isPresent();

            if (phoneAlreadyExists) {
                bot.sendResponse(new SendMessage().setText(NUMBER_ALREADY_EXISTS));

            } else {

                repository.save(student.setPhone(phoneNumber).setState(REGISTER_STEP_1.name()));
                bot.sendResponse(new SendMessage().setText(INPUT_FULL_NAME));
            }
        } else {
            bot.sendResponse(new SendMessage().setText(MAIN_WINDOW).setReplyMarkup(main()));
        }
    }

    public void completeRegistration(String text) {
        BotApiMethod response = null;
        if (text.isBlank() || text.length() < 5) {
            response = new SendMessage()
                                .setText("Iltimos to`liq ism kiriting");
        } else {
            val student = userContextUtil.getCurrentUser()
                                                .getStudent()
                                                .setFullName(text)
                                                .setRegistered(true)
                                                .setState(READY.name());

            repository.save(student);
            response = new SendMessage()
                                .setReplyMarkup(main())
                                .setText("Siz ro`yxatdan o`tdingiz!");
        }
        bot.sendResponse(response);
    }
}
