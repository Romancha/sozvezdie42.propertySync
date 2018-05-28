package ru.sozvezdie42;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import ru.sozvezdie42.res.PropertyResources;
import ru.sozvezdie42.synchronizer.Synchronization;

import java.sql.SQLException;

public class SyncJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        Synchronization synchronizer = new Synchronization();
        try {
            synchronizer.synchronizeCompany(PropertyResources.COMPANY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
