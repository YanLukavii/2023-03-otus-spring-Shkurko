package ru.otus.app.service;

import org.springframework.stereotype.Service;
import ru.otus.app.dao.QuestionDao;
import ru.otus.app.domain.Question;

import java.util.List;

@Service
public class TestStartImpl implements TestStart {

    private final StudentService studentService;

    private final TestResultService testResultService;

    private final QuestionDao questionDao;

    private final OutputQuestionAndOptionsService outputQuestionAndOptionsService;

    private final IOService ioService;

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
                ioService.print("+");
                student.setScore(student.getScore() + 1);
            } else {
                ioService.print("-");
            }
        }
        testResultService.showResult(student);
    }
}