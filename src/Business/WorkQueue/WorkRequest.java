/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.WorkQueue;

import java.util.Date;

public class WorkRequest {
    private int requestId;
    private String type;
    private int requesterId;
    private int approverId;
    private int fromEnterpriseId;
    private int toEnterpriseId;
    private Date requestDate;
    private String status;
    private String description;
    
    public WorkRequest() {
        this.requestDate = new Date();
        this.status = "Pending";
    }
    
    public WorkRequest(String type, int requesterId, int fromEnterpriseId, int toEnterpriseId, String description) {
        this.type = type;
        this.requesterId = requesterId;
        this.fromEnterpriseId = fromEnterpriseId;
        this.toEnterpriseId = toEnterpriseId;
        this.description = description;
        this.requestDate = new Date();
        this.status = "Pending";
    }
    
    // Getters
    public int getRequestId() { return requestId; }
    public String getType() { return type; }
    public int getRequesterId() { return requesterId; }
    public int getApproverId() { return approverId; }
    public int getFromEnterpriseId() { return fromEnterpriseId; }
    public int getToEnterpriseId() { return toEnterpriseId; }
    public Date getRequestDate() { return requestDate; }
    public String getStatus() { return status; }
    public String getDescription() { return description; }
    
    // Setters
    public void setRequestId(int requestId) { this.requestId = requestId; }
    public void setType(String type) { this.type = type; }
    public void setRequesterId(int requesterId) { this.requesterId = requesterId; }
    public void setApproverId(int approverId) { this.approverId = approverId; }
    public void setFromEnterpriseId(int fromEnterpriseId) { this.fromEnterpriseId = fromEnterpriseId; }
    public void setToEnterpriseId(int toEnterpriseId) { this.toEnterpriseId = toEnterpriseId; }
    public void setRequestDate(Date requestDate) { this.requestDate = requestDate; }
    public void setStatus(String status) { this.status = status; }
    public void setDescription(String description) { this.description = description; }
    
    public void submit() {
        this.status = "Submitted";
    }
    
    public void approve() {
        this.status = "Approved";
    }
    
    public void reject() {
        this.status = "Rejected";
    }
    
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }
    
    @Override
    public String toString() {
        return type + " - " + status + " (Request #" + requestId + ")";
    }
}
