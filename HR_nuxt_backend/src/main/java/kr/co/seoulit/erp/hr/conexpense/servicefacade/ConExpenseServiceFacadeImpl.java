package kr.co.seoulit.erp.hr.conexpense.servicefacade;

import kr.co.seoulit.erp.hr.conexpense.applicationservice.ConExpenseApplicationService;
import kr.co.seoulit.erp.hr.conexpense.to.ConExpenseTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.seoulit.erp.hr.conexpense.dao.ConExpenseDAO;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ConExpenseServiceFacadeImpl implements ConExpenseServiceFacade {

    @Autowired
    private ConExpenseApplicationService conExpenseApplicationService;

    @Autowired
    private ConExpenseDAO conExpenseDAO;


    @Override
    public ArrayList<ConExpenseTO> getAllConExpenseList() {
        return conExpenseApplicationService.getAllConExpenseList();
    }

    // 경조사 신청 조회
    @Override
    public ArrayList<ConExpenseTO> getConExpenseList(String startDate, String endDate, String searchEmpCode) {
        return conExpenseApplicationService.getConExpenseList(startDate, endDate, searchEmpCode);
    }

    // 경조사 신청 등록
    @Override
    public void insertConExpense(ConExpenseTO conExpenseTO) {
        conExpenseApplicationService.insertConExpense(conExpenseTO);
    }

    @Override
    public void updateConExpense(ConExpenseTO conExpenseTO) {
        conExpenseApplicationService.updateConExpense(conExpenseTO);
    }

    @Override
    public void deleteConExpense(String reqNum) {
        conExpenseApplicationService.deleteConExpense(reqNum);
    }

    @Override
    public void updateApprovalStatus(List<String> reqNumList, String status) {
        for (String reqNum : reqNumList) {
            conExpenseDAO.updateApprovalStatus(reqNum, status);
        }
    }

}
