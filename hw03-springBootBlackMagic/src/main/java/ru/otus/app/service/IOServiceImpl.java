package ru.otus.app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class IOServiceImpl implements IOService {

    private final PrintStream out;

    private final Scanner sc;

    public IOServiceImpl(@Value("#{ T(java.lang.System).out}") PrintStream out,
                         @Value("#{ T(java.lang.System).in}") InputStream in) {
        this.out = out;
        this.sc = new Scanner(in);
    }

    @Override
    public void print(String line) {
        out.println(line);
    }

    @Override
    public String read() {
        return sc.nextLine();
    }
}