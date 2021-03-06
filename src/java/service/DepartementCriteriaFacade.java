/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.DemandCategoryCalculation;
import bean.DemandCategoryCalculationItem;
import bean.DemandCategoryDepartementCalculation;
import bean.Departement;
import bean.DepartementCriteria;
import bean.DepartementCriteriaItem;
import bean.DepartementDetail;
import bean.User;
import controler.util.SearchUtil;
import controler.util.SessionUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.script.ScriptException;

/**
 *
 * @author
 */
@Stateless
public class DepartementCriteriaFacade extends AbstractFacade<DepartementCriteria> {

    @PersistenceContext(unitName = "kt_FST_2PU")
    private EntityManager em;

    private @EJB
    DepartementCriteriaItemFacade departementCriteriaItemFacade;
    @EJB
    private DepartementFacade departementFacade;
    @EJB
    private DemandCategoryDepartementCalculationFacade demandCategoryDepartementCalculationFacade;

    public List<DepartementCriteria> findDepartementCriteriaWithItemsByDepartement(Departement departement) {
        List<DepartementCriteria> departementCriterias = findByDepartement(departement);
        for (DepartementCriteria departementCriteria : departementCriterias) {
            departementCriteria.setDepartementCriteriaItems(departementCriteriaItemFacade.findByDepartementCriteria(departementCriteria));
        }
        return departementCriterias;
    }

    public List<DepartementCriteria> findByDepartement(Departement departement) {
        String query = "SELECT item FROM DepartementCriteria item WHERE 1=1";
        if (departement != null && departement.getId() != null) {
            query += SearchUtil.addConstraint("item", "departement.id", "=", departement.getId());
        }
        return em.createQuery(query).getResultList();
    }

    public List<DepartementDetail> detailDepartement(List<DemandCategoryDepartementCalculation> demandCategoryDepartementCalculations) throws ScriptException {

        List<DepartementDetail> departementDetails = new ArrayList<>();
        int i = 0;
        for (DemandCategoryDepartementCalculation demandCategoryDepartementCalculation : demandCategoryDepartementCalculations) {
            String summ = demandCategoryDepartementCalculation.getSumme() + "";
            for (DemandCategoryCalculation demandCategoryCalculation : demandCategoryDepartementCalculation.getDemandCategoryCalculations()) {
                System.out.println("Departement ::::::::::: " + demandCategoryCalculation);
                String summCriteria = demandCategoryCalculation.getSumme() + "";
                String nomDepCriteria = demandCategoryCalculation.getDepartementCriteria().getName();
                for (DemandCategoryCalculationItem demandCategoryCalculationItem : demandCategoryCalculation.getDemandCategoryCalculationItems()) {
                    DepartementCriteriaItem departementCriteriaItem = demandCategoryCalculationItem.getDepartementCriteriaItem();
                    DepartementDetail departementDetail = new DepartementDetail();
                    departementDetail.setPrice(demandCategoryCalculationItem.getPrice() + "");
                    departementDetail.setPriceUpdate(demandCategoryCalculationItem.getPriceUpdate()+ "");
                    departementDetail.setPriceGlobal(demandCategoryCalculationItem.getPriceGlobal() + "");
                    departementDetail.setPriceGlobalUpdate(demandCategoryCalculationItem.getPriceGlobalUpdate()+ "");
                    departementDetail.setNomDepCritera(nomDepCriteria);
                    departementDetail.setDescrDepCriteriaItem(departementCriteriaItem.getDescription());
                    departementDetail.setDescrDepCriteriaItemGlobal(departementCriteriaItem.getDescriptionGlobal());
                    departementDetail.setArithmitiqueExpresionForUnitePrice(departementCriteriaItem.getArithmitiqueExpresionForUnitePrice());
                    departementDetail.setArithmitiqueExpresionForGlobalPrice(departementCriteriaItem.getArithmitiqueExpresionForGlobalPrice());
                    departementDetail.setSummCriteria(summCriteria);
                    departementDetail.setSummDepartement(summ);
                    departementDetail.setChecked(demandCategoryCalculationItem.getCalcultaed());
                    departementDetail.setId(new Long(i));
                    departementDetail.setDemandCategoryCalcuationId(demandCategoryCalculation.getId());
                    departementDetail.setDemandCategoryDepartementCalculationId(demandCategoryDepartementCalculation.getId());
                    departementDetail.setDemandCategoryCalculationItemId(demandCategoryCalculationItem.getId());
                    departementDetails.add(departementDetail);
                    i++;
                }
            }
        }

        return departementDetails;
    }

    public List<List<DepartementDetail>> allDetailDepartements() {
        List<Departement> departements = departementFacade.findAll();
        List<List<DepartementDetail>> departementDetails = new ArrayList<>();
//        for (Departement departement : departements) {
//            departementDetails.add(detailDepartement(departement));
//        }
        return departementDetails;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DepartementCriteriaFacade() {
        super(DepartementCriteria.class);
    }

    public void deleteCriteriaWithCriteriaItem(DepartementCriteria criteria) {
        System.out.println("hahowa criteria : " + criteria);
        String requette = "select item from DepartementCriteriaItem item where item.departementCriteria.id ='" + criteria.getId() + "'";
        System.out.println("hahiya requette : " + requette);
        List<DepartementCriteriaItem> departementCriteriaItems = em.createQuery(requette).getResultList();

        for (DepartementCriteriaItem departementCriteriaItem : departementCriteriaItems) {
            departementCriteriaItemFacade.remove(departementCriteriaItem);

        }
        remove(criteria);
    }

    public List<DepartementCriteria> findByUser() {

        User connectedUser = SessionUtil.getConnectedUser();
        String requette = "select dep from DepartementCriteria dep where dep.departement.id = '" + connectedUser.getDepartement().getId() + "'";
        return em.createQuery(requette).getResultList();
    }

}
