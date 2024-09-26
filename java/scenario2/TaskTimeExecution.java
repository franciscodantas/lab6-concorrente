import java.time.Instant;

public record TaskTimeExecution(Task task, long timeExecute, long threadTimeExecute) {
}
