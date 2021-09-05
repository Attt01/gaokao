import request from '@/utils/request'

/**
 * 获取列表数据（后端待实现）
 */
export function getVOList(params) {
  return request({
    url: '/xhr/v1/advise/listAll',
    method: 'post',
    data: params
  })
}

/**
 * 获取区域信息下拉框
 */
export function getRegion() {
  return request({
    url: '/xhr/v1/getData/region',
    method: 'get',
  })
}

/**
 * 获取批次信息下拉框
 */
export function getBatch() {
  return request({
    url: '/xhr/v1/getData/batch',
    method: 'get',
  })
}

/**
 * 获取大学分类下拉框
 */
export function getSchoolType() {
  return request({
    url: '/xhr/v1/getData/schooltype',
    method: 'get',
  })
}

/**
 * 获取专业分类下拉框
 */
export function getMajorType() {
  return request({
    url: '/xhr/v1/getData/majortype',
    method: 'get',
  })
}
