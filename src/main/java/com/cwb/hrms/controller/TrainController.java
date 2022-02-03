package com.cwb.hrms.controller;

import com.cwb.hrms.aspectj.BusinessType;
import com.cwb.hrms.aspectj.Log;
import com.cwb.hrms.pojo.Train;
import com.cwb.hrms.pojo.entity.AjaxResult;
import com.cwb.hrms.pojo.entity.TableDataInfo;
import com.cwb.hrms.service.*;
import com.cwb.hrms.utils.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 培训信息
 */
@Controller
@RequestMapping("/system/train")
public class TrainController extends BaseController {
    private String prefix = "system/train";

    @Autowired
    private ITrainService trainService;

    @Autowired
    private IEmployeeService employeeService;

    @RequiresPermissions("system:train:view")
    @GetMapping()
    public String train() {
        return prefix + "/train";
    }

    @RequiresPermissions("system:train:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Train train) {
        startPage();
        List<Train> list = trainService.selectTrainList(train);
        return getDataTable(list);
    }

    @Log(title = "培训管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:train:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Train train) {
        List<Train> list = trainService.selectTrainList(train);
        ExcelUtil<Train> util = new ExcelUtil<Train>(Train.class);
        return util.exportExcel(list, "培训数据");
    }

    @Log(title = "培训管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:train:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<Train> util = new ExcelUtil<Train>(Train.class);
        List<Train> trainList = util.importExcel(file.getInputStream());
        String message = trainService.importTrain(trainList, updateSupport);
        return AjaxResult.success(message);
    }

    @RequiresPermissions("system:train:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<Train> util = new ExcelUtil<Train>(Train.class);
        return util.importTemplateExcel("培训数据");
    }

    /**
     * 新增培训
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("emps", employeeService.selectEmpList(null));
        return prefix + "/add";
    }

    /**
     * 新增保存培训
     */
    @RequiresPermissions("system:train:add")
    @Log(title = "培训管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Train train) {
        return toAjax(trainService.insertTrain(train));
    }

    /**
     * 修改培训
     */
    @GetMapping("/edit/{tId}")
    public String edit(@PathVariable("tId") Long tId, ModelMap mmap) {
        mmap.put("train", trainService.selectTrainById(tId));
        mmap.put("emps", employeeService.selectEmployeesByTrainId(tId));
        return prefix + "/edit";
    }

    /**
     * 修改保存培训
     */
    @RequiresPermissions("system:train:edit")
    @Log(title = "培训管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Train train) {
        return toAjax(trainService.updateTrain(train));
    }


    @RequiresPermissions("system:train:remove")
    @Log(title = "培训管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(trainService.deleteTrainByIds(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 校验培训标题
     */
    @PostMapping("/checkTitleUnique")
    @ResponseBody
    public String checkTitleUnique(Train train) {
        return trainService.checkTitleUnique(train.getTitle());
    }

    /**
     * 培训状态修改
     */
    @Log(title = "培训管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:train:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(Train train) {
        return toAjax(trainService.changeStatus(train));
    }
}