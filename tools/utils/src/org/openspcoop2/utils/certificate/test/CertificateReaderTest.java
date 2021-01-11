/*
 * GovWay - A customizable API Gateway 
 * https://govway.org
 * 
 * Copyright (c) 2005-2021 Link.it srl (https://link.it). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3, as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.openspcoop2.utils.certificate.test;
import org.openspcoop2.utils.Utilities;
import org.openspcoop2.utils.certificate.ArchiveLoader;
import org.openspcoop2.utils.certificate.Certificate;
import org.openspcoop2.utils.certificate.CertificateDecodeConfig;
import org.openspcoop2.utils.certificate.CertificateUtils;

/**
 * CertificateTest
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class CertificateReaderTest {

	public static void main(String[] args) throws Exception {
		test();
	}
	public static void test() throws Exception {
		
		byte [] PEM = Utilities.getAsByteArray(CertificateReaderTest.class.getResourceAsStream("/org/openspcoop2/utils/certificate/test/Soggetto1.pem"));
		System.out.println("PEM ...");
		Certificate certDecoded =  ArchiveLoader.load(PEM);
		System.out.println("PEM: "+certDecoded.getCertificate().toString());
		if(!"Soggetto1".equals(certDecoded.getCertificate().getSubject().getCN())) {
			throw new Exception("CN '"+certDecoded.getCertificate().getSubject().getCN()+"' differente da quello atteso");
		}
		
		byte [] DER = Utilities.getAsByteArray(CertificateReaderTest.class.getResourceAsStream("/org/openspcoop2/utils/certificate/test/Soggetto1.der"));
		System.out.println("DER ...");
		certDecoded =  ArchiveLoader.load(DER);
		System.out.println("DER: "+certDecoded.getCertificate().toString());
		if(!"Soggetto1".equals(certDecoded.getCertificate().getSubject().getCN())) {
			throw new Exception("CN '"+certDecoded.getCertificate().getSubject().getCN()+"' differente da quello atteso");
		}
		
		
		// Gli esempi sottostanti si riferiscono a manipolazioni del certificato Soggetto1.*
		
		// Esempio PEM
		String cert = "-----BEGIN CERTIFICATE-----\n"+
"MIICgDCCAekCBE6Vnp0wDQYJKoZIhvcNAQEFBQAwgYYxHDAaBgkqhkiG9w0BCQEW\n"+
"DWFwb2xpQGxpbmsuaXQxCzAJBgNVBAYTAklUMQ4wDAYDVQQIDAVJdGFseTENMAsG\n"+
"A1UEBwwEUGlzYTEXMBUGA1UECgwOb3BlbnNwY29vcC5vcmcxDTALBgNVBAsMBHRl\n"+
"c3QxEjAQBgNVBAMMCVNvZ2dldHRvMTAeFw0xMTEwMTIxNDA1MTdaFw0yMTEwMDkx\n"+
"NDA1MTdaMIGGMRwwGgYJKoZIhvcNAQkBFg1hcG9saUBsaW5rLml0MQswCQYDVQQG\n"+
"EwJJVDEOMAwGA1UECAwFSXRhbHkxDTALBgNVBAcMBFBpc2ExFzAVBgNVBAoMDm9w\n"+
"ZW5zcGNvb3Aub3JnMQ0wCwYDVQQLDAR0ZXN0MRIwEAYDVQQDDAlTb2dnZXR0bzEw\n"+
"gZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAI7Zy01BmPwitnoX+rOc+zUHpOSt\n"+
"8JXndItBQVOfNGZ8i+qFV564eLYPHOS5pyNlG0xpivNYokO12CAANrnv4lkqG7W3\n"+
"lv2hX0qLlq0h+IdhV7jqTxOOVfwMiYMaI9IRiRs26Af/1RMMH3Q3KhiM4blW6q/J\n"+
"KkQeVPeBYqyffIZlAgMBAAEwDQYJKoZIhvcNAQEFBQADgYEATeUM8Flh3BmhArqZ\n"+
"GVntBS3tEaGzHimyMUjMDncpKxR9aSIMib4t0Fq8jBCNsnQRPoUWObrdbMm7Yknm\n"+
"zK86buXUG6n/nJruzAM1Wp8Tqc4dN9XX7F/MAszxOLxr4Acr4jbHExsTSPD1yEo9\n"+
"9yRr/onMnZurvTMTwzcJgpRjheE=\n"+
"-----END CERTIFICATE-----";
		CertificateDecodeConfig config = new CertificateDecodeConfig();
		config.setBase64Decode(false);
		config.setUrlDecode(false);
		System.out.println("PEM (asString) ...");
		certDecoded = CertificateUtils.readCertificate(config , cert);
		System.out.println("PEM (asString): "+certDecoded.getCertificate().toString());
		if(!"Soggetto1".equals(certDecoded.getCertificate().getSubject().getCN())) {
			throw new Exception("CN '"+certDecoded.getCertificate().getSubject().getCN()+"' differente da quello atteso");
		}
		
		// Esempio PEM senza BEGIN e END (PEM NON CORRETTO!!!!)
		cert = 
"MIICgDCCAekCBE6Vnp0wDQYJKoZIhvcNAQEFBQAwgYYxHDAaBgkqhkiG9w0BCQEW\n"+
"DWFwb2xpQGxpbmsuaXQxCzAJBgNVBAYTAklUMQ4wDAYDVQQIDAVJdGFseTENMAsG\n"+
"A1UEBwwEUGlzYTEXMBUGA1UECgwOb3BlbnNwY29vcC5vcmcxDTALBgNVBAsMBHRl\n"+
"c3QxEjAQBgNVBAMMCVNvZ2dldHRvMTAeFw0xMTEwMTIxNDA1MTdaFw0yMTEwMDkx\n"+
"NDA1MTdaMIGGMRwwGgYJKoZIhvcNAQkBFg1hcG9saUBsaW5rLml0MQswCQYDVQQG\n"+
"EwJJVDEOMAwGA1UECAwFSXRhbHkxDTALBgNVBAcMBFBpc2ExFzAVBgNVBAoMDm9w\n"+
"ZW5zcGNvb3Aub3JnMQ0wCwYDVQQLDAR0ZXN0MRIwEAYDVQQDDAlTb2dnZXR0bzEw\n"+
"gZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAI7Zy01BmPwitnoX+rOc+zUHpOSt\n"+
"8JXndItBQVOfNGZ8i+qFV564eLYPHOS5pyNlG0xpivNYokO12CAANrnv4lkqG7W3\n"+
"lv2hX0qLlq0h+IdhV7jqTxOOVfwMiYMaI9IRiRs26Af/1RMMH3Q3KhiM4blW6q/J\n"+
"KkQeVPeBYqyffIZlAgMBAAEwDQYJKoZIhvcNAQEFBQADgYEATeUM8Flh3BmhArqZ\n"+
"GVntBS3tEaGzHimyMUjMDncpKxR9aSIMib4t0Fq8jBCNsnQRPoUWObrdbMm7Yknm\n"+
"zK86buXUG6n/nJruzAM1Wp8Tqc4dN9XX7F/MAszxOLxr4Acr4jbHExsTSPD1yEo9\n"+
"9yRr/onMnZurvTMTwzcJgpRjheE=";
		config = new CertificateDecodeConfig();
		config.setBase64Decode(false);
		config.setUrlDecode(false);
		config.setEnrich_BEGIN_END(true);
		System.out.println("PEM senza BEGIN e END ...");
		certDecoded = CertificateUtils.readCertificate(config , cert);
		System.out.println("PEM senza BEGIN e END: "+certDecoded.getCertificate().toString());
		if(!"Soggetto1".equals(certDecoded.getCertificate().getSubject().getCN())) {
			throw new Exception("CN '"+certDecoded.getCertificate().getSubject().getCN()+"' differente da quello atteso");
		}
			
		// Esempio PEM con URL Encoded
		cert = "-----BEGIN%20CERTIFICATE-----%0AMIICgDCCAekCBE6Vnp0wDQYJKoZIhvcNAQEFBQAwgYYxHDAaBgkqhkiG9w0BCQEW%0ADWFwb2xpQGxpbmsuaXQxCzAJBgNVBAYTAklUMQ4wDAYDVQQIDAVJdGFseTENMAsG%0AA1UEBwwEUGlzYTEXMBUGA1UECgwOb3BlbnNwY29vcC5vcmcxDTALBgNVBAsMBHRl%0Ac3QxEjAQBgNVBAMMCVNvZ2dldHRvMTAeFw0xMTEwMTIxNDA1MTdaFw0yMTEwMDkx%0ANDA1MTdaMIGGMRwwGgYJKoZIhvcNAQkBFg1hcG9saUBsaW5rLml0MQswCQYDVQQG%0AEwJJVDEOMAwGA1UECAwFSXRhbHkxDTALBgNVBAcMBFBpc2ExFzAVBgNVBAoMDm9w%0AZW5zcGNvb3Aub3JnMQ0wCwYDVQQLDAR0ZXN0MRIwEAYDVQQDDAlTb2dnZXR0bzEw%0AgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAI7Zy01BmPwitnoX%2BrOc%2BzUHpOSt%0A8JXndItBQVOfNGZ8i%2BqFV564eLYPHOS5pyNlG0xpivNYokO12CAANrnv4lkqG7W3%0Alv2hX0qLlq0h%2BIdhV7jqTxOOVfwMiYMaI9IRiRs26Af%2F1RMMH3Q3KhiM4blW6q%2FJ%0AKkQeVPeBYqyffIZlAgMBAAEwDQYJKoZIhvcNAQEFBQADgYEATeUM8Flh3BmhArqZ%0AGVntBS3tEaGzHimyMUjMDncpKxR9aSIMib4t0Fq8jBCNsnQRPoUWObrdbMm7Yknm%0AzK86buXUG6n%2FnJruzAM1Wp8Tqc4dN9XX7F%2FMAszxOLxr4Acr4jbHExsTSPD1yEo9%0A9yRr%2FonMnZurvTMTwzcJgpRjheE%3D%0A-----END%20CERTIFICATE-----";
		config = new CertificateDecodeConfig();
		config.setBase64Decode(false);
		config.setUrlDecode(true);
		System.out.println("PEM + URL Encoded ...");
		certDecoded = CertificateUtils.readCertificate(config , cert);
		System.out.println("PEM + URL Encoded: "+certDecoded.getCertificate().toString());
		if(!"Soggetto1".equals(certDecoded.getCertificate().getSubject().getCN())) {
			throw new Exception("CN '"+certDecoded.getCertificate().getSubject().getCN()+"' differente da quello atteso");
		}
		
		// Esempio PEM con URL Encoded senza BEGIN e END e \n (PEM NON CORRETTO!!!!)
		cert = "MIICgDCCAekCBE6Vnp0wDQYJKoZIhvcNAQEFBQAwgYYxHDAaBgkqhkiG9w0BCQEW%0ADWFwb2xpQGxpbmsuaXQxCzAJBgNVBAYTAklUMQ4wDAYDVQQIDAVJdGFseTENMAsG%0AA1UEBwwEUGlzYTEXMBUGA1UECgwOb3BlbnNwY29vcC5vcmcxDTALBgNVBAsMBHRl%0Ac3QxEjAQBgNVBAMMCVNvZ2dldHRvMTAeFw0xMTEwMTIxNDA1MTdaFw0yMTEwMDkx%0ANDA1MTdaMIGGMRwwGgYJKoZIhvcNAQkBFg1hcG9saUBsaW5rLml0MQswCQYDVQQG%0AEwJJVDEOMAwGA1UECAwFSXRhbHkxDTALBgNVBAcMBFBpc2ExFzAVBgNVBAoMDm9w%0AZW5zcGNvb3Aub3JnMQ0wCwYDVQQLDAR0ZXN0MRIwEAYDVQQDDAlTb2dnZXR0bzEw%0AgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAI7Zy01BmPwitnoX%2BrOc%2BzUHpOSt%0A8JXndItBQVOfNGZ8i%2BqFV564eLYPHOS5pyNlG0xpivNYokO12CAANrnv4lkqG7W3%0Alv2hX0qLlq0h%2BIdhV7jqTxOOVfwMiYMaI9IRiRs26Af%2F1RMMH3Q3KhiM4blW6q%2FJ%0AKkQeVPeBYqyffIZlAgMBAAEwDQYJKoZIhvcNAQEFBQADgYEATeUM8Flh3BmhArqZ%0AGVntBS3tEaGzHimyMUjMDncpKxR9aSIMib4t0Fq8jBCNsnQRPoUWObrdbMm7Yknm%0AzK86buXUG6n%2FnJruzAM1Wp8Tqc4dN9XX7F%2FMAszxOLxr4Acr4jbHExsTSPD1yEo9%0A9yRr%2FonMnZurvTMTwzcJgpRjheE";
		config = new CertificateDecodeConfig();
		config.setBase64Decode(false);
		config.setEnrich_BEGIN_END(true);
		config.setUrlDecode(true);
		System.out.println("PEM + URL Encoded senza BEGIN e END ...");
		certDecoded = CertificateUtils.readCertificate(config , cert);
		System.out.println("PEM + URL Encoded senza BEGIN e END: "+certDecoded.getCertificate().toString());
		if(!"Soggetto1".equals(certDecoded.getCertificate().getSubject().getCN())) {
			throw new Exception("CN '"+certDecoded.getCertificate().getSubject().getCN()+"' differente da quello atteso");
		}
		
		// Esempio DER codificato in base64
		cert = "MIICgDCCAekCBE6Vnp0wDQYJKoZIhvcNAQEFBQAwgYYxHDAaBgkqhkiG9w0BCQEWDWFwb2xpQGxpbmsuaXQxCzAJBgNVBAYTAklUMQ4wDAYDVQQIDAVJdGFseTENMAsGA1UEBwwEUGlzYTEXMBUGA1UECgwOb3BlbnNwY29vcC5vcmcxDTALBgNVBAsMBHRlc3QxEjAQBgNVBAMMCVNvZ2dldHRvMTAeFw0xMTEwMTIxNDA1MTdaFw0yMTEwMDkxNDA1MTdaMIGGMRwwGgYJKoZIhvcNAQkBFg1hcG9saUBsaW5rLml0MQswCQYDVQQGEwJJVDEOMAwGA1UECAwFSXRhbHkxDTALBgNVBAcMBFBpc2ExFzAVBgNVBAoMDm9wZW5zcGNvb3Aub3JnMQ0wCwYDVQQLDAR0ZXN0MRIwEAYDVQQDDAlTb2dnZXR0bzEwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAI7Zy01BmPwitnoX+rOc+zUHpOSt8JXndItBQVOfNGZ8i+qFV564eLYPHOS5pyNlG0xpivNYokO12CAANrnv4lkqG7W3lv2hX0qLlq0h+IdhV7jqTxOOVfwMiYMaI9IRiRs26Af/1RMMH3Q3KhiM4blW6q/JKkQeVPeBYqyffIZlAgMBAAEwDQYJKoZIhvcNAQEFBQADgYEATeUM8Flh3BmhArqZGVntBS3tEaGzHimyMUjMDncpKxR9aSIMib4t0Fq8jBCNsnQRPoUWObrdbMm7YknmzK86buXUG6n/nJruzAM1Wp8Tqc4dN9XX7F/MAszxOLxr4Acr4jbHExsTSPD1yEo99yRr/onMnZurvTMTwzcJgpRjheE=";
		config = new CertificateDecodeConfig();
		config.setBase64Decode(true);
		config.setUrlDecode(false);
		System.out.println("DER + BASE64 ...");
		certDecoded = CertificateUtils.readCertificate(config , cert);
		System.out.println("DER + BASE64: "+certDecoded.getCertificate().toString());
		if(!"Soggetto1".equals(certDecoded.getCertificate().getSubject().getCN())) {
			throw new Exception("CN '"+certDecoded.getCertificate().getSubject().getCN()+"' differente da quello atteso");
		}
		
		
	}

}
