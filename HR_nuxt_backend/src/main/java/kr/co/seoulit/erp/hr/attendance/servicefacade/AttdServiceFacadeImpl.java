package kr.co.seoulit.erp.hr.attendance.servicefacade;

import kr.co.seoulit.erp.hr.affair.to.EmpTO;
import kr.co.seoulit.erp.hr.attendance.applicationservice.AttdApplicationService;
import kr.co.seoulit.erp.hr.attendance.dao.DayAttdMgtDAO;
import kr.co.seoulit.erp.hr.attendance.to.*;
import kr.co.seoulit.erp.hr.base.to.HrDetailCodeTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class AttdServiceFacadeImpl implements AttdServiceFacade {
	@Autowired
	private AttdApplicationService attdApplicationService;
	@Autowired
	private DayAttdMgtDAO dayAttdMgtDAO;
	@Autowired
	private DataSource dataSource;

	@Override
	public ArrayList<DayAttdTO> findDayAttdList(String empCode, String applyDay) {
		return attdApplicationService.findDayAttdList(empCode, applyDay);
	}

	@Override
	public ArrayList<EmpTO> findEmpListByDept(String deptCode) {
		return attdApplicationService.findEmpListByDept(deptCode);
	}

	@Override
	public void insertDayAttd(DayAttdTO dayAttd) {
		attdApplicationService.insertDayAttd(dayAttd);

	}

	@Override
	public void deleteDayAttd(String empCode, String dayAttdCode) {
		attdApplicationService.deleteDayAttd(empCode, dayAttdCode);
	}

	@Override
	public void updateDayAttd(DayAttdTO dayAttdTO) {
		attdApplicationService.updateDayAttd(dayAttdTO);
	}

	@Override
	public ArrayList<AnnualVacationMgtTO> findAnnualVactionMgtListByYear(String year, String empCode) {
		return attdApplicationService.findAnnualVactionMgtListByYear(year, empCode);
	}

	@Override
	public ArrayList<RestAttdTO> findRestAttdListByEmpCodeAndYMD(String empCode, String yearMonth) {
		ArrayList<RestAttdTO> restAttdList = attdApplicationService.findRestAttdListByEmpCodeAndYM(empCode, yearMonth);
		System.out.println(restAttdList);
		return restAttdList;
	}

	@Override
	public void regiestRequestVacation(RestAttdTO restAttdTO) {
		attdApplicationService.regiestRequestVacation(restAttdTO);
	}

	@Override
	public void updateVacation(RestAttdTO restAttdTO) {
		attdApplicationService.updateVacation(restAttdTO);
	}

	@Override
	public ArrayList<RestAttdTO> findTravelAndEducationList(String empCode, String startDate, String endDate) {
		return attdApplicationService.findTravelAndEducationList(empCode, startDate, endDate);
	}

	@Override
	public void modifyTravelAndEducationList(RestAttdTO restAttdTO) {
		attdApplicationService.modifyTravelAndEducationList(restAttdTO);
	}

	@Override
	public void removeTravelAndEducationList(String restAttdCode, String empCode) {
		attdApplicationService.removeTravelAndEducationList(restAttdCode, empCode);
	}

	@Override
	public ArrayList<DayAttdMgtTO> findDayAttdMgtListCategory(String startDate, String endDate, String applyStatus) {
		return attdApplicationService.findDayAttdMgtListCategory(startDate, endDate, applyStatus);
	}

	@Override
	public ArrayList<DayAttdMgtTO> findDayAttdMgtListCategoryByEmpCode(String startDate, String endDate, String applyStatus, String empCode) {
		return attdApplicationService.findDayAttdMgtListCategoryByEmpCode(startDate, endDate, applyStatus, empCode);
	}


	@Override
	public void executeAnnualVacationProcess(ModelMap modelMap) {
		attdApplicationService.executeAnnualVacationProcess(modelMap);
	}

	@Override
	public void modifyAnnualVacationMgtList(ModelMap modelMap) {
		// TODO Auto-generated method stub
		attdApplicationService.modifyAnnualVacationMgtList(modelMap);
	}

	@Override
	public HashMap<String, Object> registDayAttd(DayAttdTO dayAttd) {
		return attdApplicationService.registDayAttd(dayAttd);
	}

	@Override
	public ArrayList<RestAttdTO> findRestAttdList(String empCode, String startDate, String endDate, String code) {
		return attdApplicationService.findRestAttdList(empCode, startDate, endDate, code);

	}

	@Override
	public ArrayList<RestAttdTO> findRestAttdListByDept(HashMap<String, String> attdApplMap) {
		return attdApplicationService.findRestAttdListByDept(attdApplMap);
	}

	@Override
	public ArrayList<RestAttdTO> findRestAttdListByToday(String empCode, String toDay) {
		return attdApplicationService.findRestAttdListByToday(empCode, toDay);
	}

	@Override
	public ArrayList<RestAttdTO> findRestAttdListByAnnulVacation(String empCode, String yearMonth) {
		return attdApplicationService.findRestAttdListByAnnulVacation(empCode, yearMonth);
	}

	@Override
	public void registRestAttd(RestAttdTO restAttd) {
		attdApplicationService.registRestAttd(restAttd);
	}

	@Override
	public void deleteRestAttd(String restAttdCode) {
		attdApplicationService.deleteRestAttd(restAttdCode);

	}

	@Override
	public ArrayList<DayAttdMgtTO> findDayAttdMgtList(String applyDay) {
		return attdApplicationService.findDayAttdMgtList(applyDay);
	}

	@Override
	public void modifyDayAttdMgtList(ArrayList<DayAttdMgtTO> dayAttdMgtList) {
		attdApplicationService.modifyDayAttdMgtList(dayAttdMgtList);
	}

	@Override
	public void removeDayAttdMgtList(String dayAttdMgtList) {

	}

	@Override
	public ArrayList<MonthAttdMgtTO> findMonthAttdMgtList(String applyYearMonth) {

		return attdApplicationService.findMonthAttdMgtList(applyYearMonth);
	}

	@Override
	public void modifyMonthAttdMgtList(ArrayList<MonthAttdMgtTO> monthAttdMgtList) {
		attdApplicationService.modifyMonthAttdMgtList(monthAttdMgtList);
	}

	@Override
	public ArrayList<DayAttdMgtTO> findDayAttdMgtListAll(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return attdApplicationService.findDayAttdMgtListAll(map);
	}

	@Override
	public ArrayList<DayAttdMgtTO> dayDeadlineRegister(HashMap<String, Object> map) {
		return attdApplicationService.dayDeadlineRegister(map);
	}

	@Override
	public HashMap<String, Object> findDayAttdMgtList(HashMap<String, Object> map) {
		return attdApplicationService.findDayAttdMgtList(map);
	}

	@Override
	public void dayDeadlineCancel(HashMap<String, Object> map) {
		attdApplicationService.dayDeadlineCancel(map);
	}

	@Override
	public ArrayList<HrDetailCodeTO> searchRestAttdType() {
		return attdApplicationService.searchRestAttendanceType();
	}


	@Override
	public ArrayList<RestAttdTO> modifyRestAttdList(List<RestAttdTO> restAttdTo, String deptCode, String startDate, String endDate) {
		return attdApplicationService.modifyRestAttdList(restAttdTo, deptCode, startDate, endDate);
	}

	//일집계
	@Override
	public Map<String, Object> callAggregateProcedure(String applyDay) {
		Map<String, Object> result = new HashMap<>();

		try (Connection conn = dataSource.getConnection();
			 CallableStatement stmt = conn.prepareCall("{call P_aggregate_DAY_ATTD(?, ?, ?)}")) {

			stmt.setString(1, applyDay); // IN 파라미터
			stmt.registerOutParameter(2, Types.NUMERIC); // OUT: p_error_code
			stmt.registerOutParameter(3, Types.VARCHAR); // OUT: p_error_msg

			stmt.execute();
			System.out.println("[Serviceㅋㅋㅋ] 프로시저 결과: " + stmt.getInt(2) + ", " + stmt.getString(3));
			int errorCode = stmt.getInt(2);
			String errorMsg = stmt.getString(3);

			result.put("errorCode", errorCode);
			result.put("errorMsg", errorMsg);

			if (stmt.getInt(2) == 0) {
				List<DayAttdMgtTO> list = dayAttdMgtDAO.selectDayAttdMgtListByApplyDay(applyDay);
				result.put("dayAttdMgtList", list);
			}


		} catch (Exception e) {
			result.put("errorCode", -1);
			result.put("errorMsg", "집계 프로시저 호출 실패: " + e.getMessage());
		}

		return result;
	}

	//월집계
	@Override
	public Map<String, Object> callMonthlyAggregateProcedure(String applyMonth) {
		Map<String, Object> result = new HashMap<>();

		try (Connection conn = dataSource.getConnection();
			 CallableStatement stmt = conn.prepareCall("{call HR8081VUE.P_AGGREGATE_MONTH_ATTD(?, ?, ?)}")) {

			System.out.println("ㅋㅋㅋ프로시저 : " );
			stmt.setString(1, applyMonth); // IN 파라미터
			stmt.registerOutParameter(2, Types.NUMERIC); // OUT: p_error_code
			stmt.registerOutParameter(3, Types.VARCHAR); // OUT: p_error_msg

			stmt.execute();

			int errorCode = stmt.getInt(2);
			String errorMsg = stmt.getString(3);

			result.put("errorCode", errorCode);
			result.put("errorMsg", errorMsg);

			if (errorCode == 0) {
				// 너가 등록한 DAO에서 가져오면 됨
				List<MonthAttdMgtTO> list = attdApplicationService.findMonthAttdMgtList(applyMonth);
				result.put("monthAttdMgtList", list);
			}

		} catch (Exception e) {
			result.put("errorCode", -999);
			result.put("errorMsg", "월근태 집계 실패: " + e.getMessage());
		}

		return result;
	}
}

