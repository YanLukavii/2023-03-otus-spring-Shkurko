package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.service.QuestionsServiceImpl;

public class Demo {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        context.getBean(QuestionsServiceImpl.class).printAllQuestions();
    }
}
