package proyecto.umg.utils;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;





public class Correo{
	
	
	
	private static final String CORREO_REMITENTE = "Notificaciones ChequeControl <ronaldorantes19@gmail.com>";
	
	private static final String SMTP_SERVER_TELEFONICA = "mail.telefonica.com";
	private static final String SMTP_SERVER_GMAIL = "smtp.gmail.com";
	
	protected static final String MENSAJE_CUERPO = "Estimado Cliente, Se adjunta el reporte de saldos solicitado desde el Centro de Atención Virtual.";
	protected static final String MENSAJE_CUERPO_SIN_DATA = "Buen Día, El reporte solicitado desde el Centro de Atención Virtual no contiene datos.";
	
	public static void sendExcelToMail(List<String> to, String subject, String body, String fileAttachment,String name) throws MessagingException{
		System.out.println("Inicia envio de Correo - Para:" + to + ", Asunto:" + subject + ", NombreArchivo: " + name);
		//sendMailTelefonica(to, subject, body, fileAttachment, name);
     	
     	//Este se debe utilizar unicamente para pruebas
     	//sendMailGmail(to, subject, body, fileAttachment, name);
		send(to,subject, body);
     	
     	if(name !=null){
     		File file = new File(name);
     		file.delete();
     	}
     	
     	System.out.println("Culmina envio de Correo - Para:" + to + ", Asunto:" + subject + ", NombreArchivo: " + name);
	}
	
	private static void sendMailTelefonica(String to, String subject, String body, String fileAttachment,String name) throws MessagingException{
	    Properties props = System.getProperties();
	    props.put("mail.smtp.host", SMTP_SERVER_TELEFONICA);
	    
	    Session session = Session.getInstance(props, null);
	    
	    MimeBodyPart messageBodyPart = new MimeBodyPart();
	    messageBodyPart.setText(body);
	    Multipart multipart = new MimeMultipart();
	    multipart.addBodyPart(messageBodyPart);
	    
	    if(fileAttachment != null && !fileAttachment.equals("")){
	        messageBodyPart = new MimeBodyPart();
	        javax.activation.DataSource source = new FileDataSource(fileAttachment);
	        messageBodyPart.setDataHandler(new DataHandler(source));
	        messageBodyPart.setFileName(name);
	        multipart.addBodyPart(messageBodyPart);
	    }
	    
	    MimeMessage message = new MimeMessage(session);
	    message.setFrom(new InternetAddress(CORREO_REMITENTE));
	    message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
	    message.setSubject(subject);
	    message.setContent(multipart);
	    
	    Transport.send(message);
	}
	
	
	private static void sendMailGmail(List<String>to, String subject, String body, String fileAttachment,String name) throws MessagingException{
	    Properties props = System.getProperties();
	    
	    props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", SMTP_SERVER_GMAIL);
		props.put("mail.smtp.port", "587");
		final String username = "ronaldorantes19@gmail.com";
		final String password = "dlanoR2013";
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
	    
	    
	   
	    
	    MimeBodyPart messageBodyPart = new MimeBodyPart();
	    messageBodyPart.setContent(body, "text/html");
	    
	    
	    Multipart multipart = new MimeMultipart();
	    multipart.addBodyPart(messageBodyPart);
	    
	    
	    
	    if(fileAttachment != null && !fileAttachment.equals("")){
	        messageBodyPart = new MimeBodyPart();
	        javax.activation.DataSource source = new FileDataSource(fileAttachment);
	        messageBodyPart.setDataHandler(new DataHandler(source));
	        messageBodyPart.setFileName(name);
	        multipart.addBodyPart(messageBodyPart);
	    }
	    
	    MimeMessage message = new MimeMessage(session);
	    message.setFrom(new InternetAddress(CORREO_REMITENTE));
	    for (String s: to){
	    	message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(s));
	    }
	    
	    message.setSubject(subject);
	    
	    message.setContent(multipart);
		Transport.send(message);
	}
	
	
	public  static void send(List<String> to, String subject, String body) throws MessagingException{
        System.out.println("Sending mail...");
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", SMTP_SERVER_GMAIL);
		props.put("mail.smtp.port", "587");
		final String username = "ronaldorantes19@gmail.com";
		final String password = "dlanoR2013";
		Session mailSession = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });

//		 MimeBodyPart messageBodyPart = new MimeBodyPart();
//		    messageBodyPart.setContent(body, "text/html");
//		    
//		    
//		    Multipart multipart = new MimeMultipart();
//		    multipart.addBodyPart(messageBodyPart);
//		    
//		    if(fileAttachment != null && !fileAttachment.equals("")){
//		        messageBodyPart = new MimeBodyPart();
//		        javax.activation.DataSource source = new FileDataSource(fileAttachment);
//		        messageBodyPart.setDataHandler(new DataHandler(source));
//		        messageBodyPart.setFileName(name);
//		        multipart.addBodyPart(messageBodyPart);
//		    }
//		    
//		  
//		    
//		    if(fileAttachment != null && !fileAttachment.equals("")){
//		        messageBodyPart = new MimeBodyPart();
//		        javax.activation.DataSource source = new FileDataSource(fileAttachment);
//		        messageBodyPart.setDataHandler(new DataHandler(source));
//		        messageBodyPart.setFileName(name);
//		        multipart.addBodyPart(messageBodyPart);
//		    }
//		    
//		    MimeMessage message = new MimeMessage(session);
//		    message.setFrom(new InternetAddress(CORREO_REMITENTE));
//		    for (String s: to){
//		    	message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(s));
//		    }
//		    
//		    message.setSubject(subject);
//		    
//		    message.setContent(multipart);
//			Transport.send(message);
//        mailSession.setDebug(true);
        
		MimeMessage message = new MimeMessage(mailSession);
        message.setSubject(subject);
        message.setFrom(new InternetAddress(CORREO_REMITENTE));
        for (String s: to){
        	message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(s));
        }
        

        //
        // This HTML mail have to 2 part, the BODY and the embedded image
        //
        MimeMultipart multipart = new MimeMultipart("related");

        // first part  (the html)
        BodyPart messageBodyPart = new MimeBodyPart();
        String htmlText = "<img src=\"cid:image\"><H1>Notificaciones ChequeControl</H1>";
        messageBodyPart.setContent(htmlText+"<br>"+body, "text/html");

        // add it
        multipart.addBodyPart(messageBodyPart);
        
        // second part (the image)
        messageBodyPart = new MimeBodyPart();
        DataSource fds = new FileDataSource
          ("/home/Downloads/logoumg.png");
        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-ID","<image>");

        // add it
        multipart.addBodyPart(messageBodyPart);

        // put everything together
        message.setContent(multipart);

       
        
   
	    
	    
	    Transport.send(message);

      
        }

	


}
