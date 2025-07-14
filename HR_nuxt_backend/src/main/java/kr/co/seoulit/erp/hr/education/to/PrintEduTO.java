package kr.co.seoulit.erp.hr.education.to;

import lombok.*;

@Data
public class PrintEduTO {
    private String eduCode;
    private String eduName;
    private String empName;
    private String gender;
    private String socialSecurityNumber;
    private String phoneNumber;
    private String basicAddress;
    private String numberOfFamily;
    private String deptName;
    private String positionName;
    private String hireDate;
    private String retirementDate;
    private String levelOfEducation;

    private String empCode;
    private String deptCode;
    private String eduHistory;
    private String status;
    private String rejectCause;
    private String etc;
}
