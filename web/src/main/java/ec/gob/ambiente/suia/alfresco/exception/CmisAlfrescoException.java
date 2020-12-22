/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.ambiente.suia.alfresco.exception;

/**
 *
 * @author diebasgue 
 */
public class CmisAlfrescoException extends Exception{
    private String code = ""; 
    private String message = "";
    private String classMethod = "";

    public CmisAlfrescoException(String code, String message, String classMethod) {
        this.code = code;
        this.message = message;
        this.classMethod = classMethod;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

    public String getClassMethod() {
        return classMethod;
    }

    public void setClassMethod(String classMethod) {
        this.classMethod = classMethod;
    }  
        
    @Override
    public String getMessage() {
        return message;
    }
}
