package sort;

/**
 * @author Zhang Bowen
 * @Description
 * @ClassName SelectionSort
 * @date 2021.01.20 17:26
 */

/*
选择排序的原理很容易理解，就是在每一轮遍历时找出未排序元素中最小的元素位置
并将该位置上的元素与未遍历元素的第一个元素交换
时间复杂度为O(n^2)
 */
public class SelectionSort {
    public static void sort(int[] a){
        int n = a.length;
        /*
        第一层循环是遍历n-1趟
        遍历n-1的原因是在检索倒数第二个数的时候，要么已经排序完成，要么更改完顺序后排序完成，因此只需遍历n-1趟
        第二层循环是从该数的后一个数开始遍历，将之后最小的数找出来，放在此位置
        也就是说在第i（i从0开始）趟遍历时，前i个数已经排序，因此内层遍历是从i+1开始的
        且该已经确保数之后（包括该数）不会有比之前更小的数出现
         */
        for(int i = 0; i < n-1; i++){
            //假设i位置上元素最小
            int min = i;
            for(int j = i+1; j < n; j++){
                //若某位置上元素大小比min位置上元素更新，则更新min值
                if(a[j] < a[min])
                    min = j;
            }

            //如果min值发生了更改，则交换i位置和min位置元素
            if(i != min)
                exch(a,i,min);
        }
    }


    public static void exch(int[] a, int i, int j){
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
