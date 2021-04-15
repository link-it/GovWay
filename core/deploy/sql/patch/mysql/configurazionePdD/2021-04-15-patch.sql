
CREATE TABLE sa_properties
(
	id_servizio_applicativo BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(4000) NOT NULL,
	-- fk/pk columns
	id BIGINT AUTO_INCREMENT,
	-- unique constraints
	CONSTRAINT uniq_sa_properties_1 UNIQUE (id_servizio_applicativo,nome),
	-- fk/pk keys constraints
	CONSTRAINT fk_sa_properties_1 FOREIGN KEY (id_servizio_applicativo) REFERENCES servizi_applicativi(id),
	CONSTRAINT pk_sa_properties PRIMARY KEY (id)
)ENGINE INNODB CHARACTER SET latin1 COLLATE latin1_general_cs ROW_FORMAT DYNAMIC;

-- index
CREATE INDEX INDEX_SA_PROP ON sa_properties (id_servizio_applicativo);


