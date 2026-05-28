package com.groupX.grade;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 成绩统计模块
 * 提供分数分布直方图、分数段人数统计、排序等功能
 */
public class StatisticsModule {

    /**
     * 获取分数段分布（默认按 [0-59], [60-69], [70-79], [80-89], [90-100] 五档）
     * @param list 成绩列表
     * @return 长度为5的int数组，依次表示每个区间的人数
     */
    public int[] getDefaultDistribution(ArrayList<StudentScore> list) {
        int[] dist = new int[5]; // 0: 0-59, 1:60-69, 2:70-79, 3:80-89, 4:90-100
        if (list == null) return dist;
        for (StudentScore ss : list) {
            double score = ss.getScore();
            if (score < 60) dist[0]++;
            else if (score < 70) dist[1]++;
            else if (score < 80) dist[2]++;
            else if (score < 90) dist[3]++;
            else dist[4]++;
        }
        return dist;
    }

    /**
     * 自定义区间的分数分布（传入区间边界，例如 [0,60,70,80,90,100] 会生成5个区间）
     * @param list     成绩列表
     * @param boundaries 分界点数组（升序，第一个为最小值，最后一个为最大值）
     * @return 每个区间的人数，长度 = boundaries.length - 1
     */
    public int[] getCustomDistribution(ArrayList<StudentScore> list, double[] boundaries) {
        if (list == null || boundaries == null || boundaries.length < 2) {
            return new int[0];
        }
        int[] dist = new int[boundaries.length - 1];
        for (StudentScore ss : list) {
            double score = ss.getScore();
            for (int i = 0; i < boundaries.length - 1; i++) {
                if (score >= boundaries[i] && score <= boundaries[i+1]) {
                    dist[i]++;
                    break;
                }
            }
        }
        return dist;
    }

    /**
     * 打印分数分布直方图（使用星号*）
     * @param dist    分布数组
     * @param labels  每个区间的标签，例如 ["0-59", "60-69"...]
     */
    public void printHistogram(int[] dist, String[] labels) {
        if (dist == null || labels == null || dist.length != labels.length) {
            System.out.println("分布数据或标签无效。");
            return;
        }
        System.out.println("\n===== 分数分布直方图 =====");
        int maxCount = 0;
        for (int count : dist) {
            if (count > maxCount) maxCount = count;
        }
        // 如果最大数量超过50，按比例压缩
        double scale = (maxCount > 50) ? (50.0 / maxCount) : 1.0;
        for (int i = 0; i < dist.length; i++) {
            System.out.printf("%-10s : ", labels[i]);
            int barLength = (int) Math.ceil(dist[i] * scale);
            for (int j = 0; j < barLength; j++) {
                System.out.print("*");
            }
            System.out.printf(" (%d人)\n", dist[i]);
        }
    }

    /**
     * 默认打印五档直方图
     * @param list 成绩列表
     */
    public void printDefaultHistogram(ArrayList<StudentScore> list) {
        int[] dist = getDefaultDistribution(list);
        String[] labels = {"0-59", "60-69", "70-79", "80-89", "90-100"};
        printHistogram(dist, labels);
    }

    /**
     * 将成绩列表按分数升序排序（改变原列表顺序）
     * @param list 成绩列表
     */
    public void sortByScoreAscending(ArrayList<StudentScore> list) {
        if (list == null) return;
        Collections.sort(list, (s1, s2) -> Double.compare(s1.getScore(), s2.getScore()));
    }

    /**
     * 将成绩列表按分数降序排序
     * @param list 成绩列表
     */
    public void sortByScoreDescending(ArrayList<StudentScore> list) {
        if (list == null) return;
        Collections.sort(list, (s1, s2) -> Double.compare(s2.getScore(), s1.getScore()));
    }

    /**
     * 按学号升序排序
     * @param list 成绩列表
     */
    public void sortByIdAscending(ArrayList<StudentScore> list) {
        if (list == null) return;
        Collections.sort(list, (s1, s2) -> s1.getStudentId().compareTo(s2.getStudentId()));
    }

    /**
     * 获取成绩的中位数
     * @param list 成绩列表
     * @return 中位数，若列表为空返回0.0
     */
    public double getMedian(ArrayList<StudentScore> list) {
        if (list == null || list.isEmpty()) return 0.0;
        // 先复制一份进行排序，不改变原列表
        ArrayList<Double> scores = new ArrayList<>();
        for (StudentScore ss : list) {
            scores.add(ss.getScore());
        }
        Collections.sort(scores);
        int size = scores.size();
        if (size % 2 == 0) {
            return (scores.get(size/2 - 1) + scores.get(size/2)) / 2.0;
        } else {
            return scores.get(size/2);
        }
    }

    /**
     * 获取标准差（总体标准差）
     * @param list 成绩列表
     * @return 标准差，列表为空返回0.0
     */
    public double getStandardDeviation(ArrayList<StudentScore> list) {
        if (list == null || list.isEmpty()) return 0.0;
        double avg = 0.0;
        for (StudentScore ss : list) {
            avg += ss.getScore();
        }
        avg /= list.size();
        double sumSqDiff = 0.0;
        for (StudentScore ss : list) {
            double diff = ss.getScore() - avg;
            sumSqDiff += diff * diff;
        }
        return Math.sqrt(sumSqDiff / list.size());
    }

    /**
     * 打印完整的统计报告（包含分布直方图、中位数、标准差）
     * @param list 成绩列表
     */
    public void printAdvancedStatistics(ArrayList<StudentScore> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("无数据，无法生成高级统计报告。");
            return;
        }
        System.out.println("\n========== 高级统计分析 ==========");
        System.out.printf("学生总数：%d\n", list.size());
        System.out.printf("平均分：%.2f\n", new CalculateModule().calculateAverage(list));
        System.out.printf("中位数：%.2f\n", getMedian(list));
        System.out.printf("标准差：%.4f\n", getStandardDeviation(list));
        printDefaultHistogram(list);
        System.out.println("===================================");
    }
}