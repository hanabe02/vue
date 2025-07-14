package kr.co.seoulit.erp.hr.conexpense.to;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)

public class ConExpenseTO {
    private String reqNum;
    private String empCode;
    private String empName;
    private String reqDate;
    private String conType;
    private String trgt;
    private String conTermStart;
    private String conTermEnd;
    private String conAmt;
    private String rel;
    private String conPlace;
    private String bank;
    private String accNum;
    private String accHolder;
    private String note;
    private String approvalStatus;
    private int errorCode = 0;
	private String errorMsg = "success";

}
