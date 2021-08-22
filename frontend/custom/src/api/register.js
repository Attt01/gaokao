import request from '@/utils/request'

export function register(formdata) {
  //还不清楚选课的枚举暂时先随便整个字符串了qwq
  formdata.subject = "test";
  return request({
    url: '/xhr/v1/userMember/reg',
    method: 'post',
    data: formdata
    // data: {
    //   "password": password,
    //   "phone": phone,
    //   "veryCode": veryCode,
    //   "nickname": "qwq",
    //   "score": 233,
    //   "subject": "1231",
    //   "province_rank": 1322
    // }
  });
}

export function sendVeryCode() {
  return request({
    url: '/xhr/v1/userMember/sendVerifyCode',
    method: 'get'
  });
}