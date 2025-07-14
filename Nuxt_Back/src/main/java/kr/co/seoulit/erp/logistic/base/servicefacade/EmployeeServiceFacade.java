package kr.co.seoulit.erp.logistic.base.servicefacade;

import org.springframework.ui.ModelMap;
import kr.co.seoulit.erp.hr.company.to.CompanyTO;

import java.util.ArrayList;
import java.util.Map;

public interface EmployeeServiceFacade {
    public ModelMap getEmployeeInfo();
    public void getEmployeeDelete(String empCode);
    public ArrayList<CompanyTO> getCompanyInfo();
    public void getEmployeeAdd(Map<String, String> clientdata);
    public void getEmployeeEdit(Map<String, String> updatedEmployee);
}
