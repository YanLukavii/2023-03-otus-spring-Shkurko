package ru.otus.hw.springbatch;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.h2.tools.Console;

import java.sql.SQLException;

@EnableMongock
@SpringBootApplication
public class Main {
    public static void main(String[] args) throws SQLException {
        SpringApplication.run(Main.class, args);
        Console.main(args);
    }
}