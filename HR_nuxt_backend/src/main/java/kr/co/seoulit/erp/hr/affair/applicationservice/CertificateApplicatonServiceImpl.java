package kr.co.seoulit.erp.hr.affair.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.seoulit.erp.hr.affair.to.PrintCertificateTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.seoulit.erp.hr.affair.dao.CertificateDAO;
import kr.co.seoulit.erp.hr.affair.to.CertificateTO;


@Component
public class CertificateApplicatonServiceImpl implements CertificateApplicationService {
	@Autowired
	private CertificateDAO certificateDAO;

	@Override
	public void registRequest(CertificateTO certificate) {
		String[] parts = certificate.getRequestDate().split("-");
		String reqDateNo = parts[0].substring(2)+parts[1]+parts[2];

		int randomNo = (int) (Math.random() * 900000) + 100000;
		String formattedRandomNo = String.format("%06d", randomNo);
		String certificateNo = "CERT" + reqDateNo + formattedRandomNo;

//		int count = certificateDAO.selectCertificateCount(certificate.getRequestDate())+1;
//		String formattedCount = String.format("%04d", Math.min(count, 1000));
//
//		String certificateNo = null;
//		certificateNo = "CERT"+reqDateNo.concat(formattedCount);
//
//		String maxNo = certificateDAO.selectCertificateMaxNo(reqDateNo);
//
//		if(certificateNo.equals(maxNo)) {
//			String prefix = certificateNo.substring(0, 11);
//			int number = Integer.parseInt(certificateNo.substring(11)) + 1;
//			String formattedNumber = String.format("%03d", number);
//			certificateNo = prefix + formattedNumber;
//		}

		certificate.setCertificateNo(certificateNo);
		certificateDAO.insertCertificateRequest(certificate);
	}

	@Override
	public ArrayList<CertificateTO> findCertificateList(String empCode, String startDate, String endDate) {

		HashMap<String, String> data = new HashMap<>();
		data.put("empCode", empCode);
		data.put("startDate", startDate);
		data.put("endDate", endDate);
		ArrayList<CertificateTO> certificateList = certificateDAO.selectCertificateList(data);
		return certificateList;
	}

	@Override
	public void updateCertificateRequest(CertificateTO certificate) {
		certificateDAO.updateCertificateRequest(certificate);
	}

	@Override
	public void removeCertificateRequest(ArrayList<CertificateTO> certificateList) {

		for (CertificateTO certificate : certificateList) {
			certificateDAO.deleteCertificate(certificate);
		}

	}

	@Override
	public void removeCertificateRequestOne(CertificateTO certificate) {
		certificateDAO.deleteCertificate(certificate);
	}

	@Override
	public ArrayList<CertificateTO> findCertificateListByDept(String deptName, String startDate, String endDate) {
		ArrayList<CertificateTO> certificateList = null;
		HashMap<String, String> data = new HashMap<>();
		if (deptName.equals("모든부서")) {
			data.put("startDate", startDate);
			data.put("endDate", endDate);
			certificateList = certificateDAO.selectCertificateListByAllDept(data);
		} else {
			data.put("deptName", deptName);
			data.put("startDate", startDate);
			data.put("endDate", endDate);
			certificateList = certificateDAO.selectCertificateListByDept(data);
		}
		return certificateList;
	}

	@Override
	public void approvalCertificateList(ArrayList<CertificateTO> certificateList) throws Exception {
		System.out.println("재직증명업데이트 어플리케이션");
		HashMap<String, String> map = new HashMap<>();

		for (CertificateTO certificate : certificateList) {
			System.out.println(certificate.getApprovalStatus());
			switch (certificate.getApprovalStatus()) {
				case "approval":
					certificate.setApprovalStatus("승인");
					certificate.setRejectCause("");
					break;
				case "cancel":
					certificate.setApprovalStatus("승인취소");
					break;
				case "refer":
					certificate.setApprovalStatus("반려");
					break;
				default:
					throw new Exception("::::: ERROR MESSAGE: 승인 관련 오류입니다. 승인상태를 확인해주세요. :::::");
			}
			map.put("approvalStatus", certificate.getApprovalStatus());
			map.put("rejectCause", certificate.getRejectCause());
			map.put("certificateNo", certificate.getCertificateNo());

			certificateDAO.approvalCertificate(map);
		}
	}

	@Override
	public void upgradeUseDate(String certificateNo) {
		certificateDAO.updateUsageDate(certificateNo);
	}

	@Override
	public PrintCertificateTO printCertificate(String certificateNo) {
		return certificateDAO.printCertificateInfo(certificateNo);
	}


}
