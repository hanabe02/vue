package kr.co.seoulit.erp.hr.attendance.servicefacade;

import kr.co.seoulit.erp.hr.affair.to.EmpTO;
import kr.co.seoulit.erp.hr.attendance.to.*;
import kr.co.seoulit.erp.hr.base.to.HrDetailCodeTO;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AttdServiceFacade {

	ArrayList<EmpTO> findEmpListByDept(String deptCode);


	ArrayList<DayAttdTO> findDayAttdList(String empCode, String applyDay);
	void insertDayAttd(DayAttdTO dayAttd);
	void updateDayAttd(DayAttdTO dayAttdTO);
	void deleteDayAttd(String empCode, String dayAttdCode);
	HashMap<String, Object> registDayAttd(DayAttdTO dayAttd);


	ArrayList<AnnualVacationMgtTO> findAnnualVactionMgtListByYear(String yearMonth, String empCode);
	void executeAnnualVacationProcess(ModelMap modelMap);
	void modifyAnnualVacationMgtList(ModelMap modelMap);
	void regiestRequestVacation (RestAttdTO restAttdTO);

	void updateVacation (RestAttdTO restAttdTO);
	ArrayList<RestAttdTO> findRestAttdListByEmpCodeAndYMD(String empCode, String yearMonth);


	ArrayList<RestAttdTO> findTravelAndEducationList (String empCode, String startDate, String endDate);
	void modifyTravelAndEducationList(RestAttdTO restAttdTO);
	void removeTravelAndEducationList(String restAttdCode, String empCode);


	ArrayList<DayAttdMgtTO> findDayAttdMgtListCategory(String startDate, String endDate, String applyStatus);
	ArrayList<DayAttdMgtTO> findDayAttdMgtListCategoryByEmpCode (String startDate, String endDate, String applyStatus, String empCode);
	void modifyDayAttdMgtList(ArrayList<DayAttdMgtTO> dayAttdMgtList);
	void removeDayAttdMgtList(String dayAttdMgtList);
	ArrayList<DayAttdMgtTO> findDayAttdMgtList(String applyDay);
	HashMap<String, Object> findDayAttdMgtList(HashMap<String, Object> map);
	ArrayList<DayAttdMgtTO> findDayAttdMgtListAll(HashMap<String, Object> map);




	ArrayList<RestAttdTO> findRestAttdList(String empCode,
										   String startDate,
										   String endDate,
										   String code);
	ArrayList<RestAttdTO> findRestAttdListByDept(HashMap<String, String> attdApplMap);
	ArrayList<RestAttdTO> findRestAttdListByToday(String empCode, String toDay);
	ArrayList<RestAttdTO> findRestAttdListByAnnulVacation(String empCode, String yearMonth);
	void registRestAttd(RestAttdTO restAttd);
	void deleteRestAttd(String restAttdCode);
	ArrayList<HrDetailCodeTO> searchRestAttdType();
	ArrayList<RestAttdTO> modifyRestAttdList(List<RestAttdTO> restAttdTo,
											 String deptCode,
											 String startDate,
											 String endDate);



	ArrayList<MonthAttdMgtTO> findMonthAttdMgtList(String applyYearMonth);
	void modifyMonthAttdMgtList(ArrayList<MonthAttdMgtTO> monthAttdMgtList);


	ArrayList<DayAttdMgtTO> dayDeadlineRegister(HashMap<String, Object> map);
	void dayDeadlineCancel(HashMap<String, Object> map);

	//일집계마감
	Map<String, Object> callAggregateProcedure(String applyDay);

	//월집계
	Map<String, Object> callMonthlyAggregateProcedure(String applyMonth);

}