package com.ruoyi.business.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 供应商对象 supplier
 * 
 * @author yjl
 * @date 2024-03-25
 */
public class Supplier extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 供应商id */
    @Excel(name = "供应商id")
    private Long supplierId;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String supplierName;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String contact;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 合作开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "合作开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 最近供货时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最近供货时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastSupplyTime;

    public void setSupplierId(Long supplierId) 
    {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() 
    {
        return supplierId;
    }
    public void setSupplierName(String supplierName) 
    {
        this.supplierName = supplierName;
    }

    public String getSupplierName() 
    {
        return supplierName;
    }
    public void setContact(String contact) 
    {
        this.contact = contact;
    }

    public String getContact() 
    {
        return contact;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setStartDate(Date startDate) 
    {
        this.startDate = startDate;
    }

    public Date getStartDate() 
    {
        return startDate;
    }
    public void setLastSupplyTime(Date lastSupplyTime) 
    {
        this.lastSupplyTime = lastSupplyTime;
    }

    public Date getLastSupplyTime() 
    {
        return lastSupplyTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("supplierId", getSupplierId())
            .append("supplierName", getSupplierName())
            .append("contact", getContact())
            .append("address", getAddress())
            .append("remark", getRemark())
            .append("startDate", getStartDate())
            .append("lastSupplyTime", getLastSupplyTime())
            .toString();
    }
}
