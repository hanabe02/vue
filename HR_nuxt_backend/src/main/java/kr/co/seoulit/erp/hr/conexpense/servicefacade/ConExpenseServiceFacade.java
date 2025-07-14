package kr.co.seoulit.erp.hr.conexpense.servicefacade;

import kr.co.seoulit.erp.hr.conexpense.to.ConExpenseTO;

import java.util.ArrayList;
import java.util.List;


public interface ConExpenseServiceFacade {

    ArrayList<ConExpenseTO> getAllConExpenseList();

    // 경조사 신청 조회
    ArrayList<ConExpenseTO> getConExpenseList(String startDate, String endDate, String searchEmpCode);

    // 경조사 신청 등록
    void insertConExpense(ConExpenseTO conExpenseTO);

    // 경조사 신청 수정
    void updateConExpense(ConExpenseTO conExpenseTO);

    // 경조사 신청 삭제
    void deleteConExpense(String reqNum);

    // 경조비 승인
    void updateApprovalStatus(List<String> reqNumList, String status);

}
