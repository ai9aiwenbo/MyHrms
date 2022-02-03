package com.cwb.hrms.mapper;

import com.cwb.hrms.pojo.Train;
import com.cwb.hrms.pojo.User;

import java.util.List;

/**
 * 培训表 数据层
 */
public interface TrainMapper {
    /**
     * 根据条件分页查询培训列表
     *
     * @param train 培训信息
     * @return 培训信息集合信息
     */
    public List<Train> selectTrainList(Train train);


    /**
     * 通过培训标题查询培训
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
     */
    public int deleteTrainByIds(Long[] ids);

    /**
     * 修改培训信息
     *
     * @param train 培训信息
     * @return 结果
     */
    public int updateTrain(Train train);

    /**
     * 新增培训信息
     *
     * @param train 培训信息
     * @return 结果
     */
    public int insertTrain(Train train);

    /**
     * 校验培训名称是否唯一
     *
     * @param title 登录名称
     * @return 结果
     */
    public int checkTitleUnique(String title);

}
