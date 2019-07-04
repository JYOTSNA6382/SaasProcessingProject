package com.altimetrik.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowTableInvoice {
	static Connection con = null;

	public static PreparedStatement stm1 = null;
	public static PreparedStatement stm2 = null;

	public static void show_Table(String invodceDetails) {
		try {

			con = connection.createConnection();

			stm1 = con.prepareStatement("SELECT * FROM INVOICE_DATA WHERE INVOICENO=?");
			stm1.setString(1, invodceDetails);
			ResultSet rs = stm1.executeQuery();

			while (rs.next()) {
				if (rs.getString(6).equals("False")) {
					stm2 = con.prepareStatement("update invoice_data set flag=? where InvoiceNO=?");
					stm2.setString(1, "True");
					stm2.setString(2, invodceDetails);
					stm2.executeUpdate();
					System.out.println("Invoice number is approved");
				} else {
					System.out.println("The invoice number is already aprroved");
					System.out.println(
							"INVOICE NUMBER        DATE           CUSTOMER PO          ADRESS                                   TOTAL AMOUNT       FLAG  ");
					System.out.println(rs.getString(1) + "\t" + rs.getString(2) + " \t" + rs.getString(3) + "\t"
							+ rs.getString(4) + "\t" + rs.getString(5) + rs.getString(6));
					System.out.println(
							"***********************************************************************************************************************************");

				}

			}
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
