import request from '@/utils/request'

// 查询采购单列表
export function listOrder(query) {
  return request({
    url: '/business/order/list',
    method: 'get',
    params: query
  })
}

// 查询采购单详细
export function getOrder(purchaseOrderId) {
  return request({
    url: '/business/order/' + purchaseOrderId,
    method: 'get'
  })
}

// 新增采购单
export function addOrder(data) {
  return request({
    url: '/business/order',
    method: 'post',
    data: data
  })
}

// 修改采购单
export function updateOrder(data) {
  return request({
    url: '/business/order',
    method: 'put',
    data: data
  })
}

// 删除采购单
export function delOrder(purchaseOrderId) {
  return request({
    url: '/business/order/' + purchaseOrderId,
    method: 'delete'
  })
}
