CREATE TABLE sonde
(
	nome VARCHAR(35) NOT NULL,
	classe VARCHAR(255) NOT NULL,
	soglia_warn NUMBER NOT NULL,
	soglia_error NUMBER NOT NULL,
	data_ok TIMESTAMP,
	data_warn TIMESTAMP,
	data_error TIMESTAMP,
	data_ultimo_check TIMESTAMP,
	dati_check CLOB,
	stato_ultimo_check NUMBER,
	-- fk/pk columns
	-- fk/pk keys constraints
	CONSTRAINT pk_sonde PRIMARY KEY (nome)
);

