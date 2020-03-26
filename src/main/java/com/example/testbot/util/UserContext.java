package com.example.testbot.util;

import com.example.testbot.student.Student;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.telegram.telegrambots.meta.api.objects.Update;

@Data
@Builder
public class UserContext implements SecurityContext {
    private long chatId;
    private Student student;
    private Update update;

    public boolean isNotRegistered() {

        return !student.isRegistered();
    }

    public Authentication getAuthentication() {
        return null;
    }

    public void setAuthentication(Authentication authentication) {

    }
}
