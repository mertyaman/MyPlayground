import org.apache.commons.configuration.ConfigurationException;

/**
 * Created by mertyaman on 13/06/16 for MyPlayground.
 */
public class MainClass {


    public static void main(String[] args){

        dbQuery();

        ExchangeApiMail();

    }
    public static void ExchangeApiMail(){
        decrypt();

        ////Microsoft Exchange Server API
        EwsAPIOperations ewsAPIOperations = null;
        try {
            ewsAPIOperations = new EwsAPIOperations();
            ewsAPIOperations.sendMessage();
            ewsAPIOperations.listFirstTenItems();
        }
        catch (ConfigurationException e) {
            System.out.println(e.getMessage());
        }
        finally {
            encrypt();
        }
    }

    public static void dbQuery(){
        decrypt();

        ////DBQueries
        DBQueries dbQueries = null;
        try {
            dbQueries = new DBQueries();
            String cityName=dbQueries.getCityName("35");
            System.out.println("DB'den gelen sorgu cevabi: "+cityName);
        }
        catch (ConfigurationException e) {
            System.out.println(e.getMessage());
        }
        finally {
            encrypt();
        }
    }

    public static void encrypt(){
        ////Read values from file and encrypt
        EncryptDecrypt encryptDecrypt = new EncryptDecrypt();

        ////DB part
        encryptDecrypt.getEncryptedValue("DBhost","isDBhostEncrypted");
        encryptDecrypt.getEncryptedValue("DBusername","isDBusernameEncrypted");
        encryptDecrypt.getEncryptedValue("DBpassword","isDBpasswordEncrypted");
        ////Mail part
        encryptDecrypt.getEncryptedValue("mailPassword","isMailPassEncrypted");
    }

    public static void decrypt(){
        ////Read values from file and decrypt
        EncryptDecrypt encryptDecrypt = new EncryptDecrypt();

        ////DB part
        encryptDecrypt.getDecryptedValue("DBhost","isDBhostEncrypted");
        encryptDecrypt.getDecryptedValue("DBusername","isDBusernameEncrypted");
        encryptDecrypt.getDecryptedValue("DBpassword","isDBpasswordEncrypted");
        ////Mail part
        encryptDecrypt.getDecryptedValue("mailPassword","isMailPassEncrypted");
    }
}
