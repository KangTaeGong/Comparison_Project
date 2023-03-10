package project.reviews.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/*
* 2023-03-10
* @Scheduled
* */
@Slf4j
@Component
public class BatchScheduler {

    @Autowired JobLauncher jobLauncher;

    @Autowired BatchConfig batchConfig;

    /*
    * Seconds / Minutes / Hours / Day of Month 1-31 / Month / Day of Week / Year
    * * : 필드의 모든 값을 선택
    * 모든 달의 오전 00시에 작업을 실행
    * */
    @Scheduled(cron = "0 0 0 * * *")
    public void runJob() {

        // job parameter 설정
        Map<String, JobParameter> config_map = new HashMap<>();
        config_map.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(config_map);

        try {
            /*
            * 첫번째 파라미터로 Job, 두번째 파라미터로 Job Parameter를 받는다.
            * Job Paramter의 역할은 반복해서 실행되는 Job의 유일한 ID.
            * */
            jobLauncher.run(batchConfig.job(), jobParameters);
        } catch (JobInstanceAlreadyCompleteException | JobExecutionAlreadyRunningException
                | JobParametersInvalidException | JobRestartException e) {

            log.error(e.getMessage());
        }
    }
}
