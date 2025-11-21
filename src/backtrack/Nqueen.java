package backtrack;

import java.util.*;
public class Nqueen {
    static List<List<String>> res = new ArrayList<>();

    public static List<List<String>> solveNQueens(int n) {
        char[][] chessboard = new char[n][n];
        // 初始化棋盘
        for (char[] c : chessboard) {
            Arrays.fill(c, '.');
        }
        backtracking(chessboard, n, 0);
        return res;
    }

    public static void backtracking(char[][] chessboard, int n, int row) {
        if (row == n) {
            // 每轮需要新建一个list存放当前解
            List<String> path = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                // 每行新建一个StringBuilder存放当前行的字符串
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < n; j++) {
                    sb.append(chessboard[i][j]);
                }
                // 把当前行的字符串加入path
                path.add(sb.toString());
            }
            res.add(path);
            return;
        }
        for (int col = 0; col < n; col++) {
            if (isValid(col, row, n, chessboard)) {
                chessboard[row][col] = 'Q';
                backtracking(chessboard, n, row + 1);
                chessboard[row][col] = '.';
            }
        }
    }

    public static boolean isValid(int col, int row, int n, char[][] chessboard) {
        // 从上往下放因此行不会有冲突
        // 检查同一列是否有皇后冲突
        for (int i = 0; i < row; i++) {
            if (chessboard[i][col] == 'Q') {
                return false;
            }
        }
        // 检查左上和右上对角线是否有皇后冲突
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (chessboard[i][j] == 'Q')
                return false;
        }
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (chessboard[i][j] == 'Q')
                return false;
        }

        return true;
    }


    // 解数独
    // 数独要求：
    //  每一行 1~9 不重复
    //  每一列 1~9 不重复
    //  每个 3×3 小宫格 1~9 不重复
    public static void solveSudoku(char[][] board) {
        solveSudokuHelper(board);
    }
    private static boolean solveSudokuHelper(char[][] board){
        //「一个for循环遍历棋盘的行，一个for循环遍历棋盘的列，
        // 一行一列确定下来之后，递归遍历这个位置放9个数字的可能性！」
        for (int i = 0; i < 9; i++){ // 遍历行
            for (int j = 0; j < 9; j++){ // 遍历列
                if (board[i][j] != '.'){ // 跳过原始数字
                    continue;
                }
                for (char k = '1'; k <= '9'; k++){ // (i, j) 这个位置放k是否合适
                    if (isValidSudoku(i, j, k, board)){
                        board[i][j] = k;
                        if (solveSudokuHelper(board)){ // 如果找到合适一组立刻返回
                            return true;
                        }
                        board[i][j] = '.';
                    }
                }
                // 9个数都试完了，都不行，那么就返回false
                return false;
                // 因为如果一行一列确定下来了，这里尝试了9个数都不行，说明这个棋盘找不到解决数独问题的解！
                // 那么会直接返回， 「这也就是为什么没有终止条件也不会永远填不满棋盘而无限递归下去！」
            }
        }
        // 遍历完没有返回false，说明找到了合适棋盘位置了
        return true;
    }

    private static boolean isValidSudoku(int row, int col, char val, char[][] board){
        // 同行是否重复
        for (int i = 0; i < 9; i++){
            if (board[row][i] == val){
                return false;
            }
        }
        // 同列是否重复
        for (int j = 0; j < 9; j++){
            if (board[j][col] == val){
                return false;
            }
        }
        // 9宫格里是否重复
        // 先整除再乘3得到起始位置
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = startRow; i < startRow + 3; i++){
            for (int j = startCol; j < startCol + 3; j++){
                if (board[i][j] == val){
                    return false;
                }
            }
        }
        return true;
    }

    public static void main (String[] args) {
        int n = 4;
        List<List<String>> solutions = solveNQueens(n);
        for (List<String> solution : solutions) {
            for (String row : solution) {
                System.out.println(row);
            }
            System.out.println();
        }

        char[][] board = {
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '6', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        solveSudoku(board);
        System.out.println("Solved Sudoku:");
        for (char[] row : board) {
            System.out.println(Arrays.toString(row));
        }
    }
}
