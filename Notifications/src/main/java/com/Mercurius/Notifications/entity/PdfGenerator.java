package com.Mercurius.Notifications.entity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfGenerator {

	public PdfGenerator() {
		// TODO Auto-generated constructor stub
	}


	public byte[] generatePdf(Order order) throws Exception,IOException {
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			Document document = new Document();

			PdfWriter.getInstance(document, byteArrayOutputStream);

			document.open();

			Paragraph title = new Paragraph("Order Invoice");
			title.setAlignment(Element.ALIGN_CENTER);

			document.add(title);

			document.add(new Paragraph("Order ID: " + order.getOrderId()));
			document.add(new Paragraph("Account ID: " + order.getAccountId()));
			document.add(new Paragraph("Order Date: " + order.getOrderDate().toString()));

			PdfPTable table = new PdfPTable(6);
			table.setWidthPercentage(100);
			table.setSpacingBefore(10f);
			table.setSpacingAfter(10f);

			table.addCell("Item ID");
			table.addCell("Product ID");
			table.addCell("Quantity");
			table.addCell("Payment Type");
			table.addCell("Status");
			table.addCell("Price");

			List<OrderItem> orderItems = order.getorderItems();
			for (OrderItem item : orderItems) {
				table.addCell(item.getOrderItemId());
				table.addCell(item.getProductId());
				table.addCell(String.valueOf(item.getQuantity()));
				table.addCell(String.valueOf(item.getPaymentType()));
				table.addCell(String.valueOf(item.getStatus()));
				table.addCell(String.valueOf(item.getPriceComponents().getTotalPrice()));
			}

			document.add(table);

			document.close();

			return byteArrayOutputStream.toByteArray();
		} catch (Exception e) {
			System.err.println("Exception Occured"+e.getMessage());;
			/* throw new Exception(e.getMessage()); */
		  return null;
		}
		
	}
}
