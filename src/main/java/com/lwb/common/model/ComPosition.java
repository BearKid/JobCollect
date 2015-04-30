package com.lwb.common.model;

import org.hibernate.annotations.*;
import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;
/**
 * 
* @ClassName: ComPosition 
* @Description: 公司职位实体类 
* @author xh 
* @date 2013-7-2 上午9:49:45 
*
 */
@Entity
@DynamicUpdate
@DynamicInsert
@SelectBeforeUpdate
public class ComPosition implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id ;
	private Integer comId;                                  //公司关联ID
	private Integer departmentId;                           //部门关联ID
	private Integer comUserId;                              //发布职位公司账户ID
	private String posType;                                 //职位类别用json方式保存
	private String posName;                                 //职位名称
	private Integer urgent;                                 //是否为急聘1=是
	private String posKeyword;                              //职位关键字用json方式保存 例如:["行政","销售","教育"]
	private Integer property;                               //工作性质，0=未选择，1=全职，2=兼职，3=实习
	private Integer recruitmentNumber;                      //招聘人数，0=若干
	private Date endDate;                                   //截止时间
	private String workLocation;                            //工作地区用json方式保存
	private Integer salary;                                 //薪资待遇
	private Integer negotiableFlag;                         //是否显示为面议   '是否面议，0,Null=否，1=是'
	private String taoLabel;                                //淘标签多个以json array保存
	private Integer reqGender;                              //性别要求，0=未选择，1=男，2=女，3=不限
	private Integer reqDegree;                              //学历要求，0=未选择，
	private Integer reqAgeMin;                              //最小年龄,0=未选择
	private Integer reqAgeMax;                              //最大年龄，0=未选择
	private Integer reqWorkYear;                            //工作经验，0=未选择
	private Integer reqLocation;                            //现所在地，0=未选择
	private Integer reqLanguage;                            //语言要求，0=未选择
	private Integer reqLanguageLevel;                       //语言等级，0=未选择
	private Integer filterDegree;                           //学历是否必须符合要求，0=否，1=是
	private Integer filterAge;                              //年龄是否必须符合要求，0=否，1=是
	private Integer filterWorkYear;                         //工作经验是否必须符合要求，0=否，1=是
	private Integer filterGender;                           //性别是否必须符合要求，0=否，1=是
	private String description;                             //职位描述
	private String interviewNote;                           //面试须知
	private Integer autoReply;                              //接收到简历后是否自动回复，0=否，1=是
	private Integer schoolFlag;                             //是否为校园职位，0=否，1=是
	private Date createDate;                                //创建日期
	private Date updateDate;                                //修改日期
	private Date refreshDate;                               //职位刷新时间
	private Integer posStatus;                              //职位标识，解释见 Constants
	private Integer auditorId;                              //审核人id
	private Date auditDate;                                 //审核日期
	private Integer delStatus;                              //是否已删除，-1=已删除
	private Double score;                                   //职位得分
	private Integer viewCount;                              //职位被查看次数
	private Integer resumeCount;                            //收到的简历数
	private Integer resumeUnreadCount;                      //未读简历数
    //private Integer acceptResumeType;                       // -1:所有(包含名片)  0:正式简历(默认,不包含名片) 1：仅长沙简历 2：仅名片,参见 CommonEnum.acceptResumeType
	@Embedded
	private ComContactBase comContactBase;

     // ====================== getter && setter  ==============================
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getComId() {
		return comId;
	}
	public void setComId(Integer comId) {
		this.comId = comId;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public Integer getComUserId() {
		return comUserId;
	}
	public void setComUserId(Integer comUserId) {
		this.comUserId = comUserId;
	}
	public String getPosType() {
		return posType;
	}
	public void setPosType(String posType) {
		this.posType = posType;
	}
	public String getPosName() {
		return posName;
	}
	public void setPosName(String posName) {
		this.posName = posName;
	}
	public Integer getUrgent() {
		return urgent;
	}
	public void setUrgent(Integer urgent) {
		this.urgent = urgent;
	}
	public String getPosKeyword() {
		return posKeyword;
	}
	public void setPosKeyword(String posKeyword) {
		this.posKeyword = posKeyword;
	}
	public Integer getProperty() {
		return property;
	}
	public void setProperty(Integer property) {
		this.property = property;
	}
	public Integer getRecruitmentNumber() {
		return recruitmentNumber;
	}
	public void setRecruitmentNumber(Integer recruitmentNumber) {
		this.recruitmentNumber = recruitmentNumber;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getWorkLocation() {
		return workLocation;
	}
	public void setWorkLocation(String workLocation) {
		this.workLocation = workLocation;
	}
	public Integer getSalary() {
		return salary;
	}
	public void setSalary(Integer salary) {
		this.salary = salary;
	}
	public Integer getNegotiableFlag() {
		return negotiableFlag;
	}
	public void setNegotiableFlag(Integer negotiableFlag) {
		this.negotiableFlag = negotiableFlag;
	}
	public String getTaoLabel() {
		return taoLabel;
	}
	public void setTaoLabel(String taoLabel) {
		this.taoLabel = taoLabel;
	}
	public Integer getReqGender() {
		return reqGender;
	}
	public void setReqGender(Integer reqGender) {
		this.reqGender = reqGender;
	}
	public Integer getReqDegree() {
		return reqDegree;
	}
	public void setReqDegree(Integer reqDegree) {
		this.reqDegree = reqDegree;
	}
	public Integer getReqAgeMin() {
		return reqAgeMin;
	}
	public void setReqAgeMin(Integer reqAgeMin) {
		this.reqAgeMin = reqAgeMin;
	}
	public Integer getReqAgeMax() {
		return reqAgeMax;
	}
	public void setReqAgeMax(Integer reqAgeMax) {
		this.reqAgeMax = reqAgeMax;
	}
	public Integer getReqWorkYear() {
		return reqWorkYear;
	}
	public void setReqWorkYear(Integer reqWorkYear) {
		this.reqWorkYear = reqWorkYear;
	}
	public Integer getReqLocation() {
		return reqLocation;
	}
	public void setReqLocation(Integer reqLocation) {
		this.reqLocation = reqLocation;
	}
	public Integer getReqLanguage() {
		return reqLanguage;
	}
	public void setReqLanguage(Integer reqLanguage) {
		this.reqLanguage = reqLanguage;
	}
	public Integer getReqLanguageLevel() {
		return reqLanguageLevel;
	}
	public void setReqLanguageLevel(Integer reqLanguageLevel) {
		this.reqLanguageLevel = reqLanguageLevel;
	}
	public Integer getFilterDegree() {
		return filterDegree;
	}
	public void setFilterDegree(Integer filterDegree) {
		this.filterDegree = filterDegree;
	}
	public Integer getFilterAge() {
		return filterAge;
	}
	public void setFilterAge(Integer filterAge) {
		this.filterAge = filterAge;
	}
	public Integer getFilterWorkYear() {
		return filterWorkYear;
	}
	public void setFilterWorkYear(Integer filterWorkYear) {
		this.filterWorkYear = filterWorkYear;
	}
	public Integer getFilterGender() {
		return filterGender;
	}
	public void setFilterGender(Integer filterGender) {
		this.filterGender = filterGender;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getInterviewNote() {
		return interviewNote;
	}
	public void setInterviewNote(String interviewNote) {
		this.interviewNote = interviewNote;
	}
	public Integer getAutoReply() {
		return autoReply;
	}
	public void setAutoReply(Integer autoReply) {
		this.autoReply = autoReply;
	}
	public Integer getSchoolFlag() {
		return schoolFlag;
	}
	public void setSchoolFlag(Integer schoolFlag) {
		this.schoolFlag = schoolFlag;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Date getRefreshDate() {
		return refreshDate;
	}
	public void setRefreshDate(Date refreshDate) {
		this.refreshDate = refreshDate;
	}
	public Integer getPosStatus() {
		return posStatus;
	}
	public void setPosStatus(Integer posStatus) {
		this.posStatus = posStatus;
	}
	public Integer getAuditorId() {
		return auditorId;
	}
	public void setAuditorId(Integer auditorId) {
		this.auditorId = auditorId;
	}
	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	public Integer getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(Integer delStatus) {
		this.delStatus = delStatus;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public Integer getViewCount() {
		return viewCount;
	}
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
	public Integer getResumeCount() {
		return resumeCount;
	}
	public void setResumeCount(Integer resumeCount) {
		this.resumeCount = resumeCount;
	}
	public Integer getResumeUnreadCount() {
		return resumeUnreadCount;
	}
	public void setResumeUnreadCount(Integer resumeUnreadCount) {
		this.resumeUnreadCount = resumeUnreadCount;
	}

//    public Integer getAcceptResumeType() {
//        return acceptResumeType;
//    }

//    public void setAcceptResumeType(Integer acceptResumeType) {
//        this.acceptResumeType = acceptResumeType;
//    }
}
