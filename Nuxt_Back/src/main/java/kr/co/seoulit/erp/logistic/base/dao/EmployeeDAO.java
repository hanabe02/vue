package kr.co.seoulit.erp.logistic.base.dao;
import kr.co.seoulit.erp.hr.affair.to.EmployeeBasicTO;
import kr.co.seoulit.erp.hr.company.to.CompanyTO;
import kr.co.seoulit.erp.logistic.sales.to.OutputTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface EmployeeDAO {
    List<EmployeeBasicTO> getEmployee(String companyCode);
    void getEmployeeDelete(String empCode);
    ArrayList<CompanyTO> getCompanyInfo();
    void getEmployeeAdd(EmployeeBasicTO employee);
    void getEmployeeEdit(EmployeeBasicTO employee);
}
