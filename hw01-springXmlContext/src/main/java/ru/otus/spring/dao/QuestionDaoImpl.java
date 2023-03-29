package ru.otus.spring.dao;

import lombok.AllArgsConstructor;
import ru.otus.spring.domain.Question;
import ru.otus.spring.repository.ResourceReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class QuestionDaoImpl implements QuestionDao {

    private final ResourceReader resourceReader;

    @Override
    public List<Question> findAll() {

        List<Question> questionList = new ArrayList<>();

        var resource = resourceReader.getResource();

        try (Scanner sc = new Scanner(resource.getInputStream())) {
            while (sc.hasNext()) {
                String line = sc.nextLine();
                String[] splitLine = line.split(";");
                questionList.add(new Question(splitLine[0], List.of(splitLine[1], splitLine[2], splitLine[3])));
            }
        } catch (IOException e) {
             System.err.println(e.getMessage());
         }
        return questionList;
    }
}
