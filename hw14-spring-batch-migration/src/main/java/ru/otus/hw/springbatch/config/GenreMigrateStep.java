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
import ru.otus.hw.springbatch.model.h2.Genre;
import ru.otus.hw.springbatch.model.mongo.GenreMongo;
import ru.otus.hw.springbatch.service.RelationshipService;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class GenreMigrateStep {

    private static final int CHUNK_SIZE = 5;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

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
        return relationshipService::convert;
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

}
