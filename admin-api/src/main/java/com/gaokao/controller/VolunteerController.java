package com.gaokao.controller;

import com.gaokao.common.constants.VolunteerConstant;
import com.gaokao.common.meta.AjaxResult;
import com.gaokao.common.meta.po.Volunteer;
import com.gaokao.common.meta.vo.advise.AdviseVO;
import com.gaokao.common.meta.vo.advise.FilterParams;
import com.gaokao.common.meta.vo.volunteer.AddVolunteerParams;
import com.gaokao.common.meta.vo.volunteer.DeleteVolunteerParams;
import com.gaokao.common.service.AdviseService;
import com.gaokao.common.service.VolunteerService;
import com.gaokao.common.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/xhr/v1/admin_volunteer")
public class VolunteerController {
    @Autowired
    private VolunteerService volunteerService;

    @GetMapping("getVolunteers")
    public AjaxResult<Page<Volunteer>> getVolunteers(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                     @RequestParam(required = false, defaultValue = "10") Integer limit,
                                                     @RequestParam(required = false, defaultValue = "") String searchText) {
        return AjaxResult.SUCCESS(volunteerService.getVolunteers(page, limit, searchText));
    }

    void checkVolunteer(Volunteer volunteer) {

        if (volunteer.getName() == null) {
            volunteer.setName("");
        }
        if (volunteer.getUniversityCode() == null) {
            volunteer.setUniversityCode("");
        }
        if (volunteer.getProvince() == null) {
            volunteer.setProvince("");
        }
        if (volunteer.getCity() == null) {
            volunteer.setCity("");
        }
        if (volunteer.getAddress() == null) {
            volunteer.setAddress("");
        }
        if (volunteer.getCategory() == null) {
            volunteer.setCategory("");
        }
        if (volunteer.getPicLink() == null) {
            volunteer.setPicLink("");
        }
        if (volunteer.getProfessionalName() == null) {
            volunteer.setProfessionalName("");
        }
        if (volunteer.getSubjectRestrictionDetail() == null) {
            volunteer.setSubjectRestrictionDetail("");
        }

        // 检查并设置默认值
        if (volunteer.getUndergraduateSchoolIsOrNot() == null) {
            volunteer.setUndergraduateSchoolIsOrNot(false);
        }
        if (volunteer.getJuniorSchoolIsOrNot() == null) {
            volunteer.setJuniorSchoolIsOrNot(false);
        }
        if (volunteer.getIs985() == null) {
            volunteer.setIs985(false);
        }
        if (volunteer.getIs211() == null) {
            volunteer.setIs211(false);
        }
        if (volunteer.getPublicIsOrNot() == null) {
            volunteer.setPublicIsOrNot(false);
        }
        if (volunteer.getPrivateIsOrNot() == null) {
            volunteer.setPrivateIsOrNot(false);
        }
        if (volunteer.getEmploymentRate() == null) {
            volunteer.setEmploymentRate(0);
        }
        if (volunteer.getAbroadRate() == null) {
            volunteer.setAbroadRate(0);
        }
        if (volunteer.getFurtherRate() == null) {
            volunteer.setFurtherRate(0);
        }
        if (volunteer.getVolunteerSection() == null) {
            volunteer.setVolunteerSection(0);
        }
        if (volunteer.getLowestScore() == null) {
            volunteer.setLowestScore(0);
        }
        if (volunteer.getLowestPosition() == null) {
            volunteer.setLowestPosition(0);
        }
        if (volunteer.getSubjectRestrictionType() == null) {
            volunteer.setSubjectRestrictionType(0);
        }
        if (volunteer.getScore() == null) {
            volunteer.setScore(0);
        }
        if (volunteer.getPosition() == null) {
            volunteer.setPosition(0);
        }
        if (volunteer.getEnrollment() == null) {
            volunteer.setEnrollment(0);
        }
        if (volunteer.getTime() == null) {
            volunteer.setTime(0);
        }
        if (volunteer.getFee() == null) {
            volunteer.setFee(0);
        }
        if (volunteer.getDoubleFirstClassSubjectNumber() == null) {
            volunteer.setDoubleFirstClassSubjectNumber(0);
        }
        if (volunteer.getCountrySpecificSubjectNumber() == null) {
            volunteer.setCountrySpecificSubjectNumber(0);
        }
        if (volunteer.getMasterPoint() == null) {
            volunteer.setMasterPoint(0);
        }
        if (volunteer.getDoctorPoint() == null) {
            volunteer.setDoctorPoint(0);
        }
    }
    @PostMapping("addVolunteer")
    public AjaxResult<Boolean> addVolunteer(@RequestBody Volunteer volunteer) {
        checkVolunteer(volunteer);
        return AjaxResult.SUCCESS(volunteerService.addOneVolunteer(volunteer));
    }

    @PutMapping("updateVolunteer")
    public AjaxResult<Boolean> updateVolunteer(@RequestBody Volunteer volunteer) {
        checkVolunteer(volunteer);
        return AjaxResult.SUCCESS(volunteerService.updateOneVolunteer(volunteer));
    }

    @DeleteMapping("deleteVolunteer/{volunteerId}")
    public AjaxResult<Boolean> deleteVolunteer(@PathVariable Long volunteerId) {
        return AjaxResult.SUCCESS(volunteerService.deleteOneVolunteer(volunteerId));
    }
}