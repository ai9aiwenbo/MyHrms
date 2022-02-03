package com.cwb.hrms.mapper;

import com.cwb.hrms.pojo.TrainEmp;

import java.util.List;

/**
 * 培训与员工 表 数据层
 */
public interface TrainEmpMapper {
    /**
     * 通过培训ID删除培训和员工关联
     *
     * @param tId 用户ID
     * @return 结果
     */
    public int deleteTrainEmpBytId(Long tId);

    /**
     * 通过培训ID查询员工数量
     *
     * @param tId 岗位ID
     * @return 结果
     */
    public int countEmpBytId(Long tId);

    /**
     * 批量删除培训和员工关联
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTrainEmp(Long[] ids);

    /**
     * 批量新增培训员工信息
     *
     * @param trainEmpList 培训员工列表
     * @return 结果
     */
    public int batchTrainEmp(List<TrainEmp> trainEmpList);
}
