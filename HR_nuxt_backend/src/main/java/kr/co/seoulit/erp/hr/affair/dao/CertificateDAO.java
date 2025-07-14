package kr.co.seoulit.erp.hr.affair.dao;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.seoulit.erp.hr.affair.to.PrintCertificateTO;
import org.apache.ibatis.annotations.Mapper;

import kr.co.seoulit.erp.hr.affair.to.CertificateTO;

@Mapper
public interface CertificateDAO {

	public void insertCertificateRequest(CertificateTO certificate);

	public ArrayList<CertificateTO> selectCertificateList(HashMap<String, String> data);

	public void updateCertificateRequest(CertificateTO certificate);

	public void deleteCertificate(CertificateTO certificate);

	public ArrayList<CertificateTO> selectCertificateListByAllDept(HashMap<String, String> data);

	public ArrayList<CertificateTO> selectCertificateListByDept(HashMap<String, String> data);

	public void approvalCertificate(HashMap<String, String> map);

	public String selectCertificateMaxNo(String today);

	public int selectCertificateCount(String requestDate);

	public void updateUsageDate(String certificateNo);

	public PrintCertificateTO printCertificateInfo(String certificateNo);
}