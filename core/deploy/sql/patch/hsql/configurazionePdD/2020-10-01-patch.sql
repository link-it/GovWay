UPDATE porte_delegate set id_soggetto_erogatore=(select id from soggetti s where s.tipo_soggetto=porte_delegate.tipo_soggetto_erogatore AND s.nome_soggetto=porte_delegate.nome_soggetto_erogatore) where porte_delegate.id_soggetto_erogatore<=0;
UPDATE porte_delegate set id_servizio=(select id from servizi s where s.tipo_servizio=porte_delegate.tipo_servizio AND s.nome_servizio=porte_delegate.nome_servizio AND s.versione_servizio=porte_delegate.versione_servizio AND s.id_soggetto=porte_delegate.id_soggetto_erogatore ) where porte_delegate.id_servizio<=0;

UPDATE porte_applicative set id_servizio=(select id from servizi s where s.tipo_servizio=porte_applicative.tipo_servizio AND s.nome_servizio=porte_applicative.servizio AND s.versione_servizio=porte_applicative.versione_servizio AND s.id_soggetto=porte_applicative.id_soggetto ) where porte_applicative.id_servizio<=0;
