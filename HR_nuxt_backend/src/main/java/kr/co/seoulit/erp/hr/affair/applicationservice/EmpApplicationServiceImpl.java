package kr.co.seoulit.erp.hr.affair.applicationservice;

import kr.co.seoulit.erp.hr.affair.dao.*;
import kr.co.seoulit.erp.hr.affair.to.*;
import kr.co.seoulit.erp.hr.base.applicationservice.BaseApplicationService;
import kr.co.seoulit.erp.hr.base.dao.DeptDAO;
import kr.co.seoulit.erp.hr.base.to.DeptTO;
import kr.co.seoulit.erp.hr.company.dao.PositionDAO;
import kr.co.seoulit.erp.hr.company.to.PositionTO;
import kr.co.seoulit.erp.hr.salary.applicationservice.SalaryApplicationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Log4j2
@Component
public class EmpApplicationServiceImpl implements EmpApplicationService {

	@Autowired
	SalaryApplicationService salaryApplicationService;
//	@Autowired
//	BaseApplicationService baseApplicationService;
	@Autowired
	private EmpDAO empDAO;
	@Autowired
	private PositionDAO positionDAO;
	@Autowired
	private DeptDAO deptDAO;
	@Autowired
	private EmployeeBasicDAO employeeBasicDAO;
	@Autowired
	private EmpAssignDAO empAssignDAO;
	@Autowired
	private BestEmpDAO bestEmpDAO;
	@Autowired
	private EmployeeDetailDAO employeeDetailDAO;


	/////////////////////////75기 인사  직원/////////////////////////
	// 직원 리스트 조회
	@Override
	public ArrayList<EmpTO> findEmpList() {
		System.out.println("===============직원 전체 조회~~==============");
		// TODO Auto-generated method stub
		return empDAO.selectEmpAllList();
	}
	//선택 직원 조회
	@Override
	public ArrayList<EmployeeDetailTO> findEmpDetail(String empCode){
		return empDAO.selectEmployeeDetail(empCode);
	}
	// 직원 정보 수정
	@Override
	public void empInfoUpdate(EmpUpdateTO empBean) {
		System.out.println("=============================EmpApplicationServiceFacadeImpl===================================");
		System.out.println("EmpApplicationServiceFacade" + empBean);
		empDAO.updateEmployee(empBean);
	}
	// 직원 정보 삭제
	@Override
	public void deleteEmp(String empBean) {
		System.out.println("=============================EmpApplicationServiceFacadeImpl===================================");
		System.out.println("deleteEmp"+empBean);
		empDAO.deleteEmp(empBean);
	}
	// 직원 등록
	@Override
	public void registEmployee(RegiEmpTO regiEmp) {
		System.out.println("프로시저 호출 시작 " + regiEmp);
		empDAO.registEmployee(regiEmp);
		System.out.println("프로시저 호출 끝 ");
	}
	/////////////////////////75기 인사  직원 끝/////////////////////////


	/////////////////////////75기 인사 발령/////////////////////////
	// 발령 리스트 조회
	@Override
	public ArrayList<AssignEmpTO> getAppointList() {
		// TODO Auto-generated method stub
		return empAssignDAO.selectAppointList();
	}
	// 발령 승인 반려 내역 조회
	public ArrayList<AssignEmpTO> findAppointLog(){
		return empAssignDAO.selectAppointLog();
	}
	//발령 등록
	@Override
	public void insertAssign(RegiAppTO appRegi) {
		// TODO Auto-generated method stub
		System.out.println("===============발령등록~~==============");
		System.out.println(appRegi);
		empAssignDAO.insertAssign(appRegi);
	}
	// 발령 승인 내역 갱신
	@Override
	public void updateAppoint(AppointTO appResult){
		empAssignDAO.updateAppoint(appResult);
	}
	// 발령 삭제
	public void appointdelete(HashMap<String, String> appResult)	{
		empAssignDAO.deleteAppoint(appResult);
	}
	/////////////////////////75기 인사 발령 끝/////////////////////////

	@Override
	public void updateEmpInfo(EmpUpdateTO emp){
		empDAO.updateEmp(emp);
	}


	@Override
	public ArrayList<EmpTO> findEmployeeListByDept(String deptName) {
		ArrayList<EmpTO> empList = null;
		// System.out.println("어플리케이션 호출");
		// System.out.println("짤린이름"+deptName.substring(deptName.length()-5));
		if (deptName.equals("전체부서")) {
			System.out.println("1");
			empList = empDAO.selectEmpList();
		} else if (deptName.substring(0, 3).equals("DPT")) {
			System.out.println("2");
			empList = empDAO.selectEmpListD(deptName);
		} else {
			System.out.println("3");
			empList = empDAO.selectEmpListN(deptName);
		}
		return empList;
	}
	@Override
	public ArrayList<EmpTO> findEmpMemberListByDept(String deptName) {
		ArrayList<EmpTO> empList = null;

		empList = empDAO.selectEmpMemberListD(deptName);

		return empList;
	}
	@Override
	public ArrayList<DeptTO> findDeptList() {
		ArrayList<DeptTO> deptList = deptDAO.selectDeptList();
		log.debug("dept 내역" , deptList);
		return deptList;
	}
	@Override
	public ArrayList<PositionTO> selectPositionList() {

		return positionDAO.selectPositionList();
	}
	@Override
	public ArrayList<EmpTO> findAllEmpInfo(HashMap<String, String> map) {

		return empDAO.selectEmployee(map);
	}
	@Override
	public ArrayList<EmployeeBasicTO> getEmpBasicInfo(String empCode) {

		return employeeBasicDAO.getEmpBasicInfo(empCode);
	}

	@Override
	public ArrayList<BestEmpTO> getBestEmp() {

		System.out.println("applicationService");
		return bestEmpDAO.getBestEmp();
	}



	/////////////////////////75기 인사 보너스 /////////////////////////
	@Override
	public EmpTO getBonusEmp(String empCode) {
		return empDAO.getBonusEmp(empCode);
	}

	@Override
	public List<DeptTO> getEmpListByDept(String deptCode) {
		return empDAO.getEmpListByDept(deptCode);
	}


	public void updateDeptCode(HashMap<String, String> map){}



	////////////////////기초정보 부서////////////////////////

	@Override
	public ArrayList<EmpTO> findDeptEmpList(String deptCode) {
		// TODO Auto-generated method stub
		return empDAO.selectDeptEmpList(deptCode);
	}

	@Override
	public ArrayList<DeptTO> findDeptManageList() {
		// TODO Auto-generated method stub
		return deptDAO.selectDeptManageList();
	}


}
