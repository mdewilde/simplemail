/*
	Copyright 2015 Marceau Dewilde <m@ceau.be>

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
*/
package be.ceau.simplemail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;

public class Mail implements Serializable {

	private static final long serialVersionUID = 1438888844956L;

	private InternetAddress from;
	private final List<InternetAddress> tos = new ArrayList<InternetAddress>();
	private final List<InternetAddress> ccs = new ArrayList<InternetAddress>();
	private final List<InternetAddress> bccs = new ArrayList<InternetAddress>();
	private String subject;
	private String txt;
	private String html;

	/**
	 * Constructs a new empty Mail instance
	 */
	public Mail() {	}

	/**
	 * Adds an email address as sender.<br>
	 * Calling this method <strong>overwrites any value for the <code>from</code> field</strong> set previously. 
	 * @param from a valid, non-null {@link javax.mail.internet.InternetAddress}
	 * @return <code>this</code> Mail instance, to allow for method chaining
	 * @throws IllegalArgumentException if argument is <code>null</code>
	 */
	public Mail from(InternetAddress from) {
		if (from == null) {
			throw new IllegalArgumentException("from argument can not be null");
		}
		this.from = from;
		return this;
	}

	/**
	 * @param from a valid, non-blank String that can be parsed as {@link javax.mail.internet.InternetAddress}
	 * @return <code>this</code> Mail instance, to allow for method chaining
	 * @throws IllegalArgumentException if argument is not a legal email address
	 * @see #from(InternetAddress)
	 */
	public Mail from(String from) {
		this.from = Mailer.convert(from);
		return this;
	}

	/**
	 * Adds an email address as <code>to</code> recipient.<br>
	 * Calling this method multiple times adds multiple <code>to</code> addresses to the email. 
	 * It <strong>does not overwrite</strong> previously set addresses.
	 * @param to a valid, non-null {@link javax.mail.internet.InternetAddress}
	 * @return <code>this</code> Mail instance, to allow for method chaining
	 * @throws IllegalArgumentException if argument is <code>null</code>
	 */
	public Mail to(InternetAddress to) {
		if (to == null) {
			throw new IllegalArgumentException("to argument can not be null");
		}
		this.tos.add(to);
		return this;
	}

	/**
	 * @param to a valid, non-blank String that can be parsed as {@link javax.mail.internet.InternetAddress}
	 * @return <code>this</code> Mail instance
	 * @throws IllegalArgumentException if argument is not a legal email address
	 * @see #to(InternetAddress)
	 */
	public Mail to(String to) {
		this.tos.add(Mailer.convert(to));
		return this;
	}

	/**
	 * Adds an email address as <code>cc</code> recipient.<br>
	 * Calling this method multiple times adds multiple <code>cc</code> addresses to the email. 
	 * It <strong>does not overwrite</strong> previously set addresses.
	 * @param cc a valid, non-null {@link javax.mail.internet.InternetAddress}
	 * @return <code>this</code> Mail instance, to allow for method chaining
	 * @throws IllegalArgumentException if argument is <code>null</code>
	 */
	public Mail cc(InternetAddress cc) {
		if (cc == null) {
			throw new IllegalArgumentException("cc argument can not be null");
		}
		this.ccs.add(cc);
		return this;
	}
	
	/**
	 * @param cc a valid, non-blank String that can be parsed as {@link javax.mail.internet.InternetAddress}
	 * @return <code>this</code> Mail instance
	 * @throws IllegalArgumentException if argument is not a legal email address
	 * @see #cc(InternetAddress)
	 */
	public Mail cc(String cc) {
		this.ccs.add(Mailer.convert(cc));
		return this;
	}

	/**
	 * Adds an email address as <code>bcc</code> recipient.<br>
	 * Calling this method multiple times adds multiple <code>bcc</code> addresses to the email. 
	 * It <strong>does not overwrite</strong> previously set addresses.
	 * @param bcc a valid, non-null {@link javax.mail.internet.InternetAddress}
	 * @return <code>this</code> Mail instance, to allow for method chaining
	 * @throws IllegalArgumentException if argument is <code>null</code>
	 */
	public Mail bcc(InternetAddress bcc) {
		if (bcc == null) {
			throw new IllegalArgumentException("bcc argument can not be null");
		}
		this.bccs.add(bcc);
		return this;
	}

	/**
	 * @param bcc a valid, non-blank String that can be parsed as {@link javax.mail.internet.InternetAddress}
	 * @return <code>this</code> Mail instance
	 * @throws IllegalArgumentException if argument is not a legal email address
	 * @see #bcc(InternetAddress)
	 */
	public Mail bcc(String bcc) {
		this.bccs.add(Mailer.convert(bcc));
		return this;
	}

	/**
	 * @param subject a String (no restrictions)
	 * @return <code>this</code> Mail instance, to allow for method chaining
	 */
	public Mail withSubject(String subject) {
		this.subject = subject;
		return this;
	}

	/**
	 * @param txt a String (no restrictions)
	 * @return <code>this</code> Mail instance, to allow for method chaining
	 */
	public Mail withText(String txt) {
		this.txt = txt;
		return this;
	}

	/**
	 * @param html a String (no restrictions)
	 * @return <code>this</code> Mail instance, to allow for method chaining
	 */
	public Mail withHtml(String html) {
		this.html = html;
		return this;
	}

	@Override
	public String toString() {
		return "Mail [from=" + from + ", tos=" + tos + ", ccs=" + ccs + ", bccs=" + bccs + ", subject=" + subject + ", txt=" + txt + ", html=" + html + "]";
	}

	InternetAddress getFrom() {
		return from;
	}

	boolean hasFrom() {
		return from != null;
	}

	List<InternetAddress> getTos() {
		return tos;
	}

	/**
	 * @return true if <code>this</code> Mail instance has at least one <code>to</code> InternetAddress
	 */
	boolean hasTo() {
		return tos.size() > 0;
	}

	List<InternetAddress> getCcs() {
		return ccs;
	}

	List<InternetAddress> getBccs() {
		return bccs;
	}

	String getSubject() {
		return subject;
	}

	boolean hasSubject() {
		return subject != null;
	}

	boolean hasTxt() {
		return txt != null && !txt.isEmpty();
	}

	String getTxt() {
		return txt;
	}

	void setTxt(String txt) {
		this.txt = txt;
	}

	boolean hasHtml() {
		return html != null && !html.isEmpty();
	}
	
	String getHtml() {
		return html;
	}

	void setHtml(String html) {
		this.html = html;
	}

	BodyPart getTxtBodyPart() throws MessagingException {
		BodyPart part = new MimeBodyPart();
		part.setContent(txt, "text/plain; charset=UTF-8");
		return part;
	}
	
	BodyPart getHtmlBodyPart() throws MessagingException {
		BodyPart part = new MimeBodyPart();
		part.setContent(html, "text/html; charset=UTF-8");
		return part;
	}

}