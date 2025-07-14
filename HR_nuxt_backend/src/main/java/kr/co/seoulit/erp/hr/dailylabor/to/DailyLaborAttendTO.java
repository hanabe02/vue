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
public class DailyLaborAttendTO extends BaseTO {

   private String employeeId;
   private String employeeName;
   private String project;
   private String workDate;
   private String workStartTime;
   private String workEndTime;
   private String totalWorkTime;
   private String workTime;
   private String workOverTime;
   private String workNightTime;
   private String workType;
   private String deadlineRequest;
}