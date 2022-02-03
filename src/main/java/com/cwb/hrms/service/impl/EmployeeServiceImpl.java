package com.cwb.hrms.service.impl;

import com.cwb.hrms.constant.UserConstants;
import com.cwb.hrms.exception.BusinessException;
import com.cwb.hrms.mapper.*;
import com.cwb.hrms.pojo.*;
import com.cwb.hrms.service.IEmployeeService;
import com.cwb.hrms.utils.Convert;
import com.cwb.hrms.utils.ShiroUtils;
import com.cwb.hrms.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 员工 业务层处理
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService {
    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeMapper employeeMapper;


    @Autowired
    private PostMapper postMapper;


    /**
     * 根据条件分页查询员工列表
     *
     * @param employee 员工信息
     * @return 员工信息集合信息
     */
    @Override
    public List<Employee> selectEmpList(Employee employee) {
        // 生成数据权限过滤条件
        return employeeMapper.selectEmpList(employee);
    }


    /**
     * 通过员工名查询员工
     *
     * @param empName 员工名
     * @return 员工对象信息
     */
    @Override
    public List<Employee> selectEmpByName(String empName) {
        return employeeMapper.selectEmpByName(empName);
    }

    /**
     * 通过手机号码查询员工
     *
     * @param phoneNumber 手机号码
     * @return 员工对象信息
     */
    @Override
    public Employee selectEmpByPhoneNumber(String phoneNumber) {
        return employeeMapper.selectEmpByPhoneNumber(phoneNumber);
    }

    /**
     * 通过邮箱查询员工
     *
     * @param email 邮箱
     * @return 员工对象信息
     */
    @Override
    public Employee selectEmpByEmail(String email) {
        return employeeMapper.selectEmpByEmail(email);
    }

    /**
     * 通过员工ID查询员工
     *
     * @param empId 员工ID
     * @return 员工对象信息
     */
    @Override
    public Employee selectEmpById(Long empId) {
        return employeeMapper.selectEmpById(empId);
    }


    /**
     * 批量删除员工信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteEmpByIds(String ids) throws BusinessException {
        Long[] empIds = Convert.toLongArray(ids);

        return employeeMapper.deleteEmpByIds(empIds);
    }

    /**
     * 新增保存员工信息
     *
     * @param employee 员工信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertEmp(Employee employee) {
        employee.setCreateBy(ShiroUtils.getLoginName());
        // 新增员工信息
        int rows = employeeMapper.insertEmp(employee);
        return rows;
    }

    /**
     * 修改保存员工信息
     *
     * @param employee 员工信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateEmp(Employee employee) {
        employee.setUpdateBy(ShiroUtils.getLoginName());
        return employeeMapper.updateEmp(employee);
    }

    /**
     * 修改员工个人详细信息
     *
     * @param employee 员工信息
     * @return 结果
     */
    @Override
    public int updateEmpInfo(Employee employee) {
        return employeeMapper.updateEmp(employee);
    }


    /**
     * 校验员工手机是否唯一
     *
     * @param employee 员工信息
     * @return
     */
    @Override
    public String checkPhoneUnique(Employee employee) {
        Long empId = StringUtils.isNull(employee.getEmpId()) ? -1L : employee.getEmpId();
        Employee info = employeeMapper.checkPhoneUnique(employee.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getEmpId().longValue() != empId.longValue()) {
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.USER_PHONE_UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param employee 员工信息
     * @return
     */
    @Override
    public String checkEmailUnique(Employee employee) {
        Long empId = StringUtils.isNull(employee.getEmpId()) ? -1L : employee.getEmpId();
        Employee info = employeeMapper.checkEmailUnique(employee.getEmail());
        if (StringUtils.isNotNull(info) && info.getEmpId().longValue() != empId.longValue()) {
            return UserConstants.USER_EMAIL_NOT_UNIQUE;
        }
        return UserConstants.USER_EMAIL_UNIQUE;
    }

    /**
     * 查询员工所属岗位组
     *
     * @param empId 员工ID
     * @return 结果
     */
    @Override
    public String selectEmpPostGroup(Long empId) {
        List<Post> list = postMapper.selectPostsByUserId(empId);
        StringBuffer idsStr = new StringBuffer();
        for (Post post : list) {
            idsStr.append(post.getPostName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 导入员工数据
     *
     * @param empList         员工数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public String importEmp(List<Employee> empList, Boolean isUpdateSupport) {
        if (StringUtils.isNull(empList) || empList.size() == 0) {
            throw new BusinessException("导入员工数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (Employee employee : empList) {
            try {
                boolean flag = false;//是否存在
                // 验证是否存在这个员工
                List<Employee> emps = employeeMapper.selectEmpByName(employee.getName());
                for (Employee e : emps) {
                    if (employee.equals(e)) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {//员工不存在
                    employee.setCreateBy(operName);
                    this.insertEmp(employee);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + employee.getName() + " 导入成功");
                } else if (isUpdateSupport) {//员工存在 是否可以更新
                    employee.setUpdateBy(operName);
                    this.updateEmp(employee);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + employee.getName() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + employee.getName() + " 已存在");
                }

            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + employee.getName() + " 导入失败：";
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
     * 员工状态修改
     *
     * @param employee 员工信息
     * @return 结果
     */
    @Override
    public int changeStatus(Employee employee) {
        return employeeMapper.updateEmp(employee);
    }

    @Override
    public List<Employee> selectEmployeesByTrainId(Long tId) {
        List<Employee> trainEmps = employeeMapper.selectEmployeesByTrainId(tId);
        List<Employee> emps = employeeMapper.selectEmpList(null);
        for (Employee emp : emps) {
            for (Employee trainEmp : trainEmps) {
                if (emp.getEmpId().longValue() == trainEmp.getEmpId().longValue()) {
                    emp.setFlag(true);
                    break;
                }
            }
        }
        return emps;
    }
}
