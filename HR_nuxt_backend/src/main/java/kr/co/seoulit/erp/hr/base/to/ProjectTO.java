package kr.co.seoulit.erp.hr.base.to;

import kr.co.seoulit.common.to.BaseTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProjectTO extends BaseTO {
    private String projectID,
            projectName,
            startDate,
            endDate,
            projectType,
            projectAmount,
            projectCategory,
            remarks,
            jobDescription;
}