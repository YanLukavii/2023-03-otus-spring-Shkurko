package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;

@Service
public class TestResultServiceImpl implements TestResultService {

    private final static String PASSED_PATTERN = " TEST PASSED";

    private final static String FAILED_PATTERN = " TEST FAILED";

    private final IOService ioService;

    private final int passedPoints;

    @Autowired
    public TestResultServiceImpl(IOService ioService, @Value("${test.pass-points}") int passedPoints) {
        this.ioService = ioService;
        this.passedPoints = passedPoints;
    }

    @Override
    public void showResult(Student student) {

        ioService.print(student.getName() + " " + student.getSurname() + " your result " + student.getScore());
        if (student.getScore() >= passedPoints) {
            ioService.print(PASSED_PATTERN);
        } else {
            ioService.print(FAILED_PATTERN);
        }
    }
}