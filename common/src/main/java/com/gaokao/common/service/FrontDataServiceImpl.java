package com.gaokao.common.service;

import com.gaokao.common.dao.FrontDataDao;
import com.gaokao.common.meta.po.ThirdData;
import com.gaokao.common.meta.vo.frontdata.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<BatchVO> getBatches(){
        List<BatchVO> batchVOS = new ArrayList<>();
        List<Integer> batchesId = frontDataDao.getSecondIds(1);
        for(Integer id :batchesId){
            BatchVO batchVO = new BatchVO();
            batchVO.setValue(id);
            batchVO.setLabel(frontDataDao.getSecondNameById(id));
            batchVOS.add(batchVO);
        }
        return batchVOS;
    }

    @Override
    public List<FrontDataVO> getFrontData(Integer firstId){
        List<FrontDataVO> frontDataVOS = new ArrayList<>();
        List<Integer> secondIds = frontDataDao.getSecondIds(firstId);
        for(Integer secondId: secondIds){
            FrontDataVO frontDataVO = new FrontDataVO();
            frontDataVO.setValue(secondId);
            frontDataVO.setLabel(frontDataDao.getSecondNameById(secondId));
            List<ThirdData> thirdDataList = frontDataDao.getThirdData(secondId);
            List<ThirdDataVO> thirdDataVOList = new ArrayList<>();
            thirdDataList.forEach(item -> {
                ThirdData2VO thirdData2VO = new ThirdData2VO();
                BeanUtils.copyProperties(item, thirdData2VO);
                ThirdDataVO thirdDataVO = new ThirdDataVO();
                thirdDataVO.setValue(thirdData2VO.getId());
                thirdDataVO.setLabel(thirdData2VO.getValue());
                thirdDataVOList.add(thirdDataVO);
            });
            frontDataVO.setChildren(thirdDataVOList);
            frontDataVOS.add(frontDataVO);
        }
        return frontDataVOS;
    }
}
