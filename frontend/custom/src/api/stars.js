/**
 * 获取列表数据
 */
export function getStarsList(params) {
  return request({
    url: '/xhr/v1/stars/list',
    method: 'post',
    data: params
  })
}
