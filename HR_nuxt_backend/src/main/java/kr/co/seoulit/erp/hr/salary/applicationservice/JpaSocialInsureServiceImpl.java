package kr.co.seoulit.erp.hr.salary.applicationservice;

import kr.co.seoulit.erp.hr.salary.entity.SocialInsureEntity;
import kr.co.seoulit.erp.hr.salary.repository.JpaSocialInsureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class JpaSocialInsureServiceImpl implements JpaSocialInsureService {
    @Autowired
    JpaSocialInsureRepository jpaSocialInsureRepository;
    @Override
    public ArrayList<SocialInsureEntity> findSocialInsureList(String year) {
        ArrayList<SocialInsureEntity> socialInsureList = jpaSocialInsureRepository.findByAttributionYear(year);
        return socialInsureList;
    }
}
