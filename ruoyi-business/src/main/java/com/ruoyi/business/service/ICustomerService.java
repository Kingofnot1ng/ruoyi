package com.ruoyi.business.service;

import java.util.List;
import com.ruoyi.business.domain.Customer;

/**
 * 客户Service接口
 * 
 * @author 姚家乐
 * @date 2024-03-25
 */
public interface ICustomerService 
{
    /**
     * 查询客户
     * 
     * @param customerId 客户主键
     * @return 客户
     */
    public Customer selectCustomerByCustomerId(Long customerId);

    /**
     * 查询客户列表
     * 
     * @param customer 客户
     * @return 客户集合
     */
    public List<Customer> selectCustomerList(Customer customer);

    /**
     * 新增客户
     * 
     * @param customer 客户
     * @return 结果
     */
    public int insertCustomer(Customer customer);

    /**
     * 修改客户
     * 
     * @param customer 客户
     * @return 结果
     */
    public int updateCustomer(Customer customer);

    /**
     * 批量删除客户
     * 
     * @param customerIds 需要删除的客户主键集合
     * @return 结果
     */
    public int deleteCustomerByCustomerIds(Long[] customerIds);

    /**
     * 删除客户信息
     * 
     * @param customerId 客户主键
     * @return 结果
     */
    public int deleteCustomerByCustomerId(Long customerId);
}
