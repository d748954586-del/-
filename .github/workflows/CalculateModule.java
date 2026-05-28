package com.groupX.grade;

import java.util.ArrayList;

/**
 * 成绩计算模块
 * 提供总分、平均分、最高/最低分、加权总分等计算功能
 */
public class CalculateModule {

    /**
     * 计算所有成绩的总分
     * @param list 成绩列表
     * @return 总分，若列表为空则返回0.0
     */
    public double calculateTotal(ArrayList<StudentScore> list) {
        if (list == null || list.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (StudentScore ss : list) {
            sum += ss.getScore();
        }
        return sum;
    }

    /**
     * 计算平均分
     * @param list 成绩列表
     * @return 平均分，若列表为空则返回0.0
     */
    public double calculateAverage(ArrayList<StudentScore> list) {
        if (list == null || list.isEmpty()) {
            return 0.0;
        }
        return calculateTotal(list) / list.size();
    }

    /**
     * 获取最高分
     * @param list 成绩列表
     * @return 最高分，如果列表为空返回0.0
     */
    public double getMaxScore(ArrayList<StudentScore> list) {
        if (list == null || list.isEmpty()) {
            return 0.0;
        }
        double max = list.get(0).getScore();
        for (StudentScore ss : list) {
            if (ss.getScore() > max) {
                max = ss.getScore();
            }
        }
        return max;
    }

    /**
     * 获取最低分
     * @param list 成绩列表
     * @return 最低分，如果列表为空返回0.0
     */
    public double getMinScore(ArrayList<StudentScore> list) {
        if (list == null || list.isEmpty()) {
            return 0.0;
        }
        double min = list.get(0).getScore();
        for (StudentScore ss : list) {
            if (ss.getScore() < min) {
                min = ss.getScore();
            }
        }
        return min;
    }

    /**
     * 计算加权总分（课程成绩占比由权重数组指定，需与成绩列表长度一致）
     * @param list    成绩列表
     * @param weights 每个成绩对应的权重（0~1之间，总和应为1）
     * @return 加权总分，若列表为空或长度不匹配返回0.0
     */
    public double calculateWeightedScore(ArrayList<StudentScore> list, double[] weights) {
        if (list == null || list.isEmpty() || weights == null) {
            return 0.0;
        }
        if (list.size() != weights.length) {
            System.err.println("错误：成绩数量与权重数量不一致！");
            return 0.0;
        }
        double weightedSum = 0.0;
        for (int i = 0; i < list.size(); i++) {
            weightedSum += list.get(i).getScore() * weights[i];
        }
        return weightedSum;
    }

    /**
     * 计算及格率（成绩 >= 60 视为及格）
     * @param list 成绩列表
     * @return 及格率（0~1之间的double）
     */
    public double calculatePassRate(ArrayList<StudentScore> list) {
        if (list == null || list.isEmpty()) {
            return 0.0;
        }
        int passCount = 0;
        for (StudentScore ss : list) {
            if (ss.getScore() >= 60.0) {
                passCount++;
            }
        }
        return (double) passCount / list.size();
    }

    /**
     * 计算优秀率（成绩 >= 90 视为优秀）
     * @param list 成绩列表
     * @return 优秀率
     */
    public double calculateExcellentRate(ArrayList<StudentScore> list) {
        if (list == null || list.isEmpty()) {
            return 0.0;
        }
        int excellentCount = 0;
        for (StudentScore ss : list) {
            if (ss.getScore() >= 90.0) {
                excellentCount++;
            }
        }
        return (double) excellentCount / list.size();
    }

    /**
     * 打印完整统计报告（总分、平均分、最高/低分、及格率、优秀率）
     * @param list 成绩列表
     */
    public void printFullReport(ArrayList<StudentScore> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("无成绩数据，无法生成统计报告。");
            return;
        }
        System.out.println("\n========== 成绩统计报告 ==========");
        System.out.printf("学生总人数：%d\n", list.size());
        System.out.printf("总成绩：%.2f\n", calculateTotal(list));
        System.out.printf("平均分：%.2f\n", calculateAverage(list));
        System.out.printf("最高分：%.2f\n", getMaxScore(list));
        System.out.printf("最低分：%.2f\n", getMinScore(list));
        System.out.printf("及格率：%.2f%%\n", calculatePassRate(list) * 100);
        System.out.printf("优秀率（≥90）：%.2f%%\n", calculateExcellentRate(list) * 100);
        System.out.println("===================================");
    }
}