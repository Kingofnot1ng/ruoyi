package com.ruoyi.web.controller.business;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.business.domain.PurchaseOrder;
import com.ruoyi.business.service.IPurchaseOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 采购单Controller
 * 
 * @author yjl
 * @date 2024-03-27
 */
@RestController
@RequestMapping("/business/order")
public class PurchaseOrderController extends BaseController
{
    @Autowired
    private IPurchaseOrderService purchaseOrderService;

    /**
     * 查询采购单列表
     */
    @PreAuthorize("@ss.hasPermi('business:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(PurchaseOrder purchaseOrder)
    {
        startPage();
        List<PurchaseOrder> list = purchaseOrderService.selectPurchaseOrderList(purchaseOrder);
        return getDataTable(list);
    }

    /**
     * 导出采购单列表
     */
    @PreAuthorize("@ss.hasPermi('business:order:export')")
    @Log(title = "采购单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PurchaseOrder purchaseOrder)
    {
        List<PurchaseOrder> list = purchaseOrderService.selectPurchaseOrderList(purchaseOrder);
        ExcelUtil<PurchaseOrder> util = new ExcelUtil<PurchaseOrder>(PurchaseOrder.class);
        util.exportExcel(response, list, "采购单数据");
    }

    /**
     * 获取采购单详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:order:query')")
    @GetMapping(value = "/{purchaseOrderId}")
    public AjaxResult getInfo(@PathVariable("purchaseOrderId") Long purchaseOrderId)
    {
        return success(purchaseOrderService.selectPurchaseOrderByPurchaseOrderId(purchaseOrderId));
    }

    /**
     * 新增采购单
     */
    @PreAuthorize("@ss.hasPermi('business:order:add')")
    @Log(title = "采购单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PurchaseOrder purchaseOrder)
    {
        //这里做个逻辑，新增采购单会同步更新供应商供货记录
        //新增采购记录同步更新仓库数量，如果没有仓库记录就创建一个
        return toAjax(purchaseOrderService.insertPurchaseOrder(purchaseOrder));
    }

    /**
     * 修改采购单
     */
    @PreAuthorize("@ss.hasPermi('business:order:edit')")
    @Log(title = "采购单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PurchaseOrder purchaseOrder)
    {
        return toAjax(purchaseOrderService.updatePurchaseOrder(purchaseOrder));
    }

    /**
     * 删除采购单
     */
    @PreAuthorize("@ss.hasPermi('business:order:remove')")
    @Log(title = "采购单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{purchaseOrderIds}")
    public AjaxResult remove(@PathVariable Long[] purchaseOrderIds)
    {
        return toAjax(purchaseOrderService.deletePurchaseOrderByPurchaseOrderIds(purchaseOrderIds));
    }
}
