package graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class CourseSchedule {
    // 课程表
    // 你这个学期必须选修 numCourses 门课程，记为 0 到 numCourses-1 。
    // 在选修某些课程之前需要一些先修课程。 例如，想要学习课程 0 ，你需要先完成课程 1 ，
    // 这表示为先修课程对 [0,1] 。
    // 给你课程总量 numCourses 和一个先修课程列表 prerequisites ，
    // 请你判断是否可能完成所有课程的学习？如果可以，返回 true；否则返回 false 。

    // 拓扑排序，检测有向图中是否有环，如果有环则无法完成课程

    // DFS解法
    // 从每门课程出发，进行深度优先搜索（DFS），遍历所有课程
    // 如果遇到正在访问的课程，说明存在环，返回 false
    // 如果所有课程都能遍历完成且没有环，返回 true
    public static boolean canFinishDFS(int numCourses, int[][] prerequisites) {
        // 构建课程的邻接表
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }

        // 填充邻接表
        for (int[] prereq : prerequisites) {
            graph.get(prereq[1]).add(prereq[0]); // 找到索引为 prereq[1] 的课程（先修课程），添加索引为 prereq[0] 的课程（后续课程）
        }
        // b->a 表示要想学 a 课程，必须先学 b 课程，即 b 指向 a
        // 例：graph[1] = [0] 表示要想学 0 课程，必须先学 1 课程

        // 用一个数组来标记课程的状态：0 -> 未访问，1 -> 访问中，2 -> 已访问
        int[] visited = new int[numCourses];

        // 遍历所有课程
        for (int i = 0; i < numCourses; i++) {
            if (visited[i] == 0) {
                // 如果该课程未访问过，则开始DFS
                if (hasCycle(graph, visited, i)) {
                    return false; // 如果存在环，返回 false
                }
            }
        }

        return true; // 如果没有环，返回 true
    }

    // 深度优先搜索，检测是否有环
    private static boolean hasCycle(List<List<Integer>> graph, int[] visited, int node) {
        if (visited[node] == 1) {
            // 如果节点在当前的递归路径中（正在访问），则表示存在环
            return true;
        }

        if (visited[node] == 2) {
            // 如果节点已经访问过，说明没有环
            return false;
        }

        visited[node] = 1; // 标记当前节点正在访问
        // 遍历当前节点的所有邻居，看是否有环
        for (int neighbor : graph.get(node)) {
            if (hasCycle(graph, visited, neighbor)) {
                return true;
            }
        }
        visited[node] = 2; // 标记当前节点访问完成
        return false;
    }

    // BFS解法（Kahn算法）
    // 计算每门课程的入度（即有多少先修课程指向它）
    // 将所有入度为 0 的课程加入队列，表示这些课程可以直接学习
    // 依次从队列中取出课程，减少其后续课程的入度
    // 如果某个后续课程的入度变为 0，则将其加入队列
    // 最终如果所有课程都被处理过，说明可以完成所有课程，返回 true；否则返回 false
    public static boolean canFinishBFS(int numCourses, int[][] prerequisites) {
        // 构建课程的邻接表
        List<List<Integer>> graph = new ArrayList<>();
        int[] indegree = new int[numCourses]; // 记录每个课程的入度

        // 初始化图和入度
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] prereq : prerequisites) {
            graph.get(prereq[1]).add(prereq[0]); // 课程后面的课程依赖关系
            indegree[prereq[0]]++; // 课程的入度增加
        }
        // graph[1] = [0] 表示要想学 0 课程，必须先学 1 课程，graph下标表示先修课程，值表示后续课程
        // indegree[0] = 1 表示课程 0 有 1 门先修课程，indegree 下标表示课程，值表示入度

        Queue<Integer> queue = new ArrayDeque<>();
        int count = 0; // 记录已经处理过的课程数量
        // 假如需要返回课程的学习顺序，可以在这里定义一个数组来存储结果
//        int[] res = new int[numCourses]; // 存储课程的学习顺序

        // 把所有入度为 0 的课程加入队列
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        // BFS 过程
        while (!queue.isEmpty()) {
            int course = queue.poll();
            count++;
            // res[count++] = course; // 记录课程的学习顺序
            // 找到当前course的所有后续课程
            for (int nextCourse : graph.get(course)) {
                indegree[nextCourse]--; // 学完当前课程，后续课程的入度减 1
                if (indegree[nextCourse] == 0) {
                    queue.offer(nextCourse);
                }
            }
        }

        // 如果需要返回课程的学习顺序，可以在这里判断是否所有课程都被处理过，并返回结果
//        return count == numCourses ? res : new int[0];
        // 如果所有课程都被处理过，返回 true，否则返回 false
        return count == numCourses;
    }

    public static void main(String[] args) {
        int numCourses = 2;
        int[][] prerequisites = {{1,0},{0,1}};
        System.out.println(canFinishDFS(numCourses, prerequisites)); // false
        System.out.println(canFinishBFS(numCourses, prerequisites)); // false
    }
}
