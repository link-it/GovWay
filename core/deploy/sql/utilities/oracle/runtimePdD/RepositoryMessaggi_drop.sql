-- Gli indici vengono eliminati automaticamente una volta eliminata la tabella
-- DROP INDEX CORR_APPL_OLD;
-- DROP INDEX CORR_APPL_SCADUTE;
-- DROP INDEX MSG_SERV_APPL_RELEASE_SEND;
-- DROP INDEX MSG_SERV_APPL_ACQUIRE_SEND;
-- DROP INDEX MSG_SERV_APPL_TIMEOUT;
-- DROP INDEX MSG_SERV_APPL_LIST;
-- DROP INDEX MESSAGGI_TESTSUITE;
-- DROP INDEX MESSAGGI_RIFMSG;
-- DROP INDEX MESSAGGI_ACQUIRE_SEND;
-- DROP INDEX MESSAGGI_SEARCH;
-- DROP INDEX MESSAGGI_TRANS;
DROP TRIGGER trg_CORRELAZIONE_APPLICATIVA;
DROP TABLE CORRELAZIONE_APPLICATIVA;
DROP TABLE DEFINIZIONE_MESSAGGI;
DROP TABLE MSG_SERVIZI_APPLICATIVI;
DROP TABLE MESSAGGI;
DROP SEQUENCE seq_CORRELAZIONE_APPLICATIVA;
