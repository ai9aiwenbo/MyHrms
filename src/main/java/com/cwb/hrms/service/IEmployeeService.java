package com.cwb.hrms.service;

import com.cwb.hrms.pojo.Employee;
import com.cwb.hrms.pojo.Post;

import java.util.List;

/**
 * 员工 业务层
 */
public interface IEmployeeService {
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
     * @param empeId 员工ID
     * @return 员工对象信息
     */
    public Employee selectEmpById(Long empeId);


    /**
     * 批量删除员工信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     * @throws Exception 异常
     */
    public int deleteEmpByIds(String ids) throws Exception;

    /**
     * 保存员工信息
     *
     * @param employee 员工信息
     * @return 结果
     */
    public int insertEmp(Employee employee);

    /**
     * 保存员工信息
     *
     * @param employee 员工信息
     * @return 结果
     */
    public int updateEmp(Employee employee);

    /**
     * 修改员工详细信息
     *
     * @param employee 员工信息
     * @return 结果
     */
    public int updateEmpInfo(Employee employee);



    /**
     * 校验手机号码是否唯一
     *
     * @param employee 员工信息
     * @return 结果
     */
    public String checkPhoneUnique(Employee employee);

    /**
     * 校验email是否唯一
     *
     * @param employee 员工信息
     * @return 结果
     */
    public String checkEmailUnique(Employee employee);


    /**
     * 根据员工ID查询员工所属岗位组
     *
     * @param empId 员工ID
     * @return 结果
     */
    public String selectEmpPostGroup(Long empId);

    /**
     * 导入员工数据
     *
     * @param empList        员工数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    public String importEmp(List<Employee> empList, Boolean isUpdateSupport);

    /**
     * 员工状态修改
     *
     * @param employee 员工信息
     * @return 结果
     */
    public int changeStatus(Employee employee);
    /**
     * 根据培训ID查询员工
     *
     * @param tId 培训ID
     * @return 员工列表
     */
    public List<Employee> selectEmployeesByTrainId(Long tId);
}
