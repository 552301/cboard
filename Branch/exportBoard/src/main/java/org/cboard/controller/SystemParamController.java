package org.cboard.controller;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import org.cboard.dao.RoleDao;
import org.cboard.dao.UserDao;
import org.cboard.dto.DashboardMenu;
import org.cboard.dto.User;
import org.cboard.pojo.DashboardRole;
import org.cboard.pojo.DashboardUserRole;
import org.cboard.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/systemParam")
public class SystemParamController{
    @Autowired
    private AuthenticationService authenticationService;
    
    @Autowired
    private RoleDao roleDao;
    
    @Autowired
    private UserDao userDao;
    
    @RequestMapping(value = "/getSystemParam")
    public List<String> getSystemParam(@RequestParam(name = "name") String name){
    	List<String> values = new ArrayList<String>();
    	if(name == null && name.length() == 0)
    		return values;
    	
    	switch(name) {
    		case "CURRENTUSER_BRANDS": //获取当前用户所属的品牌
    			values = getCurrnetUserBrands();
    			break;
    		default:
    			break;
    	}
    	
    	return values;
    }
    
    private List<String> getCurrnetUserBrands(){
    	List<DashboardUserRole> userRoles = userDao.getUserRoleList();
    	String userId = authenticationService.getCurrentUser().getUserId();
        List<String> brands = new ArrayList<String>();
        for(DashboardUserRole userRole : userRoles) {
        	if(userRole.getUserId().equals(userId)) {
        		DashboardRole role = roleDao.getRole(userRole.getRoleId());
	        	String brand = role.getBrandName();
	        	if(brand != null) {
	        		if(!brands.contains(brand))
	        			brands.add(brand);
	        	}
        	}
        }
        
        return brands;
    }
}