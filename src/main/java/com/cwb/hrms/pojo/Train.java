package com.cwb.hrms.pojo;

import com.cwb.hrms.aspectj.Excel;
import com.cwb.hrms.pojo.entity.BaseEntity;

import java.util.Date;

/**
 * 培训实体 sys_train
 */
public class Train extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 培训ID
     */
    @Excel(name = "培训序号", prompt = "培训编号")
    private Long tId;
    /**
     * 培训标题
     */
    @Excel(name = "培训标题")
    private String title;
    /**
     * 培训内容
     */
    @Excel(name = "培训内容")
    private String content;
    /**
     * 培训人
     */
    @Excel(name = "培训人")
    private String trainName;
    /**
     * 培训状态（0开始 1结束）
     */
    @Excel(name = "培训状态", readConverterExp = "0=开始,1=结束")
    private String status;
    /**
     * 培训时间
     */
    @Excel(name = "培训开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    private Date trainDate;
    /**
     * 培训地址
     */
    @Excel(name = "培训地址")
    private String address;

    /**
     * 员工列表
     */
    private Long[] empIds;

    public Long[] getEmpIds() {
        return empIds;
    }

    public void setEmpIds(Long[] empIds) {
        this.empIds = empIds;
    }

    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTrainDate() {
        return trainDate;
    }

    public void setTrainDate(Date trainDate) {
        this.trainDate = trainDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Train{" +
                "tId=" + tId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", trainName='" + trainName + '\'' +
                ", status='" + status + '\'' +
                ", trainDate=" + trainDate +
                ", address='" + address + '\'' +
                '}';
    }
}
