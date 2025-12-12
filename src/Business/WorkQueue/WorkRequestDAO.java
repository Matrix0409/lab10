/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.WorkQueue;

import Database.CRUD;
import Database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class WorkRequestDAO implements CRUD {
    private WorkRequest request;
    
    public WorkRequestDAO(WorkRequest request) {
        this.request = request;
    }
    
    @Override
    public boolean create() {
        try {
            Database db = Database.getInstance();
            String query = "INSERT INTO WorkRequest (type, requesterId, fromEnterpriseId, toEnterpriseId, status, description) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = db.prepareStatement(query);
            
            pstmt.setString(1, request.getType());
            pstmt.setInt(2, request.getRequesterId());
            pstmt.setInt(3, request.getFromEnterpriseId());
            pstmt.setInt(4, request.getToEnterpriseId());
            pstmt.setString(5, request.getStatus());
            pstmt.setString(6, request.getDescription());
            
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    request.setRequestId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public Object read(int id) {
        try {
            Database db = Database.getInstance();
            String query = "SELECT * FROM WorkRequest WHERE requestId = ?";
            PreparedStatement pstmt = db.prepareStatement(query);
            pstmt.setInt(1, id);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                WorkRequest wr = new WorkRequest();
                wr.setRequestId(rs.getInt("requestId"));
                wr.setType(rs.getString("type"));
                wr.setRequesterId(rs.getInt("requesterId"));
                wr.setApproverId(rs.getInt("approverId"));
                wr.setFromEnterpriseId(rs.getInt("fromEnterpriseId"));
                wr.setToEnterpriseId(rs.getInt("toEnterpriseId"));
                wr.setRequestDate(rs.getTimestamp("requestDate"));
                wr.setStatus(rs.getString("status"));
                wr.setDescription(rs.getString("description"));
                return wr;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public boolean update() {
        try {
            Database db = Database.getInstance();
            String query = "UPDATE WorkRequest SET type=?, requesterId=?, approverId=?, fromEnterpriseId=?, toEnterpriseId=?, status=?, description=? WHERE requestId=?";
            PreparedStatement pstmt = db.prepareStatement(query);
            
            pstmt.setString(1, request.getType());
            pstmt.setInt(2, request.getRequesterId());
            pstmt.setInt(3, request.getApproverId());
            pstmt.setInt(4, request.getFromEnterpriseId());
            pstmt.setInt(5, request.getToEnterpriseId());
            pstmt.setString(6, request.getStatus());
            pstmt.setString(7, request.getDescription());
            pstmt.setInt(8, request.getRequestId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public boolean delete(int id) {
        try {
            Database db = Database.getInstance();
            String query = "DELETE FROM WorkRequest WHERE requestId = ?";
            PreparedStatement pstmt = db.prepareStatement(query);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Get all pending requests
    public static List<WorkRequest> getPendingRequests() {
        List<WorkRequest> requests = new ArrayList<>();
        try {
            Database db = Database.getInstance();
            String query = "SELECT * FROM WorkRequest WHERE status = 'Pending' ORDER BY requestDate DESC";
            ResultSet rs = db.executeQuery(query);
            
            while (rs.next()) {
                WorkRequest wr = new WorkRequest();
                wr.setRequestId(rs.getInt("requestId"));
                wr.setType(rs.getString("type"));
                wr.setRequesterId(rs.getInt("requesterId"));
                wr.setFromEnterpriseId(rs.getInt("fromEnterpriseId"));
                wr.setToEnterpriseId(rs.getInt("toEnterpriseId"));
                wr.setRequestDate(rs.getTimestamp("requestDate"));
                wr.setStatus(rs.getString("status"));
                wr.setDescription(rs.getString("description"));
                requests.add(wr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }
    
    // Get requests by member
    public static List<WorkRequest> getRequestsByMember(int memberId) {
        List<WorkRequest> requests = new ArrayList<>();
        try {
            Database db = Database.getInstance();
            String query = "SELECT * FROM WorkRequest WHERE requesterId = ? ORDER BY requestDate DESC";
            PreparedStatement pstmt = db.prepareStatement(query);
            pstmt.setInt(1, memberId);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                WorkRequest wr = new WorkRequest();
                wr.setRequestId(rs.getInt("requestId"));
                wr.setType(rs.getString("type"));
                wr.setRequesterId(rs.getInt("requesterId"));
                wr.setFromEnterpriseId(rs.getInt("fromEnterpriseId"));
                wr.setToEnterpriseId(rs.getInt("toEnterpriseId"));
                wr.setRequestDate(rs.getTimestamp("requestDate"));
                wr.setStatus(rs.getString("status"));
                wr.setDescription(rs.getString("description"));
                requests.add(wr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }
}