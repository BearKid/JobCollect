package com.lwb.collect.service.fix;

import com.lwb.common.model.ComContact;
import com.lwb.common.model.ComInfo;
import com.lwb.common.model.ComPosition;
import com.lwb.common.utils.ApplicationUtils;
import com.lwb.common.utils.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p></p>
 * Date: 2015/4/21 10:02
 *
 * @version 1.0
 * @autor: Lu Weibiao
 */
@Service
@Lazy
public class ImportJobs {
    @Resource
    private HibernateTemplate downJobProdHibernateTemplate;
    @Resource
    private HibernateTemplate downJobDevHibernateTemplate;

    public static void main(String[] args){
        Date startDate = DateUtils.parseDate("2015-04-21 00:00:00");
        Date endDate = DateUtils.parseDate("2015-04-22 00:00:00");

        ImportJobs importJobs = (ImportJobs)ApplicationUtils.getBean("importJobs");
//        importJobs.saveComInfoTestDev();
//        importJobs.saveComInfoTestProd();
        importJobs.run(startDate,endDate);
        System.out.println();
    }
    @Transactional(value = "downJobProdTransactionManager")
    public void saveComInfoTestProd(){
        ComInfo comInfo = new ComInfo();
        comInfo.setComName("del");
        comInfo.setSalerName("del");
        comInfo.setCreateDate(new Date());
        downJobProdHibernateTemplate.save(comInfo);
    }

    @Transactional(value = "downJobDevTransactionManager")
    public void saveComInfoTestDev(){
        ComInfo comInfo = new ComInfo();
        comInfo.setComName("del");
        comInfo.setSalerName("del");
        comInfo.setCreateDate(new Date());
        downJobDevHibernateTemplate.save(comInfo);
    }

    @Transactional(value = "downJobProdTransactionManager")//TODO 为什么要在这里加事务管理器，写操作才不报错
    public void run(Date startDate, Date endDate){
        List<ComInfo> comInfoList = getComInfoListFromDev(startDate,endDate);
        for(ComInfo srcComInfo : comInfoList){
            Integer comId = srcComInfo.getId();
            ComContact srcComContact = getComContactFromDev(comId);
            List<ComPosition> srcComPositionList = getComPositionListFromDev(comId);

            ComInfo destComInfo = new ComInfo();
            BeanUtils.copyProperties(srcComInfo,destComInfo);

            ComContact destComContact = new ComContact();
            BeanUtils.copyProperties(srcComContact,destComContact);

            List<ComPosition> destComPostionList = new ArrayList<ComPosition>(srcComPositionList.size());
            for(ComPosition srcComPosition : srcComPositionList){
                ComPosition destComPosition = new ComPosition();
                BeanUtils.copyProperties(srcComPosition,destComPosition);
                destComPostionList.add(destComPosition);
            }
            saveJobsOfOneComToProd(destComInfo, destComContact, destComPostionList);//TODO 为什么要在被调用方法里声明事务管理器，写操作才不报错
        }
    }

    @Transactional(value = "downJobProdTransactionManager")
    public void saveJobsOfOneComToProd(ComInfo comInfo, final ComContact comContact, final List<ComPosition> comPositionList) {
       Date now = new Date();
        ComInfo comInfoTemp = getExistedComInfo(comInfo.getComName());
        if(comInfoTemp == null) {
            comInfo.setCreateDate(now);
            comInfo.setUpdateDate(now);
            downJobProdHibernateTemplate.save(comInfo);
        } else{
            comInfo = comInfoTemp;
        }
        Integer comId = comInfo.getId();

        if(!isCompanyHasContact(comId)) {
            comContact.setComId(comId);
            comContact.setCreateDate(comInfo.getCreateDate());
            downJobProdHibernateTemplate.save(comContact);
        }

        for(ComPosition comPosition : comPositionList) {
            comPosition.setComId(comId);
            comPosition.setCreateDate(now);
            comPosition.setUpdateDate(now);
            comPosition.setEndDate(org.apache.commons.lang.time.DateUtils.addMonths(now,3));
            downJobProdHibernateTemplate.save(comPosition);
        }
    }

    @Transactional(value = "downJobDevTransactionManager",readOnly = true)
    public List<ComInfo> getComInfoListFromDev(Date startDate, Date endDate){
        String hql = "from ComInfo com where com.createDate >=? and com.createDate < ?";
        Object[] params = new Object[]{
                startDate,endDate
        };
        return (List<ComInfo>)downJobDevHibernateTemplate.find(hql,params);
    }

    @Transactional(value = "downJobDevTransactionManager",readOnly = true)
    public ComContact getComContactFromDev(final Integer comId){

        ComContact comContact = downJobDevHibernateTemplate.execute(new HibernateCallback<ComContact>() {
            @Override
            public ComContact doInHibernate(Session session) throws HibernateException {
                String hql = "from ComContact cc where cc.comId = ?";
                Query query = session.createQuery(hql);
                query.setParameter(0,comId);
                return (ComContact)query.uniqueResult();
            }
        });
        return comContact;
    }

    @Transactional(value = "downJobDevTransactionManager",readOnly = true)
    public List<ComPosition> getComPositionListFromDev(Integer comId){
        String hql = "from ComPosition p where p.comId = ?";
        Object[] params = new Object[]{comId};
        return (List<ComPosition>)downJobDevHibernateTemplate.find(hql,params);
    }

    /**
     * 根据传入的ComInfo尝试查出对应的已采集的公司信息
     * 目前已公司名作为查询条件
     * @param comName
     * @return
     */
    @Transactional(value = "downJobProdTransactionManager",readOnly = true)
    private ComInfo getExistedComInfo(final String comName) {
        if(StringUtils.isBlank(comName)){
            throw new RuntimeException("ComInfo.comName is empty");
        }
        final String hql = "select c from ComInfo c where c.comName = ?";
        return downJobProdHibernateTemplate.execute(new HibernateCallback<ComInfo>() {
            @Override
            public ComInfo doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                query.setParameter(0,comName);
                return (ComInfo)query.uniqueResult();
            }
        });
    }
    /**
     * 判断公司是否已有联系信息
     * @param comInfoId
     * @return
     */
    @Transactional(value = "downJobProdTransactionManager",readOnly = true)
    public boolean isCompanyHasContact(final Integer comInfoId) {
        if(comInfoId == null){
            throw new NullPointerException("comInfoId is null");
        }
        final String hql = "select id from ComContact where comId = ?";
        return downJobProdHibernateTemplate.execute(new HibernateCallback<Boolean>() {
            @Override
            public Boolean doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                query.setParameter(0,comInfoId);
                query.setMaxResults(1);
                return query.uniqueResult() != null;
            }
        });
    }
}
