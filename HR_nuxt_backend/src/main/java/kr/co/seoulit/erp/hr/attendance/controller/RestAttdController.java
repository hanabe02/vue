package kr.co.seoulit.erp.hr.attendance.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.seoulit.erp.hr.attendance.servicefacade.AttdServiceFacade;
import kr.co.seoulit.erp.hr.attendance.to.RestAttdTO;
import kr.co.seoulit.erp.hr.base.to.HrDetailCodeTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "근태신청관리", description = "근태신청관리 API")
@CrossOrigin("*")
@RestController
@RequestMapping("/hr/attendance/*")
public class RestAttdController {

	@Autowired
	private AttdServiceFacade attdServiceFacade;
	private ModelAndView modelAndView = null;
	private ModelMap modelMap = new ModelMap();
	private final HashMap<String, Object> map = new HashMap<>();


	// 근태 신청 및 등록 - 근태 신청 내역 조회
	@GetMapping("/findRestAttdListByEmpCodeAndYM")
	public HashMap<String, Object> findRestAttdListByEmpCodeAndYMD(@RequestParam("empCode") String empCode
			 													  ,@RequestParam("yearMonth") String yearMonth) {
		try {
			map.clear();
			ArrayList<RestAttdTO> restAttdList = attdServiceFacade.findRestAttdListByEmpCodeAndYMD(empCode, yearMonth);
			map.put("restAttdList", restAttdList);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		} catch (Exception ioe) {
			map.clear();
			map.put("errorMsg", ioe.getMessage());
			map.put("errorCode", -1);
		}
		return map;
	}
	@GetMapping("/findRestAttdListByAnnulVacation")
	public HashMap<String, Object> findRestAttdListByAnnulVacation(@RequestParam("empCode") String empCode,
																   @RequestParam("yearMonth") String yearMonth) {

		System.out.println(":::::::::: empCode = ::::::::::" + empCode);
		System.out.println(":::::::::: yearMonth = ::::::::::" + yearMonth);
		try {
			map.clear();
			ArrayList<RestAttdTO> restAttdList = attdServiceFacade.findRestAttdListByAnnulVacation(empCode, yearMonth);
			map.put("restAttdList", restAttdList);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		} catch (Exception ioe) {
			map.clear();
			map.put("errorMsg", ioe.getMessage());
			map.put("errorCode", -1);
		}
		return map;
	}

	@PostMapping("/registeRequestVacation")
	public HashMap<String, Object> regiestRequestVacation(@RequestBody RestAttdTO restAttdTO) {
		System.out.println(restAttdTO);
		try {
			map.clear();
			attdServiceFacade.regiestRequestVacation(restAttdTO);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		} catch (Exception ioe) {
			map.clear();
			map.put("errorMsg", ioe.getMessage());
			map.put("errorCode", -1);
		}
		return map;
	}

	// 휴가 수정
	@PutMapping("/updateVacation")
	public ResponseEntity<Map<String, Object>> updateVacation (@RequestBody RestAttdTO restAttdTO) {
		Map<String, Object> map = new HashMap<>();
		System.out.println(" :::::::::: restAttdTO = :::::::::: " + restAttdTO);
		try {
			attdServiceFacade.updateVacation(restAttdTO);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
			return ResponseEntity.ok(map);
		} catch (Exception ioe) {
			map.clear();
			map.put("errorMsg", ioe.getMessage());
			map.put("errorCode", -1);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
		}
	}

	// ************************* 외출 및 조퇴 신청 시작 _준서 _20.08.24
	@PostMapping(value = "/registRestAttd")
	public ModelMap registRestAttd(@RequestBody RestAttdTO restAttdTO) {
		try {
			modelMap = new ModelMap();
			System.out.println(restAttdTO);
			attdServiceFacade.registRestAttd(restAttdTO);
			modelMap.put("errorMsg", "success");
			modelMap.put("errorCode", 0);
		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
			modelMap.put("errorCode", -1);
		}
		return modelMap;
	}

	@GetMapping("/findTravelAndEducationList")
	public HashMap<String, Object> findTravelAndEducationList(
			@RequestParam("empCode") String empCode, @RequestParam("startDate") String startDate
			, @RequestParam("endDate") String endDate){
		try {
			map.clear();
			ArrayList<RestAttdTO> travelAndEducationList = attdServiceFacade.findTravelAndEducationList(empCode,startDate,endDate);
			map.put("travelAndEducationList",travelAndEducationList);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		} catch (Exception ioe) {
			map.clear();
			map.put("errorMsg", ioe.getMessage());
			map.put("errorCode", -1);
		}
		return map;
	}

	@PutMapping("/modifyTravelAndEducationList")
	public HashMap<String, Object> modifyTravelAndEducationList(@RequestBody RestAttdTO restAttdTO){

		try {
			map.clear();
			attdServiceFacade.modifyTravelAndEducationList(restAttdTO);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		} catch (Exception ioe) {
			map.clear();
			map.put("errorMsg", ioe.getMessage());
			map.put("errorCode", -1);
		}
		return map;
	}

	@DeleteMapping("/removeTravelAndEducationList")
	public HashMap<String, Object> removeTravelAndEducationList(@RequestParam("restAttdCode") String restAttdCode,
																@RequestParam("empCode")String empCode){
		try {
			map.clear();
			attdServiceFacade.removeTravelAndEducationList(restAttdCode, empCode);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		} catch (Exception ioe) {
			map.clear();
			map.put("errorMsg", ioe.getMessage());
			map.put("errorCode", -1);
		}
		return map;
	}

	@DeleteMapping("/removeRestAttd")
	public HashMap<String, Object> removeRestAttdList(
				@RequestParam("restAttdCode") String restAttdCode){
		try {
			map.clear();
			attdServiceFacade.deleteRestAttd(restAttdCode);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		} catch (Exception ioe) {
			map.clear();
			map.put("errorMsg", ioe.getMessage());
			map.put("errorCode", -1);
		}
		return map;
	}

	public ModelAndView findRestAttdListByToday(HttpServletRequest request, HttpServletResponse response) {
		String empCode = request.getParameter("empCode");
		String toDay = request.getParameter("toDay");

		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			ArrayList<RestAttdTO> restAttdList = attdServiceFacade.findRestAttdListByToday(empCode, toDay);
			modelMap.put("errorMsg", "success");
			modelMap.put("errorCode", 0);
			modelMap.put("restAttdList", restAttdList);

		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		}
		modelAndView = new ModelAndView("/jsonView", modelMap);
		return modelAndView;
	}
	// ************************* 외출 및 조퇴 신청 종료 _준서 _20.08.24
	@RequestMapping("/findRestAttdList")
	public ModelMap findRestAttdList(HttpServletRequest request, HttpServletResponse response) {

		String empCode = request.getParameter("empCode");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String code = request.getParameter("code");

		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			ArrayList<RestAttdTO> restAttdList = attdServiceFacade.findRestAttdList(empCode, startDate, endDate, code);
			modelMap.put("restAttdList", restAttdList);
			modelMap.put("errorMsg", "success");
			modelMap.put("errorCode", 0);
		} catch (Exception ioe) {

			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		}
		return modelMap;
	}

	@RequestMapping("/removeRestAttdList")
	public ModelMap removeRestAttdList(HttpServletRequest request, HttpServletResponse response) {

		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=UTF-8");
			modelMap.put("errorMsg", "success");
			modelMap.put("errorCode", 0);
		} catch (Exception ioe) {

			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		}
		return modelMap;
	}

	// ************************* 외출 및 조퇴 조회 시작 _준서 _20.08.24
	@RequestMapping(value = "/findRestAttdList", method = RequestMethod.GET)
	public ModelMap findRestAttdList(@RequestParam HashMap<String, String> attdRestMap, HttpServletResponse response) {
		System.out.println("<< findRestAttdList >>");
		System.out.println(attdRestMap.get("empCode") + " / " + attdRestMap.get("startDate") + " / "
				+ attdRestMap.get("endDate") + " / " + attdRestMap.get("code"));

		String empCode = attdRestMap.get("empCode");
		String startDate = attdRestMap.get("startDate");
		String endDate = attdRestMap.get("endDate");
		String code = attdRestMap.get("code");
		try {
			response.setContentType("application/json; charset=UTF-8");
			if (!code.equals("")) {
				ArrayList<RestAttdTO> restAttdList = attdServiceFacade.findRestAttdList(empCode, startDate, endDate,
						code);
				System.out.println("restAttdList: " + restAttdList);
				modelMap.put("restAttdList", restAttdList);
				modelMap.put("errorMsg", "success");
				modelMap.put("errorCode", 0);
			}
		} catch (Exception ioe) {

			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		}

		return modelMap;
	}
	// ************************* 외출 및 조퇴 조회 종료 _준서 _20.08.24

	/// 인봉 근태외유형가져오기
	@RequestMapping(value = "/searchRestAttendanceType", method = RequestMethod.GET)
	public ModelMap searchRestAttendanceType() {

		try {
			ArrayList<HrDetailCodeTO> typeList = attdServiceFacade.searchRestAttdType();

			modelMap.put("errorMsg", "success");
			modelMap.put("errorCode", 0);
			modelMap.put("typeList", typeList);
		} catch (Exception ioe) {
			ioe.printStackTrace();
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
			modelMap.put("errorCode", -1);
		}
		return modelMap;
	}
}
