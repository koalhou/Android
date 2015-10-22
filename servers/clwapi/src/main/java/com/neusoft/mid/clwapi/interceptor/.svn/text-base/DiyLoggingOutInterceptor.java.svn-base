/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.neusoft.mid.clwapi.interceptor;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingMessage;
import org.apache.cxf.interceptor.StaxOutInterceptor;
import org.apache.cxf.io.CacheAndWriteOutputStream;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.io.CachedOutputStreamCallback;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2012-4-11 下午4:20:24
 */
public class DiyLoggingOutInterceptor extends DiyAbstractLoggingInterceptor {
	private static final Logger LOG = LogUtils
			.getLogger(DiyLoggingOutInterceptor.class);
	private static final String LOG_SETUP = DiyLoggingOutInterceptor.class
			.getName() + ".log-setup";

	public DiyLoggingOutInterceptor(final String phase) {
		super(phase);
		addBefore(StaxOutInterceptor.class.getName());
	}

	public DiyLoggingOutInterceptor() {
		this(Phase.PRE_STREAM);
	}

	public DiyLoggingOutInterceptor(final int lim) {
		this();
		limit = lim;
	}

	public DiyLoggingOutInterceptor(final PrintWriter w) {
		this();
		this.writer = w;
	}

	@Override
	public void handleMessage(final Message message) throws Fault {
		final OutputStream os = message.getContent(OutputStream.class);
		if (os == null) {
			return;
		}
		final Logger logger = getMessageLogger(message);
		if (logger.isLoggable(Level.INFO) || writer != null) {
			// Write the output while caching it for the log message
			final boolean hasLogged = message.containsKey(LOG_SETUP);
			if (!hasLogged) {
				message.put(LOG_SETUP, Boolean.TRUE);
				final CacheAndWriteOutputStream newOut = new CacheAndWriteOutputStream(
						os);
				message.setContent(OutputStream.class, newOut);
				newOut.registerCallback(new LoggingCallback(logger, message, os));
			}
		}
	}

	class LoggingCallback implements CachedOutputStreamCallback {

		private final Message message;
		private final OutputStream origStream;
		@SuppressWarnings("unused")
		private final Logger logger; // NOPMD

		public LoggingCallback(final Logger logger, final Message msg,
				final OutputStream os) {
			this.logger = logger;
			this.message = msg;
			this.origStream = os;
		}

		@Override
		public void onFlush(final CachedOutputStream cos) {

		}

		@Override
		public void onClose(final CachedOutputStream cos) {

			String id = (String) message.getExchange().get(
					LoggingMessage.ID_KEY);
			if (id == null) {
				id = LoggingMessage.nextId();
				message.getExchange().put(LoggingMessage.ID_KEY, id);
			}
			final LoggingMessage buffer = new LoggingMessage(
					"Outbound Message\n---------------------------", id);

			final Integer responseCode = (Integer) message
					.get(Message.RESPONSE_CODE);
			if (responseCode != null) {
				buffer.getResponseCode().append(responseCode);
			}

			final String encoding = (String) message.get(Message.ENCODING);

			if (encoding != null) {
				buffer.getEncoding().append(encoding);
			}
			final String httpMethod = (String) message
					.get(Message.HTTP_REQUEST_METHOD);
			if (httpMethod != null) {
				buffer.getHttpMethod().append(httpMethod);
			}
			final String address = (String) message
					.get(Message.ENDPOINT_ADDRESS);
			if (address != null) {
				buffer.getAddress().append(address);
			}
			final String ct = (String) message.get(Message.CONTENT_TYPE);
			if (ct != null) {
				buffer.getContentType().append(ct);
			}
			final Object headers = message.get(Message.PROTOCOL_HEADERS);
			if (headers != null) {
				buffer.getHeader().append(headers);
			}

			if (cos.getTempFile() == null) {
				// buffer.append("Outbound Message:\n");
				if (cos.size() > limit) {
					buffer.getMessage().append(
							"(message truncated to " + limit + " bytes)\n");
				}
			} else {
				buffer.getMessage().append(
						"Outbound Message (saved to tmp file):\n");
				buffer.getMessage().append(
						"Filename: " + cos.getTempFile().getAbsolutePath()
								+ "\n");
				if (cos.size() > limit) {
					buffer.getMessage().append(
							"(message truncated to " + limit + " bytes)\n");
				}
			}
			try {
				writePayload(buffer.getPayload(), cos, encoding, ct);
			} catch (final Exception ex) {
				// ignore
			}

			// log(logger, buffer.toString());
			LOG.info(buffer.toString());
			try {
				// empty out the cache
				cos.lockOutputStream();
				cos.resetOut(null, false);
			} catch (final Exception ex) {
				// ignore
			}
			message.setContent(OutputStream.class, origStream);
		}
	}

	@Override
	protected Logger getLogger() {
		return LOG;

	}

}
