package kr.co.seoulit.erp.sys.controller;

import kr.co.seoulit.erp.hr.base.to.HolidayTO;
import kr.co.seoulit.erp.sys.serviceFacade.BaseServiceFacade;
import kr.co.seoulit.erp.sys.to.SysCodeDetailTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/sys/*")
public class CodeDetailHandler {

	@Autowired
	private BaseServiceFacade baseServiceFacade;

	@RequestMapping("/findCodeDetailList")
	public List<SysCodeDetailTo> findCodeDetailList() throws Exception {

		return baseServiceFacade.findCodeDetailList();

	}

	@RequestMapping("/findPayStepCodeDetailList")
	public List<SysCodeDetailTo> findPayStepCodeDetailList(@RequestParam String itemClassificationCondition) throws Exception {
		System.out.println("divisionCode:" + itemClassificationCondition);
		return baseServiceFacade.findPayStepCodeDetailList(itemClassificationCondition);

	}

	@RequestMapping("/batchDetailCode")
	public void batchDetailCode(@RequestBody List<SysCodeDetailTo> codeDetailList) throws Exception {
		// System.out.println("aaaaaaa"+codeDetailList.get(0).getDetailCodeName());
		baseServiceFacade.batchDetailCodeProcess(codeDetailList);
	}
	
	@GetMapping("/findHolidayType")
	public HashMap<String, Object> findHolidayType(){
		System.out.println("한방에 가자");
		HashMap<String,Object> map = new HashMap<String, Object>();
		try {
			ArrayList<SysCodeDetailTo> holidayTypeList = baseServiceFacade.findHolidayType();
			map.clear();
			map.put("holidayTypeList", holidayTypeList);
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
