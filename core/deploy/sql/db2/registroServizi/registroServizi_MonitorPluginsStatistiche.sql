-- PLUGINS PER LE STATISTICHE PERSONALIZZATE

CREATE TABLE stat_personalizzate
(
	id VARCHAR(255) NOT NULL,
	enabled SMALLINT NOT NULL,
	label VARCHAR(255) NOT NULL,
	-- fk/pk columns
	pid BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1 NO CYCLE NO CACHE),
	id_configurazione BIGINT NOT NULL,
	id_plugin BIGINT NOT NULL,
	-- unique constraints
	CONSTRAINT uniq_stat_pers_1 UNIQUE (id_configurazione,id),
	CONSTRAINT uniq_stat_pers_2 UNIQUE (id_configurazione,id_plugin),
	-- fk/pk keys constraints
	CONSTRAINT fk_stat_personalizzate_1 FOREIGN KEY (id_configurazione) REFERENCES plugins_conf_azioni(id) ON DELETE CASCADE,
	CONSTRAINT fk_stat_personalizzate_2 FOREIGN KEY (id_plugin) REFERENCES plugins(id) ON DELETE CASCADE,
	CONSTRAINT pk_stat_personalizzate PRIMARY KEY (pid)
);

-- index
CREATE UNIQUE INDEX idx_stat_pers_1 ON stat_personalizzate (id_configurazione,id);
CREATE UNIQUE INDEX idx_stat_pers_2 ON stat_personalizzate (id_configurazione,id_plugin);


