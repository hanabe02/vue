package kr.co.seoulit.erp.hr.eval.to;

import lombok.Data;

@Data
public class SelEmpEvalTO {

    private String searchYear;
    private String empCode;
    private String empName;
    private String deptName;
    private String positionName;
    private String attendanceStatus;
    private String durationOfTraining;
    private String numberOfCertificate;
    private String numberOfRestDay;
    private String remainingHoliday;
    private String disqualification;
    private String career;
    private Double score;
    private String grade;
    private String status;

}
