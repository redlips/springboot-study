package com.redlips.java.features.currency.masterworker;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author 花落孤忆
 * @create 2018-09-28 10:42
 * @description Master进程是主要进程，它维护着一个Worker进程队列、子任务队列、子结果集，Master进程不断从任务队列中提取要处理的子任务
 * 并将子任务的处理结果写入结果集。
 * 主要作用：用于任务的分配和最终结果的合并
 */
public class Master {
    // 任务队列
    protected Queue<Object> workQueue = new ConcurrentLinkedDeque<>();
    // Worker进程队列
    protected Map<String, Thread> threadMap = new HashMap<>();
    // 子任务处理结果集
    protected Map<String, Object> resultMap = new ConcurrentHashMap<>();

    // 是否所有的任务都结束了
    public boolean isComplete() {
        for(Map.Entry<String, Thread> entry : threadMap.entrySet()) {
            if(entry.getValue().getState() != Thread.State.TERMINATED) {
                return false;
            }
        }
        return true;
    }

    // Master的构造，需要一个Worker进程逻辑，和需要Worker进程的数量
    public Master(Worker worker, int countWorker) {
        worker.setWorkQueue(workQueue);
        worker.setResultMap(resultMap);
        for (int i = 0; i < countWorker; i++) {
            threadMap.put(Integer.toString(i), new Thread(worker, Integer.toString(i)));
        }
    }

    // 提交一个任务
    public void submit(Object object) {
        workQueue.add(object);
    }

    // 返回子任务结果集
    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    // 开始运行所有的Worker进程，进行处理
    public void execute() {
        for (Map.Entry<String, Thread> entry : threadMap.entrySet()) {
            entry.getValue().start();
        }
    }


}
