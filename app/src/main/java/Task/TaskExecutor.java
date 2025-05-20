package Task;

public class TaskExecutor {
    private TaskQueue taskQueue;
    private final onTaskExecuteListener onTaskExecute;
    public TaskExecutor(TaskQueue taskQueue, onTaskExecuteListener listener) {
        this.taskQueue = taskQueue;
        this.onTaskExecute = listener;
    }

    public void executeTasks() {
        while (taskQueue.hasTasks()) {
            Task task = taskQueue.getNextTask(); // 获取下一个任务
            if (task != null) {
                task.execute(onTaskExecute); // 执行任务
            }
        }
    }
}