package net.markenwerk.utils.json.common.handler.xml;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

import net.markenwerk.utils.json.common.JsonValueException;
import net.markenwerk.utils.json.handler.JsonHandler;

@SuppressWarnings("javadoc")
public class XmlDocumentJsonHandlerTests {

	@Test(expected = IllegalArgumentException.class)
	public void create_nullDocumentBuilder() {

		new XmlDocumentJsonHandler(null);

	}

	@Test
	public void onNull() {

		JsonHandler<Document> handler = new XmlDocumentJsonHandler();

		handler.onDocumentBegin();
		handler.onNull();
		handler.onDocumentEnd();

		Document document = handler.getResult();

		assertEquals("<null />", document);

	}

	@Test
	public void onBoolean_true() {

		JsonHandler<Document> handler = new XmlDocumentJsonHandler();

		handler.onDocumentBegin();
		handler.onBoolean(true);
		handler.onDocumentEnd();

		Document document = handler.getResult();

		assertEquals("<boolean value=\"true\" />", document);

	}

	@Test
	public void onBoolean_false() {

		JsonHandler<Document> handler = new XmlDocumentJsonHandler();

		handler.onDocumentBegin();
		handler.onBoolean(false);
		handler.onDocumentEnd();

		Document document = handler.getResult();

		assertEquals("<boolean value=\"false\" />", document);

	}

	@Test
	public void onLong_zero() {

		JsonHandler<Document> handler = new XmlDocumentJsonHandler();

		handler.onDocumentBegin();
		handler.onLong(0);
		handler.onDocumentEnd();

		Document document = handler.getResult();

		assertEquals("<number value=\"" + Long.toString(0) + "\" />", document);

	}

	@Test
	public void onLong_positive() {

		JsonHandler<Document> handler = new XmlDocumentJsonHandler();

		handler.onDocumentBegin();
		handler.onLong(Long.MAX_VALUE);
		handler.onDocumentEnd();

		Document document = handler.getResult();

		assertEquals("<number value=\"" + Long.toString(Long.MAX_VALUE) + "\" />", document);

	}

	@Test
	public void onLong_negative() {

		JsonHandler<Document> handler = new XmlDocumentJsonHandler();

		handler.onDocumentBegin();
		handler.onLong(Long.MIN_VALUE);
		handler.onDocumentEnd();

		Document document = handler.getResult();

		assertEquals("<number value=\"" + Long.toString(Long.MIN_VALUE) + "\" />", document);

	}

	@Test(expected = JsonValueException.class)
	public void onDouble_notANumber() {

		JsonHandler<Document> handler = new XmlDocumentJsonHandler();

		handler.onDocumentBegin();
		handler.onDouble(Double.NaN);
		handler.onDocumentEnd();

	}

	@Test(expected = JsonValueException.class)
	public void onDouble_infinite() {

		JsonHandler<Document> handler = new XmlDocumentJsonHandler();

		handler.onDocumentBegin();
		handler.onDouble(Double.POSITIVE_INFINITY);
		handler.onDocumentEnd();

	}

	@Test
	public void onDouble_zero() {

		JsonHandler<Document> handler = new XmlDocumentJsonHandler();

		handler.onDocumentBegin();
		handler.onDouble(0);
		handler.onDocumentEnd();

		Document document = handler.getResult();

		assertEquals("<number value=\"" + Double.toString(0) + "\" />", document);

	}

	@Test
	public void onDouble_positive() {

		JsonHandler<Document> handler = new XmlDocumentJsonHandler();

		handler.onDocumentBegin();
		handler.onDouble(Double.MAX_VALUE);
		handler.onDocumentEnd();

		Document document = handler.getResult();

		assertEquals("<number value=\"" + Double.toString(Double.MAX_VALUE) + "\" />", document);

	}

	@Test
	public void onDouble_negative() {

		JsonHandler<Document> handler = new XmlDocumentJsonHandler();

		handler.onDocumentBegin();
		handler.onDouble(Double.MIN_VALUE);
		handler.onDocumentEnd();

		Document document = handler.getResult();

		assertEquals("<number value=\"" + Double.toString(Double.MIN_VALUE) + "\" />", document);

	}

	@Test(expected = JsonValueException.class)
	public void onString_null() {

		JsonHandler<Document> handler = new XmlDocumentJsonHandler();

		handler.onDocumentBegin();
		handler.onString(null);
		handler.onDocumentEnd();

	}

	@Test
	public void onString_empty() {

		JsonHandler<Document> handler = new XmlDocumentJsonHandler();

		handler.onDocumentBegin();
		handler.onString("");
		handler.onDocumentEnd();

		Document document = handler.getResult();

		assertEquals("<string value=\"\"/>", document);

	}

	@Test
	public void onString_nonEmpty() {

		JsonHandler<Document> handler = new XmlDocumentJsonHandler();

		handler.onDocumentBegin();
		handler.onString("foobar");
		handler.onDocumentEnd();

		Document document = handler.getResult();

		assertEquals("<string value=\"foobar\"/>", document);

	}

	@Test
	public void onArray_empty() {

		JsonHandler<Document> handler = new XmlDocumentJsonHandler();

		handler.onDocumentBegin();
		handler.onArrayBegin();
		handler.onArrayEnd();
		handler.onDocumentEnd();

		Document document = handler.getResult();

		assertEquals("<array />", document);

	}

	@Test
	public void onArray_nonEmpty() {

		JsonHandler<Document> handler = new XmlDocumentJsonHandler();

		handler.onDocumentBegin();
		handler.onArrayBegin();
		handler.onNull();
		handler.onArrayEnd();
		handler.onDocumentEnd();

		Document document = handler.getResult();

		assertEquals("<array><null /></array>", document);

	}

	@Test
	public void onObject_empty() {

		JsonHandler<Document> handler = new XmlDocumentJsonHandler();

		handler.onDocumentBegin();
		handler.onObjectBegin();
		handler.onObjectEnd();
		handler.onDocumentEnd();

		Document document = handler.getResult();

		assertEquals("<object />", document);

	}

	@Test
	public void onObject_nonEmpty() {

		JsonHandler<Document> handler = new XmlDocumentJsonHandler();

		handler.onDocumentBegin();
		handler.onObjectBegin();
		handler.onName("n");
		handler.onNull();
		handler.onObjectEnd();
		handler.onDocumentEnd();

		Document document = handler.getResult();

		assertEquals("<object><entry name=\"n\"><null /></entry></object>", document);

	}

	@Test
	public void onDocument_complex() {

		JsonHandler<Document> handler = new XmlDocumentJsonHandler();

		handler.onDocumentBegin();
		handler.onObjectBegin();
		handler.onName("n");
		handler.onNull();
		handler.onNext();
		handler.onName("b");
		handler.onBoolean(true);
		handler.onNext();
		handler.onName("l");
		handler.onLong(-42);
		handler.onNext();
		handler.onName("d");
		handler.onDouble(-23.42);
		handler.onNext();
		handler.onName("a");
		handler.onArrayBegin();
		handler.onString("foo");
		handler.onNext();
		handler.onString("bar");
		handler.onArrayEnd();
		handler.onObjectEnd();
		handler.onDocumentEnd();

		Document document = handler.getResult();

		assertEquals(
				"<object><entry name=\"n\"><null /></entry><entry name=\"b\"><boolean value=\"true\" /></entry><entry name=\"l\"><number value=\"-42\" /></entry><entry name=\"d\"><number value=\"-23.42\" /></entry><entry name=\"a\"><array><string value=\"foo\" /><string value=\"bar\" /></array></entry></object>",
				document);

	}

	private void assertEquals(String expected, Document actual) {
		Diff diff = DiffBuilder.compare(Input.fromString(expected)).withTest(Input.fromDocument(actual)).build();
		Assert.assertFalse(diff.toString(), diff.hasDifferences());
	}

}
