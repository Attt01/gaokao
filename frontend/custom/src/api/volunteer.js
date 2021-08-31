import request from '@/utils/request'
export function getVolunteer(params) {
  return request({
    url: '/xhr/v1/volunteer/getAll/' + params,
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
      userId: params.userId,
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
