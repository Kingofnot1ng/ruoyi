package com.ruoyi.business.mapper;

import java.util.List;
import com.ruoyi.business.domain.PurchaseOrder;
import com.ruoyi.business.domain.PurchaseOrderDetail;

/**
 * 采购单Mapper接口
 * 
 * @author yjl
 * @date 2024-03-27
 */
public interface PurchaseOrderMapper 
{
    /**
     * 查询采购单
     * 
     * @param purchaseOrderId 采购单主键
     * @return 采购单
     */
    public PurchaseOrder selectPurchaseOrderByPurchaseOrderId(Long purchaseOrderId);

    /**
     * 查询采购单列表
     * 
     * @param purchaseOrder 采购单
     * @return 采购单集合
     */
    public List<PurchaseOrder> selectPurchaseOrderList(PurchaseOrder purchaseOrder);

    /**
     * 新增采购单
     * 
     * @param purchaseOrder 采购单
     * @return 结果
     */
    public int insertPurchaseOrder(PurchaseOrder purchaseOrder);

    /**
     * 修改采购单
     * 
     * @param purchaseOrder 采购单
     * @return 结果
     */
    public int updatePurchaseOrder(PurchaseOrder purchaseOrder);

    /**
     * 删除采购单
     * 
     * @param purchaseOrderId 采购单主键
     * @return 结果
     */
    public int deletePurchaseOrderByPurchaseOrderId(Long purchaseOrderId);

    /**
     * 批量删除采购单
     * 
     * @param purchaseOrderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePurchaseOrderByPurchaseOrderIds(Long[] purchaseOrderIds);

    /**
     * 批量删除采购详情
     * 
     * @param purchaseOrderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePurchaseOrderDetailByPurchaseOrderIds(Long[] purchaseOrderIds);
    
    /**
     * 批量新增采购详情
     * 
     * @param purchaseOrderDetailList 采购详情列表
     * @return 结果
     */
    public int batchPurchaseOrderDetail(List<PurchaseOrderDetail> purchaseOrderDetailList);
    

    /**
     * 通过采购单主键删除采购详情信息
     * 
     * @param purchaseOrderId 采购单ID
     * @return 结果
     */
    public int deletePurchaseOrderDetailByPurchaseOrderId(Long purchaseOrderId);
}
