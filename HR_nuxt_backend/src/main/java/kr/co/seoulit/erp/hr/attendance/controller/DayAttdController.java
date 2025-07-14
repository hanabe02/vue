package kr.co.seoulit.erp.hr.attendance.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.seoulit.erp.hr.affair.to.EmpTO;
import kr.co.seoulit.erp.hr.attendance.servicefacade.AttdServiceFacade;
import kr.co.seoulit.erp.hr.attendance.to.DayAttdTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Tag(name = "일근태", description = "일근태 API")
@CrossOrigin("*")
@RestController
@RequestMapping("/hr/attendance/*")
public class DayAttdController {

	@Autowired
	private AttdServiceFacade attdServiceFacade;
	HashMap<String, Object> map = new HashMap<>();

	@RequestMapping(value = "/dayAttendance", method = RequestMethod.GET)
	public HashMap<String, Object> findDayAttdList(@RequestParam("applyDay") String applyDay,
													@RequestParam("empCode") String empCode) {

		HashMap<String, Object> map = new HashMap<>();
		try{
			ArrayList<DayAttdTO> dayAttdList = attdServiceFacade.findDayAttdList(empCode, applyDay);
			map.put("dayAttdList", dayAttdList);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		}catch(Exception ioe){
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", ioe.getMessage());
		}
		System.out.println("map을 확인해보자" + map);
		return map;
	}

	@GetMapping("/findEmpListByDept")
	public Map<String, Object> findEmpListByDept(@RequestParam("deptCode") String deptCode){
		try {
			ArrayList<EmpTO> list = attdServiceFacade.findEmpListByDept(deptCode);
			map.clear();
			map.put("empList", list);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		} catch(Exception ioe){
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", ioe.getMessage());
		}
		return map;
	}

	@PostMapping("/dayAttendance")
	public Map<String, Object> registDayAttd(@RequestBody DayAttdTO dayAttdTO){
		System.out.println("DayAttdTo@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + dayAttdTO + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		HashMap<String, Object> map = new HashMap<>();
		try{
			attdServiceFacade.insertDayAttd(dayAttdTO);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		}catch (Exception ioe){
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", ioe.getMessage());
		}
		System.out.println("삭제단 마지막 map" + map);

		return map;
	}

	@PutMapping("/dayAttendanceUpdate")
	public Map<String, Object> updateDayAttd(@RequestBody DayAttdTO dayAttdTO){
		System.out.println(dayAttdTO);
		HashMap<String, Object> map = new HashMap<>();
//		attdServiceFacade.updateDayAttd(dayAttdTO);
		try {
			attdServiceFacade.updateDayAttd(dayAttdTO);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		}catch (Exception ioe){
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", ioe.getMessage());
		}
		System.out.println(map);
		return map;
	}

	@DeleteMapping("/dayAttendance")
	public Map<String, Object> deleteDayAttd(@RequestParam String empCode, @RequestParam String dayAttdCode) {
		System.out.println("삭제단입니다." + empCode + "+" + dayAttdCode);
		HashMap<String, Object> map = new HashMap<>();
		try {
			attdServiceFacade.deleteDayAttd(empCode, dayAttdCode);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		} catch (Exception ioe) {
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", ioe.getMessage());
		}
		return map;
	}
}
