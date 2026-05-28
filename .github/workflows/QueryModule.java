package com.groupX.grade;

import java.util.ArrayList;

/**
 * 成绩查询模块
 * 支持按学号、姓名、分数段查询，并显示查询结果
 */
public class QueryModule {

    /**
     * 根据学号精确查询
     * @param list     成绩列表
     * @param studentId 要查询的学号
     * @return 匹配的学生成绩对象，找不到返回null
     */
    public StudentScore queryById(ArrayList<StudentScore> list, String studentId) {
        if (list == null || studentId == null || studentId.isEmpty()) {
            return null;
        }
        for (StudentScore ss : list) {
            if (ss.getStudentId().equals(studentId)) {
                return ss;
            }
        }
        return null;
    }

    /**
     * 根据姓名模糊查询（包含即可，忽略大小写）
     * @param list   成绩列表
     * @param name   要查询的姓名
     * @return 匹配的学生列表（可能多个同名）
     */
    public ArrayList<StudentScore> queryByName(ArrayList<StudentScore> list, String name) {
        ArrayList<StudentScore> result = new ArrayList<>();
        if (list == null || name == null || name.isEmpty()) {
            return result;
        }
        String lowerName = name.toLowerCase();
        for (StudentScore ss : list) {
            if (ss.getName().toLowerCase().contains(lowerName)) {
                result.add(ss);
            }
        }
        return result;
    }

    /**
     * 根据分数段查询（闭区间 [minScore, maxScore]）
     * @param list      成绩列表
     * @param minScore  最低分（包含）
     * @param maxScore  最高分（包含）
     * @return 符合分数段的学生列表
     */
    public ArrayList<StudentScore> queryByScoreRange(ArrayList<StudentScore> list, double minScore, double maxScore) {
        ArrayList<StudentScore> result = new ArrayList<>();
        if (list == null) {
            return result;
        }
        // 确保min <= max
        double low = Math.min(minScore, maxScore);
        double high = Math.max(minScore, maxScore);
        for (StudentScore ss : list) {
            double score = ss.getScore();
            if (score >= low && score <= high) {
                result.add(ss);
            }
        }
        return result;
    }

    /**
     * 查询分数低于某阈值的学生（用于预警）
     * @param list  成绩列表
     * @param threshold 阈值（低于该分数）
     * @return 低于阈值的学生列表
     */
    public ArrayList<StudentScore> queryBelowThreshold(ArrayList<StudentScore> list, double threshold) {
        return queryByScoreRange(list, 0.0, threshold - 0.01);
    }

    /**
     * 查询分数高于某阈值的学生
     * @param list  成绩列表
     * @param threshold 阈值（高于该分数）
     * @return 高于阈值的学生列表
     */
    public ArrayList<StudentScore> queryAboveThreshold(ArrayList<StudentScore> list, double threshold) {
        return queryByScoreRange(list, threshold + 0.01, 100.0);
    }

    /**
     * 打印查询结果（带序号）
     * @param result 查询结果列表
     */
    public void printQueryResult(ArrayList<StudentScore> result) {
        if (result == null || result.isEmpty()) {
            System.out.println("未找到任何匹配的记录。");
            return;
        }
        System.out.println("\n--- 查询结果（共 " + result.size() + " 条）---");
        System.out.println("序号\t学号\t\t姓名\t成绩");
        for (int i = 0; i < result.size(); i++) {
            StudentScore ss = result.get(i);
            System.out.printf("%d\t%s\t%s\t%.2f\n",
                    i + 1, ss.getStudentId(), ss.getName(), ss.getScore());
        }
    }

    /**
     * 增强查询菜单（交互式）
     * @param list 成绩列表
     */
    public void interactiveQuery(ArrayList<StudentScore> list) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        while (true) {
            System.out.println("\n========== 成绩查询子系统 ==========");
            System.out.println("1. 按学号精确查询");
            System.out.println("2. 按姓名模糊查询");
            System.out.println("3. 按分数段查询");
            System.out.println("4. 查询不及格学生（<60）");
            System.out.println("5. 查询优秀学生（≥90）");
            System.out.println("6. 返回主菜单");
            System.out.print("请选择操作：");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.print("请输入学号：");
                    String id = scanner.nextLine().trim();
                    StudentScore s = queryById(list, id);
                    if (s == null) {
                        System.out.println("未找到学号为 " + id + " 的学生。");
                    } else {
                        System.out.println(s);
                    }
                    break;
                case "2":
                    System.out.print("请输入姓名关键字：");
                    String name = scanner.nextLine().trim();
                    ArrayList<StudentScore> byName = queryByName(list, name);
                    printQueryResult(byName);
                    break;
                case "3":
                    System.out.print("请输入最低分：");
                    double min = Double.parseDouble(scanner.nextLine().trim());
                    System.out.print("请输入最高分：");
                    double max = Double.parseDouble(scanner.nextLine().trim());
                    ArrayList<StudentScore> byRange = queryByScoreRange(list, min, max);
                    printQueryResult(byRange);
                    break;
                case "4":
                    ArrayList<StudentScore> fail = queryBelowThreshold(list, 60.0);
                    printQueryResult(fail);
                    break;
                case "5":
                    ArrayList<StudentScore> excellent = queryAboveThreshold(list, 89.99);
                    printQueryResult(excellent);
                    break;
                case "6":
                    return;
                default:
                    System.out.println("无效输入，请重新选择。");
            }
        }
    }
}