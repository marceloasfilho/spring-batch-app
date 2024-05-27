package com.github.marceloasfilho.springbatchapp.configuration;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing(dataSourceRef = "batchDataSource", transactionManagerRef = "batchTransactionManager")
public class BatchConfiguration {

    @Bean(name = "batchDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.batch")
    public DataSource batchDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("batchTransactionManager")
    public PlatformTransactionManager batchTransactionManager(@Qualifier("batchDataSource") DataSource batchDataSource) {
        return new DataSourceTransactionManager(batchDataSource);
    }

    @Bean("batchJobRepository")
    public JobRepository batchJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(batchDataSource());
        factory.setTransactionManager(batchTransactionManager(batchDataSource()));
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean(name = "batchJobExplorer")
    public JobExplorer batchJobExplorer(@Qualifier("batchDataSource") DataSource batchDataSource) throws Exception {
        JobExplorerFactoryBean factory = new JobExplorerFactoryBean();
        factory.setDataSource(batchDataSource);
        factory.setTransactionManager(batchTransactionManager(batchDataSource));
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean(name = "batchJobLauncher")
    public JobLauncher batchJobLauncher() throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(batchJobRepository());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

    @Bean
    public JobLauncherApplicationRunner jobLauncherApplicationRunner(@Qualifier("batchJobLauncher") JobLauncher jobLauncher,
                                                                     @Qualifier("batchJobExplorer") JobExplorer jobExplorer,
                                                                     @Qualifier("batchJobRepository") JobRepository jobRepository) {
        JobLauncherApplicationRunner runner = new JobLauncherApplicationRunner(jobLauncher, jobExplorer, jobRepository);
        runner.setJobName("imprimeOlaMundoJob");
        return runner;
    }
}
