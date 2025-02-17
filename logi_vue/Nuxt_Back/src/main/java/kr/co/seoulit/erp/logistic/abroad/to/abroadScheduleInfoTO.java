package kr.co.seoulit.erp.logistic.abroad.to;

import kr.co.seoulit.common.to.AbroadTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class abroadScheduleInfoTO extends AbroadTO{

    private String abroadScheduleCode;
    private String eventName;
    private String eventType;
    private String eventLocation;
    private String eventDate;
    private String eventExplanation;
    private String eventTarget;
    private String eventHost;
    private String eventState;

}
