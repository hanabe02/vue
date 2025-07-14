package kr.co.seoulit.erp.hr.conexpense.applicationservice;

import kr.co.seoulit.erp.account.slip.to.JournalBean;
import kr.co.seoulit.erp.hr.conexpense.dao.ConExpenseDAO;
import kr.co.seoulit.erp.hr.conexpense.to.ConExpenseTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ConExpenseApplicationServiceImpl implements ConExpenseApplicationService {

    @Autowired
    private ConExpenseDAO conExpenseDAO;

    @Override
    public ArrayList<ConExpenseTO> getAllConExpenseList() {
        return conExpenseDAO.getAllConExpenseList();
    }

    // 사원별 경조사 신청 조회
    @Override
    public ArrayList<ConExpenseTO> getConExpenseList(String startDate, String endDate, String searchEmpCode) {
        HashMap<String, Object> param = new HashMap<>();

        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("searchEmpCode", searchEmpCode);

        return conExpenseDAO.selectConExpenseList(param);
    }

    // 경조사 신청 등록 / 수정
    @Override
    public void insertConExpense(ConExpenseTO conExpenseTO) {
        ArrayList<ConExpenseTO> conExpenseList = conExpenseDAO.getConExpenseByReqNum(conExpenseTO.getReqNum());

        if (conExpenseList.size() == 0) {
            StringBuffer reqNo = new StringBuffer();
            String reqNoDate = conExpenseTO.getReqDate().replace("-", ""); // 2023-12-13 -> 20231213
            reqNo.append(reqNoDate);
            reqNo.append("REQ"); // 20231213REQ

            String lastNo = conExpenseDAO.getMaxReqNo(conExpenseTO.getReqDate());
            if (lastNo == null){
               lastNo = "00000";
            }
            int length = lastNo.length();

            String code = "0000" + (Integer.parseInt(lastNo.substring(length - 5)) + 1) + "";
            reqNo.append(code.substring(code.length() - 5));
            conExpenseTO.setReqNum(reqNo.toString());

            conExpenseDAO.insertConExpense(conExpenseTO);
        } else {
            conExpenseDAO.updateConExpense(conExpenseTO);
        }

    }

    // 경조사 신청 수정
    @Override
    public void updateConExpense(ConExpenseTO conExpenseTO) {
        conExpenseDAO.updateConExpense(conExpenseTO);
    }

    // 경조사 신청 삭제
    @Override
    public void deleteConExpense(String reqNum) {
        conExpenseDAO.deleteConExpense(reqNum);
    }
}
