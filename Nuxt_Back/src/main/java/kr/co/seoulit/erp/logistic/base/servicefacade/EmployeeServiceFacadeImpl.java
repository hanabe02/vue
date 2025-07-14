package kr.co.seoulit.erp.logistic.base.servicefacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import kr.co.seoulit.erp.logistic.base.dao.EmployeeDAO;
import kr.co.seoulit.erp.hr.affair.to.EmployeeBasicTO;

import kr.co.seoulit.erp.hr.company.to.CompanyTO;

import java.util.ArrayList;
import java.util.List;  // 수정: ArrayList에서 List로 변경
import java.util.Map;

@Service
public class EmployeeServiceFacadeImpl implements EmployeeServiceFacade {

    private ModelMap modelMap = new ModelMap();

    @Autowired
    private EmployeeDAO employeeDAO;

    public ModelMap getEmployeeInfo() {
        String companyCode = "COM-01";
        try {
            // 수정: ArrayList에서 List로 변경
            List<EmployeeBasicTO> employeeList = employeeDAO.getEmployee(companyCode);
            modelMap.put("employeeList", employeeList);
        } catch (Exception e) {
            e.printStackTrace(); // 예외 처리 추가
        }
        return modelMap;
    }
    public void getEmployeeDelete(String empCode){
        employeeDAO.getEmployeeDelete(empCode);
    }

    public ArrayList<CompanyTO> getCompanyInfo(){
        ArrayList<CompanyTO> employee = employeeDAO.getCompanyInfo();
        return employee;
    }

    public void getEmployeeAdd(Map<String, String> clientdata){
        System.out.println("디버깅: getEmployeeAdd 실행됨");
        EmployeeBasicTO employee = new EmployeeBasicTO();
        employee.setCompanyCode(clientdata.get("companyCode"));
        employee.setEmpCode(clientdata.get("empCode"));
        employee.setEmpName(clientdata.get("empName"));
        employee.setEmpEngName(clientdata.get("empEngName"));
        employee.setSocialSecurityNumber(clientdata.get("socialSecurityNumber"));
        employee.setHireDate(clientdata.get("hireDate"));
        employee.setRetirementDate(clientdata.get("retirementDate"));
        employee.setUserOrNot(clientdata.get("userOrNot"));
        employee.setBirthDate(clientdata.get("birthDate"));
        employee.setGender(clientdata.get("gender"));

        System.out.println("디버깅: employee 객체 = " + employee); // employee 객체 값 확인
        employeeDAO.getEmployeeAdd(employee);
    }

    public void getEmployeeEdit(Map<String, String> updatedEmployee){
        System.out.println("디버깅: getEmployeeAdd 실행됨");
        EmployeeBasicTO employee = new EmployeeBasicTO();
        employee.setCompanyCode(updatedEmployee.get("companyCode"));
        employee.setEmpCode(updatedEmployee.get("empCode"));
        employee.setEmpName(updatedEmployee.get("empName"));
        employee.setEmpEngName(updatedEmployee.get("empEngName"));
        employee.setSocialSecurityNumber(updatedEmployee.get("socialSecurityNumber"));
        employee.setHireDate(updatedEmployee.get("hireDate"));
        employee.setRetirementDate(updatedEmployee.get("retirementDate"));
        employee.setUserOrNot(updatedEmployee.get("userOrNot"));
        employee.setBirthDate(updatedEmployee.get("birthDate"));
        employee.setGender(updatedEmployee.get("gender"));
        employeeDAO.getEmployeeEdit(employee);
    }
}
