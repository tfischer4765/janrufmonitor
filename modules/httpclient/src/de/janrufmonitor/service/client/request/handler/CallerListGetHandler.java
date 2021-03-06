package de.janrufmonitor.service.client.request.handler;

import java.net.URI;

import de.janrufmonitor.repository.filter.AbstractFilterSerializer;
import de.janrufmonitor.repository.filter.IFilter;
import de.janrufmonitor.runtime.IRuntime;
import de.janrufmonitor.runtime.PIMRuntime;
import de.janrufmonitor.service.commons.http.jakarta.Request;

public class CallerListGetHandler extends Request {

	private class URLFilterManager extends AbstractFilterSerializer {		
		private IRuntime m_runtime;
		
		protected IRuntime getRuntime() {
			if (this.m_runtime==null)
				this.m_runtime = PIMRuntime.getInstance();
			return this.m_runtime;
		}
	}
	
	private String m_filter;
	private String m_cm;
	
	public CallerListGetHandler(String callermanager) {
		this.m_cm = callermanager;
	}

	public CallerListGetHandler(String callermanager, IFilter[] filters) {
		this.m_filter = new URLFilterManager().getFiltersToString(filters);
		this.m_cm = callermanager;
	}

	public URI getURI() throws Exception {
		StringBuffer uri = new StringBuffer();
		uri.append("/?");
		uri.append(CallerListGetHandler.PARAMETER_ACTION);
		uri.append("=");
		uri.append(CallerListGetHandler.ACTION_GETCALLERLIST);
		if (this.m_filter!=null && this.m_filter.length()>0) {
			uri.append("&");
			uri.append(CallerListGetHandler.PARAMETER_FILTER);
			uri.append("=");
			uri.append(this.m_filter);
		}

		if (this.m_cm!=null && this.m_cm.length()>0) {
			uri.append("&");
			uri.append(CallerListGetHandler.PARAMETER_CALLERMANAGER);
			uri.append("=");
			uri.append(this.m_cm);
		}
		// add compression request
		uri.append("&");
		uri.append(CallerListGetHandler.PARAMETER_COMPRESSION);
		uri.append("=true");			
		return new URI(uri.toString());
	}

}

