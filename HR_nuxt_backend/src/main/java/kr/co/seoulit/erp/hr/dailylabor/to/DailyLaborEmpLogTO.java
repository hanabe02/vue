package kr.co.seoulit.erp.hr.dailylabor.to;

import kr.co.seoulit.common.to.BaseTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DailyLaborEmpLogTO extends BaseTO {
   private String employeeName;
   private String residentialNumber;
   private String nationality;
   private String department;
   private String project;
   private String workStartDate;
   private String workEndDate;
   private String employmentInsuranceEligibility;
   private String normalPayType;
   private double normalPay;
   private double overtimePayRate;
   private double nightPayRate;
   private String bankName;
   private String accountNumber;
   private String accountHolder;
   private String phoneNumber;
   private String email;
}