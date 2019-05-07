package com.redlips.data.structure.sort;

/**
 * @author qiaotong
 * @create 2019-03-18 11:19
 * @description 归并排序 时间复杂度O(nlog(n))
 */
public class MergeSort {
    public static void main(String[] args) {
        int[] arr = {1, 3, 1, 5, 8, 2, 7, 11, 23, 9, 9};
        printArr(arr);
        mergeSort(arr, arr.length);
        printArr(arr);
    }

    public static void mergeSort(int[] arr, int n) {
        mergeSortExec(arr, 0, n - 1);
    }

    private static void mergeSortExec(int[] arr, int p, int r) {
        // 递归终止条件
        if (p >= r) return;

        // 获取中间点,防止超过int最大值
        int q = p + (r - p) / 2;

        // 分治处理,递归调用,并进行合并
        mergeSortExec(arr, p, q);
        mergeSortExec(arr, q + 1, r);
        merge(arr, p, q, r);
    }

    private static void merge(int[] arr, int p, int q, int r) {
        // 初始化引用变量
        int i = p;
        int j = q + 1;
        int k = 0;
        // 定义和arr一样大小的临时数组
        int[] temp = new int[r - p + 1];

        while (i <= q && j <= r) {
            if (arr[i] <= arr[j])
                temp[k++] = arr[i++];
            else
                temp[k++] = arr[j++];
        }

        // 判断哪个子数组还有剩余数据
        int start = i;
        int end = q;
        if (j <= r) {
            start = j;
            end = r;
        }

        // 将剩余的数据拷贝到temp中
        while (start <= end) {
            temp[k++] = arr[start++];
        }

        // 将temp数组拷贝到arr中
        for (i = 0; i <= r - p; i++) {
            arr[p + i] = temp[i];
        }
    }

    public static void printArr(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            if (i == 0) System.out.print(arr[i]);
            else System.out.print("," + arr[i]);
        }
        System.out.println("]");
    }
}
