import microsoft.exchange.webservices.data.autodiscover.IAutodiscoverRedirectionUrl;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.WebProxy;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.ItemView;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Created by mertyaman on 16/06/16 for MyPlayground.
 */
public class EwsAPIOperations {

    private final String propertyFileName = System.getProperty("user.dir")+"/src/main/resources/passwords.properties";
    PropertiesConfiguration config = new PropertiesConfiguration(propertyFileName);


    String mailAddress=config.getString("mailAddress");
    String mailPassword=config.getString("mailPassword");
    String mailTo=config.getString("mailTo");
    String proxyHost=config.getString("proxyHost");
    String proxyPort = config.getString("proxyPort");

    public EwsAPIOperations() throws ConfigurationException {
    }



    public static class RedirectionUrlCallback implements IAutodiscoverRedirectionUrl {
        public boolean autodiscoverRedirectionUrlValidationCallback(
                String redirectionUrl) {
            return redirectionUrl.toLowerCase().startsWith("https://");
        }
    }

    public ExchangeService getConnection(){

        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
        ExchangeCredentials credentials = new WebCredentials(mailAddress, mailPassword);

        // set up the proxy if necessary
        WebProxy proxy = new WebProxy(proxyHost, Integer.parseInt(proxyPort));
        service.setWebProxy(proxy);


        service.setCredentials(credentials);

        try {
            service.autodiscoverUrl(mailAddress ,new RedirectionUrlCallback());
            System.out.println("Exchange server'a baglanildi.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return service;
    }

    public void sendMessage(){
        EmailMessage msg= null;
        try {
            msg = new EmailMessage(getConnection());
            System.out.println("Connection basarili.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            msg.setSubject("Microsoft Exchange Web Services JAVA API Test");
            msg.setBody(MessageBody.getMessageBodyFromText("Sent using the EWS Java API."));
            msg.getToRecipients().add(mailTo);
            msg.getAttachments().addFileAttachment("MailAttachment.txt");
            msg.send();
            System.out.println("Mail basarili bir sekilde gonderildi.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void listFirstTenItems() {
        ItemView view = new ItemView(10);
        Folder folder = null;
        FindItemsResults<Item> findResults = null;
        ExchangeService service=getConnection();
        try {
            folder = new Folder(service);
            findResults = service.findItems(WellKnownFolderName.Inbox, view);
            //IMPORTANT: load messages' properties before
            service.loadPropertiesForItems(findResults, PropertySet.FirstClassProperties);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        for (Item item : findResults.getItems()) {
            // Do something with the item as shown
            try {
                System.out.println("id==========" + item.getId());
                System.out.println("sub==========" + item.getSubject());
                System.out.println("body==========" +item.getBody());

            }
            catch (ServiceLocalException e) {
                System.out.println(e.getMessage());
            }

        }
    }
}
