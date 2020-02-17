ALTER TABLE porte_applicative ADD COLUMN id_sa_default BIGINT;

ALTER TABLE porte_applicative_sa ADD COLUMN connettore_nome VARCHAR(255);
ALTER TABLE porte_applicative_sa ADD COLUMN connettore_notifica INT;
ALTER TABLE porte_applicative_sa ADD COLUMN connettore_descrizione VARCHAR(4000);
ALTER TABLE porte_applicative_sa ADD COLUMN connettore_stato VARCHAR(255);
ALTER TABLE porte_applicative_sa ADD COLUMN connettore_filtri VARCHAR(65535);

ALTER TABLE servizi_applicativi ADD COLUMN tipo VARCHAR(255);
update servizi_applicativi set tipo='client' where tipologia_fruizione<>'disabilitato';

ALTER TABLE servizi_applicativi ADD COLUMN as_client INT;

CREATE SEQUENCE seq_pa_sa_properties AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_sa_properties
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT uniq_pa_sa_props_1 UNIQUE (id_porta,nome,valore),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_sa_properties_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative_sa(id),
	CONSTRAINT pk_pa_sa_properties PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_SA_PROP ON pa_sa_properties (id_porta);
CREATE TABLE pa_sa_properties_init_seq (id BIGINT);
INSERT INTO pa_sa_properties_init_seq VALUES (NEXT VALUE FOR seq_pa_sa_properties);



CREATE SEQUENCE seq_pa_behaviour_props AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_behaviour_props
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(4000) NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT uniq_pa_behaviour_props_1 UNIQUE (id_porta,nome,valore),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_behaviour_props_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_behaviour_props PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_BEHAVIOUR_PROP ON pa_behaviour_props (id_porta);
CREATE TABLE pa_behaviour_props_init_seq (id BIGINT);
INSERT INTO pa_behaviour_props_init_seq VALUES (NEXT VALUE FOR seq_pa_behaviour_props);

ALTER TABLE configurazione ADD COLUMN consegna_statocache VARCHAR(255);
ALTER TABLE configurazione ADD COLUMN consegna_dimensionecache VARCHAR(255);
ALTER TABLE configurazione ADD COLUMN consegna_algoritmocache VARCHAR(255);
ALTER TABLE configurazione ADD COLUMN consegna_idlecache VARCHAR(255);
ALTER TABLE configurazione ADD COLUMN consegna_lifecache VARCHAR(255);

UPDATE configurazione set consegna_statocache='abilitato';
UPDATE configurazione set consegna_dimensionecache='10000';
UPDATE configurazione set consegna_algoritmocache='lru';
UPDATE configurazione set consegna_lifecache='-1';