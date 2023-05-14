package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.service.TestStart;

@Configuration
@ComponentScan
public class Demo {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(Demo.class);
        context.getBean(TestStart.class).startTest();
    }
}
