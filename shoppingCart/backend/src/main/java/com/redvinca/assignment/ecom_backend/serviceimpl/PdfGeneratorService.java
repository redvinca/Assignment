package com.redvinca.assignment.ecom_backend.serviceimpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.redvinca.assignment.ecom_backend.model.Cart;


@Service
public class PdfGeneratorService {

	
    @Autowired
    private CartServiceImpl cartService; // Assuming this is your CartService implementation -------------------------------------

    
    public byte[] generatePdf(List<Cart> selectedItems) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        try {          
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            writer.setPageEvent(new PageBorder());
            document.setMargins(70, 70, 70, 70);
            document.open();

            
            // Add header ---------------------------------------------------------------------------------------------------------
            Paragraph header = new Paragraph("Sold By: MyShopping\nJohn Doe, 123 Main St, Apt 4B, 627001");
            header.setSpacingAfter(20f); // Adjust spacing here
            document.add(header);
            Paragraph footerSpacing = new Paragraph("");
            footerSpacing.setSpacingBefore(70f); // Adjust this value to add space before footer
            document.add(footerSpacing);

            
            // Add line separator -------------------------------------------------------------------------------------------------
            PdfPTable lineTable = new PdfPTable(1);
            lineTable.setWidthPercentage(100);
            lineTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            lineTable.addCell("");
            document.add(lineTable);

            
            // Add table for product details --------------------------------------------------------------------------------------
            PdfPTable table = new PdfPTable(3); // 3 columns: Item_Name, Quantity, Amount
            table.setWidthPercentage(100);
            table.setSpacingBefore(20f);
            
            
         // Set default cell properties -------------------------------------------------------------------------------------------
            PdfPCell cell = new PdfPCell();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBorder(PdfPCell.BOX);
            

            // Add table headers --------------------------------------------------------------------------------------------------
            cell.setPhrase(new Paragraph("Item Name"));
            table.addCell(cell);
            cell.setPhrase(new Paragraph("Quantity"));
            table.addCell(cell);
            cell.setPhrase(new Paragraph("Amount"));
            table.addCell(cell);

          
            long totalQuantity = 0;
            long totalprice = 0;
            // Fetch cart details and populate table rows -------------------------------------------------------------------------
            for (Cart item : selectedItems) {
            	cell.setPhrase(new Paragraph(item.getProduct().getName()));
                table.addCell(cell);
                cell.setPhrase(new Paragraph(String.valueOf(item.getQuantity())));
                table.addCell(cell);
                cell.setPhrase(new Paragraph("₹" + item.getProduct().getPrice()));
                table.addCell(cell);
                totalQuantity += item.getQuantity();
                totalprice += item.getProduct().getPrice();
 
            }
            cell.setPhrase(new Paragraph("total"));
            table.addCell(cell);
            cell.setPhrase(new Paragraph(String.valueOf(totalQuantity)));
            table.addCell(cell);
            cell.setPhrase(new Paragraph(String.valueOf(totalprice)));
            table.addCell(cell);
            

            // Add table to document ----------------------------------------------------------------------------------------------
            document.add(table);
            
           
            //add footer ----------------------------------------------------------------------------------------------------------
//            Paragraph footer1 = new Paragraph("Footer");
//            footer1.setSpacingBefore(100f);
//            document.add(footer1);

            
            // Close document -----------------------------------------------------------------------------------------------------
            document.close();
        } catch (DocumentException e) {
            throw new IOException(e.getMessage());
        }
        return baos.toByteArray();
    }
}



