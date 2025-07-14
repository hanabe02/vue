package kr.co.seoulit.erp.hr.attendance.dao;

import kr.co.seoulit.erp.hr.attendance.to.DayAttdMgtTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface DayAttdMgtDAO {

	public ArrayList<DayAttdMgtTO> selectDayAttdMgtStatus(HashMap<String, Object> map);
	public ArrayList<DayAttdMgtTO> selectDayAttdMgtStatusByEmpCode(HashMap<String, Object> map);
	public ArrayList<DayAttdMgtTO> selectDayAttdMgtAllByEmpCode(HashMap<String, Object> map);
	public ArrayList<DayAttdMgtTO> batchDayAttdMgtProcess(HashMap<String, Object> param);
	public ArrayList<DayAttdMgtTO> selectDayAttdMgtProcessAll(HashMap<String, Object> map);
	public void updateAttd(DayAttdMgtTO dayAttdMgt);



	public void updateDayAttdMgtList(HashMap<String, Object> map1);

	public ArrayList<DayAttdMgtTO> selectDayAttdMgt1(HashMap<String, Object> map);//  κ

	public HashMap<String, Object> batchDayAttdMgtProcesses(HashMap<String, Object> map); //  κ

	public void CanCelDayAttdMgtList(HashMap<String, Object> map1);

	//일집계
	List<DayAttdMgtTO> selectDayAttdMgtListByApplyDay(String applyDay);
}
