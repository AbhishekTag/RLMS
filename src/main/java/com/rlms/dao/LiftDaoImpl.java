package com.rlms.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rlms.constants.RLMSConstants;
import com.rlms.constants.Status;
import com.rlms.contract.AMCDetailsDto;
import com.rlms.contract.LiftDtlsDto;
import com.rlms.contract.UserMetaInfo;
import com.rlms.model.RlmsLiftAmcDtls;
import com.rlms.model.RlmsLiftCustomerMap;
import com.rlms.model.RlmsLiftMaster;
import com.rlms.model.RlmsSpocRoleMaster;
@Repository
public class LiftDaoImpl implements LiftDao{
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<RlmsLiftCustomerMap> getAllLiftsForCustomers(List<Integer> listOfCuistomers){		
			 Session session = this.sessionFactory.getCurrentSession();
			 Criteria criteria = session.createCriteria(RlmsLiftCustomerMap.class)
					 .createAlias("branchCustomerMap.customerMaster", "custo")
					 .add(Restrictions.in("custo.customerId", listOfCuistomers))
					 .add(Restrictions.eq("activeFlag", RLMSConstants.ACTIVE.getId()));
			 List<RlmsLiftCustomerMap> listOfAllLifts = criteria.list();
			 return listOfAllLifts;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RlmsLiftCustomerMap> getAllLiftsToBeApproved(){		
			 Session session = this.sessionFactory.getCurrentSession();
			 Criteria criteria = session.createCriteria(RlmsLiftCustomerMap.class)
					 .createAlias("liftMaster", "LM")
					 .add(Restrictions.eq("LM.status", Status.PENDING_FOR_APPROVAL.getStatusId()))
					 .add(Restrictions.eq("LM.activeFlag", RLMSConstants.INACTIVE.getId()));
			 List<RlmsLiftCustomerMap> listOfLifts = criteria.list();
			 return listOfLifts;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public RlmsLiftCustomerMap getLiftCustomerMapByLiftId(Integer liftId){		
		 Session session = this.sessionFactory.getCurrentSession();
		 Criteria criteria = session.createCriteria(RlmsLiftCustomerMap.class)
				 .add(Restrictions.eq("liftMaster.liftId", liftId));
		 RlmsLiftCustomerMap liftCustomerMap = (RlmsLiftCustomerMap) criteria.uniqueResult();
		 return liftCustomerMap;
	
	}
	
	@Override
	public Integer saveLiftM(RlmsLiftMaster liftMaster){
		return (Integer)this.sessionFactory.getCurrentSession().save(liftMaster);		
	}
	
	@Override
	public Integer mergeLiftM(RlmsLiftMaster liftMaster){
		return (Integer)this.sessionFactory.getCurrentSession().merge(liftMaster);		
	}
	
	@Override
	public Integer saveLiftCustomerMap(RlmsLiftCustomerMap liftCustomerMap){
		return (Integer) this.sessionFactory.getCurrentSession().save(liftCustomerMap);
	}
	
	@Override
	public Integer saveLiftAMCDtls(RlmsLiftAmcDtls liftAmcDtls){
		return (Integer) this.sessionFactory.getCurrentSession().save(liftAmcDtls);
	}
	
	@Override
	public void updateLiftM(RlmsLiftMaster liftMaster){
		this.sessionFactory.getCurrentSession().update(liftMaster);		
	}
	
	@Override
	public void updateLiftCustomerMap(RlmsLiftCustomerMap liftCustomerMap){
		this.sessionFactory.getCurrentSession().update(liftCustomerMap);
	}
	
	@SuppressWarnings("unchecked")
	public List<RlmsLiftCustomerMap> getAllLiftsForBranchs(Integer branchCompanyId){		
			 Session session = this.sessionFactory.getCurrentSession();
			 Criteria criteria = session.createCriteria(RlmsLiftCustomerMap.class)
					 .createAlias("branchCustomerMap.companyBranchMapDtls", "branchCompanyMap")
					 .add(Restrictions.eq("branchCompanyMap.companyBranchMapId", branchCompanyId))
					 .add(Restrictions.eq("activeFlag", RLMSConstants.ACTIVE.getId()));
			 List<RlmsLiftCustomerMap> listOfAllLifts = criteria.list();
			 return listOfAllLifts;
		
	}
	
	@Override
	public RlmsLiftCustomerMap getLiftCustomerMapById(Integer liftCustomerMapId){		
		 Session session = this.sessionFactory.getCurrentSession();
		 Criteria criteria = session.createCriteria(RlmsLiftCustomerMap.class)
				 .add(Restrictions.eq("liftCustomerMapId", liftCustomerMapId));
		 RlmsLiftCustomerMap liftCustomerMap = (RlmsLiftCustomerMap) criteria.uniqueResult();
		 return liftCustomerMap;
	
	}
	
	@SuppressWarnings("unchecked")
	public List<RlmsLiftCustomerMap> getAllLiftsForBranchsOrCustomer(LiftDtlsDto dto){		
			 Session session = this.sessionFactory.getCurrentSession();
			 Criteria criteria = session.createCriteria(RlmsLiftCustomerMap.class)
					 .createAlias("branchCustomerMap.companyBranchMapDtls", "branchCompanyMap");
					 if(null != dto.getBranchCustomerMapId() && !RLMSConstants.MINUS_ONE.getId().equals(dto.getBranchCustomerMapId())){
						 criteria.add(Restrictions.eq("branchCustomerMap.branchCustoMapId", dto.getBranchCustomerMapId()));
					 }
					 if(null != dto.getBranchCompanyMapId()){
						 criteria.add(Restrictions.eq("branchCompanyMap.companyBranchMapId", dto.getBranchCompanyMapId()));
					 }
					 criteria.add(Restrictions.eq("activeFlag", RLMSConstants.ACTIVE.getId()));
			 List<RlmsLiftCustomerMap> listOfAllLifts = criteria.list();
			 return listOfAllLifts;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<RlmsLiftCustomerMap> getAllLiftsForCustomres(List<Integer> branchCustoMapId){		
			 Session session = this.sessionFactory.getCurrentSession();
			 Criteria criteria = session.createCriteria(RlmsLiftCustomerMap.class);				 
					  criteria.add(Restrictions.in("branchCustomerMap.branchCustoMapId", branchCustoMapId));					
					 criteria.add(Restrictions.eq("activeFlag", RLMSConstants.ACTIVE.getId()));
			 List<RlmsLiftCustomerMap> listOfAllLifts = criteria.list();
			 return listOfAllLifts;
		
	}
	
	@SuppressWarnings("unchecked")
	public RlmsLiftCustomerMap getLiftMasterForType(Integer branchCustoMapId, Integer liftType){		
		RlmsLiftCustomerMap liftCustomerMap = null;
			 Session session = this.sessionFactory.getCurrentSession();
			 Criteria criteria = session.createCriteria(RlmsLiftCustomerMap.class);			
			 		  criteria.createAlias("liftMaster", "lm");
					  criteria.add(Restrictions.eq("branchCustomerMap.branchCustoMapId", branchCustoMapId));
					  criteria.add(Restrictions.eq("lm.liftType", liftType));
					  criteria.add(Restrictions.eq("activeFlag", RLMSConstants.ACTIVE.getId()));
					  criteria.addOrder(Order.desc("lm.createdDate"));
			 List<RlmsLiftCustomerMap> listOfAllLifts = criteria.list();
			 if(null != listOfAllLifts && !listOfAllLifts.isEmpty()){
				 liftCustomerMap = (RlmsLiftCustomerMap) listOfAllLifts.get(RLMSConstants.ZERO.getId());
			 }
			 return liftCustomerMap;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<RlmsLiftAmcDtls> getAMCDetilsForLifts(List<Integer> listOfLiftsForAMCDtls, AMCDetailsDto dto){		
		
			 Session session = this.sessionFactory.getCurrentSession();
			 Criteria criteria = session.createCriteria(RlmsLiftAmcDtls.class);			
			 		  criteria.createAlias("liftCustomerMap", "lcm");
					  criteria.add(Restrictions.in("lcm.liftCustomerMapId", listOfLiftsForAMCDtls));
					  if(null != dto.getListOFStatusIds() && !dto.getListOFStatusIds().isEmpty()){
						  criteria.add(Restrictions.in("status", dto.getListOFStatusIds()));
					  }
					  
					  criteria.add(Restrictions.eq("activeFlag", RLMSConstants.ACTIVE.getId()));
					  criteria.addOrder(Order.asc("lm.createdDate"));
			 List<RlmsLiftAmcDtls> listOFAMCdtlsForAllLifts = criteria.list();
			 
			 return listOFAMCdtlsForAllLifts;
		
	}
	
	
	
	
}
