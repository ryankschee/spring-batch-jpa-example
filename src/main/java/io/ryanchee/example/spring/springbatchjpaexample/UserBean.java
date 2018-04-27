package io.ryanchee.example.spring.springbatchjpaexample;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="TMOVE_USER")
@Getter @Setter @NoArgsConstructor
public class UserBean 
	implements java.io.Serializable {

	private static final long serialVersionUID = 1125037341376588942L;
	
	@Id
	@Column(name="id", unique=true)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TMOVE_USER_SEQ")
	@SequenceGenerator(name="TMOVE_USER_SEQ", sequenceName = "TMOVE_USER_SEQ", allocationSize = 1, initialValue= 1)
	Long id;
	@Column(name="emp_id")
	String employeeId;
	@Column(name="lan_id")
	String lanId;
	@Column(name="name")
	String employeeName;
	@Column(name="department")
	String department;
	@Column(name="role")
	String role;
	@Column(name="created_dt")
	Timestamp createdDate;
	@Column(name="updated_dt")
	Timestamp updatedDate;
	@Column(name="created_by")
	Long createdBy;	
	@Column(name="updated_by")
	Long updatedBy;
}

