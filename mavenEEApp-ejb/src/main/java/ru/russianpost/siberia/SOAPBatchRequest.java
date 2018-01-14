/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia;

import java.util.List;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import ru.russianpost.siberia.dataaccess.Ticket;

/*
* @author Andrey.Isakov
 */
public class SOAPBatchRequest {

    public String Login;
    public String Password;

    public SOAPBatchRequest(String Login, String Password) {
        this.Login = Login;
        this.Password = Password;
    }

    /*
  Данный код создает запросы getTicket.Метод getTicket используется для
  получения билета на подготовку информации по списку идентификаторов
  отправлений. В запросе передается список идентификаторов отправлений. При
  успешном вызове метод возвращает идентификатор билета. Идентификатор билета
  нужен для метода getResponseByTicket. В запросе метода указываются до 3000
  идентификаторов отправлений и параметры доступа к API Сервиса отслеживания
  (логин и пароль).
     */
    public SOAPMessage GetTicket(List<Ticket> tickets) throws SOAPException, TransformerConfigurationException, TransformerException {
        /*
        Пример запроса
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                            xmlns:pos="http://fclient.russianpost.org/postserver"
                            xmlns:fcl="http://fclient.russianpost.org">
        <soapenv:Header/>
        <soapenv:Body>
            <pos:ticketRequest>
                <request>
                    <fcl:Item Barcode="RA123456788RU"/>
                    <fcl:Item Barcode="RA123456789RU"/>
                    <fcl:Item Barcode="RA123456780RU"/>
                </request>
                <login>my_login</login>
                <password>my_password</password>
                <language>RUS</language>
            </pos:ticketRequest>
        </soapenv:Body>
        </soapenv:Envelope>

        Пример ответа
        <S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
        <S:Body>
            <ns2:ticketResponse xmlns:ns2="http://fclient.russianpost.org/postserver" xmlns:ns3="http://fclient.russianpost.org">
                <value>20150917162048476CLIENTID</value>
            </ns2:ticketResponse>
        </S:Body>
        </S:Envelope>
         */

        //Создаем соединение
        SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection connection = soapConnFactory.createConnection();
        String url = "https://tracking.russianpost.ru/fc";

        //Создаем сообщение
        MessageFactory messageFactory = MessageFactory.newInstance("SOAP 1.1 Protocol");
        SOAPMessage message = messageFactory.createMessage();

        //Создаем объекты, представляющие различные компоненты сообщения
        SOAPPart soapPart = message.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        SOAPBody body = envelope.getBody();

        envelope.addNamespaceDeclaration(
                "soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
        envelope.addNamespaceDeclaration(
                "pos", "http://fclient.russianpost.org/postserver");
        envelope.addNamespaceDeclaration(
                "fcl", "http://fclient.russianpost.org");
        SOAPElement ticketRequest = body.addChildElement("ticketRequest", "pos");
        SOAPElement request = ticketRequest.addChildElement("request");
        for (Ticket ticket : tickets) {
            ticket.item = request.addChildElement("Item", "fcl");
        }
        SOAPElement login = ticketRequest.addChildElement("login");
        SOAPElement password = ticketRequest.addChildElement("password");
        SOAPElement language = ticketRequest.addChildElement("language");

        // Заполняем значения
        SOAPFactory sf = SOAPFactory.newInstance();
        Name barcode = sf.createName("Barcode");
        for (Ticket ticket : tickets) {
            ticket.item.addAttribute(barcode, ticket.getBarcode());
        }
        login.addTextNode(Login);
        password.addTextNode(Password);
        language.addTextNode("RUS");

        //Сохранение сообщения
        message.saveChanges();

        //Отправляем запрос и выводим ответ на экран
        SOAPMessage soapResponse = connection.call(message, url);
        //Закрываем соединение

        connection.close();
        return soapResponse;
    }

    /*
    Данный код создает запрос getResponseByTicket.
    Запрос используется для получения информации об отправлениях по ранее
    полученному билету. Ответ выводится на экран в формате xml.
    Рекомендуется выполнять первое обращение за ответом по билету не ранее,
    чем через 15 минут от момента выдачи билета.
     */
    public SOAPMessage GetResponseByTicket(String TicketReq) throws SOAPException {
        /*
        Пример запроса:
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                            xmlns:pos="http://fclient.russianpost.org/postserver">
        <soapenv:Header/>
        <soapenv:Body>
            <pos:answerByTicketRequest>
                <ticket>20150917162048476CLIENTID</ticket>
                <login>my_login</login>
                <password>my_password</password>
            </pos:answerByTicketRequest>
        </soapenv:Body>
        </soapenv:Envelope>
         */

        //Сначала создаем соединение
        SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection connection = soapConnFactory.createConnection();
        String url = "https://tracking.russianpost.ru/fc";

        //Затем создаем сообщение
        MessageFactory messageFactory = MessageFactory.newInstance("SOAP 1.1 Protocol");
        SOAPMessage message = messageFactory.createMessage();

        //Создаем объекты, представляющие различные компоненты сообщения
        SOAPPart soapPart = message.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        SOAPBody body = envelope.getBody();

        envelope.addNamespaceDeclaration("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
        envelope.addNamespaceDeclaration("pos", "http://fclient.russianpost.org/postserver");
        SOAPElement answerByTicketRequest = body.addChildElement("answerByTicketRequest", "pos");
        SOAPElement ticket = answerByTicketRequest.addChildElement("ticket");
        SOAPElement login = answerByTicketRequest.addChildElement("login");
        SOAPElement password = answerByTicketRequest.addChildElement("password");

        // Заполняем значения
        ticket.addTextNode(TicketReq);
        login.addTextNode(Login);
        password.addTextNode(Password);

        //Сохранение сообщения
        message.saveChanges();

        //Отправляем запрос и выводим ответ на экран
        SOAPMessage soapResponse = connection.call(message, url);

        //Закрываем соединение
        connection.close();
        return soapResponse;
    }
}
