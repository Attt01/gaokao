import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'
const userInfoKey = "userInfo"
const platformUserCorpId = 0 || -1

//这里可得记得改回来qwq
export function getToken() {
  //return sessionStorage.getItem(TokenKey)
  if (sessionStorage.getItem(TokenKey))
    return sessionStorage.getItem(TokenKey);
  else
    return "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Mjc5ODg2NzUsInVzZXJuYW1lIjoiYWRtaW4ifQ.Et3146xhO0Szaq06LR_euK3V3DpWIekOri4yelAlHtI";
}

export function setToken(token) {
  return sessionStorage.setItem(TokenKey, token)
}


export function setUserInfo(userInfo) {
  sessionStorage.setItem(userInfoKey, JSON.stringify(userInfo))
}

export function isPlatformUser() {
  let userInfo = JSON.parse(sessionStorage.getItem(userInfoKey))
  console.log(userInfo)
  return userInfo != null && userInfo.corp === platformUserCorpId
}

export function getUserInfo() {
  return sessionStorage.getItem(userInfoKey);
}

export function getPermissions() {
  let userInfo = JSON.parse(sessionStorage.getItem(userInfoKey))
  if (userInfo != null) {
    return userInfo.permissions;
  }
  return null;
}

export function logOut() {
  sessionStorage.clear()
}
