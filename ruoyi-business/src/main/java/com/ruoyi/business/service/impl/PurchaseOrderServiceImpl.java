package com.ruoyi.business.service.impl;

import java.util.List;

import com.ruoyi.business.domain.*;
import com.ruoyi.business.mapper.InventoryMapper;
import com.ruoyi.business.mapper.ProductMapper;
import com.ruoyi.business.mapper.SupplierMapper;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.business.mapper.PurchaseOrderMapper;
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

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private ProductMapper productMapper;


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

        //业务：插入时根据 供应商id字段 更新 供应商表
        Supplier supplier = new Supplier();
        supplier.setSupplierId(purchaseOrder.getSupplierId());
        supplier.setLastSupplyTime(purchaseOrder.getCreateTime());
        supplierMapper.updateSupplier(supplier);
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

        List<PurchaseOrderDetail> list = new ArrayList<PurchaseOrderDetail>();
        for (PurchaseOrderDetail purchaseOrderDetail : purchaseOrderDetailList)
        {
            purchaseOrderDetail.setPurchaseOrderId(purchaseOrderId);
            list.add(purchaseOrderDetail);
        }

        if (StringUtils.isNotNull(purchaseOrderDetailList))
        {
            if (list.size() > 0)
            {
                purchaseOrderMapper.batchPurchaseOrderDetail(list);
            }
        }

        //业务： 根据采购单详情（商品id字段，商品数量字段） 更新 库存表
        //流程：先根据商品id查询库存id，然后更新库存数量。
        for(PurchaseOrderDetail detail : list){
            Inventory inventory = inventoryMapper.selectInventoryByProductId(detail.getProductId());
            //更新记录
            if(inventory!=null){
                inventory.setStockQuantity(inventory.getStockQuantity()+detail.getQuantity());
                inventoryMapper.updateInventory(inventory);
            }else{
                Product product = productMapper.selectProductByProductId(detail.getProductId());
                inventory = new Inventory();
                inventory.setProductId(product.getProductId());
                inventory.setProductName(product.getProductName());
                inventory.setStockQuantity(detail.getQuantity());
                inventory.setCreateTime(DateUtils.getNowDate());
                inventory.setLastUpdateTime(DateUtils.getNowDate());
                inventoryMapper.insertInventory(inventory);
            }
        }
    }
}
