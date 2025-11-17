package sort;

public class heapSort {
    // 堆排序
    public static int[] heapSort(int[] arr) {
        if (arr.length < 2) return arr;
        heapify(arr, arr.length - 1); // 构建大顶堆
        for (int i = arr.length - 1; i > 0; i--) {
            swap.swap(arr, 0, i);        // 最大值（堆顶）换到未排序部分的最后
            siftDown(arr, 0, i - 1);     // 对 [0, i-1] 再次堆化（只需要从堆顶下滤）
        }
        return arr;
    }

    // 堆化方法
    // 数组用完全二叉树的方式映射堆，父节点i，左孩子2i+1，右孩子2i+2，r是最后一个元素下标，(r - 1) / 2 为最后一个非叶子结点的下标
    private static void heapify(int[] arr, int r) {
        for (int hole = (r - 1) / 2; hole >= 0; hole--) { // 从最后一个非叶子结点开始，依次下滤，一直到根结点
            siftDown(arr, hole, r);
        }
    }

    // 下滤方法
    private static void siftDown(int[] arr, int hole, int r) {
        int target = arr[hole], child = hole * 2 + 1; // target 是要下滤的结点的值
        while(child <= r) { // child 最大为 r
            if (child < r && arr[child + 1] > arr[child]) child++; // 选出左右孩子更大的那个
            if (arr[child] > target) { // 若 child 大于 target
                arr[hole] = arr[child]; // 则 arr[child] 上移到下标 hole 处
                hole = child; // hole 更新为 child (下滤)
                child = hole * 2 + 1; // 更新 child ，也可以写成 child = child * 2 + 1
            } else break; // 若 arr[child] <= target ，说明下标 hole 处已经满足堆序，退出 while
        }
        arr[hole] = target; // 将 target 填 入hole 中
    }
}
