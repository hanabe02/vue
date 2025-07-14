package kr.co.seoulit.erp.hr.attendance.dao;

import kr.co.seoulit.erp.hr.attendance.to.EduAttendeeTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EduAttendeeDAO {
	public List<EduAttendeeTO> selectAttendeeAll();

	public List<EduAttendeeTO> selectAttendeeList(String classCode);

	public EduAttendeeTO selectAttendee(String empNo);

	public void insertAttendee(EduAttendeeTO to);

	public void updateAttendee(EduAttendeeTO to);

	public void deleteAttendee(@Param("empNo") String empNo, @Param("classCode") String classCode);

}
