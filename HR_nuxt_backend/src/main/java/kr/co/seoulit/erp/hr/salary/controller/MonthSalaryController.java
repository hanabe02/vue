package kr.co.seoulit.erp.hr.salary.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import kr.co.seoulit.erp.hr.salary.servicefacade.SalaryServiceFacade;
import kr.co.seoulit.erp.hr.salary.to.BaseSalaryTO;
import kr.co.seoulit.erp.hr.salary.to.MonthSalaryTO;

@CrossOrigin("*")
@RestController
@Tag(name = "월급관리", description = "월급관리 API")
@RequestMapping(value = "/hr/salary/*", produces = "application/json")
public class MonthSalaryController {
	
	@Autowired
	private SalaryServiceFacade salaryServiceFacade;

	
	HashMap<String, Object> map = new HashMap<>();
	@Operation(summary = "직원 월급 리스트 조회", description = "월급 리스트를 출력합니다.")

	@GetMapping("/searchSalaryInit")
	public HashMap<String, Object> initSalaryList(@RequestParam String applyYearMonth,@RequestParam String empCode){
		System.out.println(applyYearMonth);
		try {
			ArrayList<MonthSalaryTO> initSalaryList = salaryServiceFacade.initSalaryList(applyYearMonth,empCode);
			map.clear();
			map.put("initSalaryList", initSalaryList);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		} catch (Exception ioe) {
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", ioe.getMessage());
		}
		System.out.println(map);
		return map;
	}

	@Operation(summary = "직원 월급 등록을 위한 프로시저 실행", description = "월급 등록하기 위해 세부 내용을 프로시저 실행 후 출력합니다.")
	@GetMapping("/salaryProcess")
	public HashMap<String, Object> findSalaryList(@RequestParam String applyYearMonth,
			@RequestParam String empCode, @RequestParam String deptCode){

			HashMap<String, Object> resultMap = salaryServiceFacade.findSalaryList(applyYearMonth, empCode, deptCode);

			System.out.println("resultMap 내용: " + resultMap);

			ArrayList<MonthSalaryTO> salaryList = (ArrayList<MonthSalaryTO>)resultMap.get("RESULT");
			System.out.println("salaryList 결과: " + salaryList);
		HashMap<String, Object> map = new HashMap<>();
		map.put("salaryList", salaryList);
		map.put("errorCode", resultMap.get("ERROR_CODE"));
		map.put("errorMsg", resultMap.get("ERROR_MSG"));

		System.out.println("map 확인5");
		System.out.println(map+"ㄴㄴ");
		return map;
	}

	@Operation(summary = "직원 월급 수정", description = "월급 수정합니다.")
	@PutMapping("/updateMonthSal")
	public HashMap<String, Object> modifyMonthSal(@RequestBody MonthSalaryTO monthSalaryTo){
		System.out.println(monthSalaryTo);
		try {
			salaryServiceFacade.modifyMonthSalary(monthSalaryTo);
			map.clear();
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		} catch (Exception ioe) {
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", ioe.getMessage());
		}
		return map;
	}
	@Operation(summary = "직원 월급 삭제", description = "해당 직원의 월급을 삭제합니다.")
	@DeleteMapping("/removeMonthSalary")
	public HashMap<String, Object> removeMonthSalary(@RequestParam String empCode, @RequestParam String applyYearMonth){
		try {
			salaryServiceFacade.removeMonthSalary(empCode, applyYearMonth);
			map.clear();
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		} catch (Exception ioe) {
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", ioe.getMessage());
		}
		return map;
	}

	@Operation(summary = "직원 월급 조회", description = "선택한 직원 월급을 조회합니다.")
	@GetMapping("/findSalary")
	public HashMap<String, Object> findSalary(@RequestParam String applyYearMonth,@RequestParam String empCode){
		System.out.println(applyYearMonth);
		System.out.println(empCode);
		try {
			ArrayList<MonthSalaryTO> salaryList = salaryServiceFacade.findSalary(applyYearMonth,empCode);
			map.clear();
			map.put("salaryList", salaryList);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		} catch (Exception ioe) {
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", ioe.getMessage());
		}
		System.out.println(map);
		return map;
	}
	

//

}