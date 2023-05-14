package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;

@Service
public class StudentServiceImpl implements StudentService {

    private final IOService ioService;

    @Autowired
    public StudentServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public Student createStudent() {

        ioService.print("Write Your Name");
        String name = ioService.read();
        ioService.print("Write Your Surname");
        String surname = ioService.read();
        return new Student(name, surname);
    }

}