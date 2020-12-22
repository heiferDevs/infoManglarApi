/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.ambiente.suia.alfresco.connectionCmisAlfresco;

import java.io.Serializable;

/**
 * 
 * @author dbasurto
 */
public class Folders implements Serializable{
    private static final long serialVersionUID = 5946195721920875497L;
    private String Id = "";
    private String objectId = "";    
    private String name = "";
    private String path = "";
    private String node = "";
    private String parentId = "";

    public Folders() {
        super();
    }
    
    public Folders(String Id, String objectId, String name, String path) {
        this.Id = Id;
        this.objectId = objectId;
        this.name = name;
        this.path = path;
    }

    public Folders(String Id, String objectId, String name, String path, String node, String parentId) {
        this.Id = Id;
        this.objectId = objectId;
        this.name = name;
        this.path = path;
        this.node = node;
        this.parentId = parentId;
    }
    
    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }    
    
}
