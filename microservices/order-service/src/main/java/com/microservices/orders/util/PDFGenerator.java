package com.microservices.orders.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.microservices.orders.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.stream.Stream;

public class PDFGenerator {
	
	private static Logger logger = LoggerFactory.getLogger(PDFGenerator.class);
	
	public static ByteArrayInputStream customerPDFReport(Order order) {
		Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String CheckIndate = simpleDateFormat.format(order.getDate());

        try {
        	
        	PdfWriter.getInstance(document, out);
            document.open();
        	
            // Add Text to PDF file ->
        	Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
        	Paragraph para = new Paragraph( "Order", font);
        	para.setAlignment(Element.ALIGN_CENTER);
        	document.add(para);
        	document.add(Chunk.NEWLINE);
        	document.addTitle("Order Booking details");
        	
        	PdfPTable table = new PdfPTable(6);
        	// Add PDF Table Header ->
            Stream.of("Order ID", "Products","Coupon ","Total Amount" +
                    "","Date","Payment Status")
	            .forEach(headerTitle -> {
		              PdfPCell header = new PdfPCell();
		              Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		              header.setBackgroundColor(BaseColor.LIGHT_GRAY);
		              header.setHorizontalAlignment(Element.ALIGN_CENTER);
		              header.setBorderWidth(2);
		              header.setPhrase(new Phrase(headerTitle, headFont));
		              table.addCell(header);
	            });
                PdfPCell NameCell = new PdfPCell(new Phrase(order.getOrderId()));
                NameCell.setPaddingLeft(4);
                NameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                NameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(NameCell);
                    StringBuilder sb = new StringBuilder();
                    order.getProductOrders().stream().forEach(productOrder -> sb.append(productOrder.getProduct()+" "+productOrder.getQuantity()+ "\n") );
                PdfPCell roomNoCell = new PdfPCell(new Phrase(String.valueOf(sb)));
                roomNoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                roomNoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                roomNoCell.setPaddingRight(4);
                table.addCell(roomNoCell);
                PdfPCell checkInCell = new PdfPCell(new Phrase(order.getCouponId()));
                checkInCell.setPaddingLeft(4);
                checkInCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                checkInCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(checkInCell);

                PdfPCell checkOutCell = new PdfPCell(new Phrase(String.valueOf(order.getAmount())));
                checkOutCell.setPaddingLeft(4);
                checkOutCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                checkOutCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(checkOutCell);

                PdfPCell amountCell = new PdfPCell(new Phrase(String.valueOf(order.getDate())));
                amountCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                amountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                amountCell.setPaddingRight(4);
                table.addCell(amountCell);
                PdfPCell payStatusCell = new PdfPCell(new Phrase(String.valueOf("PAID")));
                payStatusCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                payStatusCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                payStatusCell.setPaddingRight(4);
                table.addCell(payStatusCell);
            document.add(table);
            
            document.close();
        }catch(DocumentException e) {
        	logger.error(e.toString());
        }
        
		return new ByteArrayInputStream(out.toByteArray());
	}
}