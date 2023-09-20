package koreaUniv.koreaUnivRankSys.global.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Slf4j
@RequiredArgsConstructor
public class BatchSchedulerConfiguration {

    private final JobLauncher jobLauncher;
    private final Job centralLibraryRecordMigrationJob;
    private final Job centralSquareRecordMigrationJob;
    private final Job educationHallRecordMigrationJob;
    private final Job hanaSquareRecordMigrationJob;
    private final Job memorialHallRecordMigrationJob;
    private final Job scienceLibraryRecordMigrationJob;

    private final Job memberHighlightUpdateJob;

    @Scheduled(cron = "*/30 * * * * *")
    public void centralLibraryRecordMigrationJobRun() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        JobParameters jobParameters = new JobParameters(
                Collections.singletonMap("requestTime", new JobParameter(System.currentTimeMillis()))
        );

        jobLauncher.run(centralLibraryRecordMigrationJob, jobParameters);
    }

    @Scheduled(cron = "*/30 * * * * *")
    public void centralSquareRecordMigrationJobRun() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        JobParameters jobParameters = new JobParameters(
                Collections.singletonMap("requestTime", new JobParameter(System.currentTimeMillis()))
        );

        jobLauncher.run(centralSquareRecordMigrationJob, jobParameters);
    }

    @Scheduled(cron = "*/30 * * * * *")
    public void memorialHallRecordMigrationJobRun() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        JobParameters jobParameters = new JobParameters(
                Collections.singletonMap("requestTime", new JobParameter(System.currentTimeMillis()))
        );

        jobLauncher.run(memorialHallRecordMigrationJob, jobParameters);
    }

    @Scheduled(cron = "*/30 * * * * *")
    public void educationHallRecordMigrationJobRun() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        JobParameters jobParameters = new JobParameters(
                Collections.singletonMap("requestTime", new JobParameter(System.currentTimeMillis()))
        );

        jobLauncher.run(educationHallRecordMigrationJob, jobParameters);
    }

    @Scheduled(cron = "*/30 * * * * *")
    public void hanaSquareRecordMigrationJobRun() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        JobParameters jobParameters = new JobParameters(
                Collections.singletonMap("requestTime", new JobParameter(System.currentTimeMillis()))
        );

        jobLauncher.run(hanaSquareRecordMigrationJob, jobParameters);
    }

    @Scheduled(cron = "*/30 * * * * *")
    public void scienceLibraryRecordMigrationJobRun() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        JobParameters jobParameters = new JobParameters(
                Collections.singletonMap("requestTime", new JobParameter(System.currentTimeMillis()))
        );

        jobLauncher.run(scienceLibraryRecordMigrationJob, jobParameters);
    }

    @Scheduled(cron = "*/50 * * * * *")
    public void memberHighlightUpdateJobRun() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        JobParameters jobParameters = new JobParameters(
                Collections.singletonMap("requestTime", new JobParameter(System.currentTimeMillis()))
        );

        jobLauncher.run(memberHighlightUpdateJob, jobParameters);
    }

}
