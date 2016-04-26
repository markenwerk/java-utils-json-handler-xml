/*
 * Copyright (c) 2016 Torsten Krause, Markenwerk GmbH
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.markenwerk.utils.json.common.handler.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import net.markenwerk.utils.json.common.InvalidJsonNameException;
import net.markenwerk.utils.json.common.InvalidJsonValueException;
import net.markenwerk.utils.json.handler.IdleJsonHandler;
import net.markenwerk.utils.json.handler.JsonHandler;
import net.markenwerk.utils.json.handler.JsonHandlingException;

/**
 * A {@link XmlDocumentJsonHandler} is a {@link JsonHandler} that creates a XML
 * {@link Document} that represents the same data as the handled JSON document
 * using the following rules:
 * 
 * <ul>
 * <li>A JSON array is represented as a tag with name {@code array}.</li>
 * <li>A JSON object is represented as a tag with name {@code object}.</li>
 * <li>A JSON object entry is represented as a tag with name {@code entry}.</li>
 * <li>The name of a JSON object entry is represented as an attribute of the
 * {@code entry} tag with name {@code name}.</li>
 * <li>A JSON null is represented as a tag with name {@code null}.</li>
 * <li>A JSON boolean is represented as a tag with name {@code boolean}.</li>
 * <li>The value of the JSON boolean represented as an attribute of the
 * {@code boolean} tag with name {@code value} .</li>
 * <li>A JSON number is represented as a tag with name {@code number}.</li>
 * <li>The value of the JSON number represented as an attribute of the
 * {@code number} tag with name {@code value}.</li>
 * <li>A JSON string is represented as a tag with name {@code string}.</li>
 * <li>The value of the JSON string represented as an attribute of the
 * {@code string} tag with name {@code value} .</li>
 * </ul>
 * 
 * <p>
 * <b>Example:</b>
 * 
 * <p>
 * Input JSON document:
 * 
 * <pre>
 * {
 *     "nullValue": null,
 *     "booleanValue": true,
 *     "longValue": -10000,
 *     "doubleValue": 42.23,
 *     "stringValue": "foobar",
 *     "arrayValue": [],
 *     "objectValue": {}
 * }
 * </pre>
 * 
 * Output XML document:
 * 
 * <pre>
 * {@code
 * <?xml version="1.0" encoding="UTF-8"?>
 * <object>
 *    <entry name="nullValue">
 *       <null />
 *    </entry>
 *    <entry name="booleanValue">
 *       <boolean value="true" />
 *    </entry>
 *    <entry name="longValue">
 *       <number value="-10000"/>
 *    </entry>
 *    <entry name="doubleValue">
 *       <number value="42.23" />
 *    </entry>
 *    <entry name="stringValue">
 *       <string value="foobar">
 *    </entry>
 *    <entry name="arrayValue">
 *       <array />
 *    </entry>
 *    <entry name="objectValue">
 *       <object />
 *    </entry>
 * </object>
 * }
 * </pre>
 * 
 *
 * @author Torsten Krause (tk at markenwerk dot net)
 * @since 1.0.0
 */
public final class XmlDocumentJsonHandler extends IdleJsonHandler<Document> {

	private final Document document;

	private Node node;

	/**
	 * Creates a new {@link XmlDocumentJsonHandler} using a @link
	 * {@link DocumentBuilderFactory#newDocumentBuilder() default}
	 * {@link DocumentBuilder} created by a
	 * {@link DocumentBuilderFactory#newInstance() default}
	 * {@link DocumentBuilderFactory}.
	 */
	public XmlDocumentJsonHandler() {
		this(createDocumentBuilder());
	}

	private static DocumentBuilder createDocumentBuilder() {
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException("The default DocumentBuilder faild to create a XML Document", e);
		}
	}

	/**
	 * Create a new {@link XmlDocumentJsonHandler} using the given
	 * {@link DocumentBuilder} to create a new XML {@link Document}.
	 * 
	 * @param documentBuilder
	 *            The {@link DocumentBuilder} to be used.
	 * @throws IllegalArgumentException
	 *             If the given {@link DocumentBuilder} is {@literal null}.
	 */
	public XmlDocumentJsonHandler(DocumentBuilder documentBuilder) throws IllegalArgumentException {
		if (null == documentBuilder) {
			throw new IllegalArgumentException("documentBuilder is null");
		}
		document = documentBuilder.newDocument();
		node = document;
	}

	@Override
	public void onDocumentBegin() throws JsonHandlingException {
	}

	@Override
	public void onDocumentEnd() throws JsonHandlingException {
	}

	@Override
	public void onArrayBegin() throws JsonHandlingException {
		Element element = document.createElement("array");
		this.node.appendChild(element);
		this.node = element;
	}

	@Override
	public void onArrayEnd() throws JsonHandlingException {
		node = node.getParentNode();
		if (node.getNodeName().equals("entry")) {
			node = node.getParentNode();
		}
	}

	@Override
	public void onObjectBegin() throws JsonHandlingException {
		Element element = document.createElement("object");
		this.node.appendChild(element);
		this.node = element;
	}

	@Override
	public void onObjectEnd() throws JsonHandlingException {
		node = node.getParentNode();
		if (node.getNodeName().equals("entry")) {
			node = node.getParentNode();
		}
	}

	@Override
	public void onName(String name) throws InvalidJsonNameException, JsonHandlingException {
		checkName(name);
		Element element = document.createElement("entry");
		element.setAttribute("name", name);
		this.node.appendChild(element);
		this.node = element;
	}

	@Override
	public void onNext() throws JsonHandlingException {
	}

	@Override
	public void onNull() throws JsonHandlingException {
		Element element = document.createElement("null");
		appendChild(element);
	}

	@Override
	public void onBoolean(boolean value) throws JsonHandlingException {
		Element element = document.createElement("boolean");
		element.setAttribute("value", value ? "true" : "false");
		appendChild(element);
	}

	@Override
	public void onLong(long value) throws JsonHandlingException {
		Element element = document.createElement("number");
		element.setAttribute("value", Long.toString(value));
		appendChild(element);
	}

	@Override
	public void onDouble(double value) throws InvalidJsonValueException, JsonHandlingException {
		checkDouble(value);
		Element element = document.createElement("number");
		element.setAttribute("value", Double.toString(value));
		appendChild(element);
	}

	@Override
	public void onString(String value) throws InvalidJsonValueException, JsonHandlingException {
		checkString(value);
		Element element = document.createElement("string");
		element.setAttribute("value", value);
		appendChild(element);
	}

	private void appendChild(Element element) {
		node.appendChild(element);
		if (node.getNodeName().equals("entry")) {
			node = node.getParentNode();
		}
	}

	@Override
	public Document getResult() throws JsonHandlingException {
		return document;
	}

}
