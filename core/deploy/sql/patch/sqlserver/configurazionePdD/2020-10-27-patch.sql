ALTER TABLE connettori ALTER COLUMN url VARCHAR(4000);
ALTER TABLE connettori_custom ALTER COLUMN value VARCHAR(4000);

ALTER TABLE configurazione ADD cors_all_allow_methods VARCHAR(255);
ALTER TABLE configurazione ADD cors_all_allow_headers VARCHAR(255);
ALTER TABLE porte_delegate ADD cors_all_allow_methods VARCHAR(255);
ALTER TABLE porte_delegate ADD cors_all_allow_headers VARCHAR(255);
ALTER TABLE porte_applicative ADD cors_all_allow_methods VARCHAR(255);
ALTER TABLE porte_applicative ADD cors_all_allow_headers VARCHAR(255);
