package kr.co.seoulit.erp.hr.attendance.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.seoulit.erp.hr.attendance.servicefacade.AttdServiceFacade;
import kr.co.seoulit.erp.hr.attendance.to.MonthAttdMgtTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@Tag(name = "월근태 관리", description = "월근태 관리 API")
@CrossOrigin("*")
@RestController
@RequestMapping("/hr/attendance")
public class MonthAttdManageController {

	@Autowired
	private AttdServiceFacade attdServiceFacade;

	// ✅ 월근태 조회
	@GetMapping("/monthAttendanceManage")
	public ModelMap findMonthAttdMgtList(@RequestParam String applyYearMonth) {
		ModelMap modelMap = new ModelMap();
		try {
			ArrayList<MonthAttdMgtTO> monthAttdMgtList = attdServiceFacade.findMonthAttdMgtList(applyYearMonth);
			modelMap.put("monthAttdMgtList", monthAttdMgtList);
			modelMap.put("errorMsg", "success");
			modelMap.put("errorCode", 0);
		} catch (Exception e) {
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e.getMessage());
		}
		return modelMap;
	}

	// ✅ 월근태 마감
	@PostMapping("/monthAttendanceClose")
	public ModelMap modifyMonthAttdList(@RequestBody HashMap<String, ArrayList<MonthAttdMgtTO>> monthAttdMgt) {
		ModelMap modelMap = new ModelMap();
		try {
			ArrayList<MonthAttdMgtTO> monthAttdMgtList = monthAttdMgt.get("monthAttdMgt");
			attdServiceFacade.modifyMonthAttdMgtList(monthAttdMgtList);
			modelMap.put("errorMsg", "success");
			modelMap.put("errorCode", 0);
		} catch (Exception e) {
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e.getMessage());
		}
		return modelMap;
	}

	// ✅ 월근태 집계 실행
	@PostMapping("/batchMonthAttd")
	public HashMap<String, Object> batchMonthAttd(@RequestBody HashMap<String, String> param) {
		HashMap<String, Object> result = new HashMap<>();
		try {
			String applyYearMonth = param.get("applyYearMonth");

			if (applyYearMonth == null || applyYearMonth.trim().isEmpty()) {
				result.put("errorCode", -1);
				result.put("errorMsg", "applyYearMonth 값이 없습니다.");
				return result;
			}

			return (HashMap<String, Object>) attdServiceFacade.callMonthlyAggregateProcedure(applyYearMonth);

		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorCode", -999);
			result.put("errorMsg", "월근태 집계 중 서버 오류 발생: " + e.getMessage());
			return result;
		}
	}
}
