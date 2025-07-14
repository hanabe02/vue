package kr.co.seoulit.erp.sys.serviceFacade;

import kr.co.seoulit.erp.hr.affair.dao.EmpSearchingDAO;
import kr.co.seoulit.erp.hr.affair.dao.EmployeeSecretDAO;
import kr.co.seoulit.erp.hr.affair.to.EmpInfoTO;
import kr.co.seoulit.erp.hr.affair.to.EmployeeSecretTO;
import kr.co.seoulit.erp.sys.dao.AuthorityDao;
import kr.co.seoulit.erp.sys.dao.CodeDao;
import kr.co.seoulit.erp.sys.dao.CodeDetailDao;
import kr.co.seoulit.erp.sys.dao.MenuDao;
import kr.co.seoulit.erp.sys.exception.IdNotFoundException;
import kr.co.seoulit.erp.sys.exception.PwMissMatchException;
import kr.co.seoulit.erp.sys.to.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BaseServiceFacadeImpl implements BaseServiceFacade {

	@Autowired
	MenuDao menuDao;
	@Autowired
	CodeDao codeDao;
	@Autowired
	CodeDetailDao codeDetailDao;
	@Autowired
	AuthorityDao authorityDao;
	@Autowired
	EmpSearchingDAO empSearchingDAO;
	@Autowired
	EmployeeSecretDAO empSecretDAO;

	@Override
	public List<MenuTo> findMenuList() {
		List<MenuTo> flatMenuList = menuDao.selectMenuList();
		Map<String, MenuTo> treeMenuList = new HashMap<>();
		List<MenuTo> topMenuList = new ArrayList<>();

		for (MenuTo flatMenu : flatMenuList) {
			treeMenuList.put(flatMenu.getMenuCode(), flatMenu);
			// �옄�떊�씠 理쒖긽�쐞 硫붾돱�씪 寃쎌슦
			if (flatMenu.getSuperMenuCode() == null)
				topMenuList.add(flatMenu);

			// �긽�쐞 硫붾돱媛� 議댁옱�븷 寃쎌슦
			else {
				if (!treeMenuList.containsKey(flatMenu.getSuperMenuCode())) {
					System.out.println("�긽�쐞 硫붾돱媛� 議댁옱�븯吏� �븡�뒿�땲�떎.");
					continue;
				}

				MenuTo menu = treeMenuList.get(flatMenu.getSuperMenuCode());

				// subMenuList媛� �깮�꽦�릺�뼱 �엳吏� �븡�쓣 寃쎌슦
				if (menu.getSubMenuList() == null)
					menu.setSubMenuList(new ArrayList<>());

				menu.getSubMenuList().add(flatMenu);
			}
		}
		System.out.print("########################################");
		System.out.print(topMenuList);
		return topMenuList;
	}

	@Override
	public List<SysCodeTo> findCodeList() {
		return codeDao.selectCodeList();
	}

	@Override
	public List<SysCodeDetailTo> findCodeDetailList() {
		return codeDetailDao.selectAllCodeDetailList();
	}

	@Override
	public List<SysCodeDetailTo> findPayStepCodeDetailList(String itemClassificationCondition) {
		// TODO Auto-generated method stub
		return codeDetailDao.selectPayStepCodeDetailList(itemClassificationCondition);
	}

	@Override
	public List<AuthorityTo> findAuthorityList() {
		return authorityDao.selectAuthorityList();
	}

	@Override
	public List<AuthorityTo> findMenuAuthorityList(Map<String, Object> data) {
		String authorityCode = data.get("authorityCode").toString();
		return authorityDao.selectMenuAuthorityList(authorityCode);
	}

	@Override
	public <T> void batchDetailCodeProcess(T TO, Map<String, Object> codeColumn) {
		// TODO Auto-generated method stub
		try {
			SysCodeDetailTo codeDetailTO = new SysCodeDetailTo();
			Method method = TO.getClass().getMethod("getStatus");
			codeDetailTO.setStatus((String) method.invoke(TO));
			for (String key : codeColumn.keySet()) {
				String colValue = (String) codeColumn.get(key);
				if (colValue != null) {
					switch (key) {
					case "divisionCodeNo":
						codeDetailTO.setDivisionCodeNo(colValue);
						break;
					case "detailCode":
						codeDetailTO.setDetailCode(colValue);
						break;
					case "detailCodeName":
						codeDetailTO.setDetailCodeName(colValue);
						break;
					}
				}
			}
			switch (codeDetailTO.getStatus()) {
			case "inserted":
				codeDetailDao.insertDetailCode(codeDetailTO);
				break;
			case "deleted":
				codeDetailDao.deleteDetailCode(codeDetailTO);
				break;
			case "updated":
				codeDetailDao.updateDetailCode(codeDetailTO);
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void batchDetailCodeProcess(List<SysCodeDetailTo> codeDetailList) {
		for (SysCodeDetailTo codeDetailTo : codeDetailList) {
			switch (codeDetailTo.getStatus()) {
			case "inserted":
				codeDetailDao.insertDetailCode(codeDetailTo);
				break;
			case "updated":
				codeDetailDao.updateDetailCode(codeDetailTo);
				break;
			case "deleted":
				codeDetailDao.deleteDetailCode(codeDetailTo);
				break;
			}
		}
	}

	@Override
	public EmpInfoTO myLogin(LoginTo loginTo) throws DataAccessException, IdNotFoundException, PwMissMatchException {

		EmpInfoTO empTo = checkEmpInfo(loginTo.getCompanyCode(), loginTo.getEmpCode(), loginTo.getPassword(), loginTo.getWorkplaceCode());

		checkPassword(empTo.getEmpCode(), loginTo.getCompanyCode(), loginTo.getPassword());

		return empTo;

	}
	// 반환형이 String 객체 인자라 순서 신경 안 쓰면 해쉬맵이어도 오류남 -ㅂㅎㅂ
	public EmpInfoTO checkEmpInfo(String companyCode, String empCode, String password, String workplaceCode) throws IdNotFoundException {

		HashMap<String, String> map = new HashMap<>();
		System.out.println("정보"+password+empCode);
		map.put("companyCode", companyCode);
		map.put("empCode", empCode);
		map.put("password", password);
		map.put("workplaceCode", workplaceCode);
		System.out.println("정보"+companyCode+empCode+workplaceCode);
		EmpInfoTO empTo = empSearchingDAO.getTotalEmpInfo(map);
		System.out.println("정보"+empTo);
		if (empTo == null) {
			throw new IdNotFoundException("Check your account info.");
		}
		return empTo;

	};

	public void checkPassword(String empCode, String companyCode, String password) throws PwMissMatchException {

		HashMap<String, String> map = new HashMap<>();

		map.put("companyCode", companyCode);
		map.put("empCode", empCode);
		System.out.println("로그인 디버깅 포인트2:"+companyCode+empCode);
		EmployeeSecretTO empSecretInfo = empSecretDAO.selectUserPassWord(map);

		if (empSecretInfo == null)
			if (!empSecretInfo.getUserPassword().equals(password))
				throw new PwMissMatchException("Check your account info.");

	}

	@Override
	public ArrayList<SysCodeDetailTo> findHolidayType() {
		// TODO Auto-generated method stub
		return codeDetailDao.searchHolidayType();
	};

}
