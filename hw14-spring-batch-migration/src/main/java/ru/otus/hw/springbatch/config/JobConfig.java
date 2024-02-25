package ru.otus.hw.springbatch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoPagingItemReader;
import org.springframework.batch.item.data.builder.MongoPagingItemReaderBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.hw.springbatch.dto.BookDto;
import ru.otus.hw.springbatch.dto.CommentDto;
import ru.otus.hw.springbatch.model.h2.Author;
import ru.otus.hw.springbatch.model.h2.Genre;
import ru.otus.hw.springbatch.model.mongo.AuthorMongo;
import ru.otus.hw.springbatch.model.mongo.BookMongo;
import ru.otus.hw.springbatch.model.mongo.CommentMongo;
import ru.otus.hw.springbatch.model.mongo.GenreMongo;
import ru.otus.hw.springbatch.service.RelationshipService;

import javax.sql.DataSource;
import java.util.HashMap;


@SuppressWarnings("unused")
@Configuration
public class JobConfig {

    public static final String MIGRATE_DB_JOB_NAME = "migrateDbJob";

    private static final int CHUNK_SIZE = 5;

    private final Logger logger = LoggerFactory.getLogger("Batch");


    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;


    @Bean
    public MongoPagingItemReader<AuthorMongo> authorMongoReader(MongoTemplate mongoTemplate) {
        return new MongoPagingItemReaderBuilder<AuthorMongo>()
                .name("authorMongoReader")
                .template(mongoTemplate)
                .jsonQuery("{}")
                .targetType(AuthorMongo.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<Author> authorWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Author>()
                .dataSource(dataSource)
                .sql("INSERT INTO authors (id, full_name) VALUES (:id, :fullName)")
                .beanMapped()
                .build();
    }


    @Bean
    public ItemProcessor<AuthorMongo, Author> authorProcessor(RelationshipService relationshipService) {
        return relationshipService::updateAuthor;
    }

    @Bean
    public Step migrateAuthorStep(ItemReader<AuthorMongo> authorMongoReader,
                                  JdbcBatchItemWriter<Author>  authorWriter,
                                  ItemProcessor<AuthorMongo, Author> processor
    ) {
        return new StepBuilder("migrateAuthorStep", jobRepository)
                .<AuthorMongo, Author>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(authorMongoReader)
                .processor(processor)
                .writer(authorWriter)
                .build();
    }

    @Bean
    public MongoPagingItemReader<GenreMongo> genreMongoReader(MongoTemplate mongoTemplate) {
        return new MongoPagingItemReaderBuilder<GenreMongo>()
                .name("genreMongoReader")
                .template(mongoTemplate)
                .jsonQuery("{}")
                .targetType(GenreMongo.class)
                .sorts(new HashMap<>())
                .build();
    }


    @Bean
    public JdbcBatchItemWriter<Genre> genreWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Genre>()
                .dataSource(dataSource)
                .sql("INSERT INTO genres (id, name) VALUES (:id, :name)")
                .beanMapped()
                .build();
    }


    @Bean
    public ItemProcessor<GenreMongo, Genre> genreProcessor(RelationshipService relationshipService) {
        return relationshipService::updateGenre;
    }

    @Bean
    public Step migrateGenreStep(ItemReader<GenreMongo> genreMongoItemReader,
                                 JdbcBatchItemWriter<Genre>  genreJdbcBatchItemWriter,
                                 ItemProcessor<GenreMongo, Genre> processor
    ) {
        return new StepBuilder("migrateGenreStep", jobRepository)
                .<GenreMongo, Genre>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(genreMongoItemReader)
                .processor(processor)
                .writer(genreJdbcBatchItemWriter)
                .build();
    }

    @Bean
    public MongoPagingItemReader<BookMongo> bookMongoReader(MongoTemplate mongoTemplate) {
        return new MongoPagingItemReaderBuilder<BookMongo>()
                .name("bookMongoReader")
                .template(mongoTemplate)
                .jsonQuery("{}")
                .targetType(BookMongo.class)
                .sorts(new HashMap<>())
                .build();
    }


    @Bean
    public JdbcBatchItemWriter<BookDto> bookWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<BookDto>()
                .dataSource(dataSource)
                .sql("INSERT INTO books (id, title, author_id, genre_id) VALUES (:id, :title, :authorId, :genreId)")
                .beanMapped()
                .build();
    }


    @Bean
    public ItemProcessor<BookMongo, BookDto> bookProcessor(RelationshipService relationshipService) {
        return relationshipService::updateBook;
    }

    @Bean
    public Step migrateBookStep(ItemReader<BookMongo> bookMongoItemReader,
                                JdbcBatchItemWriter<BookDto> bookJdbcBatchItemWriter,
                                ItemProcessor<BookMongo, BookDto> processor
    ) {
        return new StepBuilder("migrateBookStep", jobRepository)
                .<BookMongo, BookDto>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(bookMongoItemReader)
                .processor(processor)
                .writer(bookJdbcBatchItemWriter)
                .build();
    }

    @Bean
    public MongoPagingItemReader<CommentMongo> commentMongoReader(MongoTemplate mongoTemplate) {
        return new MongoPagingItemReaderBuilder<CommentMongo>()
                .name("commentMongoReader")
                .template(mongoTemplate)
                .jsonQuery("{}")
                .targetType(CommentMongo.class)
                .sorts(new HashMap<>())
                .build();
    }


    @Bean
    public JdbcBatchItemWriter<CommentDto> commentWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<CommentDto>()
                .dataSource(dataSource)
                .sql("INSERT INTO comments (id, text, book_id) VALUES (:id, :text, :bookId)")
                .beanMapped()
                .build();
    }


    @Bean
    public ItemProcessor<CommentMongo, CommentDto> commentProcessor(RelationshipService relationshipService) {
        return relationshipService::updateComment;
    }

    @Bean
    public Step migrateCommentStep(ItemReader<CommentMongo> commentMongoItemReader,
                                   JdbcBatchItemWriter<CommentDto> commentJdbcBatchItemWriter,
                                   ItemProcessor<CommentMongo, CommentDto> processor
    ) {
        return new StepBuilder("migrateCommentStep", jobRepository)
                .<CommentMongo, CommentDto>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(commentMongoItemReader)
                .processor(processor)
                .writer(commentJdbcBatchItemWriter)
                .build();
    }

    @Bean
    public Job importUserJob(Step migrateAuthorStep, Step migrateGenreStep, Step migrateBookStep,
                             Step migrateCommentStep) {
        return new JobBuilder(MIGRATE_DB_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(migrateAuthorStep)
                .next(migrateGenreStep)
                .next(migrateBookStep)
                .next(migrateCommentStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }

}