package kr.co.seoulit.erp.hr.salary.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import kr.co.seoulit.erp.hr.salary.servicefacade.SalaryServiceFacade;
import kr.co.seoulit.erp.hr.salary.to.BaseSalaryTO;

@CrossOrigin("*")
@RequestMapping("/hr/salary")
@RestController
@Tag(name = "기본급 기준 관리", description = "기본급 관리 API")
public class BaseSalaryController {
	@Autowired
	private SalaryServiceFacade salaryServiceFacade;
	HashMap<String, Object> map = new HashMap<>();
	
	// **********08-28 손유찬 종료**************
//	@RequestMapping(value = "/BaseSalaryList", method = RequestMethod.GET)
//	public HashMap<String, Object> BaseSalaryList(HttpServletRequest request, HttpServletResponse response) {
//		String selectDeptTitle = request.getParameter("selectDeptTitle");
//		try {
//			ArrayList<BaseSalaryTO> baseSalaryList = salaryServiceFacade.BaseSalaryList(selectDeptTitle);
//			map.clear();
//			map.put("gridRowJson", baseSalaryList);
//			map.put("errorMsg", "success");
//			map.put("errorCode", 0);
//		} catch (Exception ioe) {
//			map.clear();
//			map.put("errorCode", -1);
//			map.put("errorMsg", ioe.getMessage());
//		}
//		return map;
//	}
	@Operation(summary = "기본급 리스트 조회", description = "부서별 직원 기본급 리스트를 출력합니다.")

	@GetMapping("/BaseSalaryList")
	public HashMap<String, Object> baseSalaryList(@RequestParam String payload) {
		System.out.println(payload);
		try {
			ArrayList<BaseSalaryTO> baseSalaryList = salaryServiceFacade.BaseSalaryList(payload);
			map.clear();
			map.put("baseSalaryList", baseSalaryList);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		} catch (Exception ioe) {
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", ioe.getMessage());
		}
		return map;
	}

//	// **********08-28 손유찬 시작**************
//	@RequestMapping(value = "/baseSalaryManage", method = RequestMethod.GET)
//	public HashMap<String, Object> findBaseSalaryList() {
//
//		try {
//			ArrayList<BaseSalaryTO> baseSalaryList = salaryServiceFacade.findBaseSalaryList();
//			map.clear();
//			map.put("baseSalaryList", baseSalaryList);
//			map.put("errorMsg", "success");
//			map.put("errorCode", 0);
//		} catch (Exception ioe) {
//			map.clear();
//			map.put("errorCode", -1);
//			map.put("errorMsg", ioe.getMessage());
//		}
//		return map;
//	}
@Operation(summary = "기본급 수정", description = " 직원 기본급을 수정합니다.")
	@PutMapping("/updateBaseSalary")
	public HashMap<String, Object> modifyBaseSalary(@RequestBody BaseSalaryTO baseSalaryTO){
		try{
			salaryServiceFacade.modifyBaseSalary(baseSalaryTO);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);

		} catch (Exception err) {
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", err.getMessage());
		}
		return map;
	}

//	@RequestMapping(value = "/baseSalaryManage", method = RequestMethod.POST)
//	public HashMap<String, Object> modifyBaseSalaryList(@RequestBody Map<String, ArrayList<BaseSalaryTO>> sendData) {
//		try {
//			System.out.println("BaseSalaryController 시작");
//			ArrayList<BaseSalaryTO> baseSalaryList = sendData.get("sendData");
//			System.out.println(baseSalaryList);
//			salaryServiceFacade.modifyBaseSalaryList(baseSalaryList);
//			map.clear();
//			map.put("errorMsg", "success");
//			map.put("errorCode", 0);
//
//		} catch (Exception ioe) {
//			map.clear();
//			map.put("errorCode", -1);
//			map.put("errorMsg", ioe.getMessage());
//		}
//		return map;
//	}
@Operation(summary = "기본급 등록", description = " 직원 기본급을 등록합니다.")
	@PostMapping("/registerBaseSalary")
	public HashMap<String, Object> registerBaseSalary(@RequestBody BaseSalaryTO baseSalaryTo){
		try {
			salaryServiceFacade.registerBaseSalary(baseSalaryTo);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		}catch(Exception err) {
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", err.getMessage());
		}
		System.out.println(map);
		return map;
	}
	@Operation(summary = "기본급 삭제", description = " 직원 기본급을 삭제합니다.")
	@DeleteMapping("/removeBaseSalary")
	public HashMap<String, Object> removeBaseSalay(@RequestParam String deptCode
			,@RequestParam String positionCode){
		try {
			salaryServiceFacade.removeBaseSalary(deptCode, positionCode);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		}catch(Exception err) {
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", err.getMessage());
		}
		System.out.println(map);
		return map;
	}

}