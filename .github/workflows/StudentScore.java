package com.groupX.grade;

/**
 * 学生成绩实体类
 * 用于存储单个学生的成绩信息
 */
public class StudentScore {
    private String studentId;   // 学号
    private String name;        // 姓名
    private double score;       // 成绩（0-100）

    public StudentScore(String studentId, String name, double score) {
        this.studentId = studentId;
        this.name = name;
        setScore(score); // 使用setter进行校验
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        if (score < 0.0) {
            this.score = 0.0;
        } else if (score > 100.0) {
            this.score = 100.0;
        } else {
            this.score = score;
        }
    }

    @Override
    public String toString() {
        return String.format("学号：%s  姓名：%s  成绩：%.2f", studentId, name, score);
    }
}