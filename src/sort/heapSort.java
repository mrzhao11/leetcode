package sort;

public class heapSort {
    // 堆排序
    // 时间复杂度 O(nlogn)，空间复杂度 O(1)，不稳定排序
    // 利用完全二叉树构成的 大顶堆，不断把 最大值（堆顶）换到数组末尾，再对剩余部分重新维护堆结构。
    public static int[] heapSort(int[] arr) {
        if (arr.length < 2) return arr;
        heapify(arr, arr.length - 1); // 构建大顶堆
        for (int i = arr.length - 1; i > 0; i--) {
            swap.swap(arr, 0, i);        // 堆顶和末尾交换，这个时候后面是有序的了，只需对前面部分堆化
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

    // 下滤方法, hole 是要下滤的结点下标，r 是最后一个元素下标，把 hole 处的元素下滤到合适位置
    // 下滤结束后，区间 [0, r] 满足堆序
    private static void siftDown(int[] arr, int hole, int r) {
        int target = arr[hole], child = hole * 2 + 1; // target 是要下滤的结点的值
        while(child <= r) { // child 最大为 r
            // 如果右孩子存在，且右孩子大于左孩子，则 child 指向右孩子
            if (child < r && arr[child + 1] > arr[child])
                child++; // 选出较大的孩子结点
            if (arr[child] > target) { // 若 child 大于 target
                arr[hole] = arr[child]; // 则 arr[child] 上移到下标 hole 处
                hole = child; // hole 更新为 child (下滤)
                child = hole * 2 + 1; // 更新 child ，也可以写成 child = child * 2 + 1
            } else break; // 若 arr[child] <= target ，说明下标 hole 处已经满足堆序，退出 while
        }
        // 下滤结束后，hole 处是空穴，放入 target
        arr[hole] = target;
    }
}
