package sort;

import java.util.Scanner;

/**
 * @author Zhang Bowen
 * @Description
 * @ClassName SortTest
 * @date 2021.01.20 17:13
 */
public class SortTest {
    /*
    test sort algorithms
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        String[] arrays = str.split(" ");
        int[] a = new int[arrays.length];
        for (int i = 0; i < arrays.length; i++){
            a[i] = Integer.parseInt(arrays[i]);
        }

        //选择排序
        int[] a1 = a.clone();
        SelectionSort.sort(a1);
        assert isSorted(a1);
        show(a1, "SelectionSort: ");

        //冒泡排序
        int[] a2 = a.clone();
        BubbleSort.sort(a2);
        assert isSorted(a2);
        show(a2,"BubbleSort: ");

        //快速排序
        int[] a3 = a.clone();
        QuickSort.sort(a3,0,a.length-1);
        assert isSorted(a3);
        show(a3,"QuickSort: ");

    }


    public static boolean isSorted(int[] a){
        int n = a.length;

        /*
        从第二个元素开始遍历，如果比前一个数小则说明未按升序排列
         */
        for(int i = 1; i < n; i++){
            if(a[i] < a[i-1])
                return false;
        }
        return true;
    }

    public static void show(int[] a, String name){
        System.out.print(name);
        int n = a.length;
        for (int i = 0; i < n; i++)
            /*
            print不换行，printIn换行
             */
            System.out.print(a[i] + " ");

        System.out.println();
    }
}
