ALTER TABLE porte_delegate ADD canale VARCHAR2(20);
ALTER TABLE porte_applicative ADD canale VARCHAR2(20);
ALTER TABLE configurazione ADD canali_stato VARCHAR2(255);

CREATE SEQUENCE seq_canali_configurazione MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE canali_configurazione
(
	nome VARCHAR2(20) NOT NULL,
	descrizione VARCHAR2(255),
	canale_default NUMBER NOT NULL,
	-- fk/pk columns
	id NUMBER NOT NULL,
	-- unique constraints
	CONSTRAINT unique_canali_configurazione_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_canali_configurazione PRIMARY KEY (id)
);

CREATE TRIGGER trg_canali_configurazione
BEFORE
insert on canali_configurazione
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_canali_configurazione.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/



CREATE SEQUENCE seq_canali_nodi MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;

CREATE TABLE canali_nodi
(
	nome VARCHAR2(255) NOT NULL,
	descrizione VARCHAR2(255),
	canali VARCHAR2(4000) NOT NULL,
	-- fk/pk columns
	id NUMBER NOT NULL,
	-- unique constraints
	CONSTRAINT unique_canali_nodi_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_canali_nodi PRIMARY KEY (id)
);

CREATE TRIGGER trg_canali_nodi
BEFORE
insert on canali_nodi
for each row
begin
   IF (:new.id IS NULL) THEN
      SELECT seq_canali_nodi.nextval INTO :new.id
                FROM DUAL;
   END IF;
end;
/


