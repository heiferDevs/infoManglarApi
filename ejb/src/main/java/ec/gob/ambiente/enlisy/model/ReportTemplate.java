package ec.gob.ambiente.enlisy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "report_template", schema = "public")
public class ReportTemplate {	

    @Id
    @Column(name = "rete_id")   
	@Getter @Setter
    private Integer reteId;

	@Getter @Setter
    @Column(name = "rete_html_template", length=5000)
    private String reteHtmlTemplate;

	@Getter @Setter
    @Column(name = "rete_process_name")
    private String reteProcessName;

	@Getter @Setter
    @Column(name = "rete_process_code")
    private String reteProcessCode;	

	
}
