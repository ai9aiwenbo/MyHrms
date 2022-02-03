package com.cwb.hrms.mapper;

import com.cwb.hrms.pojo.Employee;
import com.cwb.hrms.pojo.Post;
import com.cwb.hrms.pojo.User;

import java.util.List;

/**
 * 员工表 数据层
 */
public interface EmployeeMapper {
    /**
     * 根据条件分页查询员工列表
     *
     * @param employee 员工信息
     * @return 员工信息集合信息
     */
    public List<Employee> selectEmpList(Employee employee);


    /**
     * 通过员工名查询员工
     *
     * @param empName 员工名
     * @return 员工对象信息
     */
    public List<Employee> selectEmpByName(String empName);

    /**
     * 通过手机号码查询员工
     *
     * @param phoneNumber 手机号码
     * @return 员工对象信息
     */
    public Employee selectEmpByPhoneNumber(String phoneNumber);

    /**
     * 通过邮箱查询员工
     *
     * @param email 邮箱
     * @return 员工对象信息
     */
    public Employee selectEmpByEmail(String email);

    /**
     * 通过员工ID查询员工
     *
     * @param empId 员工ID
     * @return 员工对象信息
     */
    public Employee selectEmpById(Long empId);


    /**
     * 批量删除员工信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteEmpByIds(Long[] ids);

    /**
     * 修改员工信息
     *
     * @param employee 员工信息
     * @return 结果
     */
    public int updateEmp(Employee employee);

    /**
     * 新增员工信息
     *
     * @param employee 员工信息
     * @return 结果
     */
    public int insertEmp(Employee employee);
    /**
     * 根据培训ID查询员工
     *
     * @param tId 培训ID
     * @return 员工列表
     */
    public List<Employee> selectEmployeesByTrainId(Long tId);

    /**
     * 校验手机号码是否唯一
     *
     * @param phonenumber 手机号码
     * @return 结果
     */
    public Employee checkPhoneUnique(String phonenumber);

    /**
     * 校验email是否唯一
     *
     * @param email 员工邮箱
     * @return 结果
     */
    public Employee checkEmailUnique(String email);
}
