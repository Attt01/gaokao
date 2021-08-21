package com.gaokao.common.service;

import com.gaokao.common.dao.FrontDataDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MaeYon-Z
 * date 2021-08-21
 */
@Slf4j
@Service
public class FrontDataServiceImpl implements FrontDataService{
    @Autowired
    private FrontDataDao frontDataDao;

    @Override
    public List<String> getProvinces(){
        List<String> provincesList = frontDataDao.getProvinces();
        return provincesList;
    }

    @Override
    public List<String> getCities(String provinceName){
        List<String> citiesList = frontDataDao.getCities(provinceName);
        return citiesList;
    }

}
