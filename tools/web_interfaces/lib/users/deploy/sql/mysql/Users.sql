-- **** Utenti ****

CREATE TABLE users
(
	login VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	tipo_interfaccia VARCHAR(255) NOT NULL,
	interfaccia_completa INT,
	permessi VARCHAR(255) NOT NULL,
	protocolli TEXT,
	protocollo VARCHAR(255),
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	-- unique constraints
	CONSTRAINT unique_users_1 UNIQUE (login),
	-- fk/pk keys constraints
	CONSTRAINT pk_users PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs;



