/**   
 * License Agreement for OpenSearchServer
 *
 * Copyright (C) 2008-2013 Emmanuel Keller / Jaeksoft
 * 
 * http://www.open-search-server.com
 * 
 * This file is part of OpenSearchServer.
 *
 * OpenSearchServer is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 * OpenSearchServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with OpenSearchServer. 
 *  If not, see <http://www.gnu.org/licenses/>.
 **/

package com.jaeksoft.searchlib.crawler.web.spider;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;

import com.jaeksoft.searchlib.SearchLibException;
import com.jaeksoft.searchlib.crawler.web.database.CookieItem;
import com.jaeksoft.searchlib.crawler.web.database.CredentialItem;

public class HttpDownloader extends HttpAbstract {

	public static enum Method {
		GET(false), POST(true), PUT(false), DELETE(false), HEAD(false), PATCH(
				true), OPTIONS(false);

		private final boolean acceptPayload;

		private Method(boolean payload) {
			this.acceptPayload = payload;
		}

		/**
		 * @return the acceptPayload
		 */
		public boolean isAcceptPayload() {
			return acceptPayload;
		}

		public final static Method find(String text, Method defaultMethod) {
			for (Method method : values())
				if (method.name().equalsIgnoreCase(text))
					return method;
			return defaultMethod;
		}

	}

	public HttpDownloader(final String userAgent,
			final boolean bFollowRedirect, final ProxyHandler proxyHandler) {
		super(userAgent, bFollowRedirect, proxyHandler);
	}

	private final void addHeader(HttpRequest httpRequest,
			List<Header> additionalHeaders) {
		if (additionalHeaders == null)
			return;
		for (Header header : additionalHeaders)
			httpRequest.addHeader(header);
	}

	private final DownloadItem getDownloadItem(final URI uri)
			throws IllegalStateException, IOException, SearchLibException {
		DownloadItem downloadItem = new DownloadItem(uri);
		downloadItem.setRedirectLocation(getRedirectLocation());
		downloadItem.setContentLength(getContentLength());
		downloadItem.setLastModified(getLastModified());
		downloadItem
				.setContentDispositionFilename(getContentDispositionFilename());
		downloadItem.setContentBaseType(getContentBaseType());
		downloadItem.setContentEncoding(getContentEncoding());
		downloadItem.setContentLocation(getContentLocation());
		downloadItem.setContentTypeCharset(getContentTypeCharset());
		downloadItem.setStatusCode(getStatusCode());
		downloadItem.setReasonPhrase(getReasonPhrase());
		downloadItem.setContentInputStream(getContent());
		downloadItem.setHeaders(getHeaders());
		return downloadItem;
	}

	private final DownloadItem request(final HttpRequestBase httpRequestBase,
			final CredentialItem credentialItem,
			final List<Header> additionalHeaders,
			final List<CookieItem> cookies, final HttpEntity entity)
			throws ClientProtocolException, IOException, URISyntaxException,
			IllegalStateException, SearchLibException {
		synchronized (this) {
			reset();
			if (entity != null)
				((HttpEntityEnclosingRequest) httpRequestBase)
						.setEntity(entity);
			addHeader(httpRequestBase, additionalHeaders);
			execute(httpRequestBase, credentialItem, cookies);
			return getDownloadItem(httpRequestBase.getURI());
		}
	}

	public final DownloadItem request(final URI uri, final Method method,
			final CredentialItem credentialItem,
			final List<Header> additionalHeaders,
			final List<CookieItem> cookies, final HttpEntity entity)
			throws ClientProtocolException, IllegalStateException, IOException,
			URISyntaxException, SearchLibException {
		HttpRequestBase httpRequestBase;
		switch (method) {
		case GET:
			httpRequestBase = new HttpGet(uri);
			break;
		case POST:
			httpRequestBase = new HttpPost(uri);
			break;
		case PUT:
			httpRequestBase = new HttpPut(uri);
			break;
		case DELETE:
			httpRequestBase = new HttpDelete(uri);
			break;
		case OPTIONS:
			httpRequestBase = new HttpOptions(uri);
			break;
		case PATCH:
			httpRequestBase = new HttpPatch(uri);
			break;
		case HEAD:
			httpRequestBase = new HttpHead(uri);
			break;
		default:
			throw new SearchLibException("Unkown method: " + method);
		}
		return request(httpRequestBase, credentialItem, additionalHeaders,
				cookies, entity);
	}

	public DownloadItem patch(URI uri, CredentialItem credentialItem,
			List<Header> additionalHeaders, List<CookieItem> cookies,
			HttpEntity entity) throws ClientProtocolException, IOException,
			URISyntaxException, IllegalStateException, SearchLibException {
		return request(uri, Method.PATCH, credentialItem, additionalHeaders,
				cookies, entity);
	}

	public DownloadItem get(URI uri, CredentialItem credentialItem,
			List<Header> additionalHeaders, List<CookieItem> cookies)
			throws ClientProtocolException, IOException, IllegalStateException,
			SearchLibException, URISyntaxException {
		return request(uri, Method.GET, credentialItem, additionalHeaders,
				cookies, null);
	}

	public DownloadItem get(URI uri, CredentialItem credentialItem)
			throws ClientProtocolException, IOException, IllegalStateException,
			SearchLibException, URISyntaxException {
		return get(uri, credentialItem, null, null);
	}

	public DownloadItem head(URI uri, CredentialItem credentialItem,
			List<Header> additionalHeaders, List<CookieItem> cookies)
			throws ClientProtocolException, IOException, IllegalStateException,
			SearchLibException, URISyntaxException {
		return request(uri, Method.HEAD, credentialItem, additionalHeaders,
				cookies, null);
	}

	public DownloadItem head(URI uri, CredentialItem credentialItem)
			throws ClientProtocolException, IOException, IllegalStateException,
			SearchLibException, URISyntaxException {
		return head(uri, credentialItem, null, null);
	}

	public DownloadItem post(URI uri, CredentialItem credentialItem,
			List<Header> additionalHeaders, List<CookieItem> cookies,
			HttpEntity entity) throws ClientProtocolException, IOException,
			IllegalStateException, SearchLibException, URISyntaxException {
		return request(uri, Method.POST, credentialItem, additionalHeaders,
				cookies, entity);
	}

	public DownloadItem options(URI uri, CredentialItem credentialItem,
			List<Header> additionalHeaders, List<CookieItem> cookies)
			throws ClientProtocolException, IOException, IllegalStateException,
			SearchLibException, URISyntaxException {
		return request(uri, Method.OPTIONS, credentialItem, additionalHeaders,
				cookies, null);
	}

	public DownloadItem delete(URI uri, CredentialItem credentialItem,
			List<Header> additionalHeaders, List<CookieItem> cookies)
			throws ClientProtocolException, IOException, IllegalStateException,
			SearchLibException, URISyntaxException {
		return request(uri, Method.DELETE, credentialItem, additionalHeaders,
				cookies, null);
	}

	public DownloadItem put(URI uri, CredentialItem credentialItem,
			List<Header> additionalHeaders, List<CookieItem> cookies,
			HttpEntity entity) throws ClientProtocolException, IOException,
			IllegalStateException, SearchLibException, URISyntaxException {
		return request(uri, Method.PUT, credentialItem, additionalHeaders,
				cookies, entity);
	}
}
