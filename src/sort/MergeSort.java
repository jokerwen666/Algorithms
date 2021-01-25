package sort;

/**
 * @author Zhang Bowen
 * @Description
 * @ClassName MergeSort
 * @date 2021.01.23 19:37
 */

/*
归并算法的时间复杂度是O(NlgN)
一长度为N的待排序序列，根据其构造归并排序的构造树
因为每次都会对半分开，因此这个树有lgN=n层
第k层的结点数目为2^k，每一个结点中序列长度为2^(n-k)
merge操作最少需要比较(1/2)*(2^(n-k))次，此时对应的情况是带合并序列已经是有序的
merge操作最多需要比较(2^(n-k))次，此时对应的情况是恰好隔一个比较一次
因此每一层需要比较(1/2)*(2^n)至(2^n)次，共n层，因此共需要比较1/2NlgN至NlgN次
因此归并算法的时间复杂度为O(NlgN)
 */
public class MergeSort {

    //自顶向下的归并排序方法
    public static void sort(int[] a, int[] aux, int lo, int hi){
        //如果只剩一个或零个元素，直接返回
        if(hi <= lo)
            return;
        //计算中间位置
        int mid = (hi - lo)/2 + lo;
        //对左边排序，对右边排序，最后合并
        sort(a,aux,lo,mid);
        sort(a,aux,mid+1,hi);

        /*
        是否对于所有情况都需要merge呢?
        显然，当a[mid] <= a[mid+1]时不需要进行合并了
        这一改动不影响排序的递归调用，但是对任意有序的子数组算法的运行时间就变为线性了
         */
        if (a[mid] > a[mid+1])
            merge(a,aux,lo,mid,hi);
    }

    //自底向上的归并排序算法
    public static void sortBU(int[] a, int[] aux){
        int n = a.length;

        //sz为子数组大小，子数组大小表示已经按序的数组大小，初始值为1
        for (int sz = 1; sz < n; sz+=sz){
            //每次令lo=0开始遍历，下一次lo的起始为（lo+2*sz），因为每次merge是将两个sz大小的子数组进行合并
            for (int lo = 0; lo+sz < n; lo += sz+sz){
                merge(a,aux, lo, lo+sz-1, Math.min(lo+sz+sz-1,n-1));
            }
        }
    }

    /*
    将数组a中的已经排序的前半部分和后半部分合并
    前半部分：lo~mid
    后半部分：mid+1~hi
     */
    public static void merge(int[] a, int[] aux, int lo, int mid, int hi){
        int i = lo;
        int j = mid+1;
        /*
        先将a数组克隆一份到aux

        需要说明，为什么不在merge里创建局部变量aux
        为了避免每次归并时，即使是归并最小的数组，都创建一个新的数组
        如果这么做，那么创建新数组将成为归并排序的主要部分
        因此这里通过传递参数来实现
         */
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];

        int pos = 0;

        //定义两个指针，一个指向数组前半段，一个指向数组后半段
        //当pos=a.length的时候说明完成合并跳出循环
        while (pos < a.length) {

            //如果左边元素取尽，则将右边元素放回a中
            if(i > mid) a[pos++] = aux[j++];

            //如果右边元素取尽，则将左边元素放回a中
            else if (j > hi) a[pos++] = a[pos++] = aux[i++];

            //如果左边小于等于右边，则将左边元素放回a中
            else if (aux[i] <= aux[j]) a[pos++] = aux[i++];

            //如果右边小于左边，则将右边元素放回a中
            else a[pos++] = aux[j++];
        }
    }
}
