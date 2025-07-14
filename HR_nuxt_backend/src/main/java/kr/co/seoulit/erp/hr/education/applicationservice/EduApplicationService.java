package kr.co.seoulit.erp.hr.education.applicationservice;

import kr.co.seoulit.erp.hr.education.to.EduTO;
import kr.co.seoulit.erp.hr.education.to.PrintEduTO;

import java.util.ArrayList;
import java.util.HashMap;

public interface EduApplicationService {

    public void requestEduList(EduTO eduTO);

    public void addEduList(EduTO eduTO);

    public ArrayList<EduTO> selEduNameList();

    public void approEdu(EduTO eduTO);

    public ArrayList<EduTO> selEdu(String startDate, String endDate, String status);

    public ArrayList<EduTO> delEdu(EduTO eduTO);

    // ✅ 전체 조회
    public ArrayList<EduTO> findAllEduList();

    public ArrayList<EduTO> delReqEdu(EduTO eduTO);

    public ArrayList<EduTO> checkEduHistory(EduTO eduTO);

    public ArrayList<EduTO> findApproEdu(String empCode, String deptName, String startDate, String endDate);

    public void modifyApproEduList(ArrayList<EduTO> eduList) throws Exception;

    public void upgradeDate(String eduCode);

    public PrintEduTO printCertificate(String eduCode);

}
