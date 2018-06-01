package ru.sozvezdie42;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import ru.sozvezdie42.res.PropertyResources;
import ru.sozvezdie42.synchronizer.Synchronization;

import java.sql.SQLException;

public class SyncJob implements Job {

    private final static Logger log = Logger.getLogger(SyncJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {

        log.info("Start sync job");

        Synchronization synchronizer = new Synchronization();
        try {
            synchronizer.synchronizeCompany(PropertyResources.COMPANY);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        log.info("Sync finished");
    }
}
