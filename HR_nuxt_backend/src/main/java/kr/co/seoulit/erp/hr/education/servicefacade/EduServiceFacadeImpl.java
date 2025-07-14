package kr.co.seoulit.erp.hr.education.servicefacade;

import kr.co.seoulit.erp.hr.affair.to.PrintCertificateTO;
import kr.co.seoulit.erp.hr.education.applicationservice.EduApplicationService;
import kr.co.seoulit.erp.hr.education.to.EduTO;
import kr.co.seoulit.erp.hr.education.to.PrintEduTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EduServiceFacadeImpl implements EduServiceFacade {

    @Autowired
    private EduApplicationService eduApplicationService;

    @Override
    public void requestEduList(EduTO eduTO) {
        eduApplicationService.requestEduList(eduTO);
    }

    @Override
    public void addEduList(EduTO eduTO) {
        eduApplicationService.addEduList(eduTO);
    }

    @Override
    public ArrayList<EduTO> selEduNameList() {
        ArrayList<EduTO> selectEduNameList = eduApplicationService.selEduNameList();
        return selectEduNameList;
    }

    @Override
    public void approReqEdu(EduTO eduTO) {
        eduApplicationService.approEdu(eduTO);
    }

    @Override
    public ArrayList<EduTO> selEdu(String startDate, String endDate, String status) {
        ArrayList<EduTO> selEduList = eduApplicationService.selEdu(startDate, endDate, status);
        return selEduList;
    }

    @Override
    public ArrayList<EduTO> delEdu(EduTO eduTO) {
        ArrayList<EduTO> delEduList = eduApplicationService.delEdu(eduTO);
        return delEduList;
    }

    @Override
    public ArrayList<EduTO> findAllEduList() {
        return eduApplicationService.findAllEduList();
    }

    @Override
    public ArrayList<EduTO> delReqEdu(EduTO eduTO) {
        ArrayList<EduTO> delReqEduList = eduApplicationService.delReqEdu(eduTO);
        return delReqEduList;
    }

    @Override
    public ArrayList<EduTO> checkEduHistory(EduTO eduTO) {
        ArrayList<EduTO> eduHistory = eduApplicationService.checkEduHistory(eduTO);
        return eduHistory;
    }

    @Override
    public ArrayList<EduTO> findApproEdu(String empCode, String deptName, String startDate, String endDate) {
        return eduApplicationService.findApproEdu(empCode, deptName, startDate, endDate);
    }

    @Override
    public void modifyApproEduList(ArrayList<EduTO> eduList) throws Exception {
        eduApplicationService.modifyApproEduList(eduList);
    }

    @Override
    public void upgradeDate(String eduCode) {
        eduApplicationService.upgradeDate(eduCode);
    }

    @Override
    public PrintEduTO printCertificate(String eduCode) {
        return eduApplicationService.printCertificate(eduCode);
    }
}
