package BinaryTree;

public class BST {

    //==================== 1. 插入 insertIntoBST ====================

    // 递归思路：
    // 契约（函数要做什么）：
    //   对于以 root 为根的这棵 BST，插入一个值为 val 的节点，并返回“更新后的这棵 BST 的根节点”。
    //
    // 递归四步：
    // 1）基本情况：root == null，说明走到空位置了，这里正是新节点应该插入的地方，直接创建返回。
    // 2）递归拆分：
    //      - 如果 val 比当前 root.val 大 → 递归去右子树插入；
    //      - 如果 val 比当前 root.val 小 → 递归去左子树插入。
    // 3）合并子结果：
    //      - 把递归返回的新子树根节点，接回到 root.left 或 root.right 上；
    // 4）返回当前 root 作为这一层子树的根。
    public TreeNode insertIntoBST(TreeNode root, int val) {
        // 基本情况：遇到空位置，新建一个节点挂在这里
        if (root == null) return new TreeNode(val);

        // 递归步骤：根据 BST 性质决定去左还是去右
        if (root.val < val) {
            // 在右子树插入，插完之后右子树的根可能被更新（例如原来是 null），要接回来
            root.right = insertIntoBST(root.right, val);
        }
        if (root.val > val) {
            // 在左子树插入，同理接回更新后的左子树根
            root.left = insertIntoBST(root.left, val);
        }

        // 合并结果：当前 root 的左右子树已经插入完毕，返回这棵子树的根
        return root;
    }

    //==================== 2. 删除 deleteNode ====================

    // 递归思路：
    // 契约：
    //   对于以 root 为根的这棵 BST，删除值为 key 的节点（如果存在），返回删除后的这棵树的根节点。
    //
    // 递归四步：
    // 1）基本情况：root == null，说明没找到，直接返回 null。
    // 2）递归拆分：
    //      - key < root.val → 目标在左子树，递归删除左子树；
    //      - key > root.val → 目标在右子树，递归删除右子树；
    //      - key == root.val → 当前就是要删的节点，进入“4 种情况处理”。
    // 3）在 key == root.val 时的 4 种情况（合并逻辑的一部分）：
    //      - 左右都空：直接删掉本节点（返回 null）；
    //      - 只有右：返回右孩子，让父节点接上；
    //      - 只有左：返回左孩子；
    //      - 左右都有：
    //          * 在右子树中找到最小节点（最左边）cur；
    //          * 把当前节点的左子树挂到 cur.left；
    //          * 整棵子树的新根就是 root.right。
    // 4）合并结果：对左/右子树递归删除后，要把返回值接回 root.left 或 root.right，并返回 root。
    public static TreeNode deleteNode(TreeNode root, int key) {
        // 基本情况：走到空节点，说明没找到要删的值
        if (root == null) return null;

        // 找到要删除的节点
        if (root.val == key) {
            // 情况1：左右孩子都为空 → 直接删除本节点
            if (root.left == null && root.right == null)
                return null;

                // 情况2：只有右孩子 → 用右孩子顶替当前节点
            else if (root.left == null && root.right != null)
                return root.right;

                // 情况3：只有左孩子 → 用左孩子顶替当前节点
            else if (root.left != null && root.right == null)
                return root.left;

                // 情况4：左右孩子都存在
            else {
                // 在右子树中找到最小节点（一直往左走）
                TreeNode cur = root.right;
                while (cur.left != null) {
                    cur = cur.left;
                }
                // 把当前节点的左子树挂到这个最小节点的左边
                cur.left = root.left;

                // 整棵子树的新根是原来的右子树
                return root.right;
            }
        }

        // 递归步骤：根据 key 与 root.val 的大小选择去左还是去右删除
        if (key < root.val) {
            // 在左子树中删除，更新左子树根
            root.left = deleteNode(root.left, key);
        } else {
            // 在右子树中删除，更新右子树根
            root.right = deleteNode(root.right, key);
        }

        // 合并结果：返回当前子树的根（左右子树已在递归中更新）
        return root;
    }

    //==================== 3. 修剪 trimBST ====================

    // 递归思路：
    // 契约：
    //   对于以 root 为根的这棵 BST，删除所有值不在 [low, high] 范围内的节点，
    //   返回修剪之后仍然是 BST 的新根。
    //
    // 递归关键点：充分利用 BST 性质 “左 < root < 右”。
    //
    // 1）基本情况：root == null → 返回 null。
    // 2）递归拆分：
    //      - 如果 root.val < low：
    //          整个左子树的值都 < root.val < low → 左子树全部不合法，可以整片丢掉，
    //          只保留“修剪后的右子树”，直接返回 trimBST(root.right)。
    //      - 如果 root.val > high：
    //          整个右子树的值都 > root.val > high → 右子树全部不合法，
    //          只保留“修剪后的左子树”，直接返回 trimBST(root.left)。
    //      - 如果 low <= root.val <= high：
    //          当前节点合法，递归修剪左右子树，然后接回。
    // 3）合并结果：对合法节点，将“修剪后的左右子树”接回，再返回 root。
    public static TreeNode trimBST(TreeNode root, int low, int high) {
        // 基本情况：空树直接返回
        if (root == null) return root;

        // 当前节点太小：整棵左子树都 < low，不可能保留
        if (root.val < low) {
            // 递归修剪右子树，并直接返回右子树作为新根
            TreeNode right = trimBST(root.right, low, high);
            return right;
        }

        // 当前节点太大：整棵右子树都 > high，不可能保留
        if (root.val > high) {
            // 递归修剪左子树，并直接返回左子树作为新根
            TreeNode left = trimBST(root.left, low, high);
            return left;
        }

        // 当前节点值在 [low, high] 内，保留当前节点
        // 递归修剪左子树和右子树，再分别接回
        root.left = trimBST(root.left, low, high);
        root.right = trimBST(root.right, low, high);

        // 返回修剪完的当前子树根
        return root;
    }

    //==================== 4. 有序数组 → 平衡 BST ====================

    // 递归思路：
    // 契约：
    //   对于有序数组 nums，构造一棵高度尽量平衡的 BST，并返回根节点。
    //
    // 典型“分治 + 递归建树”：
    // 1）整体思路：选中间元素作为根，左半部分递归建左子树，右半部分递归建右子树。
    // 2）基本情况：当区间 l > r 时，表示这个区间为空，不需要建树，返回 null。
    // 3）递归拆分：
    //      - mid = (l + r) / 2
    //      - root.val = nums[mid]
    //      - root.left = sort(nums, l, mid - 1)
    //      - root.right = sort(nums, mid + 1, r)
    // 4）合并结果：左右子树已构造完毕，返回 root。
    public static TreeNode sortedArrayToBST(int[] nums) {
        return sort(nums, 0, nums.length - 1);
    }

    private static TreeNode sort(int[] nums, int l, int r) {
        // 基本情况：区间为空，不用建树
        if (l > r)
            return null;

        // 选择中间位置作为根，使左右尽量平衡
        TreeNode root = new TreeNode(nums[(l + r) / 2]);

        // 递归构造左子树：用左半部分
        root.left = sort(nums, l, (l + r) / 2 - 1);
        // 递归构造右子树：用右半部分
        root.right = sort(nums, (l + r) / 2 + 1, r);

        // 合并结果：返回这棵子树的根
        return root;
    }

    //==================== 5. BST → 累加树 convertBST ====================

    // 递归思路：
    // 契约：
    //   把一棵 BST 转换成累加树：每个节点的新值 = 原树中所有 ≥ 当前节点值 的节点值之和。
    //
    // 利用 BST 的“中序遍历有序”性质：
    //   正常中序：左 → 根 → 右  → 值从小到大
    //   累加树需要“大到小”的累加 → 用 反中序：右 → 根 → 左
    //
    // 递归四步：
    // 1）用一个外部变量 pre 累加“到目前为止已经访问过的更大节点的和”。
    // 2）递归顺序：先处理右子树（大值），再处理当前节点，最后处理左子树。
    // 3）在“处理当前节点”时：
    //      - root.val += pre （当前节点新值 = 原值 + 已经累积的所有更大值）
    //      - pre = root.val （更新累积和）
    // 4）一直递归到底，更新整棵树。
    //
    // 注意：这里 pre 是 static，全局累加器，如果多次调用 convertBST 需要记得重置。
    static int pre = 0;

    public static TreeNode convertBST(TreeNode root) {
        // 基本情况：空节点，直接返回
        if (root == null) return root;

        // 递归步骤：先处理右子树（比当前节点大的所有值）
        root.right = convertBST(root.right);

        // 处理中间：当前节点值加上“比它大的所有值之和”
        root.val += pre;
        // 更新累加和：之后的左子树节点也要包含这个新值
        pre = root.val;

        // 再处理左子树（比当前节点小的值）
        root.left = convertBST(root.left);

        // 返回当前子树根
        return root;
    }

}
