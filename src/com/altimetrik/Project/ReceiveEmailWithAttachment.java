
package com.altimetrik.Project;

import java.io.File;

import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;


public class ReceiveEmailWithAttachment {
	public static File targetFile;
	public static String emailTo;
	public final static String userName = "jyots987@gmail.com";
	public final static String password = "Mummy@78";
	public final static String checkInvoice = "";
	private static Scanner sc;

	public static void receiveEmail(String pop3Host, String mailStoreType, String userName, String password) {

		Properties props = new Properties();
		props.put("mail.store.protocol", "pop3");
		props.put("mail.pop3.host", pop3Host);
		props.put("mail.pop3.port", "995");
		props.put("mail.pop3.starttls.enable", "true");

		Session session = Session.getInstance(props);

		try {

			Store store = session.getStore("pop3s");
			store.connect(pop3Host, userName, password);

			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);

			Message[] messages = emailFolder.getMessages();
			System.out.println("Total Message" + messages.length);

			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];
				Address[] toAddress = message.getRecipients(Message.RecipientType.TO);
				System.out.println("---------------------------------");
				System.out.println("Details of Email Message " + (i + 1) + " :");
				System.out.println("Subject: " + message.getSubject());
				// System.out.println("From: " + message.getFrom()[0]);
				emailTo = (message.getFrom()[0].toString());

				System.out.println(emailTo);
				// Iterate recipients
				System.out.println("To: ");
				for (int j = 0; j < toAddress.length; j++) {
					System.out.println(toAddress[j].toString());

				}

				Object content = message.getContent();
				if (content instanceof Multipart) {

					// Iterate multiparts
					Multipart multipart = (Multipart) message.getContent();
					for (int k = 0; k < multipart.getCount(); k++) {
						BodyPart bodyPart = multipart.getBodyPart(k);
						if (bodyPart.getDisposition() != null
								&& bodyPart.getDisposition().equalsIgnoreCase(Part.ATTACHMENT)) {
							System.out.println("file name " + bodyPart.getFileName());
							System.out.println("size " + bodyPart.getSize());
							System.out.println("content type " + bodyPart.getContentType());
							InputStream stream = (InputStream) bodyPart.getInputStream();
							targetFile = new File("D:\\pdf\\" + bodyPart.getFileName());

							java.nio.file.Files.copy(stream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

						}
					}
				}

				ReadingText.receiveMail(targetFile);

				emailFolder.close(false);

			}

		}

		catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		String pop3Host = "pop.gmail.com";
		String mailStoreType = "pop3";
		System.out.println("********************WELCOME TO ACCOUNT PAYABLE*********************");

		System.out.println("1.RECEIVE THE INVOICE\n");
		System.out.println("2.VIEW DETAILS OF VARIOUS INVOICE RECEIVED\n");
		System.out.println("3.VIEW DETAILS OF PARTICULAR INVOICE");
		System.out.println("4.Exit");
		sc = new Scanner(System.in);
		String invoceRequied = new String();
		int option;
		option = sc.nextInt();
		switch (option) {
		case 1:
			receiveEmail(pop3Host, mailStoreType, userName, password);
			break;
		case 2:
			ShowTable.showTable();
			break;
		case 3:
			System.out.println("Enter the Invoice to be checked");

			invoceRequied = sc.next();

			ShowTableInvoice.show_Table(invoceRequied);
			break;
		case 4:
			System.out.println("Have a nice day");
			break;

		}

	}
}