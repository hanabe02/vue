package kr.co.seoulit.erp.hr.attendance.dao;

import kr.co.seoulit.erp.hr.affair.to.EmpTO;
import kr.co.seoulit.erp.hr.attendance.to.DayAttdTO;
import kr.co.seoulit.erp.hr.attendance.to.MonthAttdMgtTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Mapper
public interface DayAttdDAO {
	ArrayList<EmpTO> findEmpListByDept(@Param("deptCode") String deptCode);
	ArrayList<DayAttdTO> selectDayAttdList(@Param("empCode") String empCode, @Param("applyDay")  String applyDay);
	void insertDayAttd(DayAttdTO dayAttd);
	void updateDayAttd(DayAttdTO dayAttdTO);
	void deleteDayAttd(Map<String, Object> map);

	HashMap<String,Object> batchInsertDayAttd(HashMap<String, Object> map);




}
