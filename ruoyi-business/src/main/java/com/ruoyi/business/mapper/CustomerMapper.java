package com.ruoyi.business.mapper;

import java.util.List;
import com.ruoyi.business.domain.Customer;

/**
 * 客户Mapper接口
 * 
 * @author 姚家乐
 * @date 2024-03-25
 */
public interface CustomerMapper 
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
     * 删除客户
     * 
     * @param customerId 客户主键
     * @return 结果
     */
    public int deleteCustomerByCustomerId(Long customerId);

    /**
     * 批量删除客户
     * 
     * @param customerIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCustomerByCustomerIds(Long[] customerIds);
}
