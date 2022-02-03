package com.cwb.hrms.service.impl;

import com.cwb.hrms.exception.BusinessException;
import com.cwb.hrms.mapper.*;
import com.cwb.hrms.pojo.*;
import com.cwb.hrms.service.ITrainService;
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
 * 培训 业务层处理
 */
@Service
public class TrainServiceImpl implements ITrainService {
    private static final Logger log = LoggerFactory.getLogger(TrainServiceImpl.class);

    @Autowired
    private TrainMapper trainMapper;

    @Autowired
    private TrainEmpMapper trainEmpMapper;


    @Autowired
    private EmployeeMapper employeeMapper;


    /**
     * 根据条件分页查询培训列表
     *
     * @param train 培训信息
     * @return 培训信息集合信息
     */
    @Override
    public List<Train> selectTrainList(Train train) {
        // 生成数据权限过滤条件
        return trainMapper.selectTrainList(train);
    }

    /**
     * 通过培训名查询培训
     *
     * @param title 培训名
     * @return 培训对象信息
     */
    @Override
    public Train selectTrainByTitle(String title) {
        return trainMapper.selectTrainByTitle(title);
    }

    /**
     * 通过培训人查询培训
     *
     * @param trainName 培训人
     * @return 培训对象信息
     */
    @Override
    public List<Train> selectTrainByTrainName(String trainName) {
        return trainMapper.selectTrainByTrainName(trainName);
    }


    /**
     * 通过培训ID查询培训
     *
     * @param tId 培训ID
     * @return 培训对象信息
     */
    @Override
    public Train selectTrainById(Long tId) {
        return trainMapper.selectTrainById(tId);
    }

    /**
     * 通过培训ID删除培训
     *
     * @param tId 培训ID
     * @return 结果
     */
    @Override
    public int deleteTrainById(Long tId) {
        // 删除培训与员工表
        trainEmpMapper.deleteTrainEmpBytId(tId);
        return trainMapper.deleteTrainById(tId);
    }

    /**
     * 批量删除培训信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTrainByIds(String ids) throws BusinessException {
        Long[] trainIds = Convert.toLongArray(ids);
        for (Long trainId : trainIds) {
            trainEmpMapper.deleteTrainEmpBytId(trainId);
        }
        return trainMapper.deleteTrainByIds(trainIds);
    }

    /**
     * 新增保存培训信息
     *
     * @param train 培训信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertTrain(Train train) {
        train.setCreateBy(ShiroUtils.getLoginName());
        // 新增培训信息
        int rows = trainMapper.insertTrain(train);
        // 新增培训员工关联
        insertTrainEmp(train);
        return rows;
    }

    /**
     * 修改保存培训信息
     *
     * @param train 培训信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateTrain(Train train) {
        Long tId = train.gettId();
        train.setUpdateBy(ShiroUtils.getLoginName());
        // 删除培训与员工关联
        trainEmpMapper.deleteTrainEmpBytId(tId);
        // 新增培训与岗位管理
        insertTrainEmp(train);
        return trainMapper.updateTrain(train);
    }

    /**
     * 修改培训个人详细信息
     *
     * @param train 培训信息
     * @return 结果
     */
    @Override
    public int updateTrainInfo(Train train) {
        return trainMapper.updateTrain(train);
    }

    /**
     * 新增培训员工信息
     *
     * @param train 培训对象
     */
    public void insertTrainEmp(Train train) {
        Long[] empIds = train.getEmpIds();
        if (StringUtils.isNotNull(empIds)) {
            // 新增培训与员工管理
            List<TrainEmp> list = new ArrayList<TrainEmp>();
            for (Long empId : train.getEmpIds()) {
                TrainEmp te = new TrainEmp();
                te.settId(train.gettId());
                te.setEmpId(empId);
                list.add(te);
            }
            if (list.size() > 0) {
                trainEmpMapper.batchTrainEmp(list);
            }
        }
    }

    /**
     * 校验培训标题是否唯一
     *
     * @param title 标题
     * @return 0唯一  1 不唯一
     */
    @Override
    public String checkTitleUnique(String title) {
        int count = trainMapper.checkTitleUnique(title);
        if (count > 0) {
            return "1";
        }
        return "0";
    }


    /**
     * 查询培训员工列表
     *
     * @param tId 培训ID
     * @return 结果
     */
    @Override
    public String selectTrainEmpGroup(Long tId) {
        List<Employee> list = employeeMapper.selectEmployeesByTrainId(tId);
        StringBuffer idsStr = new StringBuffer();
        for (Employee employee : list) {
            idsStr.append(employee.getName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 导入培训数据
     *
     * @param trainList       培训数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public String importTrain(List<Train> trainList, Boolean isUpdateSupport) {
        if (StringUtils.isNull(trainList) || trainList.size() == 0) {
            throw new BusinessException("导入培训数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (Train train : trainList) {
            try {
                // 验证是否存在这个培训
                Train t = trainMapper.selectTrainByTitle(train.getTitle());
                if (StringUtils.isNull(t)) {
                    train.setCreateBy(operName);
                    this.insertTrain(train);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、培训 " + train.getTitle() + " 导入成功");
                } else if (isUpdateSupport) {
                    train.setUpdateBy(operName);
                    this.updateTrain(train);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、培训 " + train.getTitle() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、培训 " + train.getTitle() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、培训 " + train.getTitle() + " 导入失败：";
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
     * 培训状态修改
     *
     * @param train 培训信息
     * @return 结果
     */
    @Override
    public int changeStatus(Train train) {
        return trainMapper.updateTrain(train);
    }
}
