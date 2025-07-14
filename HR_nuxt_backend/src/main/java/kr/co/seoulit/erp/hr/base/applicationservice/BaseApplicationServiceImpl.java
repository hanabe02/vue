package kr.co.seoulit.erp.hr.base.applicationservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.co.seoulit.erp.hr.base.dao.*;
import kr.co.seoulit.erp.hr.base.to.*;
import kr.co.seoulit.erp.hr.company.dao.PositionDAO;
import kr.co.seoulit.erp.hr.company.to.CompanyTO;
import kr.co.seoulit.erp.hr.company.to.WorkplaceTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.seoulit.erp.hr.affair.applicationservice.EmpApplicationService;
import kr.co.seoulit.erp.hr.affair.to.EmpTO;
import kr.co.seoulit.erp.hr.salary.dao.BaseSalaryDAO;
import kr.co.seoulit.erp.hr.salary.to.BaseSalaryTO;
import kr.co.seoulit.erp.hr.company.to.PositionTO;


@Component
public class BaseApplicationServiceImpl implements BaseApplicationService {


	@Autowired
    EmpApplicationService empApplicationService;
    @Autowired
    private HrDetailCodeDAO detailCodeDAO;
    @Autowired
    private HolidayDAO holidayDAO;
    @Autowired
    private DeptDAO deptDAO;
    @Autowired
    private BaseSalaryDAO baseSalaryDAO;
    @Autowired
    private HrCodeDAO codeDAO;
    @Autowired
    private ReportDAO reportDAO;
    @Autowired
    private BaseWorkTimeDAO baseWorkTimeDAO;
    @Autowired
    private YearMonthDAO yearMonthDAO;
    @Autowired
    private CompanyListDAO companyDAO;
    @Autowired
    private WorkplaceListDAO workplaceListDAO;
    @Autowired
    private PositionDAO positionDAO;
    @Autowired
    private ProjectDAO projectDAO;

      @Override
      public ArrayList<HrDetailCodeTO> findDetailCodeList(String codetype) {
         // TODO Auto-generated method stub

         ArrayList<HrDetailCodeTO> detailCodeList = null;
         detailCodeList = detailCodeDAO.selectDetailCodeList(codetype);

         return detailCodeList;
      }

      @Override
      public void registEmpCode(EmpTO emp) {
         // TODO Auto-generated method stub
         HrDetailCodeTO detailCodeto = new HrDetailCodeTO();
         detailCodeto.setDetailCodeNumber(emp.getEmpCode());
         detailCodeto.setDetailCodeName(emp.getEmpName());
         detailCodeto.setCodeNumber("CO-17");
         detailCodeto.setDetailCodeNameusing("N");
         detailCodeDAO.insertDetailCode(detailCodeto);
      }

      @Override
      public void deleteEmpCode(EmpTO emp) {
         // TODO Auto-generated method stub
         HrDetailCodeTO detailCodeto = new HrDetailCodeTO();
         detailCodeto.setDetailCodeNumber(emp.getEmpCode());
         detailCodeto.setDetailCodeName(emp.getEmpName());
         detailCodeDAO.deleteDetailCode(detailCodeto);
      }

      @Override
      public ArrayList<HrDetailCodeTO> findDetailCodeListRest(String code1, String code2, String code3) {
         // TODO Auto-generated method stub

         ArrayList<HrDetailCodeTO> detailCodeList = null;
         detailCodeList = detailCodeDAO.selectDetailCodeListRest(code1, code2, code3);

         return detailCodeList;
      }
//--------------------------------------- 휴일 관리 ---------------------------------------
      @Override
      public ArrayList<HolidayTO> findHolidayList(String applyDay) {
         // TODO Auto-generated method stub
         return holidayDAO.selectHolidayList(applyDay);
      }

  	@Override
  	public void addHoliday(HolidayTO holiday) {
  		// TODO Auto-generated method stub
  		holidayDAO.insertHoliday(holiday);
  	}

	@Override
	public void modifyHoliday(HolidayTO holiday) {
		// TODO Auto-generated method stub
		holidayDAO.updateHoliday(holiday);
	}

	@Override
	public void removeHoliday(String applyDay) {
		// TODO Auto-generated method stub
		holidayDAO.deleteHoliday(applyDay);
	}



	@Override
	public void addPublicHoliday(HolidayTO holiday) {
		// TODO Auto-generated method stub
		System.out.println("여기 임플");
		String applyDay = holiday.getLocdate().substring(0,4)+"-"+holiday.getLocdate().substring(4,6)+"-"+holiday.getLocdate().substring(6);
		holiday.setApplyDay(applyDay);
		/////여기에 case when then 으로 holidayType 설정 다 해줘야 함
		if(holiday.getDateName().equals("1월1일") || holiday.getDateName().equals("설날")
				||holiday.getDateName().equals("추석")) {
			holiday.setHolidayCode("HOL001");
			holiday.setHolidayType("명절");
		}else if(holiday.getDateName().equals("삼일절") || holiday.getDateName().equals("광복절") || holiday.getDateName().equals("개천절") || holiday.getDateName().equals("한글날")) {
			holiday.setHolidayCode("HOL002");
			holiday.setHolidayType("국경일");
		}else if(holiday.getDateName().equals("부처님오신날") || holiday.getDateName().equals("기독탄신일")) {
			holiday.setHolidayCode("HOL003");
			holiday.setHolidayType("종교기념일");
		}else if(holiday.getDateName().equals("어린이날") || holiday.getDateName().equals("현충일")) {
			holiday.setHolidayCode("HOL004");
			holiday.setHolidayType("법정기념일");
		}else if(holiday.getDateName().equals("대통령선거일") || holiday.getDateName().equals("전국동시지방선거")) {
			holiday.setHolidayCode("HOL005");
			holiday.setHolidayType("임시공휴일");
		}else if(holiday.getDateName().equals("대체공휴일")) {
			holiday.setHolidayCode("HOL006");
			holiday.setHolidayType("대체공휴일");
		}else if(holiday.getDateName().equals("기독탄신일")) {
			holiday.setDateName("크리스마스");
		}else if(holiday.getDateName().equals("1월1일")) {
			holiday.setDateName("신정");
		}
		System.out.println(holiday);
		holidayDAO.insertPublicHoliday(holiday);
	}









      @Override
      public void batchDeptProcess(ArrayList<DeptTO> deptto) {
         // TODO Auto-generated method stub
         HrDetailCodeTO detailCodeto = new HrDetailCodeTO();

         for (DeptTO dept : deptto) {
            switch (dept.getStatus()) {

            case "update":
               deptDAO.updateDept(dept);
               detailCodeto.setDetailCodeNumber(dept.getDeptCode());
               detailCodeto.setDetailCodeName(dept.getDeptName());
               detailCodeto.setCodeNumber("CO-07");
               detailCodeto.setDetailCodeNameusing("Y");
               detailCodeDAO.updateDetailCode(detailCodeto);
               break;

            case "insert":
               //deptDAO.registDept(dept);
               detailCodeto.setDetailCodeNumber(dept.getDeptCode());
               detailCodeto.setDetailCodeName(dept.getDeptName());
               detailCodeto.setCodeNumber("CO-07");
               detailCodeto.setDetailCodeNameusing("Y");
               detailCodeDAO.insertDetailCode(detailCodeto);
               break;

            case "delete":
               deptDAO.deleteDept(dept);
               detailCodeto.setDetailCodeNumber(dept.getDeptCode());
               detailCodeto.setDetailCodeName(dept.getDeptName());
               detailCodeDAO.deleteDetailCode(detailCodeto);
               break;

            case "normal":
               break;
            }
         }
      }

      @Override
      public void modifyPosition(ArrayList<BaseSalaryTO> positionList) {
         // TODO Auto-generated method stub
    	//****************************************************2020-08-28 63湲� �넀�쑀李� ********************************* **********************
         if (positionList != null && positionList.size() > 0) {
            for (BaseSalaryTO position : positionList) {
               HrDetailCodeTO detailCodeto = new HrDetailCodeTO();
               switch (position.getStatus()) {

               case "update":
                  baseSalaryDAO.updatePosition(position);
                  detailCodeto.setDetailCodeNumber(position.getPositionCode());
                  detailCodeto.setDetailCodeName(position.getPositionName());
                  detailCodeto.setCodeNumber("CO-04");
                  detailCodeto.setDetailCodeNameusing("Y");
                  detailCodeDAO.updateDetailCode(detailCodeto);
                  break;

               case "insert":
                  baseSalaryDAO.insertPosition(position);
                  detailCodeto.setDetailCodeNumber(position.getPositionCode());
                  detailCodeto.setDetailCodeName(position.getPositionName());
                  detailCodeto.setCodeNumber("CO-04");
                  detailCodeto.setDetailCodeNameusing("Y");
                  detailCodeDAO.insertDetailCode(detailCodeto);
                  break;

               case "delete":
                  baseSalaryDAO.deletePosition(position);
                  detailCodeto.setDetailCodeNumber(position.getPositionCode());
                  detailCodeto.setDetailCodeName(position.getPositionName());
                  detailCodeDAO.deleteDetailCode(detailCodeto);
                  break;
               }
            }
         }      //****************************************************2020-08-28 63湲� �넀�쑀李� ********************************* **********************
      }

      @Override
      public String findWeekDayCount(String startDate, String endDate) {
         // TODO Auto-generated method stub
         String weekdayCount = holidayDAO.selectWeekDayCount(startDate, endDate);

         return weekdayCount;
      }
   /*
    * @Override public void registEmpImg(String empCode, String company, String
    * workPlace, String position, String empName, String imgExtend) { // TODO
    * Auto-generated method stub EmpTO emp =
    * empApplicationService.findAllEmployeeInfo(
    * company,workPlace,position,empName); if (emp == null) { emp = new EmpTO();
    * emp.setEmpCode(empCode); emp.setStatus("insert"); } else {
    * emp.setStatus("update"); } emp.setImgExtend(imgExtend);
    * empApplicationService.modifyEmployee(emp); }
    */

      @Override
      public ArrayList<HrCodeTO> findCodeList() {
         // TODO Auto-generated method stub
         // TODO Auto-generated method stub
         ArrayList<HrCodeTO> codeList = codeDAO.selectCode();

         return codeList;
      }

      @Override
      public void registCodeList(List<HolidayTO> holyday) {
         // TODO Auto-generated method stub

         for (HolidayTO holiday : holyday) {
            switch (holiday.getStatus()) {

            case "update":
               holidayDAO.updateCodeList(holiday);
               break;

            case "insert":
               holidayDAO.insertCodeList(holiday);
               break;

            case "delete":
               holidayDAO.deleteCodeList(holiday);
               break;

            }
         }
      }
      @Override
      public ReportTO viewReport(String empCode) {

          ReportTO to=null;

       to=reportDAO.selectReport(empCode);

         return to;
      }

	@Override
	public void registEmpImg(String empCode, String imgExtend) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<BaseWorkTimeTO> searchBaseWorkTimeList() {
		return baseWorkTimeDAO.selectBaseWorkTimeList();
	}

	@Override
	public void deleteBaseWorkTime(List<BaseWorkTimeTO> list) {

		HashMap<String,Object> map = new HashMap<>();
		map.put("list", list);

		baseWorkTimeDAO.deleteBaseWorkTime(map);
	}

	@Override
	public void batchBaseWorkTime(List<BaseWorkTimeTO> list) {

		baseWorkTimeDAO.mergeBaseWorkTime(list);
	}

	@Override
	public ArrayList<YearMonthTO> selectYearList() {
		// TODO Auto-generated method stub
		ArrayList<YearMonthTO> yearList = yearMonthDAO.selectYearList();
		return yearList;
	}
    @Override
    public ArrayList<CompanyTO> selectCompanyList() {
        // TODO Auto-generated method stub
        ArrayList<CompanyTO> companyList = companyDAO.findCompanyList();
        return companyList;
    }

    @Override
    public ArrayList<WorkplaceTO> selectWorkplaceList() {
        // TODO Auto-generated method stub
        ArrayList<WorkplaceTO> workplaceList = workplaceListDAO.findWorkplaceList();
        return workplaceList;
    }

    @Override
    public ArrayList<ProjectTO> selectProjectList() {
        // TODO Auto-generated method stub
        ArrayList<ProjectTO> projectList = projectDAO.selectProjectAllList();
        System.out.println("projectList = " + projectList);
        return projectList;
    }

    @Override
    public void insertProject(ProjectTO projectTO) {
         projectDAO.insertProject(projectTO);
    }

    @Override
    public void modifyProject(ProjectTO projectTO) {
        projectDAO.updateProject(projectTO);
    }

    @Override
    public void removeProject(String projectID) {
        projectDAO.deleteProject(projectID);
    }

    @Override
	public ArrayList<YearMonthTO> selectMonthList() {
		// TODO Auto-generated method stub
		ArrayList<YearMonthTO> monthList = yearMonthDAO.selectMonthList();
		return monthList;
	}

	/*새로운 부서를 등록할 때
	 * CODE_DETAIL에도 새로운 부서 값을 넣어 줘야 하고,
	 * POSITION 테이블에도 새로운 부서에 대한 직급들을 생성해줘야 한다.
	*/
	@Override
	public void registerDept(DeptTO dept) {
		// TODO Auto-generated method stub
		deptDAO.insertDept(dept);
	}

	@Override
	public void modifyDept(DeptTO dept) {
		// TODO Auto-generated method stub
		deptDAO.updateDept(dept);
		HrDetailCodeTO hrDetailCode = new HrDetailCodeTO();
		hrDetailCode.setDetailCodeName(dept.getDeptName());
		hrDetailCode.setDetailCodeNumber(dept.getDeptCode());
		detailCodeDAO.updateDetailCode(hrDetailCode);
	}

	@Override
	public void removeDept(String deptCode, String workplaceCode) {
		// TODO Auto-generated method stub
		deptDAO.deleteDept(deptCode, workplaceCode);
		detailCodeDAO.deleteDetailCode(deptCode);
		positionDAO.deletePosition(deptCode);
	}

   }
