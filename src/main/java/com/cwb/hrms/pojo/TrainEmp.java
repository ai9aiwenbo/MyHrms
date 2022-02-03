package com.cwb.hrms.pojo;

/**
 * 培训和员工关联 sys_train_emp
 */
public class TrainEmp {

    /**
     * 培训ID
     */
    private Long tId;
    /**
     * 员工ID
     */
    private Long empId;

    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    @Override
    public String toString() {
        return "TrainEmp{" +
                "tId=" + tId +
                ", empId=" + empId +
                '}';
    }
}
