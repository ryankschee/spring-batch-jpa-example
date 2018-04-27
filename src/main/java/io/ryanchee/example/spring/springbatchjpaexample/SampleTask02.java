package io.ryanchee.example.spring.springbatchjpaexample;

import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class SampleTask02 implements Tasklet {
	
	private static Logger logger = Logger.getLogger(SampleTask02.class);
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) 
		throws Exception {

		logger.info("SAMPLE TASK 02");

		return RepeatStatus.FINISHED;
	} // .end of execute

}
