package kr.co.seoulit.erp.hr.eval.to;

import lombok.Data;
@Data
public class EmpKpiTO{
    private String empCode;
    private String deptCode;
    private String targetYear;
    private String perfGoal;
    private String performanceIndicator;
    private String measurementCriteria;
    private String unit;
    private int weight;
    private String calcMethod;
    private int target;
    private int atcutal;
    private String achievementRate;
    private String status;
    private String empName;
    private int actual;
    private String score;

}
