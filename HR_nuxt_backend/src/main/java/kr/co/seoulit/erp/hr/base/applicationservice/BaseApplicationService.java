package kr.co.seoulit.erp.hr.base.applicationservice;

import java.util.ArrayList;
import java.util.List;

import kr.co.seoulit.erp.hr.base.to.*;
import kr.co.seoulit.erp.hr.affair.to.EmpTO;
import kr.co.seoulit.erp.hr.company.to.CompanyTO;
import kr.co.seoulit.erp.hr.company.to.WorkplaceTO;
import kr.co.seoulit.erp.hr.salary.to.BaseSalaryTO;


public interface BaseApplicationService {

   public ArrayList<HrDetailCodeTO> findDetailCodeList(String codetype);
   public ArrayList<HrDetailCodeTO> findDetailCodeListRest(String code1, String code2, String code3);

   public ArrayList<HolidayTO> findHolidayList(String applyDay);
   public void addHoliday(HolidayTO holiday);
   public void modifyHoliday(HolidayTO holiday);
   public void removeHoliday(String applyDay);
   
   public void addPublicHoliday(HolidayTO holiday);
   
   public String findWeekDayCount(String startDate, String endDate);

   public void registEmpImg(String empCode, String imgExtend);

   public void batchDeptProcess(ArrayList<DeptTO> deptto);
   
   public void registerDept(DeptTO dept);
   public void modifyDept(DeptTO dept);
   public void removeDept(String deptCode, String workplaceCode);
   
   public void modifyPosition(ArrayList<BaseSalaryTO> positionList);

   public void registEmpCode(EmpTO emp);
   public void deleteEmpCode(EmpTO emp);

   public ArrayList<HrCodeTO> findCodeList();

   public void registCodeList(List<HolidayTO> holyday);

   public ReportTO viewReport(String empCode);
	public ArrayList<BaseWorkTimeTO> searchBaseWorkTimeList();
	public void deleteBaseWorkTime(List<BaseWorkTimeTO> list);
	public void batchBaseWorkTime(List<BaseWorkTimeTO> list);

	public ArrayList<YearMonthTO> selectYearList();
	public ArrayList<YearMonthTO> selectMonthList();
    public ArrayList<CompanyTO> selectCompanyList();
    public ArrayList<WorkplaceTO> selectWorkplaceList();
    public ArrayList<ProjectTO> selectProjectList();
   void insertProject(ProjectTO projectTO);
   void modifyProject(ProjectTO projectTO);
   void removeProject(String projectID);
}

