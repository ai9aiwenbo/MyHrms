package com.cwb.hrms.pojo;

import com.cwb.hrms.aspectj.Excel;
import com.cwb.hrms.pojo.entity.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 薪资实体类
 */
public class Salary extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 薪资ID
     */
    @Excel(name = "薪资编号")
    private Long sId;

    /**
     * 员工ID
     */
    @Excel(name = "员工编号", type = Excel.Type.IMPORT)
    private Long empId;

    /**
     * 部门对象
     */
    @Excel(name = "部门名称", targetAttr = "deptName", type = Excel.Type.EXPORT)
    private Dept dept;
    /**
     * 岗位对象
     */
    @Excel(name = "岗位名称", targetAttr = "postName", type = Excel.Type.EXPORT)
    private Post post;

    /**
     * 员工对象
     */
    @Excel(name = "员工名称", targetAttr = "name", type = Excel.Type.EXPORT)
    private Employee employee;

    /**
     * 基本工资
     */
    @Excel(name = "基本工资")
    private Long basicSalary;

    /**
     * 奖金
     */
    @Excel(name = "奖金")
    private Long bonus;
    /**
     * 午餐补助
     */
    @Excel(name = "午餐补助")
    private Long lunchSalary;
    /**
     * 交通补助
     */
    @Excel(name = "交通补助")
    private Long trafficSalary;
    /**
     * 实际工资
     */
    @Excel(name = "实际工资",type = Excel.Type.EXPORT)
    private Long allSalary;


    /**
     * 发放日期
     */
    @Excel(name = "发放日期", dateFormat = "yyyy-MM-dd",type = Excel.Type.EXPORT)
    private Date paytime;

    /**
     * 是否发放
     */
    @Excel(name = "发放情况", readConverterExp = "0=已发放,1=未发放")
    private String isPay;

    public Long getsId() {
        return sId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Long getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Long basicSalary) {
        this.basicSalary = basicSalary;
    }

    public Long getBonus() {
        return bonus;
    }

    public void setBonus(Long bonus) {
        this.bonus = bonus;
    }

    public Long getLunchSalary() {
        return lunchSalary;
    }

    public void setLunchSalary(Long lunchSalary) {
        this.lunchSalary = lunchSalary;
    }

    public Long getTrafficSalary() {
        return trafficSalary;
    }

    public void setTrafficSalary(Long trafficSalary) {
        this.trafficSalary = trafficSalary;
    }

    public Long getAllSalary() {
        return allSalary;
    }

    public void setAllSalary(Long allSalary) {
        this.allSalary = allSalary;
    }

    public Date getPaytime() {
        return paytime;
    }

    public void setPaytime(Date paytime) {
        this.paytime = paytime;
    }

    public String getIsPay() {
        return isPay;
    }

    public void setIsPay(String isPay) {
        this.isPay = isPay;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "sId=" + sId +
                ", empId=" + empId +
                ", dept=" + dept +
                ", post=" + post +
                ", employee=" + employee +
                ", basicSalary=" + basicSalary +
                ", bonus=" + bonus +
                ", lunchSalary=" + lunchSalary +
                ", trafficSalary=" + trafficSalary +
                ", allSalary=" + allSalary +
                ", paytime=" + paytime +
                ", isPay='" + isPay + '\'' +
                '}';
    }
}
