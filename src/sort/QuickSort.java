package sort;

/**
 * @author Zhang Bowen
 * @Description
 * @ClassName QuickSort
 * @date 2021.01.20 23:06
 */
public class QuickSort {
    public static void sort(int[] a, int lo, int hi){
        if (hi <= lo)
            return;
        int j = partition2(a,lo,hi);
        sort(a,lo,j-1);
        sort(a,j+1,hi);

    }

    /*
    设基准值pivot，初始设置pivot=a[lo]，
    我们希望通过partition方法得到中间位置m，a[m]=pivot，m将数组分为：
    a[lo:m-1],a[m],a[m+1:hi]
    a[m]右边的数比pivot大，左边的数比pivot小
     */
    public static int partition(int[] a, int lo, int hi){

        /*
        这里需要注意一点，因为后续的比较是将a[i]与pivot相比
        而pivot的初始值为lo，因此很容易想到i的值从lo开始增加的话，lo位置必定与pivot相等
        那么i是否可以从i+1开始移动呢？答案是否定的，原因有两点：
        （1）我们先将j指针从右向左移动（2）我们指针移动的跳出条件是i=j
        其中（2）是导致（1）的原因，在下面会进行分析
        如果j在一轮遍历的时候直接到达i处，则此时i=j=lo+1
        最后我们需要执行a[lo]与a[i]值的交换，但是此时没有办法保证a[lo+1]一定比a[lo]小
        而在交换的时候我们需要保证a[i]=a[j]<=a[lo]=pivot

         */
        int i = lo; //设定左指针初始值i
        int j = hi; //设定初始右指针初始值j
        int pivot = a[lo]; //设定基准值pivot


        while (true){

            /*
            j指针从右向左遍历，找出第一个小于pivot的值的位置
            当i与j相遇时，则说明分割完成,跳出循环

            需要补充的是，此时必须让j指针先移动，因为如果i指针先移动j指针再移动
            则此时j与i相遇时，因为i指针已经移动且有可能应找到一个比pivot大的值（a[i] > pivot）
            因此a[j]=a[i]>pivot，因此交换i与lo的位置会导致将比pivot大的数放在其左边
            所以必须j先移动
             */
            while (a[j] >= pivot && i < j) j=j-1;

            /*
            i指针从左向右遍历，找出第一个大于pivot的值的位置
            当i与j相遇时，则说明分割完成,跳出循环
             */
            while (a[i] <= pivot && i < j) i=i+1;

            /*
            i的左边均小于pivot，j的右边均大于pivot
            当i等于j时，表示i与j相遇
            说明分隔完毕，因此跳出循环
             */
            if(i == j)
                break;

            /*
            若i小于j则说明，有比pivot大的元素在比pivot小的元素的左边
            因此要交换两者位置
             */
            exch(a,i,j);
        }

        /*
        跳出循环时候，i与j的值相等
        代表了中间位置，且因为j先移动，而j扫描停止时一定有a[j]<pivot，或者a[j]=a[i]=pivot（此时j在第一轮直接移动至i处）
        因此a[i]=a[j] <= pivot=a[lo]
        因此交换lo和i的位置
         */
        exch(a,lo,i);
        return i;
    }

    /*
    上一种方法的i和j指针停下的条件是:
    (1)i指针扫描到一个比pivot大的数/j指针扫描到一个比pivot小的数
    (2)i指针与j指针相遇
    当遇到与pivot想等的元素时是不会停下来的，这么做虽然可以避免等值交换
    但是会在某些情况下造成O(n^2)的时间复杂度，因此在分割的时候也有其他写法

    需要说明的是在上一种写法中，因为内层两个while循环的第二个跳出条件是i=j的限制，
    导致了必须j先移动，导致了i不能从lo+1开始而必须从lo开始
    导致了内层关于i指针的while循环的第一个跳出条件必须是a[i]大于lo（否则i会一直卡在lo位置）
    因此在partition2的写法中我将内层两个while循环的第二个跳出条件更改为了i=hi和j=lo
    这样做的好处是，先移动i还是先移动j无所谓，同时i可以从lo+1开始扫描
    因此while循环的第一个条件可以改为a[i]>=pivot或者a[j]<=pivot
     */
    public static int partition2(int[] a, int lo, int hi){
        int i = lo;
        int j = hi+1;
        int pivot = a[lo];

        /*
        与上一个分割函数不同的是，partition2中指针i和j停下来的条件是：
        （1）i指针扫描到一个大于等于pivot的值/j指针扫描到一个小于等于pivot的值
        （2）i指针到达最右侧（i=hi）/j指针到达最左侧（j=lo）

        针对（2），出现的情况有且只有三种：
        （1）输入的待排序序列a的第一个元素a[lo]（pivot）是最大值，且中间元素中的值（a[lo+1]到a[hi-1]）均小于pivot，a[hi]小于或等于pivot
        此时i=hi，j=hi，满足i>=j的循环跳出条件
        （2）输入的待排序序列a的第一个元素a[lo]（pivot）是次大值，且中间元素中的值（a[lo+1]到a[hi-1]）均小于pivot，a[hi]大于pivot
        此时i=hi，j=hi-1，满足i>=j的循环跳出条件
        （3）输入的待排序序列a的第一个元素a[lo]（pivot）是最小值，
        此时i=lo+1，j=lo，满足i>=j的循环跳出条件

        跳出循环时，可能出现的情况有两种：
        （1）i=j，i指针与j指针相遇，此时必有a[i]=a[j]=pivot,或者a[i]=a[j]<pivot（只有当上述讨论的情况（1）出现且a[hi]小于pivot时才可能发生）
        （2）i > j，i指针超过j指针，此时必有a[i] >= pivot >= a[j]

        简言之，如果i和j在中间相遇，则必有a[i]=a[j]=pivot
        如果i和j在两端相遇，则只可能是在最右端，此时a[i]=a[j]<pivot
         */
        while (true){
            /*
            此时i与j的值与对方均无关，因此谁先谁后无所谓
             */
            while (a[++i] < pivot)
                if (i == hi)
                    break;
            while (a[--j] > pivot)
                if (j == lo)
                    break;

            if(i >= j)
                break;

            exch(a,i,j);
        }

        /*
        此时跳出状态可能是i=j或i>j
        若i>j，则必定有a[i]>= pivot >= a[j]
        因此此时需要将j设为中间位置，并将a[lo]与a[j]的值交换
         */
        exch(a,lo,j);
        return j;
    }

    public static void exch(int[] a, int i, int j){
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }


}
