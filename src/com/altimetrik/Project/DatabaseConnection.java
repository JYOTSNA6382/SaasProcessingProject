//package pdf;
package com.altimetrik.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {
	static Connection con = null;
	static PreparedStatement stm1 = null;

	public static void dataBaseConnection(String invoiceNo, String invoiceDate, String customerPO, String address,
			String amount) {

		try {

			con = connection.createConnection();

			stm1 = con.prepareStatement("select * from invoice_data where invoiceno= ?");
			stm1.setString(1, invoiceNo);
			ResultSet resultSet = stm1.executeQuery();

			if (resultSet.next()) {
				System.out.println("The invoice number is duplicated");
				System.exit(0);
			} else {
				PreparedStatement pre = con.prepareStatement("insert  into invoice_data values(?,?,?,?,?,?)");
				pre.setString(1, invoiceNo);
				pre.setString(2, invoiceDate);
				pre.setString(3, customerPO);
				pre.setString(4, address);
				pre.setString(5, amount);
				pre.setString(6, "False");
				int i = pre.executeUpdate();
				System.out.println("invoice inserted" + i);
				ShowTableInvoice.show_Table(invoiceNo);
				Acknowledgement.send(ReceiveEmailWithAttachment.userName, ReceiveEmailWithAttachment.password,
						ReceiveEmailWithAttachment.emailTo);
				pre.close();

			}

			resultSet.close();
			stm1.close();

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
