-- DROP INDEX index_config_protocolli_1 ON config_protocolli;
-- DROP TABLE config_protocolli;

CREATE TABLE config_url_invocazione
(
	base_url VARCHAR(255) NOT NULL,
	base_url_fruizione VARCHAR(255),
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	-- fk/pk keys constraints
	CONSTRAINT pk_config_url_invocazione PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs ROW_FORMAT DYNAMIC;




CREATE TABLE config_url_regole
(
	nome VARCHAR(255) NOT NULL,
	posizione INT NOT NULL,
	stato VARCHAR(255),
	descrizione TEXT,
	regexpr INT NOT NULL,
	regola VARCHAR(255) NOT NULL,
	contesto_esterno VARCHAR(255) NOT NULL,
	base_url VARCHAR(255),
	protocollo VARCHAR(255),
	ruolo VARCHAR(255),
	service_binding VARCHAR(255),
	tipo_soggetto VARCHAR(255),
	nome_soggetto VARCHAR(255),
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	-- unique constraints
	CONSTRAINT unique_config_url_regole_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_config_url_regole PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs ROW_FORMAT DYNAMIC;

-- index
CREATE UNIQUE INDEX index_config_url_regole_1 ON config_url_regole (nome);


-- Init Configurazione URL Invocazione
INSERT INTO config_url_invocazione (base_url) VALUES ('http://localhost:8080/govway/');

-- Regola per eDelivery
-- INSERT INTO config_url_regole (nome, posizione, stato, descrizione, regexpr, regola, contesto_esterno, base_url, protocollo, ruolo) VALUES ('Domibus',1,'abilitato','Servizio di ricezione dei messaggi AS4 dell''Access Point Domibus', 1, '^(.+)$', 'domibus-wildfly/services/msh', 'http://localhost:8080/','as4','portaApplicativa');

