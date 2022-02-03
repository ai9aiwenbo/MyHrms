package com.cwb.hrms.controller;

import com.cwb.hrms.aspectj.BusinessType;
import com.cwb.hrms.aspectj.Log;
import com.cwb.hrms.pojo.Dept;
import com.cwb.hrms.pojo.Employee;
import com.cwb.hrms.pojo.Post;
import com.cwb.hrms.pojo.Salary;
import com.cwb.hrms.pojo.entity.AjaxResult;
import com.cwb.hrms.pojo.entity.TableDataInfo;
import com.cwb.hrms.service.IDeptService;
import com.cwb.hrms.service.IEmployeeService;
import com.cwb.hrms.service.IPostService;
import com.cwb.hrms.service.ISalaryService;
import com.cwb.hrms.utils.ExcelUtil;
import com.cwb.hrms.utils.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 员工信息
 */
@Controller
@RequestMapping("/system/salary")
public class SalaryController extends BaseController {
    private String prefix = "system/salary";

    @Autowired
    private ISalaryService salaryService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private IPostService postService;

    @RequiresPermissions("system:salary:view")
    @GetMapping()
    public String salary() {
        return prefix + "/salary";
    }

    @RequiresPermissions("system:emp:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Salary salary) {
        startPage();
        List<Salary> list = salaryService.selectSalaryList(salary);
        return getDataTable(list);
    }

    @Log(title = "薪资管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:salary:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Salary salary) {
        List<Salary> list = salaryService.selectSalaryList(salary);
        List<Salary> salaryList = new ArrayList<>();
        ExcelUtil<Salary> util = new ExcelUtil<Salary>(Salary.class);
        for (Salary s : list) {
            Employee employee = employeeService.selectEmpById(s.getEmpId());
            if (employee != null) {
                s.setEmployee(employee);
                s.setPost(employee.getPost());
                s.setDept(employee.getDept());
                salaryList.add(s);
            }
        }
        return util.exportExcel(salaryList, "薪资数据");
    }

    @Log(title = "薪资管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:salary:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<Salary> util = new ExcelUtil<Salary>(Salary.class);
        List<Salary> userList = util.importExcel(file.getInputStream());
        String message = salaryService.importSalary(userList, updateSupport);
        return AjaxResult.success(message);
    }

    @RequiresPermissions("system:salary:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<Salary> util = new ExcelUtil<Salary>(Salary.class);
        return util.importTemplateExcel("薪资数据");
    }

    /**
     * 新增薪资
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        List<Employee> employeeList = employeeService.selectEmpList(null);
        List<Employee> emps = new ArrayList<>();
        for (Employee employee : employeeList) {
            //查找薪资列表是否有该员工的薪资
            Salary salary = salaryService.selectSalaryByEmpId(employee.getEmpId());
            if (StringUtils.isNull(salary)) {
                emps.add(employee);
            }
        }
        mmap.put("emps", emps);
        return prefix + "/add";
    }

    /**
     * 新增保存薪资
     */
    @RequiresPermissions("system:salary:add")
    @Log(title = "薪资管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Salary salary) {
        Long basicSalary = salary.getBasicSalary();
        Long bonus = salary.getBonus();
        Long lunchSalary = salary.getLunchSalary();
        Long trafficSalary = salary.getTrafficSalary();
        //计算实际工资
        Long allSalary = basicSalary + bonus + lunchSalary + trafficSalary;
        salary.setAllSalary(allSalary);
        return toAjax(salaryService.insertSalary(salary));
    }

    /**
     * 修改薪资
     */
    @GetMapping("/edit/{sId}")
    public String edit(@PathVariable("sId") Long sId, ModelMap mmap) {
        Salary salary = salaryService.selectSalaryById(sId);
        Employee emp = employeeService.selectEmpById(salary.getEmpId());
        salary.setEmployee(emp);
        mmap.put("salary", salary);
        return prefix + "/edit";
    }

    /**
     * 修改保存薪资
     */
    @RequiresPermissions("system:salary:edit")
    @Log(title = "薪资管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Salary salary) {
        return toAjax(salaryService.updateSalary(salary));
    }


    @RequiresPermissions("system:salary:remove")
    @Log(title = "薪资管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(salaryService.deleteSalaryByIds(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }


    /**
     * 薪资状态修改
     */
    @Log(title = "薪资管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:salary:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(Salary salary) {
        return toAjax(salaryService.changeStatus(salary));
    }
}