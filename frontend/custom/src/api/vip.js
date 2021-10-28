import request from '@/utils/request'
/**
 * 生成订单和支付二维码
 *
 */
export function saveOrder(userId) {
  return request({
    url: '/xhr/v1/users/orders/saveOrder?userId=' + userId,
    method: 'get'
  })
}

/**
 * 取消订单
 */
export function cancelOrder(params) {
  return request({
    url: '/xhr/v1/orders/cancel',
    method: 'post',
    data: {
      orderId: params.orderId
    }
  })
}

/**
 * 充值成功
 */
export function success(params) {
  return request({
    url: '/xhr/v1/orders/success',
    method: 'post',
    data: {
      orderId: params.orderId
    }
  })
}

