package ru.otus.hw.springbatch.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comments")
public class CommentMongo {

    @Id
    private String id;

    private String text;

    @DBRef(lazy = true)
    private BookMongo bookMongo;

    public CommentMongo(String text, BookMongo bookMongo) {
        this.text = text;
        this.bookMongo = bookMongo;
    }
}