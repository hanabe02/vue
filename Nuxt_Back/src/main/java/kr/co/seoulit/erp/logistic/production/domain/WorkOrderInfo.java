package kr.co.seoulit.erp.logistic.production.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter
@ToString
@Table(name = "WORK_ORDER_INFO")
public class WorkOrderInfo {

    @Id @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private String workOrderNo;
    private String mrpGatheringNo;
    private String itemClassification;
    private String itemCode;
    private String itemName;
    private String unitOfMrp;
    private String requiredAmount;
    private String workSiteCode;
    private String workSiteName;
    private String productionProcessCode;
    private String productionProcessName;
    private String inspectionStatus;
    private String productionStatus;
    private String completionStatus;
    // 민성 코드 수정 -- 위에 코드 주석처리함
    private String operationCompleted;
    private String actualCompletionAmount;


    @Transient
    private String mrpNo;

    @Transient
    private String mpsNo;


    public WorkOrderInfo() {
    }
}
