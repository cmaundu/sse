/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.config;

/**
 *
 * @author CMaundu
 */
import co.ke.sart.site.utils.SartUncaughtExceptionHandler;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
//import org.apache.logging.log4j.LogManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.sql.DataSource;

import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;

import org.springframework.core.env.Environment;
import org.hibernate.jpa.HibernatePersistenceProvider;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.vendor.Database;

@Configuration
@EnableScheduling

@EnableAsync(
        mode = AdviceMode.PROXY, proxyTargetClass = false,
        order = Ordered.HIGHEST_PRECEDENCE
)
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "jpaTransactionManager",
        basePackages = {"co.ke.sart.site.repository", "co.ke.sart.site.entity"}
)
@ComponentScan(basePackages = "co.ke.sart.site.controller", excludeFilters = @ComponentScan.Filter({Controller.class, ControllerAdvice.class}))
@Import({SecurityConfiguration.class})
public class RootContextConfiguration implements
        AsyncConfigurer, SchedulingConfigurer {

    @Autowired
    private Environment env;

    @Value("${spring.datasource.driver-class-name}")
    private String dbDriver;
    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String dbUserName;
    @Value("${spring.datasource.password}")
    private String dbPassword;

    private static final Logger log = LogManager.getLogger();

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setCacheSeconds(-1);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setBasenames("/WEB-INF/i18n/titles",
                "/WEB-INF/i18n/messages", "/WEB-INF/i18n/errors",
                "/WEB-INF/i18n/validation");
        return messageSource;
    }

    /*
    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(this.messageSource());
        return validator;
    }*/

 /*
    @Bean
    public MethodValidationPostProcessor
            methodValidationPostProcessor() {
        MethodValidationPostProcessor processor
                = new MethodValidationPostProcessor();
        processor.setValidator(this.localValidatorFactoryBean());
        return processor;
    }*/
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE,
                false);
        return mapper;
    }

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan(new String[]{"co.ke.sart"});
        return marshaller;
    }

    /*@Bean
    public DataSource sartDataSource() {
        JndiDataSourceLookup lookup
                = new JndiDataSourceLookup();
        DataSource ds
                = lookup.getDataSource("jdbc/safari");
        return ds;
    }*/
    @Bean(name = "dataSource")
    public DataSource sartDataSource() {
        SartDataSource dmds = new SartDataSource();
        //DriverManagerDataSource dmds = new DriverManagerDataSource(dbUrl, dbUserName, dbPassword);

        System.out.println("Driver: " + dbDriver + " - URL: " + dbUrl + " Username: " + dbUserName);
        dmds.setDriverClassName(dbDriver);
        dmds.setUrl(dbUrl);
        dmds.setUsername(dbUserName);
        dmds.setPassword(dbPassword);

        return dmds;
    }




    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        Map<String, Object> properties = new Hashtable<>();
        properties.put("javax.persistence.schema-generation.database.action", "none");

        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");
        adapter.setShowSql(false);
        adapter.setDatabase(Database.MYSQL);
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(adapter);
        factory.setDataSource(this.sartDataSource());
        factory.setPackagesToScan(new String[]{"co.ke.sart.site.entity"});
        factory.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
        factory.setValidationMode(ValidationMode.NONE);
        factory.setJpaPropertyMap(properties);

        return factory;
    }

    @Bean(name = "jpaTransactionManager")
    public PlatformTransactionManager jpaTransactionManager() {
        return new JpaTransactionManager(
                this.entityManagerFactory().getObject()
        );
    }


    
  

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        log.info("Setting up thread pool task scheduler with 20 threads.");
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(20);
        scheduler.setThreadNamePrefix("task-");
        scheduler.setAwaitTerminationSeconds(60);
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        //scheduler.setErrorHandler(t -> schedulingLogger.error("Unknown error occurred while executing task.", t));
        //scheduler.setRejectedExecutionHandler((r, e) -> schedulingLogger.error("Execution of task {} was rejected for unknown reasons.", r));
        return scheduler;
    }

    @Override
    public Executor getAsyncExecutor() {
        Executor executor = this.taskScheduler();
        log.info("Configuring asynchronous method executor {}.", executor);
        return executor;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {
        TaskScheduler scheduler = this.taskScheduler();
        log.info("Configuring scheduled method executor {}.", scheduler);
        registrar.setTaskScheduler(scheduler);
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SartUncaughtExceptionHandler();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        ClassPathResource cpr = new ClassPathResource("co/ke/sart/config/dbInfo.properties");
        pspc.setLocation(cpr);
        return pspc;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

}
