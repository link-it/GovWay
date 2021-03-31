CREATE INDEX index_dump_config_1 ON dump_config (proprietario);

ALTER TABLE dump_config_regola ADD payload VARCHAR(255) ;
UPDATE dump_config_regola set payload=body;
ALTER TABLE dump_config_regola ALTER COLUMN payload SET NOT NULL;

ALTER TABLE dump_config_regola ADD payload_parsing VARCHAR(255) ;
UPDATE dump_config_regola set payload_parsing='disabilitato';
ALTER TABLE dump_config_regola ALTER COLUMN payload_parsing SET NOT NULL;

CALL SYSPROC.ADMIN_CMD ('REORG TABLE dump_config_regola') ;
