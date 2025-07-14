package kr.co.seoulit.erp.hr.attendance.to;

import kr.co.seoulit.common.to.BaseTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RestAttdTO extends BaseTO {
	private String  empCode,
					empName,
					restAttdCode,
					restTypeCode,
					restTypeName,
					requestDate,
					startDate,
					endDate,
					cause,
					applovalStatus,
					rejectCause,
					detailCodeNumber,
					detailCodeName;
	private int cost,
				startTime,
				endTime,
				numberOfDays;

}
