package com.altimetrik.Project;

import java.io.File;
import java.io.FileWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.altimetrik.Project.ReceiveEmailWithAttachment;

public class ReadingText {
	public static void receiveMail(File targetFile) {
		try {

			PDDocument document = PDDocument.load(ReceiveEmailWithAttachment.targetFile);
			PDFTextStripper pdfStripper = new PDFTextStripper();
			String text = pdfStripper.getText(document);
			FileWriter fw = new FileWriter("D:\\testout.txt");
			fw.write(text);
			fw.close();
			String lines[] = text.split("\n");
			String invoiceNo = "", invoiceDate = "", customerPO = "", address = "", amount = "";
			for (int i = 0; i < lines.length; i++) {
				if (lines[i].trim().equals("Invoice No")) {
					invoiceNo = lines[i + 1].trim();
				}
				if (lines[i].trim().equals("Invoice Date"))
					invoiceDate = lines[i + 1].trim();
				if (lines[i].trim().equals("Customer P.O."))
					customerPO = lines[i + 1].trim();
				if (lines[i].trim().equals("Sold To")) {
					while (!lines[++i].trim().startsWith("Ship To"))
						address += lines[i] + " ";

				}

			}

		
			document.close();

			outer: for (int i = 0; i < lines.length; i++) {
				if (lines[i].trim().equals("Total Invoice")) {
					for (int j = i + 1; j < lines.length; j++) {
						if (!lines[j + 1].trim().startsWith("$")) {
							amount = lines[j].trim();
							amount = amount.replace(",", "");
							amount = amount.replace("$", "");
							break outer;
						}
					}
				}

			}

			System.out.println("Invoice No :" + invoiceNo);
			System.out.println("Invoice Date :" + invoiceDate);
			String OnlyNumber = customerPO.replaceAll("[^0-9]", "");
			System.out.println("Cust PO :" + OnlyNumber);
			System.out.println("Address :" + address);
			System.out.println("Total Amount " + amount);

			DatabaseConnection.dataBaseConnection(invoiceNo, invoiceDate, OnlyNumber, address, amount);

		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
