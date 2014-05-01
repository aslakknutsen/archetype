/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventions.archetype.service;

import org.conventions.archetype.event.UpdateList;
import org.conventions.archetype.model.Group;
import org.conventions.archetype.model.Role;
import org.conventions.archetype.qualifier.ListToUpdate;
import org.conventions.archetype.util.AppConstants;
import org.conventionsframework.exception.BusinessException;
import org.conventionsframework.model.SearchModel;
import org.conventionsframework.service.impl.BaseServiceImpl;
import org.conventionsframework.util.ResourceBundle;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

/**
 *
 * @author rmpestano
 *
 * NON EJB Service
 */
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Inject
    @ListToUpdate(ListToUpdate.ListType.ROLE)
    Event<UpdateList> updateRoleList;

    @Inject
    ResourceBundle resourceBundle;
    
    @Override
    public DetachedCriteria configPagination(SearchModel<Role> searchModel) {
        
        DetachedCriteria dc = getDetachedCriteria();
        Map<String,Object> searchFilter = searchModel.getFilter();
        Role searchEntity = searchModel.getEntity();
        List<Long> groupRoles = (List<Long>) searchFilter.get("groupRoles");
        if(groupRoles != null && !groupRoles.isEmpty()){
            //open roleModal listing only with not used groups
            //PS: not used cause roleAssociation is done with picklist now not with list of values anymore
            dc.add(Restrictions.not(Restrictions.in("id", groupRoles)));
        }
        return super.configPagination(searchModel, dc);
    }

    @Override
    public void afterStore(Role entity) {
        updateRoleList.fire(new UpdateList());
    }

    @Override
    public void afterRemove(Role entity) {
        updateRoleList.fire(new UpdateList());
    }

    @Override
    public void beforeRemove(Role entity) {
        if(entity.getName().equals(AppConstants.Role.ADMIN)){
            throw new BusinessException(resourceBundle.getString("role.be.admin"));
        }
        if(roleIsAssociatedWithGroups(entity)){
            throw new BusinessException(resourceBundle.getString("role.be.groups"));
        }
    }

    private boolean roleIsAssociatedWithGroups(Role entity) {
        Query q = getEntityManager().createNamedQuery("Group.findGroupsWithrole");
        q.setParameter("roleId",entity.getId());
        List<Group> groupsFound = q.getResultList();
        return !groupsFound.isEmpty();
    }

}
