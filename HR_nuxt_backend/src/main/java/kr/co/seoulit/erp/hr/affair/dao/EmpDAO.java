package kr.co.seoulit.erp.hr.affair.dao;

import kr.co.seoulit.erp.hr.affair.to.*;
import kr.co.seoulit.erp.hr.base.to.DeptTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface EmpDAO {

	/////////////////////////75기 인사  직원/////////////////////////
	// 직원 리스트 조회
	public ArrayList<EmpTO> selectEmpAllList();
	// 선택 직원 조회
	ArrayList<EmployeeDetailTO> selectEmployeeDetail(String empCode);
	// 사원 정보 수정
	public void updateEmployee(EmpUpdateTO empBean);
	// 사원 정보 삭제
	public void deleteEmp(String emp);
	/////////////////////////75기 인사  직원 끝/////////////////////////

	/////////////////////////75기 인사  부서/////////////////////////
	public ArrayList<EmpTO> selectDeptEmpList(String deptCode);

	/////////////////////////75기 인사  부서 끝/////////////////////////

	//updated 2022 resources/sqlMapper/hr/affair/sqlMapEmpDetailed.xml
	void updateEmp(EmpUpdateTO emp);


	public ArrayList<EmpTO> selectEmpList();

	public ArrayList<EmpTO> selectEmpListD(String dept);

	public ArrayList<EmpTO> selectEmpMemberListD(String dept);

	public ArrayList<EmpTO> selectEmpListN(String name);

	public void registEmployee(RegiEmpTO regiEmp);






	public ArrayList<EmpTO> selectEmployee(HashMap<String, String> map);




	public EmpTO getBonusEmp(String empCode);

    public List<DeptTO> getEmpListByDept(String deptCode);
}
