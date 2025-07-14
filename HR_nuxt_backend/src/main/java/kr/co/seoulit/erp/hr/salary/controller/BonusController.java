package kr.co.seoulit.erp.hr.salary.controller;


import java.util.ArrayList;
import java.util.HashMap;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import kr.co.seoulit.erp.hr.salary.servicefacade.SalaryServiceFacade;
import kr.co.seoulit.erp.hr.salary.to.BonusTO;

@CrossOrigin("*")
@RestController
@Tag(name = "상여급 & 성과금관리", description = "상여&성과금 관리 API")

@RequestMapping("/hr/salary/*")
public class BonusController {
	
	@Autowired
	private  SalaryServiceFacade salaryServiceFacade;
	HashMap<String,Object> map = new HashMap<>();
	
//	@RequestMapping(value = "/findterBonus",method = RequestMethod.GET)
//	public HashMap<String,Object> findterBonus(@RequestParam String empCode,@RequestParam String applyYearMonth){
//		try {
//			BonusTO bonus = new BonusTO();
//			bonus.setEmpCode(empCode);
//			bonus.setApplyYearMonth(applyYearMonth);
//			map.put("empBonus", salaryServiceFacade.findterBonus(bonus));
//			map.put("errorMsg","success");
//			map.put("errorCode", 0);
//
//		} catch (Exception ioe) {
//			map.clear();
//			map.put("errorCode", -1);
//			map.put("errorMsg", ioe.getMessage());
//		}
//
//		return map;
//	}
//	
//	@RequestMapping(value = "/removeAllBonus",method = RequestMethod.POST)
//	public HashMap<String,Object> removeAllBonus(){
//		try {		
//			salaryServiceFacade.removeAllBonus();
//			map.clear();
//			map.put("errorMsg","success");
//			map.put("errorCode", 0);
//		} catch (Exception ioe) {
//			map.clear();
//			map.put("errorCode", -1);
//			map.put("errorMsg", ioe.getMessage());
//		}
//		return map;
//	}

	@Operation(summary = "직원 성과&상여 리스트 조회", description = "직원 성과&상여 리스트를 출력합니다.")
	@GetMapping("/findBonus")
	public HashMap<String, Object> findBonus(@RequestParam String empCode, @RequestParam String applyYearMonth
			, @RequestParam String detailCodeName, @RequestParam String finalizeStatus){
		System.out.println(empCode);
		System.out.println(applyYearMonth);
		System.out.println(detailCodeName);
		try {		
			ArrayList<BonusTO> bonusList 
				= salaryServiceFacade.findBonusList(empCode, applyYearMonth, detailCodeName,finalizeStatus);
			map.clear();
			map.put("bonusList", bonusList);
			map.put("errorMsg","success");
			map.put("errorCode", 0);
		} catch (Exception ioe) {
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", ioe.getMessage());
		}
		System.out.println(map);
		return map;
	}
	@Operation(summary = "직원 성과&상여 등록", description = "직원 성과&상여를 등록합니다.")
	@PostMapping("/registerBonus")
	public HashMap<String,Object> registerBonus(@RequestBody BonusTO bonus){
		System.out.println(bonus);
		try {
			salaryServiceFacade.registerBonus(bonus);
			map.clear();
			map.put("errorMsg","success");
			map.put("errorCode", 0);
		} catch (Exception ioe) {
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", ioe.getMessage());
		}
		return map;
	}
	@Operation(summary = "직원 성과&상여 삭제", description = "직원 성과&상여를 삭제합니다.")
	@DeleteMapping("/removeBonus")
	public HashMap<String,Object> removeBonus(@RequestParam String empCode,
			@RequestParam String applyYearMonth, @RequestParam String bonusCode){
		try {
			salaryServiceFacade.removeBonus(empCode, applyYearMonth, bonusCode);
			map.clear();
			map.put("errorMsg","success");
			map.put("errorCode", 0);
		} catch (Exception ioe) {
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", ioe.getMessage());
		}
		return map;
	}
}