package kr.co.seoulit.erp.hr.base.servicefacade;

import java.util.ArrayList;
import java.util.List;

import kr.co.seoulit.erp.hr.base.to.*;
import kr.co.seoulit.erp.hr.company.to.CompanyTO;
import kr.co.seoulit.erp.hr.company.to.WorkplaceTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import kr.co.seoulit.erp.hr.base.applicationservice.BaseApplicationService;
import kr.co.seoulit.erp.hr.company.to.PositionTO;
import kr.co.seoulit.erp.hr.affair.applicationservice.EmpApplicationService;
import kr.co.seoulit.erp.hr.salary.to.BaseSalaryTO;

@Service
public class HrBaseServiceFacadeImpl implements HrBaseServiceFacade {

	@Autowired
	private BaseApplicationService baseApplicationService ;
	@Autowired
	private EmpApplicationService empApplicationService ;


	@Override
	public ArrayList<HrDetailCodeTO> findDetailCodeList(String codetype) {
		// TODO Auto-generated method stub
		ArrayList<HrDetailCodeTO> detailCodeto = baseApplicationService.findDetailCodeList(codetype);
		return detailCodeto;
	}

	@Override
	public ArrayList<HrDetailCodeTO> findDetailCodeListRest(String code1, String code2, String code3) {
		// TODO Auto-generated method stub
		ArrayList<HrDetailCodeTO> detailCodeto = baseApplicationService.findDetailCodeListRest(code1, code2, code3);
		return detailCodeto;
	}
	//--------------------------------------- 휴일 관리 ---------------------------------------
	@Override
	public ArrayList<HolidayTO> findHolidayList(String applyDay) {
		// TODO Auto-generated method stub
			return baseApplicationService.findHolidayList(applyDay);
	}

	@Override
	public void addHoliday(HolidayTO holiday) {
		// TODO Auto-generated method stub
		baseApplicationService.addHoliday(holiday);
	}

	@Override
	public void modifyHoliday(HolidayTO holiday) {
		// TObaseApplicationService
		baseApplicationService.modifyHoliday(holiday);
	}

	@Override
	public void removeHoliday(String applyDay) {
		// TODO Auto-generated method stub
		baseApplicationService.removeHoliday(applyDay);
	}
	

	@Override
	public void addPublicHoliday(HolidayTO holiday) {
		// TODO Auto-generated method stub
		baseApplicationService.addPublicHoliday(holiday);
	}
	

	//-----------------------------------------------------------------------
	@Override
	public String findWeekDayCount(String startDate, String endDate) {
		// TODO Auto-generated method stub
		try {
			String weekdayCount = baseApplicationService.findWeekDayCount(startDate, endDate);
			return weekdayCount;
		} catch (DataAccessException e) {

			throw e;
		} finally {

		}
	}

	@Override
	public void registEmpImg(String empCode, String imgExtend) {
		// TODO Auto-generated method stub
		try {
			baseApplicationService.registEmpImg(empCode, imgExtend);

		} catch (DataAccessException e) {

				throw e;
		} finally {

		}
	}

	@Override
	public void batchDeptProcess(ArrayList<DeptTO> deptto) {
		// TODO Auto-generated method stub
		try {
			baseApplicationService.batchDeptProcess(deptto);

		} catch (DataAccessException e) {

			throw e;
		} finally {

		}
	}

	@Override
	public void registerDept(DeptTO dept) {
		// TODO Auto-generated method stub
		baseApplicationService.registerDept(dept);
	}
	
	@Override
	public void modifyDept(DeptTO dept) {
		// TODO Auto-generated method stub
		baseApplicationService.modifyDept(dept);
	}

	@Override
	public void removeDept(String deptCode, String workplaceCode) {
		// TODO Auto-generated method stub
		baseApplicationService.removeDept(deptCode, workplaceCode);
	}

	@Override
	public ArrayList<PositionTO> findPositionList() {
		// TODO Auto-generated method stub
		ArrayList<PositionTO> positionList = empApplicationService.selectPositionList();

		return positionList;
	}

	@Override
	public void modifyPosition(ArrayList<BaseSalaryTO> positionList) {
		// TODO Auto-generated method stub
		try {
			baseApplicationService.modifyPosition(positionList);
		} catch (DataAccessException e) {
			throw e;
		} finally {

		}
	}

	@Override
	public ArrayList<HrCodeTO> findCodeList() {
		// TODO Auto-generated method stub
		ArrayList<HrCodeTO> codeto = baseApplicationService.findCodeList();
		return codeto;
	}

	@Override
	public void registCodeList(List<HolidayTO> holyday) {
		// TODO Auto-generated method stub
		try {
			baseApplicationService.registCodeList(holyday);


		} catch (DataAccessException e) {

			throw e;
		} finally {

		}
	}




	@Override
	   public ReportTO viewReport(String empCode) {
	       ReportTO to=null;
	       try {
	            to=baseApplicationService.viewReport(empCode);
	         //   dataSourceTransactionManager.commitTransaction();
	         } catch (DataAccessException e) {
	           throw e;
	         } finally {

	         }
	          return to;
	   }

	@Override
	public ArrayList<BaseWorkTimeTO> searchBaseWorkTimeList() {

		return baseApplicationService.searchBaseWorkTimeList();
	}

	@Override
	public void deleteBaseWorkTimeList(List<BaseWorkTimeTO> list) {

		baseApplicationService.deleteBaseWorkTime(list);

	}

	@Override
	public void batchBaseWorkTimeList(List<BaseWorkTimeTO> list) {
		baseApplicationService.batchBaseWorkTime(list);

	}

	@Override
	public ArrayList<YearMonthTO> findYearList() {
		// TODO Auto-generated method stub
		ArrayList<YearMonthTO> yearList = baseApplicationService.selectYearList();
		return yearList;
	}

	@Override
	public ArrayList<CompanyTO> selectCompanyList() {
		// TODO Auto-generated method stub
		ArrayList<CompanyTO> companyList = baseApplicationService.selectCompanyList();
		return companyList;
	}

	@Override
	public ArrayList<WorkplaceTO> selectWorkplaceList() {
		// TODO Auto-generated method stub
		ArrayList<WorkplaceTO> workplaceList = baseApplicationService.selectWorkplaceList();
		return workplaceList;
	}

	@Override
	public ArrayList<ProjectTO> findProjectList() {
		ArrayList<ProjectTO> projectList = baseApplicationService.selectProjectList();
		return projectList;
	}

    @Override
    public void addProject(ProjectTO projectTO) {
        baseApplicationService.insertProject(projectTO);
    }

    @Override
    public void modifyProject(ProjectTO projectTO) {
        baseApplicationService.modifyProject(projectTO);
    }

	@Override
	public void removeProject(String projectID) {
		baseApplicationService.removeProject(projectID);
	}

	public ArrayList<YearMonthTO> findMonthList() {
		// TODO Auto-generated method stub
		ArrayList<YearMonthTO> monthList = baseApplicationService.selectMonthList();
		return monthList;
	}

}
