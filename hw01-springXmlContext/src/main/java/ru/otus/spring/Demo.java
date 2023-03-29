package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.QuestionService;
import ru.otus.spring.service.QuestionsServiceImpl;

import java.util.List;

public class Demo {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

        QuestionService service = context.getBean(QuestionsServiceImpl.class);

        List<Question> questionList = service.getAll();

        System.out.println(questionList); //все вопросы и ответы
        System.out.println("Question: " + questionList.get(1).getQuestion()); //первый вопрос
        System.out.println("Answers: " + questionList.get(1).getAnswers()); //варианты ответов на первый вопрос

        context.close();
    }
}
