package sample;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class FutureScheduledThreadPool {

        public static void main(String[] args) {

                final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
                executeTasks(scheduledExecutorService);

        }

        private static void executeTasks(ExecutorService executorService) {
                if (executorService instanceof ScheduledExecutorService) {

                        final ScheduledFuture<Boolean> r1 = ((ScheduledExecutorService) executorService)
                                .schedule(new FutureCachedThreadPool.Task("Thread 1"), 2, TimeUnit.SECONDS);

                        final Future<Boolean> r2 = executorService.submit(new FutureCachedThreadPool.Task("Thread 2"));
                        final Future<Boolean> r3 = executorService.submit(new FutureCachedThreadPool.Task("Thread 3"));
                        final Future<Boolean> r4 = executorService.submit(new FutureCachedThreadPool.Task("Thread 4"));

                        try {
                                r1.get();
                                r2.get();
                                r3.get();
                                r4.get();
                        } catch (InterruptedException e) {
                                e.printStackTrace();
                        } catch (ExecutionException e) {
                                e.printStackTrace();
                        }
                }
        }

        public static class Task implements Callable<Boolean> {

                private String nameThread;

                public Task(String nameThread) {
                        this.nameThread = nameThread;
                }

                @Override
                public Boolean call() throws Exception {

                        for (int i = 0; i < 10; i++) {
                                System.out.println("Processing " + nameThread);
                                Thread.sleep(1000);
                        }
                        System.out.println("Finished " + nameThread);
                        return true;
                }
        }
}
