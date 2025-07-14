package kr.co.seoulit.erp.hr.attendance.dao;

import kr.co.seoulit.erp.hr.attendance.to.CorporateEduTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

;

@Mapper
public interface CorporateEduDAO {

	public List<CorporateEduTO> selectClassList();

	public CorporateEduTO selectClass(String classCode);

	public void insertClass(CorporateEduTO classData);

	public void updateClass(CorporateEduTO classData);

	public void deleteClass(String classCode);
}
