package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;

import java.util.List;

@Service
public class TestStartImpl implements TestStart {

    private final StudentService studentService;

    private final TestResultService testResultService;

    private final QuestionDao questionDao;

    private final OutputQuestionAndOptionsService outputQuestionAndOptionsService;

    private final IOService ioService;

    @Autowired
    public TestStartImpl(StudentService studentService,
                         TestResultService testResultService,
                         QuestionDao questionDao,
                         OutputQuestionAndOptionsService outputQuestionAndOptionsService,
                         IOService ioService) {
        this.studentService = studentService;
        this.testResultService = testResultService;
        this.questionDao = questionDao;
        this.outputQuestionAndOptionsService = outputQuestionAndOptionsService;
        this.ioService = ioService;
    }

    @Override
    public void startTest() {

        var student = studentService.createStudent();

        List<Question> questionsList = questionDao.findAll();

        for (Question question : questionsList) {

            outputQuestionAndOptionsService.printQuestionAndOptions(question);
            int studentOption = Integer.parseInt(ioService.read());

            if (studentOption == question.getRightOption()) {
                ioService.print("RIGHT");
                student.setScore(student.getScore() + 1);
            } else {
                ioService.print("WRONG");
            }
        }
        testResultService.showResult(student);
    }
}