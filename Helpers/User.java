package helpers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;


public class User {
    private int wwid;
    private String name;
    private String password;
    private boolean activated;
    private int departmentId;
    private String jobTitle;
    private int shiftId;
    private int taskId;
    private String status;
    private Date startDate;
    private Date leavingDate;
    private String badgeColor;
    private String reasonForLeaving;
    private int areaId;
    private int trainingId;
    private String userType;
    private int siteId;

    @Override
    public String toString() {
        return "User{" +
                "wwid=" + wwid +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", activated=" + activated +
                ", departmentId=" + departmentId +
                ", jobTitle='" + jobTitle + '\'' +
                ", shiftId=" + shiftId +
                ", taskId=" + taskId +
                ", status='" + status + '\'' +
                ", startDate=" + startDate +
                ", leavingDate=" + leavingDate +
                ", badgeColor='" + badgeColor + '\'' +
                ", reasonForLeaving='" + reasonForLeaving + '\'' +
                ", areaId=" + areaId +
                ", trainingId=" + trainingId +
                ", userType='" + userType + '\'' +
                ", siteId=" + siteId +
                '}';
    }
    public int getWwid() {
        return wwid;
    }

    public void setWwid(int wwid) {
        this.wwid = wwid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getLeavingDate() {
        return leavingDate;
    }

    public void setLeavingDate(Date leavingDate) {
        this.leavingDate = leavingDate;
    }

    public String getBadgeColor() {
        return badgeColor;
    }

    public void setBadgeColor(String badgeColor) {
        this.badgeColor = badgeColor;
    }

    public String getReasonForLeaving() {
        return reasonForLeaving;
    }

    public void setReasonForLeaving(String reasonForLeaving) {
        this.reasonForLeaving = reasonForLeaving;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(int trainingId) {
        this.trainingId = trainingId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }
        private String formatDate(Date date) {
        if (date == null) {
            return "null";
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(date);
        }
    }
}
