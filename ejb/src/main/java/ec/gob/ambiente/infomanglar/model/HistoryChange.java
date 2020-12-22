package ec.gob.ambiente.infomanglar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import ec.gob.ambiente.enlisy.model.GeographicalLocation;
import ec.gob.ambiente.enlisy.model.Role;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="history_changes", schema="info_manglar")
@NamedQuery(name="HistoryChange.findAll", query="SELECT o FROM HistoryChange o")
public class HistoryChange implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "HISTORY_CHANGES_GENERATOR", initialValue = 1, sequenceName = "seq_history_changes", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HISTORY_CHANGES_GENERATOR")
	@Getter
	@Setter
	@Column(name="history_change_id")
	private Integer id;

	@Getter
	@Setter
	@Column(name="history_change_status")
	private Boolean historyChangeStatus;

	@Getter
	@Setter
	@Column(name="user_id")
	private Integer userId;

	@Getter
	@Setter
	@Column(name="history_change_date")
	private Date date;

	@Getter
	@Setter
	@Column(name="form_id")
	private Integer formId;

	@Getter
	@Setter
	@Column(name="form_type")
	private String formType;
	
	@Getter
	@Setter
	@Column(name="type_change")
	private String typeChange;
	
	@Getter
	@Setter
	@Column(name="user_name")
	private String userName;
	
	@Getter
	@Setter
	@Column(name="reason")
	private String reason;
	
}
