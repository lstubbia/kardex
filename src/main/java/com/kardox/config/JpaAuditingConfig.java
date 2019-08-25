package com.kardox.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

/**
 * Esta configuración permite habilitar los hooks para:
 * @CreatedDate
 * @LastModifiedDate
 * 
 * Tambien se puede dar soporte a la auditoria del usuario, 
 * agregando en {@link com.kardox.domain.AbstractEntity} los campos:
 * @CreatedBy
 * @LastModifiedBy
 * pero para ello necesitamos incluir spring-security y poder
 * mapear el usuario logueado.
 * 
 * @author lstubbia
 *
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "provider")
public class JpaAuditingConfig {

	@Bean
	public AuditorAware<String> provider() {
		return () -> Optional.ofNullable("lstubbia");
	}
}