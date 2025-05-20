package Task;

public class test {
    public static void main(String[] args) {
        TaskQueue taskQueue = new TaskQueue();
        taskQueue.addTask(new Task("task1"));
        taskQueue.addTask(new Task("task2"));
        taskQueue.addTask(new Task("task3"));
        taskQueue.addTask(new Task("task4"));
        taskQueue.addTask(new Task("task5"));
        TaskExecutor taskExecutor = new TaskExecutor(taskQueue, new onTaskExecuteListener() {
            @Override
            public void onExecute(Task task) {
                System.out.println("Task executed: " + task.getTaskName());
            }
        });
        taskExecutor.executeTasks();
    }
}
