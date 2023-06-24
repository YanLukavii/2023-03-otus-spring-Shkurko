package ru.otus.app.dao;

import org.springframework.stereotype.Service;
import ru.otus.app.domain.Question;
import ru.otus.app.repository.ResourceProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class QuestionDaoImpl implements QuestionDao {

    private final ResourceProvider resourceProvider;

    public QuestionDaoImpl(ResourceProvider resourceProvider) {
        this.resourceProvider = resourceProvider;
    }

    @Override
    public List<Question> findAll() {

        List<Question> questionList = new ArrayList<>();

        var fileName = resourceProvider.getFilePath();

        try (var inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
             var sc = new Scanner(inputStream)) {

            while (sc.hasNext()) {
                var line = sc.nextLine();
                String[] splitLine = line.split(";");
                if (splitLine.length == 5) {
                    questionList.add(new Question(splitLine[0],
                            List.of(splitLine[1], splitLine[2], splitLine[3]),
                            Integer.parseInt(splitLine[4])));
                } else {
                    throw new RuntimeException("something with file");
                }
            }
        } catch (IOException exception) {
            throw new QuestionDaoException(exception.getMessage(), exception.getCause());
        }
        return questionList;
    }
}