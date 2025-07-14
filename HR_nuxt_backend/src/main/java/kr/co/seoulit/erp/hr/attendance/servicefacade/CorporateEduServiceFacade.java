package kr.co.seoulit.erp.hr.attendance.servicefacade;

import kr.co.seoulit.erp.hr.attendance.to.CorporateEduTO;

import java.util.List;

;


public interface CorporateEduServiceFacade {

	public CorporateEduTO getClass(String classCode);

	public List<CorporateEduTO> getClassList();

	public void modifyClass(CorporateEduTO classData);

	public void removeClass(String classCode);

	public void addClass(CorporateEduTO classData);
}
