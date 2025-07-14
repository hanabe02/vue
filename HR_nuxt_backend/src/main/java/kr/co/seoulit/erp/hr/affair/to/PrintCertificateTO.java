package kr.co.seoulit.erp.hr.affair.to;

import kr.co.seoulit.common.to.BaseTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

// 사원코드, 사원명, 주민번호, 성별, 연락처, 주소, 가족수, 부서명, 직급, 입사일, 퇴사일,학력, 경력, 계약기간, 조건, 기타 등등

@Data
@EqualsAndHashCode(callSuper = false)
public class PrintCertificateTO extends BaseTO {
    private String certificateNo; // 증명서발급번호 - certificate
    private String empCode; // 사원코드 - employeeBasic
    private String empName; // 사원명 - employeeBasic
    private String gender; // 성별 - employeeBasic
    private String socialSecurityNumber; // 주민번호 - employeeSecret
    private String phoneNumber; // 연락처 - employeeDetail
    private String basicAddress; // 주소 - employeeDetail
    private int numberOfFamily; // 가족수 = familyInfo, 구성원 count하여 계산
    private String deptName; // 부서명
    private String positionName; // 직급 - position
    private String hireDate; // 고용일- employeeBasic
    private String retirementDate; // 퇴사일 - employeeBasic
    // 경력 = 현재일 - 입사일, 퇴사일 - 입사일
    private String levelOfEducation; // 학력 - employeeDetail
    // 계약기간, 조건 등등
}
