package com.example.testbot.student;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {

    Optional<Student> findByTgChatId(long tgId);

    Optional<Student> findByPhone(String phone);

}
