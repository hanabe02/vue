package kr.co.seoulit.erp.hr.salary.dao;

import java.util.ArrayList;
import org.apache.ibatis.annotations.*;
import kr.co.seoulit.erp.hr.salary.to.MonthDeductionTO;

@Mapper
public interface MonthDeductionDAO {
	public ArrayList<MonthDeductionTO> selectMonthDeductionList(@Param("applyYearMonth") String applyYearMonth,
				@Param("empCode") String empCode);
	public void deleteMonthDeduction(String applyYearMonth, String empCode);
}
