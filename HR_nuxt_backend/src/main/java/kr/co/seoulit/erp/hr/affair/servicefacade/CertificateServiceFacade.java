package kr.co.seoulit.erp.hr.affair.servicefacade;

import java.util.ArrayList;

import kr.co.seoulit.erp.hr.affair.to.CertificateTO;
import kr.co.seoulit.erp.hr.affair.to.PrintCertificateTO;

public interface CertificateServiceFacade {

	public void registRequest(CertificateTO certificate);

	public ArrayList<CertificateTO> findCertificateList(String empCode, String startDate, String endDate);

	public void updateCertificateRequest(CertificateTO certificate);

	public void removeCertificateRequest(ArrayList<CertificateTO> certificateList);

	public void removeCertificateRequestOne(CertificateTO certificate);
	public ArrayList<CertificateTO> findCertificateListByDept(String deptName, String startDate, String endDate);

	public void approvalCertificateList(ArrayList<CertificateTO> certificateList) throws Exception;

	public void upgradeUseDate(String certificateNo);

	public PrintCertificateTO printCertificate(String certificateNo);
}
