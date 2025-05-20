package Task;

import java.util.LinkedList;
import java.util.Queue;

public class TaskQueue {
        private Queue<Task> taskQueue = new LinkedList<>();

        public void addTask(Task task) {
            taskQueue.add(task); // 将任务添加到队列中
        }

        public boolean hasTasks() {
            return !taskQueue.isEmpty(); // 判断队列是否有任务
        }

        public Task getNextTask() {
            return taskQueue.poll(); // 获取并移除队列中的下一个任务
        }
    }