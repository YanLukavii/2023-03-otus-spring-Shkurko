package ru.otus.spring.service;

import java.io.PrintStream;

public class IOServiceImpl implements IOService {

    private final PrintStream out;

    public IOServiceImpl(PrintStream out) {
        this.out = out;

    }

    @Override
    public void print(String line) {
        out.println(line);
    }
}
