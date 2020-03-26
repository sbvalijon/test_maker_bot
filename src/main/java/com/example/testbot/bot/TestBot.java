package com.example.testbot.bot;

import com.example.testbot.student.StudentService;
import com.example.testbot.util.UserContext;
import com.example.testbot.util.UserContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;

@Component
public class TestBot extends TelegramLongPollingBot {
    private static Logger logger = LoggerFactory.getLogger(TestBot.class);
    @Value("${bot.token}")
    private String token;
    @Value("${bot.name}")
    private String name;

    private TextHandler textHandler;
    private UserContextUtil userContextUtil;
    private StudentService studentService;

    static {
        ApiContextInitializer.init();
    }

    @Override
    public void onUpdateReceived(Update update) {
        logInfo(update);

        if (update.hasMessage()) {
            userContextUtil.setContext(update);

            if (update.getMessage().hasText()) {
                textHandler.handle(update);

            } else if (update.getMessage().hasContact()) {

                UserContext currentUser = userContextUtil.getCurrentUser();
                if (currentUser.isNotRegistered()) {
                    studentService.registerUser();
                }
            }
        }
    }

    private void logInfo(Update update) {
        if (logger.isInfoEnabled())
            logger.info(update.toString());
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @PostConstruct
    public void init() throws TelegramApiRequestException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        telegramBotsApi.registerBot(this);

    }

    public void sendResponse(BotApiMethod response) {
        if (response instanceof SendMessage) {
            ((SendMessage) response).setChatId(userContextUtil.getCurrentUser().getChatId());
        }

        try {
            this.execute(response);

        } catch (TelegramApiException e) {
            logger.error("Error sending response", e);
        }
    }

    @Autowired
    public void setTextHandler(TextHandler textHandler) {
        this.textHandler = textHandler;
    }

    @Autowired
    public void setUserContextUtil(UserContextUtil userContextUtil) {
        this.userContextUtil = userContextUtil;
    }

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
}
