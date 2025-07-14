package kr.co.seoulit.erp.hr.attendance.applicationservice;

import kr.co.seoulit.erp.hr.affair.to.EmpTO;
import kr.co.seoulit.erp.hr.attendance.dao.*;
import kr.co.seoulit.erp.hr.attendance.to.*;
import kr.co.seoulit.erp.hr.base.to.HrDetailCodeTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AttdApplicationServiceImpl implements AttdApplicationService {

	@Autowired
	private DayAttdDAO dayAttdDAO;
	@Autowired
	private RestAttdDAO restAttdDAO;
	@Autowired
	private DayAttdMgtDAO dayAttdMgtDAO;
	@Autowired
	private MonthAttdMgtDAO monthAttdMgtDAO;
	@Autowired
	private AnnualVacationDAO annualVacationDAO;


	@Override
	public ArrayList<DayAttdTO> findDayAttdList(String empCode, String applyDay) {
		// TODO Auto-generated method stub
		System.out.println(applyDay);
		System.out.println(empCode);
		ArrayList<DayAttdTO> dayAttdList = dayAttdDAO.selectDayAttdList(empCode, applyDay);

		for (DayAttdTO dayAttdTO : dayAttdList) {
			String time = dayAttdTO.getTime();
			if (time.length() == 3) {
				StringBuffer t1 = new StringBuffer(time);
				t1.insert(1, ":");
				String t2 = t1.toString();
				dayAttdTO.setTime(t2);
			} else if (time.length() == 4) {
				StringBuffer tt1 = new StringBuffer(time);
				tt1.insert(2, ":");
				String tt2 = tt1.toString();
				dayAttdTO.setTime(tt2);
			}
		}
		System.out.println("여기는 확인이 되나요"+dayAttdList);
		return dayAttdList;
	}
	@Override
	public ArrayList<EmpTO> findEmpListByDept(String deptCode){
		return dayAttdDAO.findEmpListByDept(deptCode);
	}
	@Override
	public ArrayList<AnnualVacationMgtTO> findAnnualVactionMgtListByYear(String year, String empCode) {
		ArrayList<AnnualVacationMgtTO> annualVacationMgtList = annualVacationDAO.findAnnualVactionMgtListByYear(year, empCode);
		return annualVacationMgtList;
	}


	@Override
	public ArrayList<RestAttdTO> findRestAttdListByEmpCodeAndYM(String empCode, String yearMonth){
		return restAttdDAO.selectRestAttdListByEmpCodeAndYM(empCode, yearMonth);
	}

	@Override
	public void regiestRequestVacation (RestAttdTO restAttdTO){
		restAttdDAO.insertRequestVacation(restAttdTO);
	}

	@Override
	public void updateVacation(RestAttdTO restAttdTO) {
		restAttdDAO.updateVacation(restAttdTO);
	}

	@Override
	public ArrayList<RestAttdTO> findTravelAndEducationList (String empCode, String startDate, String endDate){
		ArrayList<RestAttdTO> travelAndEducationList = restAttdDAO.selectTravelAndEducationList(empCode, startDate, endDate);
				return travelAndEducationList;
	}

	@Override
	public void modifyTravelAndEducationList(RestAttdTO restAttdTO){
		restAttdDAO.updateTravelAndEducationList(restAttdTO);
	}

	@Override
	public void removeTravelAndEducationList(String restAttdCode, String empCode){
		Map<String, Object> map = new HashMap<>();
		map.put("restAttdCode", restAttdCode);
		map.put("empCode", empCode);
		restAttdDAO.deleteTravelAndEducationList(map);
	}

	@Override
	public void deleteDayAttd(String empCode, String dayAttdCode) {
		Map<String, Object> map = new HashMap<>();
		map.put("dayAttdCode", dayAttdCode);
		map.put("empCode", empCode);
		dayAttdDAO.deleteDayAttd(map);
	}

	@Override
	public void insertDayAttd(DayAttdTO dayAttd) { // test
		dayAttdDAO.insertDayAttd(dayAttd);
	}

	@Override
	public void updateDayAttd(DayAttdTO dayAttdTO){
		dayAttdDAO.updateDayAttd(dayAttdTO);
	}

	@Override
	public ArrayList<DayAttdMgtTO> findDayAttdMgtListCategory(String startDate, String endDate, String applyStatus){
		ArrayList<DayAttdMgtTO> dayAttdMgtList = new ArrayList<>();
		HashMap<String, Object> map = new HashMap<>();
		if("Y".equals(applyStatus)){
			map.clear();
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("applyStatus", applyStatus);
			dayAttdMgtList=dayAttdMgtDAO.selectDayAttdMgtStatus(map);
		}else if("N".equals(applyStatus)){
			map.clear();
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("applyStatus", applyStatus);
			dayAttdMgtList=dayAttdMgtDAO.selectDayAttdMgtStatus(map);
		}else{
			map.clear();
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			dayAttdMgtList=dayAttdMgtDAO.selectDayAttdMgtProcessAll(map);
		}
		return dayAttdMgtList;
	}

	@Override
	public ArrayList<DayAttdMgtTO> findDayAttdMgtListCategoryByEmpCode(String startDate, String endDate, String applyStatus, String empCode) {
		ArrayList<DayAttdMgtTO> dayAttdMgtList = new ArrayList<>();
		HashMap<String, Object> map = new HashMap<>();

		if("Y".equals(applyStatus)) {
			map.clear();
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("applyStatus", applyStatus);
			map.put("empCode", empCode);
			dayAttdMgtList = dayAttdMgtDAO.selectDayAttdMgtStatusByEmpCode(map);
		}else if("N".equals(applyStatus)){
			map.clear();
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("applyStatus", applyStatus);
			map.put("empCode", empCode);
			dayAttdMgtList = dayAttdMgtDAO.selectDayAttdMgtStatusByEmpCode(map);
		}else{
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("empCode", empCode);
			dayAttdMgtList = dayAttdMgtDAO.selectDayAttdMgtAllByEmpCode(map);
		}
		return dayAttdMgtList;

	}


	@Override
		public void executeAnnualVacationProcess(ModelMap modelMap) {
			annualVacationDAO.batchAnnualVacationMgtProcess(modelMap);
		}
	@Override
	public void modifyAnnualVacationMgtList(ModelMap modelMap) {

		ArrayList<AnnualVacationMgtTO> annualVacationMgtList = (ArrayList<AnnualVacationMgtTO>) modelMap.get("annualVacationMgtList");

		for (AnnualVacationMgtTO annualVacationMgt : annualVacationMgtList) {
			System.out.println(annualVacationMgt);
			if (annualVacationMgt.getFinalizeStatus().equals("N")) {
				annualVacationMgt.setFinalizeStatus("Y");
				annualVacationDAO.updateAnnualVacationMgtList(annualVacationMgt);
			} else if (annualVacationMgt.getFinalizeStatus().equals("Y")) {
				annualVacationMgt.setFinalizeStatus("N");
				annualVacationDAO.updateAnnualVacationMgtList(annualVacationMgt);
			}
		}
	}


	@Override
	public HashMap<String, Object> registDayAttd(DayAttdTO dayAttd) {

		HashMap<String, Object> map = new HashMap<>();
		map.put("empCode", dayAttd.getEmpCode());
		map.put("attdTypeCode", dayAttd.getAttdTypeCode());
		map.put("attdTypeName", dayAttd.getAttdTypeName());
		map.put("applyDay", dayAttd.getApplyDay());
		map.put("time", dayAttd.getTime());

		return dayAttdDAO.batchInsertDayAttd(map);
	}
	@Override
	public ArrayList<RestAttdTO> findRestAttdListByToday(String empCode, String toDay) {
		return restAttdDAO.selectRestAttdListByToday(empCode, toDay);
	}
	@Override
	public ArrayList<RestAttdTO> findRestAttdListByAnnulVacation(String empCode, String yearMonth) {
		return restAttdDAO.selectRestAttdListByAnnulVacation(empCode, yearMonth);
	}

	@Override
	public void modifyDayAttdMgtList(ArrayList<DayAttdMgtTO> dayAttdMgtList) {

		// TODO Auto-generated method stub
		for (DayAttdMgtTO dayAttdMgt : dayAttdMgtList) {
			dayAttdMgt.setApplyDays(dayAttdMgt.getApplyDaysFormat());
			if (dayAttdMgt.getStatus().equals("update")) {
				dayAttdMgt.setFinalizeStatus("Y");
				dayAttdMgtDAO.updateAttd(dayAttdMgt);
			} else if (dayAttdMgt.getStatus().equals("cancel")) {
				dayAttdMgt.setFinalizeStatus("N");
				dayAttdMgtDAO.updateAttd(dayAttdMgt);
			}
		}
	}

	@Override
	public ArrayList<MonthAttdMgtTO> findMonthAttdMgtList(String applyYearMonth) {
		// TODO Auto-generated method stub

		HashMap<String, Object> map = new HashMap<>();
		map.put("applyYearMonth", applyYearMonth);
		monthAttdMgtDAO.batchMonthAttdMgtProcess(map);
		@SuppressWarnings("unchecked")
		ArrayList<MonthAttdMgtTO> monthAttdMgtList = (ArrayList<MonthAttdMgtTO>) map.get("result");
		System.out.println(monthAttdMgtList);

		return monthAttdMgtList;
	}

	@Override
	public ArrayList<RestAttdTO> findRestAttdListByDept(HashMap<String, String> attdApplMap) {
		// TODO Auto-generated method stub
		ArrayList<RestAttdTO> restAttdList = null;

		restAttdList = restAttdDAO.selectRestAttdListByDept(attdApplMap);

		return restAttdList;
	}

	@Override
	public void registRestAttd(RestAttdTO restAttd) {
		restAttdDAO.insertRestAttd(restAttd);
	}

	@Override
	public void deleteRestAttd(String restAttdCode) {
		restAttdDAO.deleteRestAttd(restAttdCode);
	}

	@Override
	public ArrayList<RestAttdTO> findRestAttdList(String empCode, String startDate, String endDate, String code) {
		// TODO Auto-generated method stub

		ArrayList<RestAttdTO> restAttdList = null;
		HashMap<String, Object> map = new HashMap<>();
		map.put("empCode", empCode);
		map.put("startDate", startDate);
		map.put("endDate", endDate);

		System.out.println("empCode+" + "/" + "startDate+" + "/" + "endDate");
		System.out.println(empCode + "/" + startDate + "/" + endDate);

		if (code == "")
			restAttdList = restAttdDAO.selectRestAttdList(map);
		else {
			map.put("code", code);
			restAttdList = restAttdDAO.selectRestAttdListCode(map);
		}
		return restAttdList;
	}



//	********************* 占쎈눇�뙼�맪占쏙퐦�삕占쎄땍占쎈쐻占쎈윥獄��엺�쐻占쎈윪�앓우삕�땻�떣�쐻占쎈쑟�얜뀘�쐻�뜝占� �뜝�럥苑욃뜝�럡�꼤嶺뚯빢�삕 _2020.09.04 _占쎈쐻占쎈윪占쎄땍占쎈쐻占쎈윪野껓옙 *********************

	@Override
	public ArrayList<DayAttdMgtTO> findDayAttdMgtList(@Param("applyDay") String applyDay) {
		// TODO Auto-generated method stub

		HashMap<String, Object> param = new HashMap<>();
		param.put("applyDay", applyDay);
		dayAttdMgtDAO.batchDayAttdMgtProcess(param);

		@SuppressWarnings("unchecked")
		ArrayList<DayAttdMgtTO> dayAttdMgtList = (ArrayList<DayAttdMgtTO>) param.get("result");
		return dayAttdMgtList;

	}

	@Override
	public void modifyMonthAttdMgtList(ArrayList<MonthAttdMgtTO> monthAttdMgtList) {
		// TODO Auto-generated method stub

		for (MonthAttdMgtTO monthAttdMgt : monthAttdMgtList) {

			System.out.println("占쎈쐻占쎈윪占쎄샵占쎈쐻占쎈윥占쎈빢占쎈쐻占쎈윥筌β뮪�쐻占쎈윪占쎄샵占쎈쐻占쎈윥占쎈빢占쎈쐻占쎈윥筌ο옙" + monthAttdMgt);
			if (monthAttdMgt.getStatus().equals("update")) {
				monthAttdMgt.setFinalizeStatus("Y");
				monthAttdMgtDAO.updateMonthAttdMgtList(monthAttdMgt);
			} else if (monthAttdMgt.getStatus().equals("cancel")) {
				monthAttdMgt.setFinalizeStatus("N");
				monthAttdMgtDAO.cancelMonthAttdMgtList(monthAttdMgt);
			}

		}

	}



	@Override
	public ArrayList<DayAttdMgtTO> findDayAttdMgtListAll(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return dayAttdMgtDAO.selectDayAttdMgtProcessAll(map);
	}

	@Override
	public ArrayList<DayAttdMgtTO> dayDeadlineRegister(HashMap<String, Object> map) {
		// TODO Auto-generated method stub

		HashMap<String, Object> map1 = new HashMap<String, Object>();

		Map<String, ArrayList<DayAttdMgtTO>> list = (Map<String, ArrayList<DayAttdMgtTO>>) map.get("DayAttdMgtToList");

		ArrayList<DayAttdMgtTO> DayAttdMgtList = list.get("DayAttdMgtToList");

		for (DayAttdMgtTO DayAttdMgtTo : DayAttdMgtList) {

			map1.put("empCode", DayAttdMgtTo.getEmpCode());

			map1.put("applyDays", DayAttdMgtTo.getApplyDays());

			map1.put("finalizeStatus", "Y");

			dayAttdMgtDAO.updateDayAttdMgtList(map1);
		}

		return null;
	}

	@Override
	public HashMap<String, Object> findDayAttdMgtList(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		dayAttdMgtDAO.batchDayAttdMgtProcesses(map);
		return map;
	}

	@Override
	public void dayDeadlineCancel(HashMap<String, Object> map) {
		// TODO Auto-generated method stub

		HashMap<String, Object> map1 = new HashMap<String, Object>();

		Map<String, ArrayList<DayAttdMgtTO>> list = (Map<String, ArrayList<DayAttdMgtTO>>) map.get("DayAttdMgtToList");

		ArrayList<DayAttdMgtTO> DayAttdMgtList = list.get("DayAttdMgtToList");

		for (DayAttdMgtTO DayAttdMgtTo : DayAttdMgtList) {

			map1.put("empCode", DayAttdMgtTo.getEmpCode());

			map1.put("applyDays", DayAttdMgtTo.getApplyDays());

			map1.put("finalizeStatus", "N");

			dayAttdMgtDAO.CanCelDayAttdMgtList(map1);
		}
	}

	@Override
	public ArrayList<HrDetailCodeTO> searchRestAttendanceType() {
		// TODO Auto-generated method stub
		return restAttdDAO.selectRestDatailCodeName();
	}

	// 占쎈쐻占쎈윪占쎄땍占쎈쐻占쎈윪野껓옙 占쎈쐻占쎈윪�앗껋쐺獄�占쏙옙�떁濚밸㉡�삕 占쎈섀饔낅챸占썩뼺鍮놂옙留졾뜝�럡�쀯옙�띿삕占쎈쨧
	// 占쎈쐻占쎈윞占쎈��占쎈쐻占쎈윪占쎌졆

//	@SuppressWarnings("unchecked")
//	//	********************* 占쎈눇�뙼�맪占쏙퐦�삕占쎄땍占쎈쐻占쎈윥獄��엺�쐻占쎈윪�앓우삕�땻�떣�쐻占쎈쑟�얜뀘�쐻�뜝占� 占쎈쐻占쎈윥筌묒궍�쐻占쎈윪占쎄탾 _2020.09.04 _占쎈쐻占쎈윪占쎄땍占쎈쐻占쎈윪野껓옙 *********************
//	@Override
//	public void modifyRestAttdList(HashMap<String,ArrayList<RestAttdTO>> attdApplMap) {
//		// TODO Auto-generated method stub
//
//		HashMap<String, String> data = new HashMap<>();
//
//		ArrayList<RestAttdTO> list = attdApplMap.get("checkData");
//
//		System.out.println("?????????????????"+list);
//
//		for(RestAttdTO attd : list) {
//
//			data.put("applovalStatus", attd.getApplovalStatus());
//			data.put("rejectCause", attd.getRejectCause());
//			data.put("empCode", attd.getEmpCode());
//			data.put("couse", attd.getCause());
//			data.put("restAttdCode", attd.getRestAttdCode());
//
//			System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZ"+data);
//			restAttdDAO.updateRestAttd(data);
//		}
//	}

	// =============================占쎈눇�뙼�맪占쏙퐦�삕占쎌졆占쎈쐻占쎈윥獄��엺�쐻占쎈윪�앓우삕�땻�떣�쐻占쎈쑟�얜뀘�쐻�뜝占�
	// 占쎈쐻占쎈윪占쎄섈占쎈쐻占쎈윪占쎌젳 2020-09-20 占쎈쐻占쎈윪占쎄땍占쎈쐻占쎈윪野껓옙
	// =====================================
	@Override
	public ArrayList<RestAttdTO> modifyRestAttdList(List<RestAttdTO> restAttdTo, String deptCode, String startDate,
			String endDate) {
		// TODO Auto-generated method stub
		HashMap<String, String> attdApplMap = new HashMap<>();
		HashMap<String, String> data = new HashMap<>();
		ArrayList<RestAttdTO> restAttdList = null;

		for (RestAttdTO attd : restAttdTo) {
			if (attd.getApplovalStatus().equals("apploval")) {
				attd.setApplovalStatus("占쎈쐻占쎈윥獄��엺�쐻占쎈윪�억옙");
			} else if (attd.getApplovalStatus().equals("cancel")) {
				attd.setApplovalStatus("占쎈쐻占쎈윥獄��엺�쐻占쎈윪�앓우삕�굢�냵�삕泳�怨살삕�댆占�");
			} else {
				attd.setApplovalStatus("占쎈쎗占쎈즵�몭�씛�삕占쎌죳");
			}
			data.put("applovalStatus", attd.getApplovalStatus());
			data.put("rejectCause", attd.getRejectCause());
			data.put("empCode", attd.getEmpCode());
			data.put("couse", attd.getCause());
			data.put("restAttdCode", attd.getRestAttdCode());

			System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZ" + data);

			restAttdDAO.updateRestAttd(data);
		}

		attdApplMap.put("deptCode", deptCode);
		attdApplMap.put("startDate", startDate);
		attdApplMap.put("endDate", endDate);

		restAttdList = findRestAttdListByDept(attdApplMap);

		return restAttdList;

	}




}
