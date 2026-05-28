package com.groupX.grade;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 主菜单界面
 * 整合所有模块，提供命令行交互入口
 */
public class MainUI {
    private InputModule inputModule;
    private CalculateModule calcModule;
    private QueryModule queryModule;
    private StatisticsModule statsModule;
    private ArrayList<StudentScore> currentData; // 当前数据引用

    public MainUI() {
        inputModule = new InputModule();
        calcModule = new CalculateModule();
        queryModule = new QueryModule();
        statsModule = new StatisticsModule();
        currentData = inputModule.getScoreList(); // 初始空列表
    }

    /**
     * 启动主程序
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // 每次刷新从InputModule获取最新数据引用（因为InputModule内部维护列表）
            currentData = inputModule.getScoreList();
            System.out.println("\n========== 学生成绩管理系统 ==========");
            System.out.println("当前数据记录数：" + currentData.size());
            System.out.println("1. 成绩录入（添加/查看/清空）");
            System.out.println("2. 成绩计算（总分、平均分、及格率等）");
            System.out.println("3. 成绩查询（按学号/姓名/分数段）");
            System.out.println("4. 高级统计（分布直方图、中位数、标准差）");
            System.out.println("5. 显示所有成绩");
            System.out.println("6. 排序功能（按分数/学号）");
            System.out.println("7. 退出系统");
            System.out.print("请选择操作（1-7）：");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    inputModule.startInput();
                    break;
                case "2":
                    showCalculateMenu();
                    break;
                case "3":
                    queryModule.interactiveQuery(currentData);
                    break;
                case "4":
                    statsModule.printAdvancedStatistics(currentData);
                    break;
                case "5":
                    displayAll(currentData);
                    break;
                case "6":
                    showSortMenu();
                    break;
                case "7":
                    System.out.println("感谢使用，再见！");
                    return;
                default:
                    System.out.println("无效选择，请重试。");
            }
        }
    }

    /**
     * 显示所有成绩（简单封装）
     */
    private void displayAll(ArrayList<StudentScore> list) {
        if (list.isEmpty()) {
            System.out.println("暂无成绩数据，请先录入。");
            return;
        }
        System.out.println("\n=== 所有学生成绩列表 ===");
        System.out.println("学号\t\t姓名\t成绩");
        for (StudentScore ss : list) {
            System.out.printf("%s\t%s\t%.2f\n", ss.getStudentId(), ss.getName(), ss.getScore());
        }
    }

    /**
     * 计算菜单
     */
    private void showCalculateMenu() {
        if (currentData.isEmpty()) {
            System.out.println("没有数据，无法进行计算。请先录入成绩。");
            return;
        }
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- 成绩计算子菜单 ---");
            System.out.println("1. 显示总分、平均分、最高/最低分");
            System.out.println("2. 显示及格率和优秀率");
            System.out.println("3. 生成完整统计报告");
            System.out.println("4. 返回上一级");
            System.out.print("请选择：");
            String opt = sc.nextLine().trim();
            switch (opt) {
                case "1":
                    System.out.printf("总分：%.2f\n", calcModule.calculateTotal(currentData));
                    System.out.printf("平均分：%.2f\n", calcModule.calculateAverage(currentData));
                    System.out.printf("最高分：%.2f\n", calcModule.getMaxScore(currentData));
                    System.out.printf("最低分：%.2f\n", calcModule.getMinScore(currentData));
                    break;
                case "2":
                    System.out.printf("及格率：%.2f%%\n", calcModule.calculatePassRate(currentData) * 100);
                    System.out.printf("优秀率（≥90）：%.2f%%\n", calcModule.calculateExcellentRate(currentData) * 100);
                    break;
                case "3":
                    calcModule.printFullReport(currentData);
                    break;
                case "4":
                    return;
                default:
                    System.out.println("无效输入。");
            }
        }
    }

    /**
     * 排序菜单
     */
    private void showSortMenu() {
        if (currentData.isEmpty()) {
            System.out.println("无数据，无法排序。");
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- 排序功能 ---");
        System.out.println("1. 按分数升序（低->高）");
        System.out.println("2. 按分数降序（高->低）");
        System.out.println("3. 按学号升序");
        System.out.print("请选择排序方式：");
        String opt = sc.nextLine().trim();
        // 注意：排序会改变InputModule内部列表的顺序，因为currentData是同一个ArrayList对象
        switch (opt) {
            case "1":
                statsModule.sortByScoreAscending(currentData);
                System.out.println("已按分数升序排列，当前数据如下：");
                displayAll(currentData);
                break;
            case "2":
                statsModule.sortByScoreDescending(currentData);
                System.out.println("已按分数降序排列，当前数据如下：");
                displayAll(currentData);
                break;
            case "3":
                statsModule.sortByIdAscending(currentData);
                System.out.println("已按学号升序排列，当前数据如下：");
                displayAll(currentData);
                break;
            default:
                System.out.println("无效选项，返回主菜单。");
        }
    }

    /**
     * 程序入口
     */
    public static void main(String[] args) {
        MainUI ui = new MainUI();
        ui.run();
    }
}