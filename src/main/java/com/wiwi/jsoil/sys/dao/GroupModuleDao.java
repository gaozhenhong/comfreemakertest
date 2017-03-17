package com.wiwi.jsoil.sys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wiwi.jsoil.db.DaoBase;

public class GroupModuleDao extends DaoBase {
	private String sql = null;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public GroupModuleDao() {
    }

    /**
     * add
     *
     * @param groupVO GroupVO
     */
    public void save(String groupId,String[] moduleId) throws Exception {
        try {
        	conn=getConn(true);
            sql = "delete from S_GroupModule where groupId=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, groupId);
            ps.executeUpdate();
            sql = "insert into S_GroupModule (groupId,moduleId) VALUES(?,?)";
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < moduleId.length; i++) {
                ps.setString(1, groupId);
                ps.setString(2, moduleId[i]);
                ps.executeUpdate();
            }
        }
        catch (Exception e) {
            throw new Exception("添加工作组权限出错(DAO)：" + e.toString());
        }
        finally {
            closeStmt(ps);
            closeConn(conn);
        }
    }

    /**
     * del
     *
     * @param IDs String
     */
    public void delete(String groupId,String functionIds) throws Exception {
        try {
        	conn=getConn(true);
            sql = "delete from S_GroupRight where functionId in (" +
            functionIds + ") and groupId='"+groupId+"'";
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new Exception("删除权限信息出错(DAO)：" + e.toString());
        }
        finally {
            closeStmt(ps);
            closeConn(conn);
        }
    }

    /**
     * getList : 得到列表。
     *
     * @return List 列表
     */
    public List getFunctionList(int groupId) throws Exception {
        ArrayList returnList = new ArrayList();
        try {
        	conn=getConn(true);
            sql = "select A.*,B.MODULE_NAME,C.GROUP_NAME,B.MODULE_POSITION from S_GroupRight A,SYS_MODULE B,SYS_GROUP C WHERE B.SYS_MODULE_ID=A.MODULE_ID AND C.SYS_GROUP_ID=A.GROUP_ID AND A.GROUP_ID='" +
                groupId + "' order by B.MODULE_POSITION";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            return returnList;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            closeRs(rs);
            closeStmt(ps);
            closeConn(conn);
        }
    }
}
