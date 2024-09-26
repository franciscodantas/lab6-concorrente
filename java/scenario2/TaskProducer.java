import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskProducer implements Runnable {

    private final BlockingQueue<Task> tasks;
    private final AtomicInteger tasksIds;

    private final long TIME_PRODUCE = 5000;

    public TaskProducer(BlockingQueue<Task> taskQueue) {
        this.tasks = taskQueue;
        this.tasksIds = new AtomicInteger();
    }

    @Override
    public void run() {
        Task task = new Task(tasksIds.incrementAndGet());
        tasks.add(task);
    }
}