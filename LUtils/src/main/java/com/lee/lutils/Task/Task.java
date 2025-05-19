package com.lee.lutils.Task;

public class Task {
        private String taskName;

        public Task(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskName() {
            return taskName;
        }

        public void execute(onTaskExecuteListener listener) {
            // 模拟任务执行
            try {
                listener.onExecute(this);
                Thread.sleep(3000); // 假设每个任务执行1秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }