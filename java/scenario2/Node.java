import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingDeque;

public class Node implements Runnable {

    private final BlockingQueue<Task> tasks;
    private final long nodeID;

    private long timeExec;
    private final ConcurrentMap<Long, TaskTimeExecution> nodeBlockingQueue;
    private final BlockingQueue<Task> tasksExecute;

    public Node(BlockingQueue<Task> taskQueue, long nodeID, ConcurrentMap<Long, TaskTimeExecution> nodeBlockingQueue) {
        this.tasks = taskQueue;
        this.nodeID = nodeID;
        tasksExecute = new LinkedBlockingDeque<>();
        this.nodeBlockingQueue = nodeBlockingQueue;
        timeExec = 0;
    }

    @Override
    public void run() {
        while (true){
            try {
                Task task = tasks.take();
                task.execute();
                long taskTime = task.getExecDuration();
                this.timeExec += taskTime;
                TaskTimeExecution taskTimeExecution = new TaskTimeExecution(task, this.timeExec, taskTime);
                nodeBlockingQueue.put(this.nodeID, taskTimeExecution);
                tasksExecute.add(task);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public BlockingQueue<Task> getTasks(){
        return tasksExecute;
    }
}
