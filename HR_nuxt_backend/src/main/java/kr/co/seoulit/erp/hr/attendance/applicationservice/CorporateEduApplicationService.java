package kr.co.seoulit.erp.hr.attendance.applicationservice;

import kr.co.seoulit.erp.hr.attendance.to.CorporateEduTO;

import java.util.List;

public interface CorporateEduApplicationService {
	public List<CorporateEduTO> getClassList();

	public CorporateEduTO getClass(String classCode);

	public void addClass(CorporateEduTO classData);

	public void removeClass(String classCode);

	public void modifyClass(CorporateEduTO classData);
}
