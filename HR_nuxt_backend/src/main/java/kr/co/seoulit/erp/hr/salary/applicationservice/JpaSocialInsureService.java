package kr.co.seoulit.erp.hr.salary.applicationservice;

import kr.co.seoulit.erp.hr.salary.entity.SocialInsureEntity;

import java.util.ArrayList;

public interface JpaSocialInsureService {
    ArrayList<SocialInsureEntity> findSocialInsureList(String year);
}
