package com.github.marceloasfilho.springbatchapp.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImprimeOlaMundoJobConfig {

    private final JobRepository jobRepository;

    public ImprimeOlaMundoJobConfig(@Qualifier("batchJobRepository") JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Bean
    public Job imprimeOlaMundoJob(Step imprimeOlaMundoStep) {
        return new JobBuilder("imprimeOlaMundoJob", this.jobRepository)
                .start(imprimeOlaMundoStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }
}
