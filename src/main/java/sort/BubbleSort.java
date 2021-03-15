package sort;

/**
 * @author Zhang Bowen
 * @Description
 * @ClassName BubbleSort
 * @date 2021.01.20 20:27
 */

/*
冒泡排序的原理是进行n-1趟遍历
每一趟判断相邻元素间的大小关系
如果前一元素比后一元素大，则交换位置
这样做每一趟下来都可以把尚未进行排序的元素中最大的元素移至
未排序元素末尾
 */
public class BubbleSort {

    public static void sort(int[] a){
        int n = a.length;

        /*
        为什么n-1趟就够了？
        因为在最坏情况下，第i趟开始的时候，后i个元素已经被排序
        当第n-1趟开始（i=n-2）的时候，说明后n-2个元素已经排序完成，也就是说后n-2个元素一定大于等于前两个元素且已经按序
        此时对前两个数进行比较排序后，整个序列必定已经按序
        因此最多执行n-1趟就能完成排序
         */
        for(int i = 0; i < n-1; i++){
            /*
            在第i（i从0开始）趟时，后i个元素已经排序
            因此内层循环只需要从0到第n-i-2即可
             */
            for(int j = 0; j < n-i-1; j++){
                if(a[j] > a[j+1]){
                    exch(a,j,j+1);
                }
            }
        }
    }

    /*
    第一步改进：
    其实不用遍历n-1趟，如果在某一趟中没有发生元素交换，
    则说明已经完成排序，因此不再需要下一趟遍历
    这里通过定义标志变量flag进行判断
     */
    public static void sort2(int[] a){
        int n = a.length;

        for(int i = 0; i < n-1; i++){
            int flag = 0;
            for(int j = 0; j < n-i-1; j++){
                if(a[j] > a[j+1]){
                    exch(a,j,j+1);
                    flag = 1;
                }
            }

            if (flag == 0)
                return;
        }
    }

    /*
    第二步改进：
    在第一步改进中，我们已经避免了某一趟未交换下一趟仍旧遍历的情况
    那么还有一种情况是，在上一轮中已经完成了交换，本轮并未执行任何操作
    这种情况出现的原因主要是由第二层循环导致的
    优化前的冒泡排序内层遍历在第i趟总是从0到n-i-2
    但是在观察交换过程可以发现，如果最后一个发生交换的位置是m（j=m）
    则说明m后的所有元素已经按序（从m+1到n-1已经按序），则下一趟在内层遍历时只需从0遍历至m-1
    */
    public static void sort3(int[] a){
        int n = a.length;
        int pos = 0; //记录最后一次交换的位置
        int len = n-1;

        for(int i = 0; i < n-1; i++){
            for(int j = 0; j < len; j++){
                if(a[j] > a[j+1]){
                    exch(a,j,j+1);
                    pos = j;
                }
            }
            len = pos;
        }
    }


    public static void exch(int[] a, int i, int j){
        int t = a[j];
        a[j] = a[i];
        a[i] = t;
    }
}
