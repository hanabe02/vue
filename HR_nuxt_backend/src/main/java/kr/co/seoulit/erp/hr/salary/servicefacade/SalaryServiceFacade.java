package kr.co.seoulit.erp.hr.salary.servicefacade;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestBody;

import kr.co.seoulit.erp.hr.salary.to.BaseDeductionTO;
import kr.co.seoulit.erp.hr.salary.to.BaseExtSalTO;
import kr.co.seoulit.erp.hr.salary.to.BaseSalaryTO;
import kr.co.seoulit.erp.hr.salary.to.BonusTO;
import kr.co.seoulit.erp.hr.salary.to.MonthSalaryTO;
import kr.co.seoulit.erp.hr.salary.to.SeveranceTO;
import kr.co.seoulit.erp.hr.salary.to.SocialInsureTO;
import kr.co.seoulit.erp.hr.salary.to.MonthDeductionTO;

public interface SalaryServiceFacade {
	//BaseSalary
	public ArrayList<BaseSalaryTO> findBaseSalaryList();
//	public void modifyBaseSalaryList(ArrayList<BaseSalaryTO> baseSalaryList);
	public void modifyBaseSalary(BaseSalaryTO baseSalaryTo);
	public void registerBaseSalary(BaseSalaryTO baseSalaryTo);	
	public void removeBaseSalary(String deptCode, String positionCode);

	//BaseExtSal
	public ArrayList<BaseExtSalTO> findBaseExtSalList();
	public void modifyBaseExtSal(BaseExtSalTO baseExtSalTo);
	public void registerBaseExtSal(BaseExtSalTO baseExtSalTo);
	public void removeBaseExtSal(String extSalCode);

	public ArrayList<BaseDeductionTO> findBaseDeductionList();
	public void batchBaseDeductionProcess(ArrayList<BaseDeductionTO> baseDeductionList);
	public HashMap<String, Object> findMonthSalary(String ApplyYearMonth, String empCode);
	public ArrayList<MonthSalaryTO> findYearSalary(String applyYear, String empCode);
	
	public HashMap<String, Object> CloseSalary(String applyYearMonth, String deptCode);
	public void closeMonthSalary(MonthSalaryTO empCodeList);
	
	public BonusTO findterBonus(BonusTO bonus);
	public ArrayList<BonusTO> findBonusList(String empCode, String applyYearMonth, String detailCodeName, String finalizeStatus);
	public void registerBonus(BonusTO bonus);
	public void removeAllBonus();
	public void removeBonus(String empCode,String applyYearMonth,String bonusCode);
	public ArrayList<BaseSalaryTO> BaseSalaryList(String selectDeptTitle);
	
	
	//public ArrayList<SeveranceTO> findSeverancePay(String empName);
	public ArrayList<SeveranceTO> findSeverancePay(String firstDay, String today);
	//public void registerSeverancePay(SeveranceTO severance);
	public HashMap<String, Object> registerSeverancePay(SeveranceTO severance);
	public void removeSeverance(String empCode);
	
	public ArrayList<SocialInsureTO> findSocialInsureList(String searchYear);
	public void registerSocialInsure(SocialInsureTO socialInsureTo);
	
	//MonthSalary
	public ArrayList<MonthSalaryTO> initSalaryList(String applyYearMonth, String empCode);
	public HashMap<String, Object> findSalaryList(String applyYearMonth, String empCode, String deptCode);
	public void modifyMonthSalary(MonthSalaryTO monthSalary);
	public void removeMonthSalary(String applyYearMonth, String empCode);
	
	public ArrayList<MonthSalaryTO> findSalary(String applyYearMonth, String empCode);
	
	
	public ArrayList<MonthDeductionTO> findMonthDeduction(String applyYearMonth, String empCode);


}
