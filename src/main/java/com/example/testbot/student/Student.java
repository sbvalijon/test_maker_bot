package com.example.testbot.student;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@Document(collection = "student")
@Accessors(chain = true)
public class Student {

    @Id
    private String id;

    @Field("tg_chat_id")
    private long tgChatId;

    @Field("full_name")
    private String fullName;

    @Field("state")
    private String state;

    @Field("is_registered")
    private boolean isRegistered;

    @Field("phone")
    private String phone;
}
