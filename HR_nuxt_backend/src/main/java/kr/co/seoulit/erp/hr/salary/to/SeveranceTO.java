package kr.co.seoulit.erp.hr.salary.to;

import lombok.Data;

import java.sql.Date;

@Data
public class SeveranceTO {
	/*private String
				empCode,
				empName,
				hireDate,
				retireDate,
				applyDate,
				severanceType,
				workDays,
				severancePay,
				severanceDeduction,
				realSeverancePay,
				firstDay,
				today;*/
//	private Number workDays;
	private String empCode,
			empName,
			workDays,
			severanceType,
			severancePay,
			severanceDeduction,
			realSeverancePay;
	private Date hireDate,
			retireDate,
			applyDate,
			firstDay,
			today;
}
