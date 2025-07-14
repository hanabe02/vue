package kr.co.seoulit.erp.hr.education.dao;

import kr.co.seoulit.erp.hr.education.to.EduTO;
import kr.co.seoulit.erp.hr.education.to.PrintEduTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface EduDAO {

    public void requestEduList(EduTO eduTO);

    public void addEduList(EduTO eduTO);

    public EduTO getEduListByEduName(String eduName);

    int getNextEduCodeSequence(String dateStr);

    public ArrayList<EduTO> getEmpInfo(EduTO eduTO);

    public ArrayList<EduTO> selEduNameList();

    public void approReqEdu(EduTO eduTO);

    public ArrayList<EduTO> selEdu(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("status") String status);

    public ArrayList<EduTO> delEdu(EduTO eduTO);

    // ✅ 전체 교육 신청 내역 조회
    public ArrayList<EduTO> selectAllEduList();

    public ArrayList<EduTO> delReqEdu(EduTO eduTO);

    public ArrayList<EduTO> checkEduHistory(EduTO eduTO);

    public ArrayList<EduTO> findApproEdu(@Param("empCode") String empCode, @Param("deptName") String deptName, @Param("startDate") String startDate, @Param("endDate") String endDate);

    public void modifyApproEdu(HashMap<String, String> map);

    public void upgradeDate(String eduCode);

    public PrintEduTO printCertificate(String eduCode);

}
