package kr.co.seoulit.erp.hr.education.applicationservice;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.co.seoulit.erp.hr.affair.applicationservice.CertificateApplicatonServiceImpl;
import kr.co.seoulit.erp.hr.education.dao.EduDAO;
import kr.co.seoulit.erp.hr.education.to.PrintEduTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.seoulit.erp.hr.education.to.EduTO;
import org.springframework.validation.ObjectError;

@Component
public class EduApplicationServiceImpl implements EduApplicationService {

    @Autowired
    private EduDAO eduDAO;

    @Override
    public void requestEduList(EduTO eduTO) {
        System.out.println("교육과정 신청중" + eduTO);

        // 1. 교육명이 이미 존재하는 경우 기존 eduCode 가져오기
        EduTO existingEdu = eduDAO.getEduListByEduName(eduTO.getEduName());
        if (existingEdu != null) {
            // 기존의 eduCode를 사용
            eduTO.setEduCode(existingEdu.getEduCode());
            System.out.println("이미 존재하는 교육명입니다. 기존 교육코드 " + existingEdu.getEduCode() + "를 사용합니다.");
        } else {
            return;
        }

        // 5. 교육정보에 관련된 직원 정보 설정
        ArrayList<EduTO> empInfoList = eduDAO.getEmpInfo(eduTO);
        if (!empInfoList.isEmpty()) {
            EduTO empInfo = empInfoList.get(0);
            eduTO.setEmpName(empInfo.getEmpName());
            eduTO.setDeptCode(empInfo.getDeptCode());
            eduTO.setDeptName(empInfo.getDeptName());
            eduTO.setStatus("신청완료");
            System.out.println("실제값 " + empInfo.getEmpName());
        }

        // 6. Insert (교육 리스트에 추가)
        eduDAO.requestEduList(eduTO);
    }


    @Override
    public void addEduList(EduTO eduTO) {
        System.out.println("추가중 값"+eduTO);

        EduTO existingEdu = eduDAO.getEduListByEduName(eduTO.getEduName());
        if (existingEdu != null) {
            System.err.println("이미 존재하는 교육명입니다. 등록을 생략합니다.");
            return; // 중복이면 아무 작업도 하지 않음
        }

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateStr = now.format(formatter);  // "20250415"

        // 2. 시퀀스 부분
        int nextSeq = eduDAO.getNextEduCodeSequence(dateStr); // 예: 1, 2, 3 ...
        String seqStr = String.format("%04d", nextSeq);        // "0001", "0002" ...

        // 3. eduCode 생성
        String eduCode = "EDUCATION" + dateStr + seqStr;
        eduTO.setEduCode(eduCode);  // 생성된 코드 세팅

        if (eduTO.getStartDate() != null) {
            eduTO.setStartDate(eduTO.getStartDate().replaceAll("-", ""));
        }
        if (eduTO.getEndDate() != null) {
            eduTO.setEndDate(eduTO.getEndDate().replaceAll("-", ""));
        }

        ArrayList<EduTO> empInfoList = eduDAO.getEmpInfo(eduTO);
        if (!empInfoList.isEmpty()) {
            EduTO empInfo = empInfoList.get(0);
            eduTO.setEmpName(empInfo.getEmpName());
            eduTO.setDeptCode(empInfo.getDeptCode());
            eduTO.setDeptName(empInfo.getDeptName());
            System.out.println("실제값"+empInfo.getEmpName());
        }
        // 4. Insert
        eduDAO.addEduList(eduTO);
    }

    @Override
    public ArrayList<EduTO> selEduNameList() {
        ArrayList<EduTO> selEduNameList = eduDAO.selEduNameList();
        return selEduNameList;
    }

    @Override
    public void approEdu(EduTO eduTO) {
        eduDAO.approReqEdu(eduTO);
    }

    @Override
    public ArrayList<EduTO> selEdu(String startDate, String endDate, String status) {
        ArrayList<EduTO> selEduList = eduDAO.selEdu(startDate, endDate, status);
        return selEduList;
    }

    @Override
    public ArrayList<EduTO> delEdu(EduTO eduTO) {
        ArrayList<EduTO> delEduList = eduDAO.delEdu(eduTO);
        return delEduList;
    }

    @Override
    public ArrayList<EduTO> findAllEduList() {
        return eduDAO.selectAllEduList();
    }

    @Override
    public ArrayList<EduTO> delReqEdu(EduTO eduTO) {
        ArrayList<EduTO> delReqEduList = eduDAO.delReqEdu(eduTO);
        return delReqEduList;
    }

    @Override
    public ArrayList<EduTO> checkEduHistory(EduTO eduTO) {
        ArrayList<EduTO> eduHistory = eduDAO.checkEduHistory(eduTO);
        return eduHistory;
    }

    @Override
    public ArrayList<EduTO> findApproEdu(String empCode, String deptName, String startDate, String endDate) {
        return eduDAO.findApproEdu(empCode, deptName, startDate, endDate);
    }

    @Override
    public void modifyApproEduList(ArrayList<EduTO> eduList) throws Exception {
        System.out.println("승인권한 관리자에 의해 변경중");
        HashMap<String, String> map = new HashMap<>();

        for (EduTO edu : eduList) {
            System.out.println(edu.getStatus());
            switch (edu.getStatus()) {
                case "approval":
                    edu.setStatus("승인완료");
                    edu.setRejectCause("");
                    break;
                case "refer":
                    edu.setStatus("반려");
                    break;
                default:
                    throw new Exception("::::: ERROR MESSAGE: 승인 관련 오류입니다. 승인상태를 확인해주세요. :::::");
            }
            map.put("status", edu.getStatus());
            map.put("rejectCause", edu.getRejectCause());
            map.put("eduCode", edu.getEduCode());
            map.put("deptCode", edu.getDeptCode());
            map.put("empCode", edu.getEmpCode());

            eduDAO.modifyApproEdu(map);
        }
    }

    @Override
    public void upgradeDate(String eduCode) {
        eduDAO.upgradeDate(eduCode);
    }

    @Override
    public PrintEduTO printCertificate(String eduCode) {
        return eduDAO.printCertificate(eduCode);
    }
}


