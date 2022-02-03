package com.cwb.hrms.controller;

import com.cwb.hrms.aspectj.BusinessType;
import com.cwb.hrms.aspectj.Log;
import com.cwb.hrms.pojo.Dept;
import com.cwb.hrms.pojo.Employee;
import com.cwb.hrms.pojo.Post;
import com.cwb.hrms.pojo.User;
import com.cwb.hrms.pojo.entity.AjaxResult;
import com.cwb.hrms.pojo.entity.TableDataInfo;
import com.cwb.hrms.service.IDeptService;
import com.cwb.hrms.service.IEmployeeService;
import com.cwb.hrms.service.IPostService;
import com.cwb.hrms.service.IRoleService;
import com.cwb.hrms.utils.ExcelUtil;
import com.cwb.hrms.utils.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 员工信息
 */
@Controller
@RequestMapping("/system/emp")
public class EmpController extends BaseController {
    private String prefix = "system/emp";

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IPostService postService;

    @Autowired
    private IDeptService deptService;

    @RequiresPermissions("system:emp:view")
    @GetMapping()
    public String employee() {
        return prefix + "/employee";
    }

    @RequiresPermissions("system:emp:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Employee employee) {
        startPage();
        List<Employee> list = employeeService.selectEmpList(employee);
        return getDataTable(list);
    }

    @Log(title = "员工管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:emp:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Employee employee) {
        List<Employee> list = employeeService.selectEmpList(employee);
        ExcelUtil<Employee> util = new ExcelUtil<Employee>(Employee.class);
        return util.exportExcel(list, "员工数据");
    }

    @Log(title = "员工管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:emp:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<Employee> util = new ExcelUtil<Employee>(Employee.class);
        List<Employee> userList = util.importExcel(file.getInputStream());
        String message = employeeService.importEmp(userList, updateSupport);
        return AjaxResult.success(message);
    }

    @RequiresPermissions("system:emp:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<Employee> util = new ExcelUtil<Employee>(Employee.class);
        return util.importTemplateExcel("员工数据");
    }

    /**
     * 新增员工
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("depts", deptService.selectDeptList(null));
        mmap.put("posts", postService.selectPostAll());
        return prefix + "/add";
    }

    /**
     * 新增保存员工
     */
    @RequiresPermissions("system:emp:add")
    @Log(title = "员工管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Employee employee) {
        return toAjax(employeeService.insertEmp(employee));
    }

    /**
     * 修改员工
     */
    @GetMapping("/edit/{empId}")
    public String edit(@PathVariable("empId") Long empId, ModelMap mmap) {
        Employee emp = employeeService.selectEmpById(empId);
        mmap.put("emp", emp);
        Dept dept = deptService.selectDeptById(emp.getDeptId());
        mmap.put("dept", dept);
        List<Post> posts = postService.selectPostAll();
        for (Post post : posts) {
            if (post.getPostId() == emp.getPostId())
                post.setFlag(true);
        }
        mmap.put("posts", posts);
        return prefix + "/edit";
    }

    /**
     * 修改保存员工
     */
    @RequiresPermissions("system:emp:edit")
    @Log(title = "员工管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Employee employee) {
        return toAjax(employeeService.updateEmp(employee));
    }


    @RequiresPermissions("system:emp:remove")
    @Log(title = "员工管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(employeeService.deleteEmpByIds(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }


    /**
     * 校验手机号码
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(Employee employee) {
        return employeeService.checkPhoneUnique(employee);
    }

    /**
     * 校验email邮箱
     */
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public String checkEmailUnique(Employee employee) {
        return employeeService.checkEmailUnique(employee);
    }

    /**
     * 员工状态修改
     */
    @Log(title = "员工管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:emp:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(Employee employee) {
        return toAjax(employeeService.changeStatus(employee));
    }
}