-- Gli indici vengono eliminati automaticamente una volta eliminata la tabella
-- DROP INDEX idx_gest_err_soap_1;
-- DROP INDEX idx_gest_err_trasporto_1;
DROP TRIGGER trg_gestione_errore_soap;
DROP TRIGGER trg_gestione_errore_trasporto;
DROP TRIGGER trg_gestione_errore;
DROP TABLE gestione_errore_soap;
DROP TABLE gestione_errore_trasporto;
DROP TABLE gestione_errore;
DROP SEQUENCE seq_gestione_errore_soap;
DROP SEQUENCE seq_gestione_errore_trasporto;
DROP SEQUENCE seq_gestione_errore;
