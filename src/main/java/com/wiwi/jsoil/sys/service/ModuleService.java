package com.wiwi.jsoil.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.exception.DaoException;
import com.wiwi.jsoil.sys.dao.ModuleDao;
import com.wiwi.jsoil.sys.model.Module;
import com.wiwi.jsoil.sys.model.ModuleQ;

public class ModuleService {
	private static final Logger logger = LoggerFactory.getLogger(ModuleService.class);
	public long save(Module instance) throws DaoException {
		ModuleDao dao = new ModuleDao();
		return dao.save(instance);
	}

	public void update(Module instance) throws DaoException {
		ModuleDao dao = new ModuleDao();
		dao.update(instance);
	}

	public void delete(int id) throws DaoException {
		batchDelete(id+"");
	}

	
	public void batchDelete(String ids) throws DaoException {
		if (ids == null || ids.equalsIgnoreCase("")) {
			return;
		}

		ModuleDao dao = new ModuleDao();
		dao.delete(ids);
	}
	
	public Module get(int id) throws DaoException {
		ModuleDao dao = new ModuleDao();
		return dao.get(id);
	}

	@SuppressWarnings("rawtypes")
	public List getList(PageUtil pageUtil) throws DaoException {
		ModuleDao dao = new ModuleDao();
		return dao.getList(pageUtil);
	}
	
	
	@SuppressWarnings("rawtypes")
	public List getAllModuleList( Long userId) throws Exception {
		ModuleDao moduleDao = new ModuleDao();
		return moduleDao.getMenuList("", userId);
	}
	
	/**
	 * 得到该用户组所有有权限的功能清单
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List getMenuListByGroupId( int groupId) throws Exception {
		ModuleDao moduleDao = new ModuleDao();
		return moduleDao.getMenuListByGroupId(groupId);
	}

	
	/**
	 * 返回该用户拥有权限的所有模块（嵌套返回）
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Module> getUserModuleList(Long userId){
		ModuleService serverice = new ModuleService();
		List<Module> topModuleList = new ArrayList<Module>();
		Map <Long,List<Module>> subModuleMap = new HashMap<Long,List<Module>>();
		try {
			List <Module> allModuleList = serverice.getAllModuleList( userId);
			
			for(Module module : allModuleList){
				subModuleMap.put(module.getId(), new ArrayList<Module>() );
				if(module.getParentId() == -1){
					topModuleList.add(module);
				}else {
					if(subModuleMap.get(module.getParentId()) == null){
						subModuleMap.put(module.getParentId(), new ArrayList<Module>() );
					}
					subModuleMap.get(module.getParentId()).add(module);
				}
			}
			for(Module module : allModuleList){
				module.setSubModuleList(subModuleMap.get(module.getId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("读取用户权限菜单失败【{}】！",userId);
		}
		return topModuleList;
	}
	
	
	/**
	 * 返回所有模块（嵌套返回）
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Module> getAllModuleList(){
		ModuleService serverice = new ModuleService();
		List<Module> topModuleList = new ArrayList<Module>();
		Map <Long,List<Module>> subModuleMap = new HashMap<Long,List<Module>>();
		try {
			ModuleQ query = new ModuleQ();
			query.setRecordPerPage(-1);
			query.setOrderByProperty("code");
			query.setOrderByKind("ASC");
			List <Module> allModuleList = serverice.getList( query );
			for(Module module : allModuleList){
				subModuleMap.put(module.getId(), new ArrayList<Module>() );
				if(module.getParentId() == -1){
					topModuleList.add(module);
				}else {
					if(subModuleMap.get(module.getParentId()) == null){
						subModuleMap.put(module.getParentId(), new ArrayList<Module>() );
					}
					subModuleMap.get(module.getParentId()).add(module);
				}
			}
			for(Module module : allModuleList){
				module.setSubModuleList(subModuleMap.get(module.getId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("读取所有功能菜单失败！");
		}
		return topModuleList;
	}
	
}
