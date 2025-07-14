package kr.co.seoulit.erp.hr.eval.to;

import lombok.Data;

@Data
public class EmpEvalTO {
    private String empCode;
    private String empName;
    private String deptName;
    private String deptCode;
    private String positionName;
    private int durationOfTraining;
    private int numberOfCertificate;
    private int numberOfRestDay;
    private int remainingHoliday;
    private int disqualification;
    private int lastSchool;
    private int score;
    private String grade;
    private String searchYear;
    private int career;
    private String status;
}
