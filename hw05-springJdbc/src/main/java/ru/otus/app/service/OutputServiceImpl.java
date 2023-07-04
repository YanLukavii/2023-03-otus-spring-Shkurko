package ru.otus.app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.PrintStream;

@Service
public class OutputServiceImpl implements OutputService {

    private final PrintStream out;


    public OutputServiceImpl(@Value("#{ T(java.lang.System).out}") PrintStream out) {
        this.out = out;

    }

    @Override
    public void print(String line) {
        out.println(line);
    }

}