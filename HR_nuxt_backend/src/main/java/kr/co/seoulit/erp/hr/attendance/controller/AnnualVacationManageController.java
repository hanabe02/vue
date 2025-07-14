package kr.co.seoulit.erp.hr.attendance.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.seoulit.erp.hr.attendance.servicefacade.AttdServiceFacade;
import kr.co.seoulit.erp.hr.attendance.to.AnnualVacationMgtTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;


@Tag(name = "휴가 관리", description = "휴가 관리 API")
@RestController
@CrossOrigin("*")
@RequestMapping("/hr/attendance/*")
public class AnnualVacationManageController {

	@Autowired
	private AttdServiceFacade attdServiceFacade;
	HashMap<String, Object> map = new HashMap<>();
	private ModelMap modelMap = new ModelMap();

	// 근태 관리 - 휴가 관리
	@GetMapping ("/findAnnualVactionMgtListByYear")
	public HashMap<String, Object> findAnnualVactionMgtListByYear (@RequestParam String year, @RequestParam String empCode){

		System.out.println(":::::::::: yearMonth, empCode = ::::::::::" + year + " " + empCode);
		try{
			map.clear();
			ArrayList<AnnualVacationMgtTO> annualVacationMgtList = attdServiceFacade.findAnnualVactionMgtListByYear(year, empCode);
			map.put("annualVacationMgtList",annualVacationMgtList);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
		}catch (Exception ioe){
			map.clear();
			map.put("errorMsg", ioe.getMessage());
		}
		System.out.println("여기는 값을 받습니다. +" + map);
		return map;
	}

	// 연차신청삭제구현예정



	// ��ȸ
	@GetMapping(value = "/exeAnnualVacationProcess")
	public ModelMap executeAnnualVacationProcess(@RequestParam String applyYearMonth) {
		try {
			modelMap.put("applyYearMonth", applyYearMonth);
			attdServiceFacade.executeAnnualVacationProcess(modelMap);
			modelMap.put("annualVacationMgtResult", modelMap.get("result"));
		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		}
		return modelMap;

	}

	@PostMapping(value = "/modifyAnnualVacationMgtList")
	public ModelMap modifyAnnualVacationMgtList(@RequestBody ArrayList<AnnualVacationMgtTO> annualVacationMgtList) {
		System.out.println("annualVacationMgtList::" + annualVacationMgtList);
		try {
			modelMap.put("annualVacationMgtList", annualVacationMgtList);
			attdServiceFacade.modifyAnnualVacationMgtList(modelMap);
		} catch (Exception ioe) {
			modelMap.clear();
			modelMap.put("errorMsg", ioe.getMessage());
		}
		return modelMap;
	}

	/*
	 *
	 * @RequestMapping(value="/attendance/findAnnualVacationMgtList",method=
	 * RequestMethod.GET) public void modifyAnnualVacationMgtList(@RequestParam
	 * String applyYearMonth){
	 *
	 * System.out.println("applyYearMonth�� "+applyYearMonth); //
	 * attdServiceFacade.modifyAnnualVacationMgtList(applyYearMonth); }
	 *
	 *
	 * @RequestMapping("/attendance/cancelAnnualVacationMgtList.do") public void
	 * cancelAnnualVacationMgtList(@RequestAttribute("inData") PlatformData inData,
	 *
	 * @RequestAttribute("outData") PlatformData outData) throws Exception {
	 *
	 * ArrayList<annualVacationMgtTO> annualVacationMgtList =
	 * (ArrayList<annualVacationMgtTO>)datasetBeanMapper.datasetToBeans(inData,
	 * annualVacationMgtTO.class);
	 * attdServiceFacade.cancelAnnualVacationMgtList(annualVacationMgtList); }
	 */
}
