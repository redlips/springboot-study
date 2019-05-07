package com.redlips.data.structure.sort;

/**
 * @author qiaotong
 * @create 2019-03-05 8:59
 * @description 冒泡排序
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] arr = {1, 3, 1, 5, 8, 2, 7, 11, 23, 9, 9};
        printArr(arr);
        printArr(bubbleSort(arr));
    }

    public static int[] bubbleSort(int[] arr) {
        if (arr == null || arr.length <= 1) return arr;

        for (int i = 0; i < arr.length; i++) {
            boolean isSwap = false;
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    isSwap = true;
                }
            }
            // 没有数据交换，提前退出
            if (!isSwap) break;
        }

        return arr;
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
