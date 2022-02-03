package com.cwb.hrms.service.impl;

import com.cwb.hrms.constant.UserConstants;
import com.cwb.hrms.exception.BusinessException;
import com.cwb.hrms.mapper.EmployeeMapper;
import com.cwb.hrms.mapper.PostMapper;
import com.cwb.hrms.mapper.SalaryMapper;
import com.cwb.hrms.pojo.Employee;
import com.cwb.hrms.pojo.Post;
import com.cwb.hrms.pojo.Salary;
import com.cwb.hrms.service.IEmployeeService;
import com.cwb.hrms.service.ISalaryService;
import com.cwb.hrms.utils.Convert;
import com.cwb.hrms.utils.ShiroUtils;
import com.cwb.hrms.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 薪资 业务层处理
 */
@Service
public class SalaryServiceImpl implements ISalaryService {
    private static final Logger log = LoggerFactory.getLogger(SalaryServiceImpl.class);

    @Autowired
    private SalaryMapper salaryMapper;

    /**
     * 根据条件分页查询薪资列表
     *
     * @param salary 薪资信息
     * @return 薪资信息集合信息
     */
    @Override
    public List<Salary> selectSalaryList(Salary salary) {
        return salaryMapper.selectSalaryList(salary);
    }


    /**
     * 通过员工名查询薪资
     *
     * @param empName 员工名
     * @return 薪资对象信息
     */
    @Override
    public List<Salary> selectSalaryByName(String empName) {
        return salaryMapper.selectSalaryByName(empName);
    }

    /**
     * 通过员工Id查询薪资
     *
     * @param empId 员工id
     * @return 薪资对象信息
     */
    @Override
    public Salary selectSalaryByEmpId(Long empId) {
        return salaryMapper.selectSalaryByEmpId(empId);
    }

    /**
     * 通过薪资ID查询薪资
     *
     * @param sId 薪资ID
     * @return 薪资对象信息
     */
    @Override
    public Salary selectSalaryById(Long sId) {
        return salaryMapper.selectSalaryById(sId);
    }


    /**
     * 批量删除薪资信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSalaryByIds(String ids) throws BusinessException {
        Long[] sIds = Convert.toLongArray(ids);
        return salaryMapper.deleteSalaryByIds(sIds);
    }

    /**
     * 新增保存薪资信息
     *
     * @param salary 薪资信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertSalary(Salary salary) {
        salary.setCreateBy(ShiroUtils.getLoginName());
        // 新增薪资信息
        int rows = salaryMapper.insertSalary(salary);
        return rows;
    }

    /**
     * 修改保存薪资信息
     *
     * @param salary 薪资信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateSalary(Salary salary) {
        salary.setUpdateBy(ShiroUtils.getLoginName());
        return salaryMapper.updateSalary(salary);
    }

    /**
     * 修改薪资个人详细信息
     *
     * @param salary 薪资信息
     * @return 结果
     */
    @Override
    public int updateSalaryInfo(Salary salary) {
        return salaryMapper.updateSalary(salary);
    }


    /**
     * 导入薪资数据
     *
     * @param salaryList      薪资数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public String importSalary(List<Salary> salaryList, Boolean isUpdateSupport) {
        if (StringUtils.isNull(salaryList) || salaryList.size() == 0) {
            throw new BusinessException("导入薪资数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (Salary salary : salaryList) {
            try {
                // 验证员工是否存在
                Salary s = salaryMapper.selectSalaryByEmpId(salary.getEmpId());
                if (StringUtils.isNull(s)) {//薪资不存在
                    salary.setCreateBy(operName);
                    salary.setAllSalary(salary.getBasicSalary()+salary.getBonus()+salary.getTrafficSalary());
                    this.insertSalary(salary);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + salary.getsId() + " 导入成功");
                } else if (isUpdateSupport) {//薪资存在 是否可以更新
                    salary.setUpdateBy(operName);
                    this.updateSalary(salary);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、薪资 " + salary.getsId()+" 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、薪资 " + salary.getsId()+" 已存在");
                }

            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、薪资 " + salary.getsId()+" 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 薪资状态修改
     *
     * @param salary 薪资信息
     * @return 结果
     */
    @Override
    public int changeStatus(Salary salary) {
        return salaryMapper.updateSalary(salary);
    }
}
