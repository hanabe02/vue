package kr.co.seoulit.erp.hr.attendance.dao;

import kr.co.seoulit.erp.hr.attendance.to.AnnualVacationMgtTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface AnnualVacationDAO {
	public ArrayList<AnnualVacationMgtTO> findAnnualVactionMgtListByYear(@Param("year") String year, @Param("empCode") String empCode);

	public HashMap<String, Object> batchAnnualVacationMgtProcess(HashMap<String, Object> startDate);

	public void updateAnnualVacationMgtList(AnnualVacationMgtTO annualVacationMgtList);

}
