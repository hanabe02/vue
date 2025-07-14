package kr.co.seoulit.erp.hr.salary.applicationservice;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.seoulit.erp.hr.salary.dao.BonusDAO;
import kr.co.seoulit.erp.hr.salary.to.BonusTO;

@Component
public class BonusApplicationServiceImpl implements BonusApplicationService{

	
	
	@Autowired
	private BonusDAO bonusDAO;
	
	@Override
	public BonusTO findterBonus(BonusTO bonus) {
		// TODO Auto-generated method stub
		return bonusDAO.selectBonus(bonus);
	}

	@Override
	public void registerBonus(BonusTO bonus) {
		// TODO Auto-generated method stub
		bonusDAO.insertBonus(bonus);
	}

	@Override
	public void removeAllBonus() {
		// TODO Auto-generated method stub
		bonusDAO.deleteAllBonus();
	}

	@Override
	public void removeBonus(String empCode, String applyYearMonth, String bonusCode) {
		// TODO Auto-generated method stub
		bonusDAO.deleteBonus(empCode, applyYearMonth, bonusCode);
	}

	@Override
	public void approveBonus(String empCode, String applyYearMonth) {
		// TODO Auto-generated method stub
		System.out.println("여기 안잡히냐?");
		System.out.println(empCode);
		System.out.println(applyYearMonth);
		bonusDAO.approveBonus(empCode,applyYearMonth);
	}

	@Override
	public ArrayList<BonusTO> findBonusList(String empCode,String applyYearMonth, String detailCodeName, String finalizeStatus) {
		// TODO Auto-generated method stub
		return bonusDAO.selectBonusList(empCode, applyYearMonth,detailCodeName,finalizeStatus);
	}
	
}