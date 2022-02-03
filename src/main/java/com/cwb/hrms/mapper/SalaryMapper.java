package com.cwb.hrms.mapper;

import com.cwb.hrms.pojo.Salary;

import java.util.List;

/**
 * 薪资表 数据层
 */
public interface SalaryMapper {
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
     * @param empName 员工名
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
     */
    public int deleteSalaryByIds(Long[] ids);

    /**
     * 修改薪资信息
     *
     * @param salary 薪资信息
     * @return 结果
     */
    public int updateSalary(Salary salary);

    /**
     * 新增薪资信息
     *
     * @param salary 薪资信息
     * @return 结果
     */
    public int insertSalary(Salary salary);

}
