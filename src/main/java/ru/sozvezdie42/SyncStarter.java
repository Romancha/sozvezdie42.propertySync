package ru.sozvezdie42;

import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import ru.sozvezdie42.res.PropertyResources;
import ru.sozvezdie42.synchronizer.Synchronization;

import java.sql.SQLException;

/**
 * @author Romancha on 12/5/2016.
 */
public class SyncStarter {

    private final static Logger log = Logger.getLogger(SyncStarter.class);

    public static void main(String[] args) {

        log.info("iProperty sync started");

        new PropertyResources();

        log.info("Cron: " + PropertyResources.EXECUTE_CRON);

        try {
            new Synchronization().synchronizeCompany(PropertyResources.COMPANY);
        } catch (SQLException e) {
            log.error("ERROR", e);
        }

        /*JobDetail job = JobBuilder.newJob(SyncJob.class).build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(PropertyResources.EXECUTE_CRON)).build();
        try {
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }*/
    }
}
