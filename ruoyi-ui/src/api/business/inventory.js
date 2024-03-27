import request from '@/utils/request'

// 查询库存列表
export function listInventory(query) {
  return request({
    url: '/business/inventory/list',
    method: 'get',
    params: query
  })
}

// 查询库存详细
export function getInventory(recordId) {
  return request({
    url: '/business/inventory/' + recordId,
    method: 'get'
  })
}

// 新增库存
export function addInventory(data) {
  return request({
    url: '/business/inventory',
    method: 'post',
    data: data
  })
}

// 修改库存
export function updateInventory(data) {
  return request({
    url: '/business/inventory',
    method: 'put',
    data: data
  })
}

// 删除库存
export function delInventory(recordId) {
  return request({
    url: '/business/inventory/' + recordId,
    method: 'delete'
  })
}
