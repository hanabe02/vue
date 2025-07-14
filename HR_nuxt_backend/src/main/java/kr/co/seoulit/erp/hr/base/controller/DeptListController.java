package kr.co.seoulit.erp.hr.base.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import kr.co.seoulit.erp.hr.affair.servicefacade.EmpServiceFacade;
import kr.co.seoulit.erp.hr.base.servicefacade.HrBaseServiceFacade;
import kr.co.seoulit.erp.hr.base.to.DeptTO;
import kr.co.seoulit.erp.hr.salary.to.BaseSalaryTO;

@CrossOrigin("*")
@RestController
@RequestMapping("/hr/base/*")
public class DeptListController{
	@Autowired
	private HrBaseServiceFacade baseServiceFacade;
	@Autowired
	private EmpServiceFacade empServiceFacade;

	@RequestMapping(value="/deptList", method=RequestMethod.POST)
	public void batchDeptProcess(@RequestBody HashMap<String,ArrayList<DeptTO>> sendData) {

//		  ObjectMapper mapper = new ObjectMapper();
//		ArrayList<DeptTO> deptto =null;
//				try {
//					deptto = mapper.readValue(sendData, new TypeReference<ArrayList<DeptTO>>(){});
//
//		  		} catch (IOException e) {
//		  			e.printStackTrace();
//		  		}
//
//				//수녕ssasd 몇단 전
	  baseServiceFacade.batchDeptProcess(sendData.get("sendData"));
	}

//@RequestMapping(value="/deptList", method=RequestMethod.GET )
//@ResponseBody
//	public Map<String, Object> findDeptList(){
//		Map<String, Object> map = new HashMap<String, Object>();
//
//			List<DeptTO> list = empServiceFacade.findDeptList();
//			DeptTO emptyBean = new DeptTO();
//			 map.put("emptyBean", emptyBean);
//			 map.put("list", list);
//			 map.put("errorMsg","success");
//			 map.put("errorCode", 0);
//
//
//
//		return map;
//	}FETCH_EMP_LIST_DEPT
//
	@GetMapping("/deptList")
	public Map<String, Object> findDeptList() {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<DeptTO> list = empServiceFacade.findDeptList();
		map.put("deptlist", list);
		map.put("errorMsg","success");
		map.put("errorCode", 0);
		return map;
	}

	@GetMapping("/deptListDetail")
	public Map<String, Object> findDeptListDetail(){
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			ArrayList<DeptTO> deptlistDetail = empServiceFacade.findDeptListDetail();
			map.clear();
			map.put("deptlistDetail", deptlistDetail);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		} catch (Exception ioe) {
			map.clear();
			map.put("errorCode", -1);
			map.put("errorMsg", ioe.getMessage());
		}
		return map;
	}
	@GetMapping("/findDeptManageList")
	public Map<String, Object> findDeptManageList(){
		Map<String, Object> map = new HashMap<>();
		try {
			ArrayList<DeptTO> deptManageList = empServiceFacade.findDeptManageList();
			map.clear();
			map.put("deptManageList", deptManageList);
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

	@PostMapping("/addDept")
	public Map<String,Object> registerDept(@RequestBody DeptTO dept){
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(dept);
		try {
			baseServiceFacade.registerDept(dept);
			map.clear();
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

	@PutMapping("/modifyDept")
	public Map<String, Object> modifyDept(@RequestBody DeptTO dept){
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(dept);
		try {
			baseServiceFacade.modifyDept(dept);
			map.clear();
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

	@DeleteMapping("/removeDept")
	public Map<String, Object> removeDept(@RequestParam String deptCode, @RequestParam String workplaceCode){
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(deptCode);
		System.out.println(workplaceCode);
		try {
			baseServiceFacade.removeDept(deptCode, workplaceCode);
			map.clear();
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
}
