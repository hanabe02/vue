package kr.co.seoulit.erp.hr.attendance.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.seoulit.erp.hr.attendance.servicefacade.AttdServiceFacade;
import kr.co.seoulit.erp.hr.attendance.to.DayAttdMgtTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Tag(name = "일근태관리", description = "일근태관리 API")
@CrossOrigin("*")
@RestController
@RequestMapping("/hr/attendance")
public class DayAttdManageController {

	@Autowired
	private AttdServiceFacade attdServiceFacade;
	private ModelMap modelMap = new ModelMap();

	@GetMapping("/findDayAttdMgtListCategory")
	public Map<String, Object> findDayAttdMgtListCategory(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
														  @RequestParam("applyStatus") String applyStatus){
		HashMap<String, Object> map = new HashMap<>();
		try{
			ArrayList<DayAttdMgtTO> dayAttdMgtList = attdServiceFacade.findDayAttdMgtListCategory(startDate, endDate, applyStatus);
			map.clear();
			map.put("dayAttdMgtList", dayAttdMgtList);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		}catch (Exception ioe){
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", ioe.getMessage());
		}
		System.out.println("전체조회결과"+map);
		return map;
	}

	@GetMapping("findDayAttdMgtListCategoryByEmpCode")
	public Map<String, Object> findDayAttdMgtListCategoryByEmpCode(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
																   @RequestParam("applyStatus") String applyStatus, @RequestParam("empCode") String empCode) {
		HashMap<String, Object> map = new HashMap<>();
		try{
			ArrayList<DayAttdMgtTO> dayAttdMgtList = attdServiceFacade.findDayAttdMgtListCategoryByEmpCode(startDate, endDate, applyStatus, empCode);
			map.clear();
			map.put("dayAttdMgtList", dayAttdMgtList);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		}catch (Exception ioe){
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", ioe.getMessage());
		}
		System.out.println("선택조회결과"+map);
		return map;
	}

	@PutMapping("/dayAttendanceManageUpdate")
	public HashMap<String, Object> modifyDayAttdList(@RequestBody ArrayList<DayAttdMgtTO> dayAttdMgtTO) {
		HashMap<String, Object> map = new HashMap<>();
		try {
			System.out.println(dayAttdMgtTO);
			attdServiceFacade.modifyDayAttdMgtList(dayAttdMgtTO);
			map.clear();
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		}catch(Exception ioe) {
			ioe.printStackTrace();
			map.clear();
			map.put("errorMsg", ioe.getMessage());
		}
		return map;
	}






//   @DeleteMapping("/dayAttendanceManageRemove")
//   public HashMap<String, Object> removeDayAttdList(@RequestParam("dayAttdMgtList") String dayAttdMgtList) {
//      HashMap<String, Object> map = new HashMap<>();
//      try {
//         System.out.println(dayAttdMgtList);
//         attdServiceFacade.
//         map.clear();
//         map.put("errorMsg", "success");
//         map.put("errorCode", 0);
//      }catch(Exception ioe) {
//         ioe.printStackTrace();
//         map.clear();
//         map.put("errorMsg", ioe.getMessage());
//      }
//      return map;
//   }

	// ===== 아직 사용 하지 않음 =====

	@RequestMapping(value = "/dayDeadlineRegister", method = RequestMethod.PUT)
	public ModelMap modifyDayAttdList(@RequestBody Map<String, ArrayList<DayAttdMgtTO>> DayAttdMgtToList) {

		HashMap<String, Object> map = new HashMap<>();
		map.put("DayAttdMgtToList", DayAttdMgtToList);
		try {
			ArrayList<DayAttdMgtTO> dayAttdMgtList = attdServiceFacade.dayDeadlineRegister(map);
			modelMap.put("errorMsg", "success");
			modelMap.put("errorCode", 0);
			modelMap.put("dayAttdMgtList", dayAttdMgtList);
		} catch (Exception ioe) {
			ioe.printStackTrace();
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
			modelMap.put("errorCode", -1);
		}
		return modelMap;
	}


	@RequestMapping(value = "/dayDeadlineCancel", method = RequestMethod.PUT)
	public ModelMap dayDeadlineCancel(@RequestBody Map<String, ArrayList<DayAttdMgtTO>> DayAttdMgtToList) {

		System.out.println("뭐지" + DayAttdMgtToList);
		HashMap<String, Object> map = new HashMap<>();

		map.put("DayAttdMgtToList", DayAttdMgtToList);

		try {
			attdServiceFacade.dayDeadlineCancel(map);

			modelMap.put("errorMsg", "success");
			modelMap.put("errorCode", 0);
			// modelMap.put("dayAttdMgtList",dayAttdMgtList);
		} catch (Exception ioe) {
			ioe.printStackTrace();
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
			modelMap.put("errorCode", -1);
		}
		return modelMap;
	}

	@RequestMapping(value = "/dayAttendanceManage", method = RequestMethod.GET)
	public ModelMap findDayAttdMgtList(@RequestParam("applyDay") String applyDay) {
		HashMap<String, Object> map = new HashMap<>();

		try {
			map.put("applyDay", applyDay);
			HashMap<String, Object> result = attdServiceFacade.findDayAttdMgtList(map);

			@SuppressWarnings("unchecked")
			ArrayList<DayAttdMgtTO> dayAttdMgtList = (ArrayList<DayAttdMgtTO>) result.get("result");
			modelMap.put("errorMsg", "success");
			modelMap.put("errorCode", 0);
			modelMap.put("dayAttdMgtList", dayAttdMgtList);
		} catch (Exception ioe) {

			ioe.printStackTrace();
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());

		}
		return modelMap;
	}
	//일집계

	@PostMapping("/batchDayAttd")
	public Map<String, Object> batchDayAttd(@RequestBody Map<String, String> param) {
		Map<String, Object> result = new HashMap<>();

		try {
			String applyDay = param.get("applyDay");
			System.out.println("applyDay: " + applyDay);

			if (applyDay == null || applyDay.trim().isEmpty()) {
				result.put("errorCode", -1);
				result.put("errorMsg", " 집계할 날짜를 선택하세요.");
				return result;
			}

			return attdServiceFacade.callAggregateProcedure(applyDay);
		} catch (Exception e) {
			// 예외 내용 로그 출력
			e.printStackTrace();

			// 프론트로 에러 메시지 전달
			result.put("errorCode", -999);
			result.put("errorMsg", "집계 처리 중 서버 오류 발생: " + e.getMessage());
			return result;
		}
	}

	// 기간에 따른 전체 dayAttdMgtList 조회
//   @RequestMapping(value = "/dayAttendanceManageAll", method = RequestMethod.GET)
//   public ModelMap findDayAttdMgtListAll(@Param("startDate") String startDate, @Param("endDate") String endDate) {
//      HashMap<String, Object> map = new HashMap<>();
//
//      map.put("startDate", startDate);
//      map.put("endDate", endDate);
//      try {
//         ArrayList<DayAttdMgtTO> dayAttdMgtList = attdServiceFacade.findDayAttdMgtListAll(map);
//         modelMap.put("errorMsg", "success");
//         modelMap.put("errorCode", 0);
//         modelMap.put("dayAttdMgtList", dayAttdMgtList);
//      } catch (Exception ioe) {
//         ioe.printStackTrace();
//         modelMap.clear();
//         modelMap.put("errorMsg", ioe.getMessage());
//      }
//      return modelMap;
//   }

}
