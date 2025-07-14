package kr.co.seoulit.erp.hr.education.to;

import kr.co.seoulit.common.to.BaseTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class EduTO extends BaseTO {

    private String eduCode;
    private String eduName;
    private String empCode;
    private String empName;
    private String deptCode;
    private String deptName;
    private String eduHistory;
    private String searchYear;
    private String status;
    private String rejectCause;
    private String etc;

    private String description;
    private String openDate;
    private String startDate;
    private String endDate;
}
