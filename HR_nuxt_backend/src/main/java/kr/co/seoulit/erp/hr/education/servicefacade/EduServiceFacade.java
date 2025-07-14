package kr.co.seoulit.erp.hr.education.servicefacade;

import kr.co.seoulit.erp.hr.affair.to.PrintCertificateTO;
import kr.co.seoulit.erp.hr.education.to.EduTO;
import kr.co.seoulit.erp.hr.education.to.PrintEduTO;

import java.util.ArrayList;

public interface EduServiceFacade {

    public void requestEduList(EduTO eduTO);

    public void addEduList(EduTO eduTO);

    public ArrayList<EduTO> selEduNameList();

    public void approReqEdu(EduTO eduTO);

    public ArrayList<EduTO> selEdu(String startDate, String endDate, String status);

    public ArrayList<EduTO> delEdu(EduTO eduTO);

    // ✅ 전체 교육 신청 목록 조회
    public ArrayList<EduTO> findAllEduList();

    public ArrayList<EduTO> delReqEdu(EduTO eduTO);

    public ArrayList<EduTO> checkEduHistory(EduTO eduTO);

    public ArrayList<EduTO> findApproEdu(String empCode, String deptName, String startDate, String endDate);

    public void modifyApproEduList(ArrayList<EduTO> eduTO) throws Exception;

    public void upgradeDate(String eduCode);

    public PrintEduTO printCertificate(String eduCode);
}
