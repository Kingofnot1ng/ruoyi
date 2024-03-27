package com.ruoyi.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.InventoryMapper;
import com.ruoyi.business.domain.Inventory;
import com.ruoyi.business.service.IInventoryService;

/**
 * 库存Service业务层处理
 * 
 * @author yjl
 * @date 2024-03-25
 */
@Service
public class InventoryServiceImpl implements IInventoryService 
{
    @Autowired
    private InventoryMapper inventoryMapper;

    /**
     * 查询库存
     * 
     * @param recordId 库存主键
     * @return 库存
     */
    @Override
    public Inventory selectInventoryByRecordId(Long recordId)
    {
        return inventoryMapper.selectInventoryByRecordId(recordId);
    }

    /**
     * 查询库存列表
     * 
     * @param inventory 库存
     * @return 库存
     */
    @Override
    public List<Inventory> selectInventoryList(Inventory inventory)
    {
        return inventoryMapper.selectInventoryList(inventory);
    }

    /**
     * 新增库存
     * 
     * @param inventory 库存
     * @return 结果
     */
    @Override
    public int insertInventory(Inventory inventory)
    {
        return inventoryMapper.insertInventory(inventory);
    }

    /**
     * 修改库存
     * 
     * @param inventory 库存
     * @return 结果
     */
    @Override
    public int updateInventory(Inventory inventory)
    {
        return inventoryMapper.updateInventory(inventory);
    }

    /**
     * 批量删除库存
     * 
     * @param recordIds 需要删除的库存主键
     * @return 结果
     */
    @Override
    public int deleteInventoryByRecordIds(Long[] recordIds)
    {
        return inventoryMapper.deleteInventoryByRecordIds(recordIds);
    }

    /**
     * 删除库存信息
     * 
     * @param recordId 库存主键
     * @return 结果
     */
    @Override
    public int deleteInventoryByRecordId(Long recordId)
    {
        return inventoryMapper.deleteInventoryByRecordId(recordId);
    }
}
