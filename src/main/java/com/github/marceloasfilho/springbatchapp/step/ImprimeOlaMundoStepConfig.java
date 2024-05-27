package com.github.marceloasfilho.springbatchapp.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ImprimeOlaMundoStepConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public ImprimeOlaMundoStepConfig(@Qualifier("batchJobRepository") JobRepository jobRepository,
                                     @Qualifier("batchTransactionManager") PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Step imprimeOlaMundoStep(Tasklet imprimeOlaMundoTasklet) {
        return new StepBuilder("imprimeOlaMundoStep", this.jobRepository)
                .tasklet(imprimeOlaMundoTasklet, this.transactionManager)
                .build();
    }
}
