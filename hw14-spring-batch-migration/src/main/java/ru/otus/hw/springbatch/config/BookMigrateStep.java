package ru.otus.hw.springbatch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoPagingItemReader;
import org.springframework.batch.item.data.builder.MongoPagingItemReaderBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.hw.springbatch.dto.BookDto;
import ru.otus.hw.springbatch.model.mongo.BookMongo;
import ru.otus.hw.springbatch.service.RelationshipService;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class BookMigrateStep {

    private static final int CHUNK_SIZE = 5;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

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
}