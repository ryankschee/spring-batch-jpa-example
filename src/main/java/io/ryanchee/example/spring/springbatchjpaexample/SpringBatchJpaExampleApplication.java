package io.ryanchee.example.spring.springbatchjpaexample;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
public class SpringBatchJpaExampleApplication implements SchedulingConfigurer {

	private static Logger logger = Logger.getLogger(SpringBatchJpaExampleApplication.class);
	
	@Autowired
	private SampleTask01 task01;
	@Autowired
	private SampleTask02 task02;
	@Autowired
	private JobBuilderFactory jobs;	
	@Autowired
	private StepBuilderFactory steps;	
	@Autowired
	JobLauncher jobLauncher;
	@Autowired
	Job job;
	
	private String CRONS_STRING = "0 0/5 * * * ?";
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBatchJpaExampleApplication.class, args);
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		logger.info("Start configureTasks");
		
		/* ConfigureTasks will start with nextExecutionTime method in Trigger(). 
		 * When it is the nextExecution time, it will execute the run() method 
		 * then go in to the Trigger() for a new execution time. 
		 * */
        taskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
            	logger.info("Start - run");
            	JobExecution execution = null;
            	StringBuffer sb = new StringBuffer();
            	
        		try {
    				Date dateJobStart = new Date();
        			logger.info("Job Started at :" + dateJobStart);
        			logger.info("Cron string is :" + CRONS_STRING);
        			
        			JobParameters jobParameters = 
        							new JobParametersBuilder()
        									.addLong("time", System.currentTimeMillis())
        									.toJobParameters();	
        			
        			execution = jobLauncher.run(job, jobParameters); 
        			
        		} catch (Exception e) {
            		logger.error("Error in run : " , e);            		
        		} finally {
        			logger.info("Batch Execution Status: " + execution.getStatus());					
        		} // end finally
            }
        }, new Trigger() {
			@Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
				Date nextExec = null;
           		try {           
           			logger.info("Trigger BEGIN");
           			CronTrigger trigger = new CronTrigger(CRONS_STRING);
           			nextExec = trigger.nextExecutionTime(triggerContext);
           			
           		} catch(Exception e) {
           			logger.error("Error in nextExecutionTime : " , e);           			
           		} finally {
           			CronTrigger trigger = new CronTrigger(CRONS_STRING);
           			nextExec = trigger.nextExecutionTime(triggerContext);
           			logger.info("[Finally] Next Execution Time : " + nextExec);
           		}
           		
           		return nextExec;
            }
        });        
    }
	
	@Bean
    public Job job() {
        return jobs.get("job").incrementer(new RunIdIncrementer())
				.start(task01())
				.next(task02())
				.build();				
    }

    @Bean
    public Step task01() {
        return steps.get("task01").tasklet(task01).build();
    }
    
    @Bean
    public Step task02() {
        return steps.get("task02").tasklet(task02).build();
    }
}
