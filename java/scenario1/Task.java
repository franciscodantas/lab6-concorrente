import java.util.Random;

public class Task {
    long id;
    long execDuration;

    long initialTime;

    public Task(long id, long initialTime) {
        this.id = id;
        this.initialTime = initialTime;
    }

    public long getId(){
        return this.id;
    }

    public long getInitialTime() {
        return initialTime;
    }

    synchronized long getExecDuration(){
        return execDuration;
    }

    public void execute() {
        try {
            // generating a number between 1000 and 15000
            execDuration = 1000 + (long) (new Random().nextFloat() * (15000 - 1000));
            Thread.sleep(execDuration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
