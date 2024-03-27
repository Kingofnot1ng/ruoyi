package com.ruoyi.business.service;

import java.util.List;
import com.ruoyi.business.domain.Inventory;

/**
 * 库存Service接口
 * 
 * @author yjl
 * @date 2024-03-25
 */
public interface IInventoryService 
{
    /**
     * 查询库存
     * 
     * @param recordId 库存主键
     * @return 库存
     */
    public Inventory selectInventoryByRecordId(Long recordId);

    /**
     * 查询库存列表
     * 
     * @param inventory 库存
     * @return 库存集合
     */
    public List<Inventory> selectInventoryList(Inventory inventory);

    /**
     * 新增库存
     * 
     * @param inventory 库存
     * @return 结果
     */
    public int insertInventory(Inventory inventory);

    /**
     * 修改库存
     * 
     * @param inventory 库存
     * @return 结果
     */
    public int updateInventory(Inventory inventory);

    /**
     * 批量删除库存
     * 
     * @param recordIds 需要删除的库存主键集合
     * @return 结果
     */
    public int deleteInventoryByRecordIds(Long[] recordIds);

    /**
     * 删除库存信息
     * 
     * @param recordId 库存主键
     * @return 结果
     */
    public int deleteInventoryByRecordId(Long recordId);
}
