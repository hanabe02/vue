package kr.co.seoulit.erp.hr.affair.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.seoulit.erp.hr.affair.servicefacade.EmpServiceFacade;
import kr.co.seoulit.erp.hr.affair.to.EmpTO;

@CrossOrigin("*")
@RestController
@RequestMapping("/hr/affair/*")
public class EmpListController {

	@Autowired
	private EmpServiceFacade empServiceFacade;

	@RequestMapping("/list111")
	public Map<String, Object> emplist(@RequestParam String dept, HttpServletResponse response) {
		System.out.println("부서명호출");
		System.out.println(dept);
		Map<String, Object> map = new HashMap<String, Object>();

		List<EmpTO> list = empServiceFacade.findEmpList(dept);
		map.put("list", list);

		return map;
	}

	@RequestMapping(value = "/memberList111", method = RequestMethod.GET)
	public Map<String, Object> emplist(@RequestParam String value) {
		System.out.println("리스트");
		System.out.println(value);
		Map<String, Object> map = new HashMap<String, Object>();
		if (value == null)
			value = "전체부서";

		List<EmpTO> list = empServiceFacade.findEmpMemberList(value);
		System.out.println("list:" + list);
		map.put("list", list);
		map.put("errorMsg", "success");
		map.put("errorCode", 0);

		return map;
	}

	@RequestMapping(value = "/empList111", method = RequestMethod.GET)
	public Map<String, Object> empAllList() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<EmpTO> list = empServiceFacade.findEmpList();
		System.out.println("empList:" + list);
		map.put("empList", list);
		map.put("errorMsg", "success");
		map.put("errorCode", 0);

		return map;
	}
}
