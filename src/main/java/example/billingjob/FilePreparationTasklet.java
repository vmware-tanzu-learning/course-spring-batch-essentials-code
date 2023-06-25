package example.billingjob;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class FilePreparationTasklet implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		JobParameters jobParameters = contribution.getStepExecution().getJobParameters();
		String inputFile = jobParameters.getString("input.file");
		Path source = Paths.get(inputFile);
		Path target = Paths.get("staging", source.toFile().getName());
		Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
		return RepeatStatus.FINISHED;
	}

}
