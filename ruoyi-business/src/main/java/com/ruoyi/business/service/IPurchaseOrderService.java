package com.ruoyi.business.service;

import java.util.List;
import com.ruoyi.business.domain.PurchaseOrder;

/**
 * 采购单Service接口
 * 
 * @author yjl
 * @date 2024-03-27
 */
public interface IPurchaseOrderService 
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
     * 批量删除采购单
     * 
     * @param purchaseOrderIds 需要删除的采购单主键集合
     * @return 结果
     */
    public int deletePurchaseOrderByPurchaseOrderIds(Long[] purchaseOrderIds);

    /**
     * 删除采购单信息
     * 
     * @param purchaseOrderId 采购单主键
     * @return 结果
     */
    public int deletePurchaseOrderByPurchaseOrderId(Long purchaseOrderId);
}
