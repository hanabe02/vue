package kr.co.seoulit.erp.hr.salary.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import kr.co.seoulit.erp.hr.salary.servicefacade.SalaryServiceFacade;
import kr.co.seoulit.erp.hr.salary.to.MonthDeductionTO;

@CrossOrigin("*")
@RestController
@Tag(name = "세금 관리", description = "세금 관리 API")
@RequestMapping("/hr/salary/*")
public class MonthDeductionController {

	@Autowired
	private SalaryServiceFacade salaryServiceFacade;
	HashMap<String, Object> map = new HashMap<>();
	@Operation(summary = "직원 세금 조회", description = "해당 사원의 해당 년도 세금을 조회합니다.")
	@GetMapping("/findMonthDeduction")
	public HashMap<String, Object> findMonthDeduction(@RequestParam String applyYearMonth, @RequestParam String empCode){
		try {
			ArrayList<MonthDeductionTO> monthDeductionList = salaryServiceFacade.findMonthDeduction(applyYearMonth,empCode);
			map.clear();
			map.put("monthDeductionList", monthDeductionList);
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
