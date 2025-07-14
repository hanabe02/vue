package kr.co.seoulit.erp.hr.salary.controller;

import java.util.ArrayList;
import java.util.HashMap;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import kr.co.seoulit.erp.hr.salary.servicefacade.SalaryServiceFacade;
import kr.co.seoulit.erp.hr.salary.to.BaseExtSalTO;
import kr.co.seoulit.erp.hr.salary.to.SeveranceTO;

@CrossOrigin("*")
@RequestMapping("/hr/salary/*")
@RestController
@Tag(name = "퇴직금 관리", description = "퇴직금관리 API")
public class SeverancePayController {

	// *********************2021/09/14 퇴직금조회 고범석*****************//
	@Autowired
	private SalaryServiceFacade salaryServiceFacade;
	HashMap<String, Object> map = new HashMap<>();
	
	
//	@RequestMapping(value = "/severancePay", method = RequestMethod.GET)
//	public HashMap<String, Object> findSeveranvePay(@RequestParam("empName") String empName) {
//		System.out.println(empName);
//		
//		ArrayList<SeveranceTO> severancePayList = salaryServiceFacade.findSeverancePay(empName);
//
//		map.put("severancePayList", severancePayList);
//
//		return map;
//	}
@Operation(summary = "직원 퇴직금 리스트 조회", description = "퇴직금 리스트를 출력합니다.")

@GetMapping("/findSeverancePay")
	public HashMap<String, Object> findSeverancePay(@RequestParam String firstDay, @RequestParam String today){
		System.out.println(firstDay);
		System.out.println(today);
		try {
			ArrayList<SeveranceTO> severanceList = salaryServiceFacade.findSeverancePay(firstDay, today);
			map.clear();
			map.put("severanceList", severanceList);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		} catch (Exception ioe) {
			map.put("errorCode", -1);
			map.put("errorMsg", ioe.getMessage());
		}
		System.out.println(map);
		return map;
	}

	@Operation(summary = "직원 퇴직금 등록", description = "해당 직원 퇴직금을 등록합니다.")
	@PostMapping("/registerSeverancePay")
	public HashMap<String, Object> registerSeverancePay(@RequestBody SeveranceTO severance){
		System.out.println(severance);
		try {
			HashMap<String, Object> resultMap = salaryServiceFacade.registerSeverancePay(severance); 
			ArrayList<SeveranceTO> severanceList = (ArrayList<SeveranceTO>)resultMap.get("result");
			map.clear();
			map.put("severanceList", severanceList);
			map.put("errorMsg", resultMap.get("errorMsg"));
			map.put("errorCode", resultMap.get("errorCode"));
		} catch (Exception ioe) {
			map.put("errorCode", -2);
			map.put("errorMsg", ioe.getMessage());
		}
		System.out.println("map확인");
		System.out.println(map);
		return map;
	}
	@Operation(summary = "직원 퇴직금 삭제", description = "해당 직원 퇴직금을 삭제합니다.")
	@DeleteMapping("/removeSeverance")
	public HashMap<String, Object> removeSeverance(@RequestParam String empCode){
		System.out.println(empCode);
		try {
			salaryServiceFacade.removeSeverance(empCode);
			map.clear();
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		} catch (Exception ioe) {
			map.put("errorCode", -1);
			map.put("errorMsg", ioe.getMessage());
		}
		System.out.println(map);
		return map;
	}
}
