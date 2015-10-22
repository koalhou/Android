/**
 * @(#)ClientLoggingFeature.java 2013-4-9
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.feature;

import org.apache.cxf.Bus;
import org.apache.cxf.feature.AbstractFeature;
import org.apache.cxf.interceptor.InterceptorProvider;

import com.neusoft.mid.clwapi.interceptor.ClientLoggingInInterceptor;
import com.neusoft.mid.clwapi.interceptor.ClientLoggingOutInterceptor;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-4-9 下午9:43:55
 */
public class ClientLoggingFeature extends AbstractFeature {
    private ClientLoggingOutInterceptor outInterceptor;
    private ClientLoggingInInterceptor inInterceptor;

    @Override
    protected void initializeProvider(InterceptorProvider provider, Bus bus) {
    	outInterceptor = new ClientLoggingOutInterceptor();
    	inInterceptor = new ClientLoggingInInterceptor();
    	
        provider.getOutInterceptors().add(outInterceptor);
        provider.getOutFaultInterceptors().add(outInterceptor);
        provider.getInInterceptors().add(inInterceptor);
        provider.getInFaultInterceptors().add(inInterceptor);
    }

}
