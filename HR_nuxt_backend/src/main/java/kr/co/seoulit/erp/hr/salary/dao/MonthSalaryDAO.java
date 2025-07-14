package kr.co.seoulit.erp.hr.salary.dao;

import java.util.ArrayList;
import java.util.HashMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.seoulit.erp.hr.salary.to.MonthSalaryTO;

@Mapper
public interface MonthSalaryDAO {
	public ArrayList<MonthSalaryTO> selectYearSalary(HashMap<String, Object> map);

	public HashMap<String, Object> batchMonthSalaryProcess(HashMap<String, Object> map);

	public void findMonthSalaryProcess(HashMap<String, Object> map);
	
	public ArrayList<MonthSalaryTO> selectMonthSalayInit(@Param("applyYearMonth") String applyYearMonth, @Param("empCode") String empCode);
	public ArrayList<MonthSalaryTO> selectMonthSalary(HashMap<String,Object> param);
	public void updateMonthSalary(MonthSalaryTO monthSalary);
	public void deleteMonthSalary(@Param("empCode") String empCode, @Param("applyYearMonth") String applyYearMonth);
	
	public ArrayList<MonthSalaryTO> selectMonthSalaryList(@Param("applyYearMonth") String applyYearMonth, @Param("empCode") String empCode);


}
