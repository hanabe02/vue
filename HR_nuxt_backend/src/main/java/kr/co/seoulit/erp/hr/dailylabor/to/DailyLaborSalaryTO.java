package kr.co.seoulit.erp.hr.dailylabor.to;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.seoulit.common.to.BaseTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DailyLaborSalaryTO extends BaseTO {
   private String employeeId;
   private String employeeName;
   private String workDate;
   private String totalPay;
   private String normalPay;
   private String overtimePay;
   private String nightPayRate;
   private String workTax;
   private String deadlineRequest;
   private String status;
}