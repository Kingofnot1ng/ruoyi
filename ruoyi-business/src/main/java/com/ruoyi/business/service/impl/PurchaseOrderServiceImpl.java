package com.ruoyi.business.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.business.domain.PurchaseOrderDetail;
import com.ruoyi.business.mapper.PurchaseOrderMapper;
import com.ruoyi.business.domain.PurchaseOrder;
import com.ruoyi.business.service.IPurchaseOrderService;

/**
 * 采购单Service业务层处理
 * 
 * @author yjl
 * @date 2024-03-27
 */
@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService 
{
    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

    /**
     * 查询采购单
     * 
     * @param purchaseOrderId 采购单主键
     * @return 采购单
     */
    @Override
    public PurchaseOrder selectPurchaseOrderByPurchaseOrderId(Long purchaseOrderId)
    {
        return purchaseOrderMapper.selectPurchaseOrderByPurchaseOrderId(purchaseOrderId);
    }

    /**
     * 查询采购单列表
     * 
     * @param purchaseOrder 采购单
     * @return 采购单
     */
    @Override
    public List<PurchaseOrder> selectPurchaseOrderList(PurchaseOrder purchaseOrder)
    {
        return purchaseOrderMapper.selectPurchaseOrderList(purchaseOrder);
    }

    /**
     * 新增采购单
     * 
     * @param purchaseOrder 采购单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertPurchaseOrder(PurchaseOrder purchaseOrder)
    {
        purchaseOrder.setCreateTime(DateUtils.getNowDate());
        int rows = purchaseOrderMapper.insertPurchaseOrder(purchaseOrder);
        insertPurchaseOrderDetail(purchaseOrder);
        return rows;
    }

    /**
     * 修改采购单
     * 
     * @param purchaseOrder 采购单
     * @return 结果
     */
    @Transactional
    @Override
    public int updatePurchaseOrder(PurchaseOrder purchaseOrder)
    {
        purchaseOrderMapper.deletePurchaseOrderDetailByPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
        insertPurchaseOrderDetail(purchaseOrder);
        return purchaseOrderMapper.updatePurchaseOrder(purchaseOrder);
    }

    /**
     * 批量删除采购单
     * 
     * @param purchaseOrderIds 需要删除的采购单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deletePurchaseOrderByPurchaseOrderIds(Long[] purchaseOrderIds)
    {
        purchaseOrderMapper.deletePurchaseOrderDetailByPurchaseOrderIds(purchaseOrderIds);
        return purchaseOrderMapper.deletePurchaseOrderByPurchaseOrderIds(purchaseOrderIds);
    }

    /**
     * 删除采购单信息
     * 
     * @param purchaseOrderId 采购单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deletePurchaseOrderByPurchaseOrderId(Long purchaseOrderId)
    {
        purchaseOrderMapper.deletePurchaseOrderDetailByPurchaseOrderId(purchaseOrderId);
        return purchaseOrderMapper.deletePurchaseOrderByPurchaseOrderId(purchaseOrderId);
    }

    /**
     * 新增采购详情信息
     * 
     * @param purchaseOrder 采购单对象
     */
    public void insertPurchaseOrderDetail(PurchaseOrder purchaseOrder)
    {
        List<PurchaseOrderDetail> purchaseOrderDetailList = purchaseOrder.getPurchaseOrderDetailList();
        Long purchaseOrderId = purchaseOrder.getPurchaseOrderId();
        if (StringUtils.isNotNull(purchaseOrderDetailList))
        {
            List<PurchaseOrderDetail> list = new ArrayList<PurchaseOrderDetail>();
            for (PurchaseOrderDetail purchaseOrderDetail : purchaseOrderDetailList)
            {
                purchaseOrderDetail.setPurchaseOrderId(purchaseOrderId);
                list.add(purchaseOrderDetail);
            }
            if (list.size() > 0)
            {
                purchaseOrderMapper.batchPurchaseOrderDetail(list);
            }
        }
    }
}
