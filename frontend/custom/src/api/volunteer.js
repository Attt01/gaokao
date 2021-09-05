import request from '@/utils/request'
export function getVolunteer() {
  return request({
    url: '/xhr/v1/volunteer/getAll/',
    method: 'get'
  })
}

export function deleteVolunteer(params) {
  return request({
    url: '/xhr/v1/volunteer/deleteVolunteer',
    method: 'post',
    data: {
      formId: params.id,
      section: params.section,
      volunteerPosition: params.volunteerPosition
    }
  })
}

export function changeCurrentForm(params) {
  return request({
    url: '/xhr/v1/volunteer/changeCurrentForm',
    method: 'post',
    data: {
      newFormId: params.newFormId,
      preFormId: params.preFormId,
      userId: params.userId
    }
  })
}

export function getCurrentVolunteer() {
  return request({
    url: '/xhr/v1/volunteer/getCurrent/',
    method: 'get'
  })
}

export function createVolunteerForm(formData) {
  return request({
    url: '/xhr/v1/volunteer/create',
    method: 'post',
    data: formData
  })
}

export function updateVolunteerName(formId, name) {
  return request({
    url: '/xhr/v1/volunteer/updateName',
    method: 'post',
    data: {
      formId,
      name
    }
  })
}

export function upVolunteer(data) {
  return request({
    url: '/xhr/v1/volunteer/upVolunteer',
    method: 'post',
    data: data
    // {
    //   "formId": 0,
    //   "section": true,
    //   "volunteerId": 0,
    //   "volunteerPosition": 0
    // }
  })
}

export function downVolunteer(data) {
  return request({
    url: '/xhr/v1/volunteer/downVolunteer',
    method: 'post',
    data: data
  })
}

export function swapVolunteer(data) {
  return request({
    url: '/xhr/v1/volunteer/swapVolunteer',
    method: 'post',
    data: data
    // {
    //   "firstVolunteerId": 0,
    //   "firstVolunteerPosition": 0,
    //   "formId": 0,
    //   "secondVolunteerId": 0, //0为当前位置没有志愿
    //   "secondVolunteerPosition": 0,
    //   "section": true
    // }
  })
}