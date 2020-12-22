package ec.gob.ambiente.infomanglar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import ec.gob.ambiente.enlisy.model.GeographicalLocation;
import ec.gob.ambiente.enlisy.model.User;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="email_notifications", schema="info_manglar")
@NamedQuery(name="EmailNotification.findAll", query="SELECT o FROM EmailNotification o")
public class EmailNotification implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "EMAIL_NOTIFICATIONS_GENERATOR", initialValue = 1, sequenceName = "seq_email_notification_id", schema = "info_manglar", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMAIL_NOTIFICATIONS_GENERATOR")
	@Getter
	@Setter
	@Column(name="email_notification_id")
	private Integer emailNotificationId;

	@Getter
	@Setter
	@Column(name="email_notification_status")
	private Boolean emailNotificationStatus;

	@Getter
	@Setter
	@Column(name="email_notification_name")
	private String emailNotificationName;

	@Getter
	@Setter
	@Column(name="email_notification_email")
	private String emailNotificationEmail;

	@Getter
	@Setter
	@Column(name="email_notification_provinces")
	private String emailNotificationProvinces;
	
	@Getter
	@Setter
	@Column(name="email_notification_anomalies_types")
	private String emailNotificationAnomaliesTypes;
	
}
