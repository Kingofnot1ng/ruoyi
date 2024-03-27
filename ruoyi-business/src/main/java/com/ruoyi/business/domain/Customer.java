package com.ruoyi.business.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 客户对象 customer
 * 
 * @author 姚家乐
 * @date 2024-03-25
 */
public class Customer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 客户id */
    @Excel(name = "客户id")
    private Long customerId;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String customerName;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String contact;

    /** 客户地址 */
    @Excel(name = "客户地址")
    private String address;

    /** 注册时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "注册时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date registerTime;

    /** 最近拿货时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最近拿货时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastPurchaseTime;

    public void setCustomerId(Long customerId) 
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
    }
    public void setCustomerName(String customerName) 
    {
        this.customerName = customerName;
    }

    public String getCustomerName() 
    {
        return customerName;
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
    public void setRegisterTime(Date registerTime) 
    {
        this.registerTime = registerTime;
    }

    public Date getRegisterTime() 
    {
        return registerTime;
    }
    public void setLastPurchaseTime(Date lastPurchaseTime) 
    {
        this.lastPurchaseTime = lastPurchaseTime;
    }

    public Date getLastPurchaseTime() 
    {
        return lastPurchaseTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("customerId", getCustomerId())
            .append("customerName", getCustomerName())
            .append("contact", getContact())
            .append("address", getAddress())
            .append("registerTime", getRegisterTime())
            .append("lastPurchaseTime", getLastPurchaseTime())
            .append("remark", getRemark())
            .toString();
    }
}
