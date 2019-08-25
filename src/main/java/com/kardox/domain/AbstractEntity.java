package com.kardox.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase abstracta que permite centralizar la definición de 
 * atributos como: primaryKey, createdAt, lastUpdated, version
 * y ademas define algunas operaciones hook de auditoria
 *  como @CreatedDate y @LastModifiedDate.
 * 
 * Todas las entidades creadas heredan de esta clase para la 
 * utilización de estos atributos en cada tabla de la db.
 * 
 * @author lstubbia
 *
 */
@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
	private Long id;

	@CreatedDate
	@Column(name = "CREATED_DATE")
	@JsonIgnore
	private Date createdDate;

	@LastModifiedDate
	@Column(name = "LAST_MODIFIED_DATE")
	@JsonIgnore
	private Date lastUpdatedDate;

	@Version
	@Column(name = "LOCK_VERSION")
	@JsonIgnore
	private Long Version;
}
