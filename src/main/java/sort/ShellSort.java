package sort;

/**
 * @author Zhang Bowen
 * @Description
 * @ClassName ShellSort
 * @date 2021.01.22 11:41
 */
public class ShellSort {
    public static void sort(int[] a){
        int n = a.length;
        int h = 1;
        while (h < n/3)
            h = 3*h + 1;

        while (h >= 1){
            for(int i = h; i < n; i++){
                for (int j = i; j > 0 && a[j] < a[j-h]; j-=h){
                    exch(a,j,j-=h);
                }
            }
            h = h/3;
        }

    }

    public static void exch(int[] a, int i, int j){
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
