package kr.co.seoulit.erp.hr.attendance.to;

import kr.co.seoulit.common.to.BaseTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)

public class AnnualVacationMgtTO extends BaseTO implements Serializable {

	private String empCode;
	private String applyYearMonth;
	private String empName,
			afternoonOff,
			monthlyLeave,
			remainingHoliday,
			finalizeStatus,
			totalUsing;

}
