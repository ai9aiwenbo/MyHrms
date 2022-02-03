package com.cwb.hrms.service;

import com.cwb.hrms.pojo.Train;

import java.util.List;

/**
 * 培训 业务层
 */
public interface ITrainService {
    /**
     * 根据条件分页查询培训列表
     *
     * @param train 培训信息
     * @return 培训信息集合信息
     */
    public List<Train> selectTrainList(Train train);


    /**
     * 通过标题查询培训
     *
     * @param title 培训标题
     * @return 培训对象信息
     */
    public Train selectTrainByTitle(String title);

    /**
     * 通过培训人查询培训
     *
     * @param trainName 培训人
     * @return 培训对象信息
     */
    public List<Train> selectTrainByTrainName(String trainName);

    /**
     * 通过培训ID查询培训
     *
     * @param tId 培训ID
     * @return 培训对象信息
     */
    public Train selectTrainById(Long tId);

    /**
     * 通过培训ID删除培训
     *
     * @param tId 培训ID
     * @return 结果
     */
    public int deleteTrainById(Long tId);

    /**
     * 批量删除培训信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     * @throws Exception 异常
     */
    public int deleteTrainByIds(String ids) throws Exception;

    /**
     * 保存培训信息
     *
     * @param train 培训信息
     * @return 结果
     */
    public int insertTrain(Train train);

    /**
     * 保存培训信息
     *
     * @param train 培训信息
     * @return 结果
     */
    public int updateTrain(Train train);

    /**
     * 修改培训详细信息
     *
     * @param train 培训信息
     * @return 结果
     */
    public int updateTrainInfo(Train train);


    /**
     * 校验培训标题是否唯一
     *
     * @param title 标题
     * @return 结果
     */
    public String checkTitleUnique(String title);

    /**
     * 根据培训ID查询培训员工
     *
     * @param tId 培训ID
     * @return 结果
     */
    public String selectTrainEmpGroup(Long tId);

    /**
     * 导入培训数据
     *
     * @param trainList        培训数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    public String importTrain(List<Train> trainList, Boolean isUpdateSupport);

    /**
     * 培训状态修改
     *
     * @param train 培训信息
     * @return 结果
     */
    public int changeStatus(Train train);
}
