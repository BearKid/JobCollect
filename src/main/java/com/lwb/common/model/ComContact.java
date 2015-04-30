package com.lwb.common.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Entity
@DynamicUpdate
@DynamicInsert
@SelectBeforeUpdate
public class ComContact implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id ;
	private Integer comId;//企业ID
	@Embedded
	private ComContactBase comContactBase;
	private Integer defaultFlag;//1为默认联系方式
	private Date createDate; //创建日期
	private Date updateDate;// 修改日期
	private Integer delStatus;//-1已删除

     // ============== getter && setter =======================

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
	public ComContactBase getComContactBase() {
		return comContactBase;
	}
	public void setComContactBase(ComContactBase comContactBase) {
		this.comContactBase = comContactBase;
	}
	public Integer getDefaultFlag() {
		return defaultFlag;
	}
	public void setDefaultFlag(Integer defaultFlag) {
		this.defaultFlag = defaultFlag;
	}
	public Date getCreateDate() {
		return createDate ;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate ;
	}
	public Date getUpdateDate() {
		return updateDate ;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate ;
	}
	public Integer getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(Integer delStatus) {
		this.delStatus = delStatus;
	}
}
