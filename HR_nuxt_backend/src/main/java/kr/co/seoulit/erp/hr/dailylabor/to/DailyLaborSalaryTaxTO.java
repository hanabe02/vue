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
@Schema(description = "일용근로 급여 종합 관련 DTO")
@EqualsAndHashCode(callSuper = false)
public class DailyLaborSalaryTaxTO extends BaseTO {
   @Schema(description = "사원번호")
   private String employeeId;
   @Schema(description = "성명")
   private String employeeName;
   @Schema(description = "근무날짜")
   private String workDate;
   @Schema(description = "총 급여")
   private String totalPay;
   @Schema(description = "정상급여")
   private String normalPay;
   @Schema(description = "연장급여")
   private String overtimePay;
   @Schema(description = "야간급여")
   private String nightPayRate;
   @Schema(description = "세금")
   private String workTax;
   @Schema(description = "마감유형")
   private String deadlineRequest;
   @Schema(description = "상태")
   private String status;
   @Schema(description = "총 세금")
   private String totalTax;
   @Schema(description = "국민연금")
   private String nationTax;
   @Schema(description = "건강보험")
   private String healthTax;
   @Schema(description = "장기요양보험")
   private String longTermTax;
   @Schema(description = "고용보험")
   private String empInsureTax;
   @Schema(description = "산업재해보험")
   private String industInsureTax;
   @Schema(description = "소득세")
   private String incomeTax;
   @Schema(description = "지방소득세")
   private String localIncomeTax;
}