-- Gli indici vengono eliminati automaticamente una volta eliminata la tabella
-- DROP INDEX INDEX_SOGGETTI_PROP;
-- DROP INDEX INDEX_SOGGETTI_CRED;
-- DROP INDEX INDEX_SOGGETTI_RUOLI;
DROP TRIGGER trg_soggetti_properties;
DROP TRIGGER trg_soggetti_credenziali;
DROP TRIGGER trg_soggetti_ruoli;
DROP TRIGGER trg_soggetti;
DROP TABLE soggetti_properties;
DROP TABLE soggetti_credenziali;
DROP TABLE soggetti_ruoli;
DROP TABLE soggetti;
DROP SEQUENCE seq_soggetti_properties;
DROP SEQUENCE seq_soggetti_credenziali;
DROP SEQUENCE seq_soggetti_ruoli;
DROP SEQUENCE seq_soggetti;
