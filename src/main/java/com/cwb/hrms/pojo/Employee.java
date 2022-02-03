package com.cwb.hrms.pojo;

import com.cwb.hrms.aspectj.Excel;
import com.cwb.hrms.pojo.entity.BaseEntity;

import java.util.Date;
import java.util.Objects;

/**
 * 员工信息
 */
public class Employee extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 员工ID
     */
    @Excel(name = "员工序号", prompt = "员工编号")
    private Long empId;

    /**
     * 部门ID
     */
    @Excel(name = "部门编号", type = Excel.Type.IMPORT)
    private Long deptId;

    /**
     * 部门对象
     */
    @Excel(name = "部门名称", targetAttr = "deptName", type = Excel.Type.EXPORT)
    private Dept dept;
    /**
     * 职位ID
     */
    @Excel(name = "职位ID", type = Excel.Type.IMPORT)
    private Long postId;
    /**
     * 职位对象
     */
    @Excel(name = "职位名称", targetAttr = "postName", type = Excel.Type.EXPORT)
    private Post post;
    /**
     * 员工姓名
     */
    @Excel(name = "员工姓名")
    private String name;
    /**
     * 用户性别
     */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;
    /**
     * 用户年龄
     */
    @Excel(name = "用户年龄")
    private Long age;
    /**
     * 员工邮箱
     */
    @Excel(name = "员工邮箱")
    private String email;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    private String phonenumber;


    /**
     * 员工状态（0正常 1停用）
     */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=离职")
    private String status;
    /**
     * 员工婚姻状况（0未婚 1已婚 2离异）
     */
    @Excel(name = "帐号状态", readConverterExp = "0=未婚,1=已婚,2=离异")
    private String wedlock;

    /**
     * 入职时间
     */
    @Excel(name = "入职时间", width = 30, dateFormat = "yyyy-MM-dd", type = Excel.Type.EXPORT)
    private Date beginDate;
    /**
     * 毕业学校
     */
    @Excel(name = "毕业学校")
    private String school;

    /**
     * 最高学历
     */
    @Excel(name = "最高学历")
    private String topDegree;
    /**
     * 培训是否有该员工
     */
    private Boolean flag;

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWedlock() {
        return wedlock;
    }

    public void setWedlock(String wedlock) {
        this.wedlock = wedlock;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getTopDegree() {
        return topDegree;
    }

    public void setTopDegree(String topDegree) {
        this.topDegree = topDegree;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", deptId=" + deptId +
                ", dept=" + dept +
                ", postId=" + postId +
                ", post=" + post +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", status='" + status + '\'' +
                ", wedlock='" + wedlock + '\'' +
                ", beginDate=" + beginDate +
                ", school='" + school + '\'' +
                ", topDegree='" + topDegree + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return
                Objects.equals(deptId, employee.deptId) &&
                        Objects.equals(dept, employee.dept) &&
                        Objects.equals(postId, employee.postId) &&
                        Objects.equals(post, employee.post) &&
                        Objects.equals(name, employee.name) &&
                        Objects.equals(sex, employee.sex) &&
                        Objects.equals(age, employee.age) &&
                        Objects.equals(email, employee.email) &&
                        Objects.equals(phonenumber, employee.phonenumber) &&
                        Objects.equals(status, employee.status) &&
                        Objects.equals(wedlock, employee.wedlock) &&
                        Objects.equals(beginDate, employee.beginDate) &&
                        Objects.equals(school, employee.school) &&
                        Objects.equals(topDegree, employee.topDegree);
    }

    @Override
    public int hashCode() {

        return Objects.hash(empId, deptId, dept, postId, post, name, sex, age, email, phonenumber, status, wedlock, beginDate, school, topDegree);
    }
}
