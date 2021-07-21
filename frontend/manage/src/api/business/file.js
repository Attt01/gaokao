import request from '@/utils/request'

export function uploadFile(params) {
  return request({
    url: '/xhr/v1/files/upload',
    method: 'post',
    params
  })
}
