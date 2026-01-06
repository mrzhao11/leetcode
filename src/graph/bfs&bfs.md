# 一、DFS 通用模板（Java）

## 1️⃣ DFS（递归版 · 最通用）

```java
void dfs(参数...) {
    // 1. 终止条件（越界 / 不合法 / 已访问）
    if (终止条件) return;

    // 2. 标记当前状态
    visited[当前状态] = true;
    // 或：修改原数组 / 记录路径 / 计数

    // 3. 遍历所有可选分支
    for (所有 next 状态) {
        if (!visited[next]) {
            dfs(next);
        }
    }

    // 4. 回溯（如需要）
    visited[当前状态] = false;
}
```

📌** ****适用**

* 树 / 图 / 网格
* 回溯 / 组合 / 排列
* 连通块 / 岛屿

---

## 2️⃣ DFS（回溯模板 · 状态搜索）

```java
void dfs(int index, List<Integer> path) {
    // 1. 收集结果（可选）
    res.add(new ArrayList<>(path));

    // 2. 选择列表
    for (int i = index; i < n; i++) {
        // 3. 做选择
        path.add(i);

        // 4. 递归
        dfs(i + 1, path);

        // 5. 撤销选择
        path.remove(path.size() - 1);
    }
}
```

📌** ****一句话记忆**

> DFS = 选择 → 递归 → 撤销

---

## 3️⃣ DFS（显式栈版 · 非递归）

```java
void dfs(int start) {
    Stack<Integer> stack = new Stack<>();
    boolean[] visited = new boolean[n];

    stack.push(start);

    while (!stack.isEmpty()) {
        int cur = stack.pop();
        if (visited[cur]) continue;

        visited[cur] = true;

        for (int next : graph[cur]) {
            if (!visited[next]) {
                stack.push(next);
            }
        }
    }
}
```

📌** ****用途**

* 防止递归栈溢出
* 面试官要求非递归

---

# 二、BFS 通用模板（Java）

## 1️⃣ BFS（最基础）

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

📌** ****关键词**

> Queue + visited + while

---

## 2️⃣ BFS（分层 / 最短路径模板）

```java
int bfs(int start) {
    Queue<Integer> queue = new LinkedList<>();
    boolean[] visited = new boolean[n];

    queue.offer(start);
    visited[start] = true;

    int step = 0;

    while (!queue.isEmpty()) {
        int size = queue.size(); // 当前层

        for (int i = 0; i < size; i++) {
            int cur = queue.poll();

            if (满足终止条件) return step;

            for (int next : graph[cur]) {
                if (!visited[next]) {
                    visited[next] = true;
                    queue.offer(next);
                }
            }
        }
        step++;
    }
    return -1;
}
```

📌** ****一句话**

> BFS 层数 = 最短步数

---

## 3️⃣ BFS（多源起点）

```java
void bfs() {
    Queue<int[]> queue = new LinkedList<>();
    boolean[][] visited = new boolean[m][n];

    // 1. 初始化多个起点
    for (所有起点) {
        queue.offer(起点);
        visited[x][y] = true;
    }

    // 2. 正常 BFS
    while (!queue.isEmpty()) {
        int[] cur = queue.poll();

        for (所有方向) {
            if (合法 && !visited) {
                visited = true;
                queue.offer(next);
            }
        }
    }
}
```

📌** ****用途**

* 扩散 / 感染 / 最近距离

---

# 三、图 / 网格通用方向数组（必背）

```java
int[][] dirs = {
    {1, 0}, {-1, 0}, {0, 1}, {0, -1}
};
```

---

# 四、DFS vs BFS 决策模板（面试直接说）

```text
1. 需要所有可能性 → DFS
2. 需要最短路径 → BFS
3. 需要回溯撤销 → DFS
4. 层级 / 扩散 → BFS
```


```
