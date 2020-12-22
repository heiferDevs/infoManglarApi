package ec.gob.ambiente.suia.alfresco.connectionCmisAlfresco;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Documents implements Serializable {	
	private static final long serialVersionUID = 5946195721920875497L;
	private String Id = "";
	private String objectId = "";
	private String name = "";
	private String urlDocument = "";
	private String path = "";
	private List<String> details = new ArrayList<String>(); 
	private String detail = "";
	
        /**
         * Constructor
         */
	public Documents () {
		super();
	}
	
        /**
         * Constructor
         */
	public Documents (String Id, String objectId, String name, String urlDocument) {
		this.Id = Id;
		this.objectId = objectId;
		this.name = name;
		this.urlDocument = urlDocument;
	}
	
        /**
         * Constructor
         */
	public Documents (String Id, String objectId, String name, String urlDocument, String path, String detail) {
		this.Id = Id;
		this.objectId = objectId;
		this.name = name;
		this.urlDocument = urlDocument;
		this.path = path;
		this.detail = detail;
	}
        
        /**
         * Constructor
         */
        public Documents (String Id, String objectId, String name, String urlDocument, String path, List<String> details) {
		this.Id = Id;
		this.objectId = objectId;
		this.name = name;
		this.urlDocument = urlDocument;
		this.path = path;
		this.details = details;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
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

	public String getUrlDocument() {
		return urlDocument;
	}

	public void setUrlDocument(String urlDocument) {
		this.urlDocument = urlDocument;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
}
