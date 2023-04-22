package project.reviews.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.reviews.domain.Movie;
import project.reviews.repository.RecordRepository;
import project.reviews.service.RecordService;

import java.time.LocalDateTime;
import java.util.List;

/*
* 2023-03-08
* spring boot batch + scheduler
* */
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private RecordService recordService;

    @Autowired
    private RecordRepository recordRepository;

    @Bean
    public Job job() {

        Job job = jobBuilderFactory.get("job")
                .start(step())
                .build();
        return job;
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
                .tasklet((contribution, chunkContext) -> {
                    // 업데이트 날짜가 일주일 이전인 문서 목록을 가져옴
                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime aWeekAgo = now.minusDays(7);
                    List<Movie> limitedMovies = recordRepository.findByCreatedDateLessThan(aWeekAgo);

                    if(limitedMovies.size() > 0) {
                        for(Movie movie : limitedMovies) {
                            recordService.deleteMovieRecord(movie);
                        }
                    }
                    return RepeatStatus.FINISHED;
                }).build();
    }
}