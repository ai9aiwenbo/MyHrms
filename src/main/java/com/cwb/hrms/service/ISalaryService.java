package com.cwb.hrms.service;

import com.cwb.hrms.pojo.Employee;
import com.cwb.hrms.pojo.Salary;

import java.util.List;

/**
 * 薪资 业务层
 */
public interface ISalaryService {
    /**
     * 根据条件分页查询薪资列表
     *
     * @param salary 薪资信息
     * @return 薪资信息集合信息
     */
    public List<Salary> selectSalaryList(Salary salary);



    /**
     * 通过员工名查询薪资
     *
     * @param empName 薪资名
     * @return 薪资对象信息
     */
    public List<Salary> selectSalaryByName(String empName);

    /**
     * 通过员工Id查询薪资
     *
     * @param empId 员工id
     * @return 薪资对象信息
     */
    public Salary selectSalaryByEmpId(Long empId);

    /**
     * 通过薪资ID查询薪资
     *
     * @param sId 薪资ID
     * @return 薪资对象信息
     */
    public Salary selectSalaryById(Long sId);


    /**
     * 批量删除薪资信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     * @throws Exception 异常
     */
    public int deleteSalaryByIds(String ids) throws Exception;

    /**
     * 保存薪资信息
     *
     * @param salary 薪资信息
     * @return 结果
     */
    public int insertSalary(Salary salary);

    /**
     * 保存薪资信息
     *
     * @param salary 薪资信息
     * @return 结果
     */
    public int updateSalary(Salary salary);

    /**
     * 修改薪资详细信息
     *
     * @param salary 薪资信息
     * @return 结果
     */
    public int updateSalaryInfo(Salary salary);


    /**
     * 导入薪资数据
     *
     * @param salaryList        薪资数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    public String importSalary(List<Salary> salaryList, Boolean isUpdateSupport);

    /**
     * 薪资状态修改
     *
     * @param salary 薪资信息
     * @return 结果
     */
    public int changeStatus(Salary salary);
}
