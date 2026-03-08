package hashtable;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Randomized {
    // 设计一个支持在平均 时间复杂度 O(1) 下，执行以下操作的数据结构。
    //
    // insert(val)：当元素 val 不存在时，向集合中插入该项。
    // remove(val)：当元素 val 存在时，从集合中移除该项。
    // getRandom：随机返回现有集合中的一项。每个元素应该有相同的概率被返回。

    // 这里使用Map存储元素及其在列表中的索引，使用List存储元素，方便随机访问。插入和删除操作都可以在O(1)时间内完成。
    class RandomizedSet {
        List<Integer> nums; // 存储元素的列表
        Map<Integer, Integer> indices; // 存储元素及其在列表中的索引

        public RandomizedSet() {
            nums = new ArrayList<>();
            indices = new HashMap<>();
        }

        // 插入元素，如果元素已存在返回 false，否则插入并返回 true
        public boolean insert(int val) {
            if (indices.containsKey(val)) {
                return false;
            }
            int index = nums.size(); // 新元素的索引是当前列表的大小
            nums.add(val); // 将元素添加到列表末尾
            indices.put(val, index); // 在哈希表中记录元素及其索引
            return true;
        }

        // 移除元素，如果元素不存在返回 false，否则移除并返回 true
        // 从末尾找一个元素替换要移除的元素，然后更新哈希表中的索引，最后删除多余的末尾元素
        public boolean remove(int val) {
            if (!indices.containsKey(val)) {
                return false;

            }
            int index = indices.get(val); // 获取要移除元素的索引
            int last = nums.get(nums.size() - 1); // 获取列表中最后一个元素
            nums.set(index, last); // 将最后一个元素移动到要移除元素的位置
            indices.put(last, index); // 更新最后一个元素在哈希表中的索引
            nums.remove(nums.size() - 1); // 删除列表末尾的元素
            indices.remove(val); // 从哈希表中移除要删除的元素
            return true;
        }

        public int getRandom() {
            int randomIndex = ThreadLocalRandom.current().nextInt(nums.size()); // 生成一个随机索引
            return nums.get(randomIndex);
        }
    }
}
