package com.groupX.grade;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 成绩录入模块
 * 负责从控制台读取学生信息（学号、姓名、成绩），并存入列表中
 * 支持批量录入、去重校验、成绩范围校验
 */
public class InputModule {
    private ArrayList<StudentScore> scoreList;
    private Scanner scanner;

    public InputModule() {
        scoreList = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    /**
     * 获取当前已录入的所有成绩列表
     * @return 成绩列表的副本（防止外部直接修改）
     */
    public ArrayList<StudentScore> getScoreList() {
        return new ArrayList<>(scoreList);
    }

    /**
     * 开始录入菜单，循环让用户选择
     */
    public void startInput() {
        while (true) {
            System.out.println("\n========== 成绩录入子系统 ==========");
            System.out.println("1. 添加单个学生成绩");
            System.out.println("2. 批量添加（预设示例）");
            System.out.println("3. 显示当前所有已录入成绩");
            System.out.println("4. 清空所有数据");
            System.out.println("5. 返回主菜单");
            System.out.print("请选择操作（1-5）：");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    addSingleScore();
                    break;
                case "2":
                    batchAddExample();
                    break;
                case "3":
                    displayAllScores();
                    break;
                case "4":
                    clearAll();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("输入无效，请重新选择。");
            }
        }
    }

    /**
     * 添加单个学生成绩，会检查学号是否已存在（不允许重复）
     */
    private void addSingleScore() {
        System.out.println("\n--- 添加单个学生成绩 ---");
        System.out.print("请输入学号（如 2021001）：");
        String id = scanner.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("学号不能为空！");
            return;
        }
        // 检查学号重复
        for (StudentScore ss : scoreList) {
            if (ss.getStudentId().equals(id)) {
                System.out.println("学号 " + id + " 已存在，不允许重复添加！");
                return;
            }
        }

        System.out.print("请输入姓名：");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("姓名不能为空！");
            return;
        }

        System.out.print("请输入成绩（0-100）：");
        double score;
        try {
            score = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("成绩输入格式错误！");
            return;
        }
        if (score < 0 || score > 100) {
            System.out.println("成绩必须在 0~100 之间！");
            return;
        }

        StudentScore newScore = new StudentScore(id, name, score);
        scoreList.add(newScore);
        System.out.println("添加成功！当前共有 " + scoreList.size() + " 条记录。");
    }

    /**
     * 批量添加示例数据（用于快速测试）
     */
    private void batchAddExample() {
        System.out.println("\n--- 批量添加示例数据 ---");
        // 预先准备一些示例学生
        String[][] sample = {
                {"2023001", "张三", "85.5"},
                {"2023002", "李四", "92.0"},
                {"2023003", "王五", "76.5"},
                {"2023004", "赵六", "88.0"},
                {"2023005", "小明", "67.5"}
        };
        int addedCount = 0;
        for (String[] data : sample) {
            String id = data[0];
            String name = data[1];
            double score = Double.parseDouble(data[2]);
            // 检查是否已存在
            boolean exists = false;
            for (StudentScore ss : scoreList) {
                if (ss.getStudentId().equals(id)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                scoreList.add(new StudentScore(id, name, score));
                addedCount++;
            }
        }
        System.out.println("成功添加 " + addedCount + " 条示例记录，当前共 " + scoreList.size() + " 条。");
    }

    /**
     * 显示当前所有成绩
     */
    private void displayAllScores() {
        if (scoreList.isEmpty()) {
            System.out.println("当前没有任何成绩数据，请先录入。");
            return;
        }
        System.out.println("\n--- 当前所有成绩列表 ---");
        System.out.println("序号\t学号\t\t姓名\t成绩");
        for (int i = 0; i < scoreList.size(); i++) {
            StudentScore ss = scoreList.get(i);
            System.out.printf("%d\t%s\t%s\t%.2f\n",
                    i + 1, ss.getStudentId(), ss.getName(), ss.getScore());
        }
        System.out.println("共 " + scoreList.size() + " 条记录。");
    }

    /**
     * 清空所有数据（需要二次确认）
     */
    private void clearAll() {
        if (scoreList.isEmpty()) {
            System.out.println("列表已空，无需清空。");
            return;
        }
        System.out.print("确认清空所有成绩数据吗？(y/n)：");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (confirm.equals("y") || confirm.equals("yes")) {
            scoreList.clear();
            System.out.println("已清空所有成绩记录。");
        } else {
            System.out.println("已取消清空操作。");
        }
    }
}