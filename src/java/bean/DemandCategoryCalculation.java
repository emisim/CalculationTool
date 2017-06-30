/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author pc&
 */
@Entity
public class DemandCategoryCalculation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private DepartementCriteria departementCriteria;
    private BigDecimal sum;
    @ManyToOne
    private DemandCategory demandCategory;
    private boolean valide;
    @OneToMany(mappedBy = "demandCategoryCalculation")
    private List<DemandCategoryCalculationItem> demandCategoryCalculationItems;

    public List<DemandCategoryCalculationItem> getDemandCategoryCalculationItems() {
        return demandCategoryCalculationItems;
    }

    public void setDemandCategoryCalculationItems(List<DemandCategoryCalculationItem> demandCategoryCalculationItems) {
        this.demandCategoryCalculationItems = demandCategoryCalculationItems;
    }

    public DepartementCriteria getDepartementCriteria() {
        return departementCriteria;
    }

    public void setDepartementCriteria(DepartementCriteria departementCriteria) {
        this.departementCriteria = departementCriteria;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public DemandCategory getDemandCategory() {
        return demandCategory;
    }

    public void setDemandCategory(DemandCategory demandCategory) {
        this.demandCategory = demandCategory;
    }

    public boolean isValide() {
        return valide;
    }

    public void setValide(boolean valide) {
        this.valide = valide;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DemandCategoryCalculation)) {
            return false;
        }
        DemandCategoryCalculation other = (DemandCategoryCalculation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.DemandCategoryCalculation[ id=" + id + " ]";
    }

}