# 广度优先搜索（BFS）理论基础

在讲完深度优先搜索（DFS）之后，下面我们来看** ****广度优先搜索（BFS）**。

DFS 和 BFS 都是基础搜索算法，但**搜索方式完全不同**。

---

## 一、BFS 与 DFS 的区别

* **DFS（深度优先搜索）**
  认准一个方向一直往下走，走到尽头再回头（回溯）。
* **BFS（广度优先搜索）**
  从起点开始，**先遍历当前节点能到达的所有节点**，
  再一层一层向外扩展，搜索范围像“水波纹”。

👉 DFS 更关注“路径”，
👉 BFS 更关注“层次 / 距离”。

---

## 二、BFS 的搜索过程

BFS 的搜索过程可以理解为** ****分层搜索**。

1. 先访问起点
2. 把起点能到达的节点全部加入队列
3. 按照“先进先出”的顺序依次访问
4. 每一轮扩展一层

所以** ****BFS 一定要用队列（Queue）来实现**。

---

## 三、BFS 为什么不用回溯

* BFS 不会走“死胡同再回来”
* 每个节点**第一次出队**时，已经是**最短路径**
* 因此** ****不需要回溯撤销选择**

👉 这也是 BFS 常用来求** ****最短路径** 的原因。

---

## 四、BFS 的代码框架（通用思想）

```text
queue.offer(起点);
标记起点已访问;

while (队列不为空) {
    当前节点 = queue.poll();
    for (当前节点的所有相邻节点) {
        if (未访问) {
            标记已访问;
            queue.offer(相邻节点);
        }
    }
}
```

---

## 五、BFS 三部曲

### 1️⃣ 确认数据结构

* **队列 Queue**（先进先出）
* **visited 数组**（防止重复访问）

---

### 2️⃣ 初始化

```text
queue.offer(start);
visited[start] = true;
```

---

### 3️⃣ 开始按层遍历

```text
while (!queue.isEmpty()) {
    int cur = queue.poll();
    for (next : cur 的相邻节点) {
        if (!visited[next]) {
            visited[next] = true;
            queue.offer(next);
        }
    }
}
```

---

# Java 模板（保持原始风格）

## 模板一：BFS 遍历图

```java
void bfs(int start) {
    Queue<Integer> queue = new LinkedList<>();
    boolean[] visited = new boolean[n];

    queue.offer(start);
    visited[start] = true;

    while (!queue.isEmpty()) {
        int cur = queue.poll();
        for (int next : graph[cur]) {
            if (!visited[next]) {
                visited[next] = true;
                queue.offer(next);
            }
        }
    }
}
```

---

## 模板二：BFS 求最短路径（层数）

```java
int bfs(int start, int target) {
    Queue<Integer> queue = new LinkedList<>();
    boolean[] visited = new boolean[n];
    int step = 0;

    queue.offer(start);
    visited[start] = true;

    while (!queue.isEmpty()) {
        int size = queue.size(); // 当前层节点数
        for (int i = 0; i < size; i++) {
            int cur = queue.poll();
            if (cur == target) return step;

            for (int next : graph[cur]) {
                if (!visited[next]) {
                    visited[next] = true;
                    queue.offer(next);
                }
            }
        }
        step++; // 走完一层，步数 +1
    }
    return -1; // 不可达
}
```

---

## 六、DFS vs BFS 一句话总结

> * **DFS**：一条路走到黑，靠回溯换方向
> * **BFS**：一层一层向外扩，第一次到达就是最短
