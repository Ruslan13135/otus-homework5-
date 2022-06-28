package ot.homework5plus.rushm.shell;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

    private final JobLauncher jobLauncher;

    private final Job migrateMongoToMySql;

    @SneakyThrows
    @ShellMethod(value = "startMigrationJobWithJobLauncher", key = "run")
    public void startMigrationJobWithJobLauncher() {
        jobLauncher.run(migrateMongoToMySql, new JobParameters());
    }

}
