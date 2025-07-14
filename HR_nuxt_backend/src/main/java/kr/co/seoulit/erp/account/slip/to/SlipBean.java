package kr.co.seoulit.erp.account.slip.to;

import java.util.ArrayList;

import kr.co.seoulit.common.to.BaseTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
//박미노 추가 2020-11-24

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="SLIP")
public class SlipBean extends BaseTO{

    @Id
    private String slipNo;
    private String accountPeriodNo;
    private String deptCode;
    private String slipType;
    private String expenseReport;
    private String authorizationStatus;
    private String reportingEmpCode;
    private String reportingDate;
    private String approvalEmpCode;
    private String approvalDate;
    private String slipDescription;
    private String slipStatus;
    private String reportingEmpName;
    @Transient
    private String balanceDivision;
    @Transient
    private String positionCode;



    @Transient
    private String deptName;
    @Transient
    private ArrayList<JournalBean> journalList;
    @Transient
    private ArrayList<JournalDeadlineDivsionBean> journalDeadline;
}
