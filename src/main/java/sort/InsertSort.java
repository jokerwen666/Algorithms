package sort;

/**
 * @author Zhang Bowen
 * @Description
 * @ClassName InsertSort
 * @date 2021.01.21 22:56
 */

/*
插入排序算法的原理是
每一步将一个待排序的数据插入到前面已经排好序的有序序列中
直到插完所有元素为止。
 */
public class InsertSort {
    public static void sort(int[] a){
        int n = a.length;
        for(int i = 1; i < n; i++){

            //当找到某一个位置（该位置左边的数比其小）的时候停止交换
            for (int j = i; j > 0 && a[j] < a[j-1]; j--){
                exch(a,j,j-1);
            }
        }
    }

    public static void exch(int[] a, int i, int j){
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}


