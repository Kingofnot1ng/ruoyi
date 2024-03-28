package com.ruoyi.business.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 采购单对象 purchase_order
 * 
 * @author yjl
 * @date 2024-03-27
 */
public class PurchaseOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 采购单id */
    @Excel(name = "采购单id")
    private Long purchaseOrderId;

    /** 供应商id */
    @Excel(name = "供应商id")
    private Long supplierId;

    /** 经办人id */
    @Excel(name = "经办人id")
    private Long staffId;

    /** 采购详情信息 */
    private List<PurchaseOrderDetail> purchaseOrderDetailList;

    public void setPurchaseOrderId(Long purchaseOrderId) 
    {
        this.purchaseOrderId = purchaseOrderId;
    }

    public Long getPurchaseOrderId() 
    {
        return purchaseOrderId;
    }
    public void setSupplierId(Long supplierId) 
    {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() 
    {
        return supplierId;
    }
    public void setStaffId(Long staffId) 
    {
        this.staffId = staffId;
    }

    public Long getStaffId() 
    {
        return staffId;
    }

    public List<PurchaseOrderDetail> getPurchaseOrderDetailList()
    {
        return purchaseOrderDetailList;
    }

    public void setPurchaseOrderDetailList(List<PurchaseOrderDetail> purchaseOrderDetailList)
    {
        this.purchaseOrderDetailList = purchaseOrderDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("purchaseOrderId", getPurchaseOrderId())
            .append("supplierId", getSupplierId())
            .append("staffId", getStaffId())
            .append("createTime", getCreateTime())
            .append("purchaseOrderDetailList", getPurchaseOrderDetailList())
            .toString();
    }
}
