package ru.otus.hw.springbatch.chandgelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw.springbatch.model.mongo.AuthorMongo;
import ru.otus.hw.springbatch.model.mongo.BookMongo;
import ru.otus.hw.springbatch.model.mongo.CommentMongo;
import ru.otus.hw.springbatch.model.mongo.GenreMongo;


@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private AuthorMongo authorMongo1;

    private AuthorMongo authorMongo2;

    private AuthorMongo authorMongo3;

    private GenreMongo genreMongo1;

    private GenreMongo genreMongo2;

    private GenreMongo genreMongo3;

    private BookMongo bookMongo1;

    private BookMongo bookMongo2;

    private BookMongo bookMongo3;

    @ChangeSet(order = "000", id = "dropDB", author = "yan", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "yan", runAlways = true)
    public void initAuthors(MongockTemplate mongoTemplate) {
        authorMongo1 = mongoTemplate.save(new AuthorMongo("Author_1"));
        authorMongo2 = mongoTemplate.save(new AuthorMongo("Author_2"));
        authorMongo3 = mongoTemplate.save(new AuthorMongo("Author_3"));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "yan", runAlways = true)
    public void initGenres(MongockTemplate mongoTemplate) {
        genreMongo1 = mongoTemplate.save(new GenreMongo("Genre_1"));
        genreMongo2 = mongoTemplate.save(new GenreMongo("Genre_2"));
        genreMongo3 = mongoTemplate.save(new GenreMongo("Genre_3"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "yan", runAlways = true)
    public void initBooks(MongockTemplate mongoTemplate) {
        bookMongo1 = mongoTemplate.save(new BookMongo("Book_1", authorMongo1, genreMongo1));
        bookMongo2 = mongoTemplate.save(new BookMongo("Book_2", authorMongo2, genreMongo2));
        bookMongo3 = mongoTemplate.save(new BookMongo("Book_3", authorMongo3, genreMongo3));
    }

    @ChangeSet(order = "004", id = "initComments", author = "yan", runAlways = true)
    public void initComments(MongockTemplate mongoTemplate) {
          mongoTemplate.save(new CommentMongo("Comment_1", bookMongo1));
          mongoTemplate.save(new CommentMongo("Comment_2", bookMongo2));
          mongoTemplate.save(new CommentMongo("Comment_3", bookMongo3));
    }

}