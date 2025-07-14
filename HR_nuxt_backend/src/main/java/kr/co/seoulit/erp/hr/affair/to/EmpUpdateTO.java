package kr.co.seoulit.erp.hr.affair.to;

import kr.co.seoulit.common.to.BaseTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpUpdateTO extends BaseTO {
    private String socialSecurityNumber;
    private String empEngName;
    private String deptName;
    private String updateHistory;
    private String updateDate;
    private String zipCode;
    private String positionCode;
    private String userId;
    private String levelOfEducation;
    private String userOrNot;
    private String email;
    private String empName;
    private String image;
    private String hireDate;
    private String retirementDate;
    private String workplaceCode;
    private String companyCode;
    private String birthDate;
    private String gender;
    private String deptCode;
    @Id
    private String empCode;
    private String positionName;
    private String detailAddress;
    private String workplaceName;
    private String basicAddress;
    private String phoneNumber;
    private String authorityCode;
    private int seq;
}
