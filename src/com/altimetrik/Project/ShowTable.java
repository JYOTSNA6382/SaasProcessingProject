//package pdf;
package com.altimetrik.Project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ShowTable {

	public static void showTable() {
		Connection con = null;
		Statement stm1 = null;

		try {
			con = connection.createConnection();
			stm1 = con.prepareStatement("select * from invoice_data ");
			ResultSet rs = stm1.executeQuery("SELECT * from invoice_data");

			while (rs.next()) {

				System.out.println(
						"INVOICE NUMBER        DATE           CUSTOMER PO          ADRESS                                   TOTAL AMOUNT       FLAG  ");
				System.out.println(rs.getString(1) + "\t" + rs.getString(2) + " \t" + rs.getString(3) + "\t"
						+ rs.getString(4) + "\t" + rs.getString(5) + rs.getString(6));
				System.out.println(
						"***********************************************************************************************************************************");
			}

			con.close();

		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}
}
