/*
 * GovWay - A customizable API Gateway 
 * https://govway.org
 * 
 * Copyright (c) 2005-2020 Link.it srl (https://link.it).
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
package org.openspcoop2.security.message.signature;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.transforms.Transform;
import org.apache.xml.security.transforms.TransformationException;
import org.apache.xml.security.transforms.implementations.TransformBase64Decode;

/**
 * XMLSecAttachmentBase64ContentTransform
 *
 * @author Andrea Poli (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class XMLSecAttachmentBase64ContentTransform extends TransformBase64Decode {

	@Override
	protected XMLSignatureInput enginePerformTransform(XMLSignatureInput arg0,
			OutputStream arg1, Transform arg2) throws IOException,
			CanonicalizationException, TransformationException {
		return super.enginePerformTransform(arg0, arg1, arg2);
	}

	@Override
	protected XMLSignatureInput enginePerformTransform(XMLSignatureInput arg0,
			Transform arg1) throws IOException, CanonicalizationException,
			TransformationException {
		return super.enginePerformTransform(arg0, arg1);
	}
	
}
