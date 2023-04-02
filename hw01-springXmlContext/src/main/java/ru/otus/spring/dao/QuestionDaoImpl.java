package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.domain.Question;
import ru.otus.spring.repository.ResourceProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class QuestionDaoImpl implements QuestionDao {

    private final ResourceProvider resourceProvider;

    @Override
    public List<Question> findAll() {

        List<Question> questionList = new ArrayList<>();

        var fileName = resourceProvider.getFilePath();

        try (var inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
             var sc = new Scanner(inputStream)) {

            while (sc.hasNext()) {
                var line = sc.nextLine();
                String[] splitLine = line.split(";");
                questionList.add(new Question(splitLine[0], List.of(splitLine[1], splitLine[2], splitLine[3])));

            }
        } catch (IOException | NullPointerException exception) {
            throw new QuestionDaoException(exception.getMessage(), exception.getCause());
        }
        return questionList;
    }
}
