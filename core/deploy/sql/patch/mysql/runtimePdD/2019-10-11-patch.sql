ALTER TABLE MSG_SERVIZI_APPLICATIVI ADD COLUMN LOCK_CONSEGNA TIMESTAMP(3) DEFAULT 0;
ALTER TABLE MSG_SERVIZI_APPLICATIVI ADD COLUMN CLUSTER_ID VARCHAR(255);
ALTER TABLE MSG_SERVIZI_APPLICATIVI ADD COLUMN ATTESA_ESITO INT;

