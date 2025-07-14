package kr.co.seoulit.erp.hr.affair.servicefacade;

import java.util.ArrayList;

import kr.co.seoulit.erp.hr.affair.to.PrintCertificateTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.seoulit.erp.hr.affair.applicationservice.CertificateApplicationService;
import kr.co.seoulit.erp.hr.affair.to.CertificateTO;
import kr.co.seoulit.erp.hr.affair.servicefacade.CertificateServiceFacade;

@Service
public class CertificateServiceFacadeImpl implements CertificateServiceFacade {
	
	@Autowired
	private CertificateApplicationService certificateApplicationService;

	public void registRequest(CertificateTO certificate) {
		certificateApplicationService.registRequest(certificate);
	}

	@Override
	public ArrayList<CertificateTO> findCertificateList(String empCode, String startDate, String endDate) {
		ArrayList<CertificateTO> certificateList = certificateApplicationService.findCertificateList(empCode, startDate,
				endDate);
		return certificateList;
	}

	@Override
	public void updateCertificateRequest(CertificateTO certificate) {
		certificateApplicationService.updateCertificateRequest(certificate);
	}

	@Override
	public ArrayList<CertificateTO> findCertificateListByDept(String deptName, String startDate, String endDate) {
		ArrayList<CertificateTO> certificateList = certificateApplicationService.findCertificateListByDept(deptName,
				startDate, endDate);
		return certificateList;
	}

	@Override
	public void removeCertificateRequest(ArrayList<CertificateTO> certificateList) {
		certificateApplicationService.removeCertificateRequest(certificateList);
	}

	@Override
	public void removeCertificateRequestOne(CertificateTO certificate) {
		certificateApplicationService.removeCertificateRequestOne(certificate);
	}

	@Override
	public void approvalCertificateList(ArrayList<CertificateTO> certificateList) throws Exception {
		certificateApplicationService.approvalCertificateList(certificateList);
	}

	@Override
	public void upgradeUseDate(String certificateNo) {
		certificateApplicationService.upgradeUseDate(certificateNo);
	}

	@Override
	public PrintCertificateTO printCertificate(String certificateNo) {
		return certificateApplicationService.printCertificate(certificateNo);
	}
}
