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
@Schema(description = "일용근로 급여 관련 DTO")
@EqualsAndHashCode(callSuper = false)
public class DailyLaborTaxTO extends BaseTO {
   @Schema(description = "속성연도")
   private String attributionYear;
   @Schema(description = "건강보험률")
   private String healthInsureRates;
   @Schema(description = "장기요양보험률")
   private String longTermCareRate;
   @Schema(description = "국민연금률")
   private String nationPensionRates;
   @Schema(description = "고용보험률")
   private String empInsureRates;
   @Schema(description = "근로자보험률")
   private String wrkInsureRates;
   @Schema(description = "소득세")
   private String incomeTaxRates;
   @Schema(description = "지방소득세")
   private String localIncomeTaxRates;
}