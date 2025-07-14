package kr.co.seoulit.erp.hr.base.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kr.co.seoulit.erp.hr.base.applicationservice.JpaYearMonthService;
import kr.co.seoulit.erp.hr.base.entity.MonthEntity;
import kr.co.seoulit.erp.hr.base.entity.YearEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import kr.co.seoulit.erp.hr.base.servicefacade.HrBaseServiceFacade;
import kr.co.seoulit.erp.hr.base.to.YearMonthTO;

@CrossOrigin("*")
@RestController
@RequestMapping("/hr/base/*")
public class YearMonthListController{
	@Autowired
	private HrBaseServiceFacade baseServiceFacade;
	@Autowired
	private JpaYearMonthService jpaYearMonthService;
	Map<String, Object> map = new HashMap<String, Object>();
	
	@GetMapping("/yearList")
	public Map<String, Object> findYearList(){
			//ArrayList<YearMonthTO> yearList = baseServiceFacade.findYearList();
			ArrayList<YearEntity> yearList = jpaYearMonthService.findYear();
			map.put("yearList", yearList);
			map.put("errorCode", 0);
			map.put("errorMsg", "success");
		return map;
	}
	
	@GetMapping("/monthList")
	public Map<String, Object> findMonthList(){
			ArrayList<YearMonthTO> monthList = baseServiceFacade.findMonthList();
			//ArrayList<MonthEntity> monthList = jpaYearMonthService.findMonth();
			map.put("monthList", monthList);
			map.put("errorCode", 0);
			map.put("errorMsg", "success");
			System.out.println(map);
		return map;
	}
}