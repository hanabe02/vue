package kr.co.seoulit.erp.hr.attendance.to;


import lombok.Data;

@Data
public class ElasticTO {
	private String	empCode;
	private String	applyDay;
	private String	startTime;
	private String	endTime;
	private String empName;
	private String cause;
	private int eno;
}
