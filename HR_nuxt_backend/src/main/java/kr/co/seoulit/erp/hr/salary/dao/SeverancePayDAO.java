package kr.co.seoulit.erp.hr.salary.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.seoulit.erp.hr.salary.to.SeveranceTO;

@Mapper
public interface SeverancePayDAO {
//	public ArrayList<SeveranceTO> selectSeverancePayList(String empName);
	public ArrayList<SeveranceTO> selectSeverancePay(@Param("firstDay") String firstDay,@Param("today") String today);
	
//	public void insertSeverancePay(SeveranceTO severance);
	public ArrayList<SeveranceTO> insertSeverancePay(HashMap<String, Object> param);
	public void deleteSeverance(String empCode);
}
