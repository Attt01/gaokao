import request from '@/utils/request'

export function register(phone, password, veryCode) {
  return request({
    url: '/xhr/v1/userMember/reg',
    method: 'post',
    data: {
      "password": password,
      "phone": phone,
      "veryCode": veryCode
    }
  });
}