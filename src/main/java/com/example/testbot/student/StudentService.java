package com.example.testbot.student;

import com.example.testbot.bot.TestBot;
import com.example.testbot.util.UserContextUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.example.testbot.constants.MessageTexts.INPUT_FULL_NAME;
import static com.example.testbot.constants.MessageTexts.MAIN_WINDOW;
import static com.example.testbot.constants.MessageTexts.NUMBER_ALREADY_EXISTS;
import static com.example.testbot.util.UserState.NOT_IN_DB;
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
}
