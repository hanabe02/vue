package kr.co.seoulit.erp.hr.salary.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.seoulit.erp.hr.salary.to.BaseDeductionTO;
import kr.co.seoulit.erp.hr.salary.to.BaseExtSalTO;
import kr.co.seoulit.erp.hr.salary.to.BaseSalaryTO;
import kr.co.seoulit.erp.hr.salary.to.MonthDeductionTO;
import kr.co.seoulit.erp.hr.salary.to.MonthSalaryTO;
import kr.co.seoulit.erp.hr.salary.to.SeveranceTO;
import kr.co.seoulit.erp.hr.salary.to.SocialInsureTO;

public interface SalaryApplicationService {
	
	public ArrayList<BaseSalaryTO> findBaseSalaryList();
	
//	public void modifyBaseSalaryList(ArrayList<BaseSalaryTO> baseSalaryList);
	public void modifyBaseSalary(BaseSalaryTO baseSalaryTo);
	
	public void registerBaseSalary(BaseSalaryTO baseSalaryTo);
	
	public void removeBaseSalary(String deptCode, String positionCode);

	public ArrayList<BaseDeductionTO> findBaseDeductionList();
	public void batchBaseDeductionProcess(ArrayList<BaseDeductionTO> baseDeductionList);

	public ArrayList<BaseExtSalTO> findBaseExtSalList();
	public void modifyBaseExtSal(BaseExtSalTO baseExtSalTo);
	public void registerBaseExtSal(BaseExtSalTO baseExtSalTo);
	public void removeBaseExtSal(String extSalCode);
	
	public HashMap<String, Object> findMonthSalary(String applyYearMonth, String empCode);
	public ArrayList<MonthSalaryTO> findYearSalary(String applyYear, String empCode);
	public HashMap<String, Object> findCloseSalary(String applyYearMonth, String deptCode);
	public void closeSalary(MonthSalaryTO empCodeList);
	public ArrayList<BaseSalaryTO> BaseSalaryList(String selectDeptTitle);
	
//	public ArrayList<SeveranceTO> findSeverancePayList(String empName);
	public ArrayList<SeveranceTO> findSeverancePay(String firstDay, String today);
//	public void registerSeverancePay(SeveranceTO severance);
	public HashMap<String, Object> registerSeverancePay(SeveranceTO severance);
	public void removeSeverance(String empCode);
	
	public ArrayList<SocialInsureTO> findSocialInsureList(String searchYear);
	public void registerSocialInsure(SocialInsureTO socialInsureTo);
	
	
	public ArrayList<MonthSalaryTO> initSalaryList(String applyYearMonth,String empCode);
	public HashMap<String,Object> findSalaryList(String applyYearMonth, String empCode, String deptCode);
	public void modifyMonthSalary(MonthSalaryTO monthSalary);
	public void removeMonthSalary(String applyYearMonth, String empCode);
	
	public  ArrayList<MonthSalaryTO> findSalaryList(String applyYearMonth, String empCode);
	
	public void removeMonthExtSal(String applyYearMonth,String empCode);
	public void removeMonthDeduction(String applyYearMonth,String empCode);
	

	public ArrayList<MonthDeductionTO> findMonthDeduction(String applyYearMonth, String empCode);


}