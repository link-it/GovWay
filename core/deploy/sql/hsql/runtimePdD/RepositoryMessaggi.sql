-- **** Messaggi ****

CREATE TABLE MESSAGGI
(
	ID_MESSAGGIO VARCHAR(255) NOT NULL,
	TIPO VARCHAR(255) NOT NULL,
	RIFERIMENTO_MSG VARCHAR(255),
	ERRORE_PROCESSAMENTO LONGVARCHAR,
	-- data dalla quale il msg puo' essere rispedito in caso di errori
	RISPEDIZIONE TIMESTAMP NOT NULL,
	ORA_REGISTRAZIONE TIMESTAMP NOT NULL,
	PROPRIETARIO VARCHAR(255),
	-- le colonne seguenti servono per il servizio di TransactionManager
	MOD_RICEZ_CONT_APPLICATIVI VARCHAR(255),
	MOD_RICEZ_BUSTE VARCHAR(255),
	MOD_INOLTRO_BUSTE VARCHAR(255),
	MOD_INOLTRO_RISPOSTE VARCHAR(255),
	MOD_IMBUSTAMENTO VARCHAR(255),
	MOD_IMBUSTAMENTO_RISPOSTE VARCHAR(255),
	MOD_SBUSTAMENTO VARCHAR(255),
	MOD_SBUSTAMENTO_RISPOSTE VARCHAR(255),
	-- Thread Pool:impedisce la gestione di messaggi gia schedulati
	SCHEDULING INT,
	-- permette la riconsegna del messaggio dopo tot tempo
	REDELIVERY_DELAY TIMESTAMP NOT NULL,
	-- numero di riconsegne effettuate
	REDELIVERY_COUNT INT,
	-- id del nodo del cluster che deve gestire questo messaggio.
	CLUSTER_ID VARCHAR(255),
	-- memorizza l'ora in cui il messaggio e stato schedulato la prima volta
	SCHEDULING_TIME TIMESTAMP,
	-- contiene un messaggio serializzato
	MSG_BYTES VARBINARY(16777215),
	CORRELAZIONE_APPLICATIVA VARCHAR(255),
	CORRELAZIONE_RISPOSTA VARCHAR(255),
	PROTOCOLLO VARCHAR(255) NOT NULL,
	id_transazione VARCHAR(255) NOT NULL,
	-- fk/pk columns
	-- check constraints
	CONSTRAINT chk_MESSAGGI_1 CHECK (TIPO IN ('INBOX','OUTBOX')),
	-- fk/pk keys constraints
	CONSTRAINT pk_MESSAGGI PRIMARY KEY (ID_MESSAGGIO,TIPO)
);

-- index
CREATE INDEX MESSAGGI_TRANS ON MESSAGGI (id_transazione,TIPO);
CREATE INDEX MESSAGGI_SEARCH ON MESSAGGI (ORA_REGISTRAZIONE,RIFERIMENTO_MSG,TIPO,PROPRIETARIO);
CREATE INDEX MESSAGGI_RIFMSG ON MESSAGGI (RIFERIMENTO_MSG);
CREATE INDEX MESSAGGI_TESTSUITE ON MESSAGGI (PROPRIETARIO,ID_MESSAGGIO,RIFERIMENTO_MSG);

ALTER TABLE MESSAGGI ALTER COLUMN SCHEDULING SET DEFAULT 0;
ALTER TABLE MESSAGGI ALTER COLUMN REDELIVERY_COUNT SET DEFAULT 0;


CREATE TABLE MSG_SERVIZI_APPLICATIVI
(
	ID_MESSAGGIO VARCHAR(255) NOT NULL,
	TIPO VARCHAR(255) NOT NULL,
	SERVIZIO_APPLICATIVO VARCHAR(2000) NOT NULL,
	SBUSTAMENTO_SOAP INT NOT NULL,
	SBUSTAMENTO_INFO_PROTOCOL INT NOT NULL,
	INTEGRATION_MANAGER INT NOT NULL,
	MOD_CONSEGNA_CONT_APPLICATIVI VARCHAR(255),
	-- Assume il valore 'Connettore' se la consegna avviente tramite un connettore,
	-- 'ConnectionReply' se viene ritornato tramite connectionReply,
	-- 'IntegrationManager' se e' solo ottenibile tramite IntegrationManager
	TIPO_CONSEGNA VARCHAR(255) NOT NULL,
	ERRORE_PROCESSAMENTO_COMPACT VARCHAR(255),
	ERRORE_PROCESSAMENTO VARCHAR(65535),
	-- data dalla quale il msg puo' essere rispedito in caso di errori
	RISPEDIZIONE TIMESTAMP NOT NULL,
	-- Informazioni relative alla consegna con threads
	NOME_PORTA VARCHAR(255),
	LOCK_CONSEGNA TIMESTAMP,
	CLUSTER_ID VARCHAR(255),
	ATTESA_ESITO INT,
	-- fk/pk columns
	-- check constraints
	CONSTRAINT chk_MSG_SERVIZI_APPLICATIVI_1 CHECK (TIPO IN ('INBOX','OUTBOX')),
	CONSTRAINT chk_MSG_SERVIZI_APPLICATIVI_2 CHECK (TIPO_CONSEGNA IN ('Connettore','ConnectionReply','IntegrationManager')),
	-- fk/pk keys constraints
	CONSTRAINT fk_MSG_SERVIZI_APPLICATIVI_1 FOREIGN KEY (ID_MESSAGGIO,TIPO) REFERENCES MESSAGGI(ID_MESSAGGIO,TIPO) ON DELETE CASCADE,
	CONSTRAINT pk_MSG_SERVIZI_APPLICATIVI PRIMARY KEY (ID_MESSAGGIO,SERVIZIO_APPLICATIVO)
);

-- index
CREATE INDEX MSG_SERV_APPL_LIST ON MSG_SERVIZI_APPLICATIVI (SERVIZIO_APPLICATIVO,INTEGRATION_MANAGER);
CREATE INDEX MSG_SERV_APPL_TIMEOUT ON MSG_SERVIZI_APPLICATIVI (ID_MESSAGGIO,TIPO_CONSEGNA,INTEGRATION_MANAGER);
CREATE INDEX MSG_SERV_APPL_ACQUIRE_SEND ON MSG_SERVIZI_APPLICATIVI (ID_MESSAGGIO,TIPO_CONSEGNA,ERRORE_PROCESSAMENTO_COMPACT,RISPEDIZIONE,LOCK_CONSEGNA,ATTESA_ESITO);
CREATE INDEX MSG_SERV_APPL_RELEASE_SEND ON MSG_SERVIZI_APPLICATIVI (CLUSTER_ID,LOCK_CONSEGNA);

ALTER TABLE MSG_SERVIZI_APPLICATIVI ALTER COLUMN TIPO SET DEFAULT 'INBOX';


CREATE TABLE DEFINIZIONE_MESSAGGI
(
	ID_MESSAGGIO VARCHAR(255) NOT NULL,
	TIPO VARCHAR(255) NOT NULL,
	CONTENT_TYPE VARCHAR(255) NOT NULL,
	MSG_BYTES VARBINARY(16777215),
	MSG_CONTEXT VARBINARY(16777215),
	-- fk/pk columns
	-- check constraints
	CONSTRAINT chk_DEFINIZIONE_MESSAGGI_1 CHECK (TIPO IN ('INBOX','OUTBOX')),
	-- fk/pk keys constraints
	CONSTRAINT fk_DEFINIZIONE_MESSAGGI_1 FOREIGN KEY (ID_MESSAGGIO,TIPO) REFERENCES MESSAGGI(ID_MESSAGGIO,TIPO) ON DELETE CASCADE,
	CONSTRAINT pk_DEFINIZIONE_MESSAGGI PRIMARY KEY (ID_MESSAGGIO,TIPO)
);


-- **** Correlazione Applicativa ****

CREATE SEQUENCE seq_CORRELAZIONE_APPLICATIVA AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) CYCLE;

CREATE TABLE CORRELAZIONE_APPLICATIVA
(
	ID_MESSAGGIO VARCHAR(255) NOT NULL,
	ID_APPLICATIVO VARCHAR(255) NOT NULL,
	SERVIZIO_APPLICATIVO VARCHAR(255) NOT NULL,
	TIPO_MITTENTE VARCHAR(255) NOT NULL,
	MITTENTE VARCHAR(255) NOT NULL,
	TIPO_DESTINATARIO VARCHAR(255) NOT NULL,
	DESTINATARIO VARCHAR(255) NOT NULL,
	TIPO_SERVIZIO VARCHAR(255),
	SERVIZIO VARCHAR(255),
	VERSIONE_SERVIZIO INT NOT NULL,
	AZIONE VARCHAR(255),
	SCADENZA TIMESTAMP,
	ora_registrazione TIMESTAMP NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- fk/pk keys constraints
	CONSTRAINT pk_CORRELAZIONE_APPLICATIVA PRIMARY KEY (id)
);

-- index
CREATE INDEX CORR_APPL_SCADUTE ON CORRELAZIONE_APPLICATIVA (SCADENZA);
CREATE INDEX CORR_APPL_OLD ON CORRELAZIONE_APPLICATIVA (ora_registrazione);

ALTER TABLE CORRELAZIONE_APPLICATIVA ALTER COLUMN ora_registrazione SET DEFAULT CURRENT_TIMESTAMP;

CREATE TABLE CORRELAZIONE_APPLICATIVA_init_seq (id BIGINT);
INSERT INTO CORRELAZIONE_APPLICATIVA_init_seq VALUES (NEXT VALUE FOR seq_CORRELAZIONE_APPLICATIVA);


