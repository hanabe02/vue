package kr.co.seoulit.erp.hr.attendance.applicationservice;

import kr.co.seoulit.erp.hr.attendance.to.EduAttendeeTO;

import java.util.List;

public interface EduAttendeeApplicationService {

	public List<EduAttendeeTO> getAttendeeAll();

	public List<EduAttendeeTO> getAttendeeList(String classCode);

	public EduAttendeeTO getAttendee(String empNo);

	public void addAttendee(EduAttendeeTO to);

	public void removeAttendee(String empNo, String classCode);

	public void modifyAttendee(EduAttendeeTO to);

}
