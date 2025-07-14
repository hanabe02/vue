package kr.co.seoulit.erp.hr.attendance.applicationservice;

import kr.co.seoulit.erp.hr.affair.to.EmpTO;
import kr.co.seoulit.erp.hr.attendance.to.*;
import kr.co.seoulit.erp.hr.base.to.HrDetailCodeTO;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface AttdApplicationService {
	public ArrayList<EmpTO> findEmpListByDept(String deptCode);
	public ArrayList<DayAttdTO> findDayAttdList(String empCode, String applyDay);
	public void deleteDayAttd(String empCode, String dayAttdCode);
	public void insertDayAttd(DayAttdTO dayAttd);
	public void updateDayAttd(DayAttdTO dayAttdTO);

	public ArrayList<AnnualVacationMgtTO> findAnnualVactionMgtListByYear(String yearMonth, String empCode);
	public ArrayList<RestAttdTO> findRestAttdListByEmpCodeAndYM(String empCode, String year);
	public void regiestRequestVacation (RestAttdTO restAttdTO);

	void updateVacation(RestAttdTO restAttdTO);
	public ArrayList<RestAttdTO> findTravelAndEducationList (String empCode, String startDate, String endDate);
	public void modifyTravelAndEducationList(RestAttdTO restAttdTO);
	public void removeTravelAndEducationList(String restAttdCode, String empCode);
	public ArrayList<DayAttdMgtTO> findDayAttdMgtListCategory(String startDate, String endDate, String applyStatus);
	public ArrayList<DayAttdMgtTO> findDayAttdMgtListCategoryByEmpCode(String startDate, String endDate, String applyStatus, String empCode);


	public HashMap<String,Object> registDayAttd(DayAttdTO dayAttd);
	public ArrayList<DayAttdMgtTO> findDayAttdMgtList(String applyDay);
	public void modifyDayAttdMgtList(ArrayList<DayAttdMgtTO> dayAttdMgtList);
	public ArrayList<MonthAttdMgtTO> findMonthAttdMgtList(String applyYearMonth);
	public void modifyMonthAttdMgtList(ArrayList<MonthAttdMgtTO> monthAttdMgtList);
	public ArrayList<RestAttdTO> findRestAttdList(String empCode, String startDate, String endDate, String code);
	public ArrayList<RestAttdTO> findRestAttdListByDept(HashMap<String,String> attdApplMap);
	public ArrayList<RestAttdTO> findRestAttdListByToday(String empCode, String toDay);
	ArrayList<RestAttdTO> findRestAttdListByAnnulVacation(String empCode, String yearMonth);

	public void registRestAttd(RestAttdTO restAttd);

	public void deleteRestAttd(String restAttdCode);
	public ArrayList<DayAttdMgtTO> findDayAttdMgtListAll(HashMap<String, Object> map);
	public ArrayList<DayAttdMgtTO> dayDeadlineRegister(HashMap<String, Object> map);
	public HashMap<String, Object> findDayAttdMgtList(HashMap<String, Object> map);
	public void dayDeadlineCancel(HashMap<String, Object> map);

	public ArrayList<HrDetailCodeTO> searchRestAttendanceType();
	public ArrayList<RestAttdTO> modifyRestAttdList(List<RestAttdTO> restAttdTo, String deptCode, String startDate, String endDate);
	//
	public void executeAnnualVacationProcess(ModelMap modelMap);

	 public void modifyAnnualVacationMgtList(ModelMap modelMap);


}
