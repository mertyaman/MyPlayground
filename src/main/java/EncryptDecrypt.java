import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * Created by merty on 16.06.2016 for MyPlayground.
 */
public class EncryptDecrypt {

    private final String master=System.getProperty("master");
    private final String propertyFileName = System.getProperty("user.dir")+"/src/main/resources/passwords.properties";
    private String pswrdKey = null;
    private String isPwdEcnryptedFlag = null;


    private String encryptThis() throws ConfigurationException {

        PropertiesConfiguration config = new PropertiesConfiguration(propertyFileName);
        String isEncrypted = config.getString(isPwdEcnryptedFlag);
        String encryptedPassword=null;

        //Check if password is encrypted?
        if(isEncrypted.equals("false")){
            String tmpPwd = config.getString(pswrdKey);////Decrypted password

            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
            encryptor.setPassword(master);
            encryptedPassword = encryptor.encrypt(tmpPwd);

            // Overwrite password
            config.setProperty(pswrdKey, encryptedPassword);
            config.setProperty(isPwdEcnryptedFlag,"true");
            config.save();
        }
        else{
            System.out.println("Password is already encrypted.\n ");
        }
        return encryptedPassword;
    }

    private String decryptThis() throws ConfigurationException {

        PropertiesConfiguration config = new PropertiesConfiguration(propertyFileName);
        String encryptedPropertyValue = config.getString(pswrdKey);//Encrypted password
        String isEncrypted = config.getString(isPwdEcnryptedFlag);
        String decryptedPropertyValue=null;

        if(isEncrypted.equals("true")){
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
            encryptor.setPassword(master);
            decryptedPropertyValue = encryptor.decrypt(encryptedPropertyValue);

            // Overwrite password
            config.setProperty(pswrdKey, decryptedPropertyValue);
            config.setProperty(isPwdEcnryptedFlag,"false");
//            config.save();
        }
        else{
            System.out.println("Password is already decrypted.\n ");
        }
        return decryptedPropertyValue;
    }

    public String getEncryptedValue(String propertyKey, String isPwdEcnryptedFlag){
        String encryptedValue=null;
        this.pswrdKey=propertyKey;
        this.isPwdEcnryptedFlag=isPwdEcnryptedFlag;
        try {
            encryptedValue = encryptThis();
//            System.out.println("Encryption done and encrypted password is : " + encryptedValue );
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return  encryptedValue;

    }
    public String getDecryptedValue(String propertyKey, String isPwdEcnryptedFlag){
        String decryptedValue=null;
        this.pswrdKey=propertyKey;
        this.isPwdEcnryptedFlag=isPwdEcnryptedFlag;
        try {
            decryptedValue = decryptThis();
//            System.out.println("Decryption done and decrypted password is : " + decryptedValue );
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return  decryptedValue;
    }
}
