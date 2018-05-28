package ru.sozvezdie42;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import ru.sozvezdie42.res.PropertyResources;

/**
 * @author Romancha on 12/5/2016.
 */
public class SyncStarter {
    public static void main(String[] args) {
        new PropertyResources();
        JobDetail job = JobBuilder.newJob(SyncJob.class).build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(PropertyResources.EXECUTE_CRON)).build();
        try {
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
