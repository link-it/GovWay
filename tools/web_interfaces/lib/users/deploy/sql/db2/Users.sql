-- **** Utenti ****

CREATE TABLE users
(
	login VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	data_password TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	check_data_password INT NOT NULL DEFAULT 1,
	tipo_interfaccia VARCHAR(255) NOT NULL,
	interfaccia_completa INT,
	permessi VARCHAR(255) NOT NULL,
	protocolli CLOB,
	protocollo_pddconsole VARCHAR(255),
	protocollo_pddmonitor VARCHAR(255),
	soggetto_pddconsole VARCHAR(255),
	soggetto_pddmonitor VARCHAR(255),
	soggetti_all INT,
	servizi_all INT,
	-- fk/pk columns
	id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1 NO CYCLE NO CACHE),
	-- unique constraints
	CONSTRAINT unique_users_1 UNIQUE (login),
	-- fk/pk keys constraints
	CONSTRAINT pk_users PRIMARY KEY (id)
);




CREATE TABLE users_stati
(
	oggetto VARCHAR(255) NOT NULL,
	stato CLOB NOT NULL,
	-- fk/pk columns
	id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1 NO CYCLE NO CACHE),
	id_utente BIGINT NOT NULL,
	-- fk/pk keys constraints
	CONSTRAINT fk_users_stati_1 FOREIGN KEY (id_utente) REFERENCES users(id),
	CONSTRAINT pk_users_stati PRIMARY KEY (id)
);




CREATE TABLE users_password
(
	password VARCHAR(255) NOT NULL,
	data_password TIMESTAMP NOT NULL,
	-- fk/pk columns
	id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1 NO CYCLE NO CACHE),
	id_utente BIGINT NOT NULL,
	-- fk/pk keys constraints
	CONSTRAINT fk_users_password_1 FOREIGN KEY (id_utente) REFERENCES users(id),
	CONSTRAINT pk_users_password PRIMARY KEY (id)
);




CREATE TABLE users_soggetti
(
	id_utente BIGINT NOT NULL,
	id_soggetto BIGINT NOT NULL,
	-- fk/pk columns
	id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1 NO CYCLE NO CACHE),
	-- unique constraints
	CONSTRAINT unique_users_soggetti_1 UNIQUE (id_utente,id_soggetto),
	-- fk/pk keys constraints
	CONSTRAINT fk_users_soggetti_1 FOREIGN KEY (id_soggetto) REFERENCES soggetti(id),
	CONSTRAINT fk_users_soggetti_2 FOREIGN KEY (id_utente) REFERENCES users(id),
	CONSTRAINT pk_users_soggetti PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_users_soggetti_1 ON users_soggetti (id_utente,id_soggetto);



CREATE TABLE users_servizi
(
	id_utente BIGINT NOT NULL,
	id_servizio BIGINT NOT NULL,
	-- fk/pk columns
	id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1 NO CYCLE NO CACHE),
	-- unique constraints
	CONSTRAINT unique_users_servizi_1 UNIQUE (id_utente,id_servizio),
	-- fk/pk keys constraints
	CONSTRAINT fk_users_servizi_1 FOREIGN KEY (id_servizio) REFERENCES servizi(id),
	CONSTRAINT fk_users_servizi_2 FOREIGN KEY (id_utente) REFERENCES users(id),
	CONSTRAINT pk_users_servizi PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_users_servizi_1 ON users_servizi (id_utente,id_servizio);


