package com.redvinca.assignment.ecom_backend.serviceimpl;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class PageBorder extends PdfPageEventHelper {
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte canvas = writer.getDirectContent();
        Rectangle rect = document.getPageSize();
        rect.setBorder(Rectangle.BOX); // Border type
        rect.setBorderWidth(2); // Border width
        rect.setBorderColor(BaseColor.BLACK); // Border color
        rect.setUseVariableBorders(true);
        rect.setBottom(30);
        rect.setTop(rect.getTop() - 30);
        rect.setLeft(30);
        rect.setRight(rect.getRight() - 30);
        canvas.rectangle(rect);
    }
}
