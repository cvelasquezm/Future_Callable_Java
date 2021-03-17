package sample;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureCachedThreadPool {

        public static void main(String[] args) {

                ExecutorService executorService = Executors.newCachedThreadPool();
                executeTasks(executorService);

        }

        private static void executeTasks(ExecutorService executorService) {
                final Future<Boolean> r1 = executorService.submit(new Task("Thread 1"));
                final Future<Boolean> r2 = executorService.submit(new Task("Thread 2"));
                final Future<Boolean> r3 = executorService.submit(new Task("Thread 3"));
                final Future<Boolean> r4 = executorService.submit(new Task("Thread 4"));

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
