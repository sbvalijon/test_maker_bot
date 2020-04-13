package com.example.testbot.question;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Builder
@Document(collection = "questions")
@Accessors(chain = true)
public class Question {

    @Id
    private String id;

    @Field("text")
    private String text;

    private List<Answer> answers;

    @Field("tags")
    private List<String> tags;

    @Data
    @Accessors(chain = true)
    public static class Answer {

        @Field("is_correct")
        private boolean isCorrect;

        @Field("text")
        private String text;
    }
}