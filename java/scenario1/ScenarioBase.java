import java.util.concurrent.*;

public class ScenarioBase {

    public static void main(String[] args) {
        BlockingQueue<Task> taskQueue = new LinkedBlockingDeque<>();
        ConcurrentMap<Long, TaskTimeExecution> nodeBlockingQueue = new ConcurrentHashMap<>();
        long nodeID = 1;

        ScheduledExecutorService tasksExecutor = Executors.newScheduledThreadPool(5);

        tasksExecutor.scheduleAtFixedRate(new TaskProducer(taskQueue), 0, 5, TimeUnit.SECONDS);


        ExecutorService nodeExecutor = Executors.newFixedThreadPool(3);

        // Consumidor
        for (int i = 0; i < 3; i++) {
            Node node = new Node(taskQueue, nodeID++, nodeBlockingQueue);
            nodeExecutor.submit(node);
        }

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            for (long i = 1; i <= 3; i++) {
                TaskTimeExecution taskTimeExecution = nodeBlockingQueue.get(i);
                if (taskTimeExecution != null){
                    System.out.println();
                    System.out.println("Id do node " + i);
                    System.out.println("Tempo de execução do node " + taskTimeExecution.timeExecute());
                    System.out.println("Task executada: " + taskTimeExecution.task().getId());
                    System.out.println("Tempo de execução da task: " + taskTimeExecution.threadTimeExecute());
                    System.out.println();
                }
                else {
                    System.out.println("Id do node " + i);
                    System.out.println("Nada processado ainda!");
                }

            }
        }, 5, 5, TimeUnit.SECONDS);
    }
}
