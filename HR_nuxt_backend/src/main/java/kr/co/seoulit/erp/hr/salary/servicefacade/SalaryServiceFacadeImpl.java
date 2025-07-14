package kr.co.seoulit.erp.hr.salary.servicefacade;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.seoulit.erp.hr.salary.dao.MonthSalaryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.seoulit.erp.hr.salary.applicationservice.BonusApplicationService;
import kr.co.seoulit.erp.hr.salary.applicationservice.SalaryApplicationService;
import kr.co.seoulit.erp.hr.salary.to.BaseDeductionTO;
import kr.co.seoulit.erp.hr.salary.to.BaseExtSalTO;
import kr.co.seoulit.erp.hr.salary.to.BaseSalaryTO;
import kr.co.seoulit.erp.hr.salary.to.BonusTO;
import kr.co.seoulit.erp.hr.salary.to.MonthDeductionTO;
import kr.co.seoulit.erp.hr.salary.to.MonthSalaryTO;
import kr.co.seoulit.erp.hr.salary.to.SeveranceTO;
import kr.co.seoulit.erp.hr.salary.to.SocialInsureTO;

@Service

public class SalaryServiceFacadeImpl implements SalaryServiceFacade{
	@Autowired
	private SalaryApplicationService salaryApplicationService;
	@Autowired
	private BonusApplicationService bonusApplicationService; 
	@Autowired
	private MonthSalaryDAO monthSalaryDAO;
	@Override
	public ArrayList<BaseSalaryTO> findBaseSalaryList() {		
			ArrayList<BaseSalaryTO> baseSalaryList=salaryApplicationService.findBaseSalaryList();
			return baseSalaryList;	
	}
	
//	@Override
//	public void modifyBaseSalaryList(ArrayList<BaseSalaryTO> baseSalaryList) {
//			salaryApplicationService.modifyBaseSalaryList(baseSalaryList);					
//	}
	
	@Override
	public ArrayList<BaseDeductionTO> findBaseDeductionList() {
			ArrayList<BaseDeductionTO> baseDeductionList=salaryApplicationService.findBaseDeductionList();
			return baseDeductionList;
	}
	
	@Override
	public void batchBaseDeductionProcess(ArrayList<BaseDeductionTO> baseDeductionList) {
			salaryApplicationService.batchBaseDeductionProcess(baseDeductionList);
	}
	
	@Override
	public ArrayList<BaseExtSalTO> findBaseExtSalList() {
			ArrayList<BaseExtSalTO> baseExtSalList=salaryApplicationService.findBaseExtSalList();
			return baseExtSalList;
	}	
	@Override
	public void modifyBaseExtSal(BaseExtSalTO baseExtSalTo) {
			salaryApplicationService.modifyBaseExtSal(baseExtSalTo);
	}
	
	@Override
	public void registerBaseExtSal(BaseExtSalTO baseExtSalTo) {
		// TODO Auto-generated method stub
		salaryApplicationService.registerBaseExtSal(baseExtSalTo);
	}


	@Override
	public void removeBaseExtSal(String extSalCode) {
		// TODO Auto-generated method stub
		salaryApplicationService.removeBaseExtSal(extSalCode);
	}

	@Override
	public HashMap<String, Object> findMonthSalary(String ApplyYearMonth, String empCode) {
		return salaryApplicationService.findMonthSalary(ApplyYearMonth, empCode);
				
	}

	@Override
	public ArrayList<MonthSalaryTO> findYearSalary(String applyYear, String empCode) {
			ArrayList<MonthSalaryTO> monthSalary=salaryApplicationService.findYearSalary(applyYear, empCode);
			return monthSalary;
	}

	@Override
	public HashMap<String, Object> CloseSalary(String applyYearMonth, String deptCode) {
		// TODO Auto-generated method stub
		return salaryApplicationService.findCloseSalary(applyYearMonth,deptCode);
	}

	@Override
	public void closeMonthSalary(MonthSalaryTO empCodeList) {  //占쎌뜞疫뀀맩肉� 筌띾뜃而�
		salaryApplicationService.closeSalary(empCodeList);
		
	}

	@Override
	public BonusTO findterBonus(BonusTO bonus) {
		// TODO Auto-generated method stub
		return bonusApplicationService.findterBonus(bonus);
	}

	@Override
	public void registerBonus(BonusTO bonus) {
		// TODO Auto-generated method stub
		bonusApplicationService.registerBonus(bonus);
	}

	@Override
	public void removeAllBonus() {
		// TODO Auto-generated method stub
		bonusApplicationService.removeAllBonus();
	}

	@Override
	public void removeBonus(String empCode, String applyYearMonth, String bonusCode) {
		// TODO Auto-generated method stub

		bonusApplicationService.removeBonus(empCode, applyYearMonth, bonusCode);
	}

	@Override
	public ArrayList<BonusTO> findBonusList(String empCode, String applyYearMonth, String detailCodeName, String finalizeStatus) {
		// TODO Auto-generated method stub
		return bonusApplicationService.findBonusList(empCode, applyYearMonth, detailCodeName,finalizeStatus);
	}
	
	@Override
	public ArrayList<BaseSalaryTO> BaseSalaryList(String selectDeptTitle) {
			ArrayList<BaseSalaryTO> baseExtSalList=salaryApplicationService.BaseSalaryList(selectDeptTitle);
			return baseExtSalList;
	}	
	

	
//	@Override
//	public ArrayList<SeveranceTO> findSeverancePay(String empName) {		
//			ArrayList<SeveranceTO> severancePayList=salaryApplicationService.findSeverancePayList(empName);
//			return severancePayList;	
//	}

	@Override
	public ArrayList<SeveranceTO> findSeverancePay(String firstDay, String today) {
		// TODO Auto-generated method stub
		return salaryApplicationService.findSeverancePay(firstDay,today);
	}

//	@Override
//	public void registerSeverancePay(SeveranceTO severance) {
//		// TODO Auto-generated method stub
//		salaryApplicationService.registerSeverancePay(severance);
//	}

	@Override
	public HashMap<String, Object> registerSeverancePay(SeveranceTO severance) {
		// TODO Auto-generated method stub
		return salaryApplicationService.registerSeverancePay(severance);
	}

	@Override
	public void removeSeverance(String empCode) {
		// TODO Auto-generated method stub
		salaryApplicationService.removeSeverance(empCode);
	}
	

	@Override
	public void modifyBaseSalary(BaseSalaryTO baseSalaryTo) {
		salaryApplicationService.modifyBaseSalary(baseSalaryTo);	
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerBaseSalary(BaseSalaryTO baseSalaryTo) {
		salaryApplicationService.registerBaseSalary(baseSalaryTo);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeBaseSalary(String deptCode, String positionCode) {
		// TODO Auto-generated method stub
		salaryApplicationService.removeBaseSalary(deptCode, positionCode);
	}

	
	@Override
	public ArrayList<SocialInsureTO> findSocialInsureList(String searchYear) {
			ArrayList<SocialInsureTO> baseInsureList=salaryApplicationService.findSocialInsureList(searchYear);
			return baseInsureList;
	
	}
	@Override
	public void registerSocialInsure(SocialInsureTO socialInsureTo) {
		// TODO Auto-generated method stub
		salaryApplicationService.registerSocialInsure(socialInsureTo);
	}

	@Override
	public ArrayList<MonthSalaryTO> initSalaryList(String applyYearMonth, String empCode) {
		// TODO Auto-generated method stub
		return salaryApplicationService.initSalaryList(applyYearMonth,empCode);
	}

	@Override
	public HashMap<String, Object> findSalaryList(String applyYearMonth, String empCode, String deptCode) {

		HashMap<String, Object> param = new HashMap<>();
		param.put("empCode", empCode);
		param.put("applyYearMonth", applyYearMonth);
		param.put("deptCode", deptCode);

		// OUT 파라미터 준비
		param.put("RESULT", null);
		param.put("ERROR_CODE", null);
		param.put("ERROR_MSG", null);

		// 프로시저 호출
		monthSalaryDAO.selectMonthSalary(param);

		return param;
	}
	
	@Override
	public void modifyMonthSalary(MonthSalaryTO monthSalary) {
			salaryApplicationService.modifyMonthSalary(monthSalary);
			String empCode = monthSalary.getEmpCode();
			String applyYearMonth = monthSalary.getApplyYearMonth();
			bonusApplicationService.approveBonus(empCode,applyYearMonth);
	}
	
	@Override
	public void removeMonthSalary(String applyYearMonth, String empCode) {
		// TODO Auto-generated method stub
		salaryApplicationService.removeMonthSalary(applyYearMonth,empCode);
		salaryApplicationService.removeMonthExtSal(applyYearMonth,empCode);
		salaryApplicationService.removeMonthDeduction(applyYearMonth,empCode);
	}

	@Override
	public ArrayList<MonthSalaryTO> findSalary(String applyYearMonth, String empCode) {
		// TODO Auto-generated method stub
		return salaryApplicationService.findSalaryList(applyYearMonth,empCode);
	}
	

	@Override
	public ArrayList<MonthDeductionTO> findMonthDeduction(String applyYearMonth, String empCode) {
		// TODO Auto-generated method stub
		return salaryApplicationService.findMonthDeduction(applyYearMonth, empCode);
	}




	
}
