package kr.co.seoulit.erp.hr.affair.to;


import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.seoulit.common.to.BaseTO;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@Schema(description = "직원 정보 관련 DTO")
@EqualsAndHashCode(callSuper = false)
public class EmpTO extends BaseTO{
   @Schema(description = "사원번호")
   private String empCode;
   @Schema(description = "사원이름")
   private String empName;
   @Schema(description = "생일")
   private String birthDate;
   @Schema(description = "입사일")
   private String hireDate;
   @Schema(description = "성별")
   private String gender;
   @Schema(description = "전화번호")
   private String mobileNumber;
   @Schema(description = "주소")
   private String address;
   @Schema(description = "회사명")
   private String companyName;
   @Schema(description = "상세주소")
   private String detailAddress;
   @Schema(description = "E-mail")
   private String email;
   @Schema(description = "근무지")
   private String workPlaceName;
   @Schema(description = "근무지코드")
   private String workPlaceCode;
   @Schema(description = "직급")
   private String positionName;
   @Schema(description = "직급코드")
   private String positionCode;
   @Schema(description = "부서명")
   private String deptName;
   @Schema(description = "부서코드")
   private String deptCode;
   @Schema(description = "상세코드명")
   private String detailCodeName;
   @Schema(description = "고용상태")
   private String retirementDate;
}
