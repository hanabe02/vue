package kr.co.seoulit.erp.hr.salary.controller;

import java.util.ArrayList;
import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.seoulit.erp.hr.salary.applicationservice.JpaSocialInsureService;
import kr.co.seoulit.erp.hr.salary.entity.SocialInsureEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import kr.co.seoulit.erp.hr.salary.servicefacade.SalaryServiceFacade;
import kr.co.seoulit.erp.hr.salary.to.SocialInsureTO;

@CrossOrigin("*")
@RequestMapping("/hr/salary/*")
@RestController
@Tag(name = "사대 보험 관리", description = "사대보험관리 API")
public class SocialInsureController {
	@Autowired
	private SalaryServiceFacade salaryServiceFacade;
	@Autowired
	private JpaSocialInsureService jpaSocialInsureService;
	HashMap<String, Object> map = new HashMap<>();
	@Operation(summary = "사대 보험 리스트 조회", description = "해당 년도 사대보험 리스트를 출력합니다.")
	@GetMapping("/SocialInsureList")
	public HashMap<String, Object> findSocialInsureList(@RequestParam String year) {
		System.out.println("선택 년도:"+year); 
		System.out.println(year);
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
//			ArrayList<SocialInsureTO> baseInsureList = salaryServiceFacade.findSocialInsureList(year);
			ArrayList<SocialInsureEntity> baseInsureList = jpaSocialInsureService.findSocialInsureList(year);
			map.clear();
			map.put("baseInsureList", baseInsureList);
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
	@Operation(summary = "사대 보험 등록", description = "사대보험을 등록합니다.")
	@PostMapping("/registerSocialInsure")
	public HashMap<String, Object> registerSocialInsure(@RequestBody SocialInsureTO socialInsureTo){
		System.out.println(socialInsureTo);
		try {
			salaryServiceFacade.registerSocialInsure(socialInsureTo);
		}catch(Exception err) {
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", err.getMessage());
		}
		return map;
	}
}
