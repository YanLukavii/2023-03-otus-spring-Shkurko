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
import ru.otus.hw.springbatch.model.h2.Author;
import ru.otus.hw.springbatch.model.mongo.AuthorMongo;
import ru.otus.hw.springbatch.service.RelationshipService;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class AuthorMigrateStep {

    private static final int CHUNK_SIZE = 5;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

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
        return relationshipService::convert;
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


}
