package com.ruoyi.business.mapper;

import java.util.List;
import com.ruoyi.business.domain.Inventory;

/**
 * 库存Mapper接口
 * 
 * @author yjl
 * @date 2024-03-25
 */
public interface InventoryMapper 
{
    /**
     * 查询库存
     * 
     * @param recordId 库存主键
     * @return 库存
     */
    public Inventory selectInventoryByRecordId(Long recordId);
    /**
     * 查询库存
     *
     * @param productId 库存主键
     * @return 库存
     */
    public Inventory selectInventoryByProductId(Long productId);

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
     * 删除库存
     * 
     * @param recordId 库存主键
     * @return 结果
     */
    public int deleteInventoryByRecordId(Long recordId);

    /**
     * 批量删除库存
     * 
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInventoryByRecordIds(Long[] recordIds);


}
