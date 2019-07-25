package com.redlips.multithreading.test26;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author qiaotong
 * @create 2019-07-25 14:02
 * @description 模拟MapReduce计算文件中每个单词的数量
 */
public class MapReduceDemo {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(3);

        String[] fc = {"hello me", "hello fork", "hello join x"};
        MP task = new MP(fc, 0, fc.length);

        Map<String, Long> invoke = forkJoinPool.invoke(task);

        System.out.println(invoke);
    }

    static class MP extends RecursiveTask<Map<String, Long>> {

        private String[] fc;
        private int start, end;

        public MP(String[] fc, int start, int end) {
            this.fc = fc;
            this.start = start;
            this.end = end;
        }


        @Override
        protected Map<String, Long> compute() {
            if (end - start == 1)
                return calc(fc[start]);
            else {
                int mid = (start + end) / 2;
                MP task1 = new MP(fc, start, mid);
                task1.fork();
                MP task2 = new MP(fc, mid, end);

                // 计算子任务并返回合并的结果
                return merge(task2.compute(), task1.join());
            }
        }

        // 结果合并
        private Map<String, Long> merge(Map<String, Long> m1, Map<String, Long> m2) {
            Map<String, Long> result = new HashMap<>(m1);

            m2.forEach((k, v) -> {
                Long c = result.get(k);
                if (c != null)
                    result.put(k, c + v);
                else
                    result.put(k, v);
            });

            return result;
        }

        // 统计单词数量
        private Map<String, Long> calc(String line) {
            Map<String, Long> result = new HashMap<>();

            String[] words = line.split("\\s+");
            // 统计
            for (String w : words) {
                Long v = result.get(w);
                if (v == null)
                    result.put(w, 1L);
                else
                    result.put(w, v + 1);
            }
            return result;
        }

    }
}
