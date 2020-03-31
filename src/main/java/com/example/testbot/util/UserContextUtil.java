package com.example.testbot.util;

import com.example.testbot.student.Student;
import com.example.testbot.student.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.example.testbot.util.UserState.NOT_IN_DB;

@Service
@AllArgsConstructor
public class UserContextUtil {

    private StudentRepository studentRepository;
    private UpdateUtil updateUtil;

    public void setContext(Update update) {
        long chatId = updateUtil.chatId(update);
        var unregisteredStudent = Student
                                    .builder()
                                    .tgChatId(chatId)
                                    .state(NOT_IN_DB.name())
                                    .isRegistered(false)
                                    .build();

        var student = studentRepository
                                .findByTgChatId(chatId)
                                .orElse(unregisteredStudent);

        UserContext userContext = UserContext
                                        .builder()
                                        .chatId(chatId)
                                        .update(update)
                                        .student(student)
                                        .build();

        SecurityContextHolder.setContext(userContext);
    }

    public UserContext getCurrentUser() {

        return (UserContext) SecurityContextHolder.getContext();
    }

    public UserState getState() {
        return UserState.valueOf(getCurrentUser().getStudent().getState().toUpperCase());
    }

    public Message getCurrentMessage() {

        return getCurrentUser().getUpdate().getMessage();
    }
}
