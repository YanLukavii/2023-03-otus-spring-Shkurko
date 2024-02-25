package ru.otus.hw.springbatch.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class BookMongo {

    @Id
    private String id;

    private String title;

    private AuthorMongo authorMongo;

    private GenreMongo genreMongo;

    public BookMongo(String title, AuthorMongo authorMongo, GenreMongo genreMongo) {
        this.title = title;
        this.authorMongo = authorMongo;
        this.genreMongo = genreMongo;
    }
}