/**
 * Created by mertyaman on 13/06/16 for MyPlayground.
 */
public class MainClass {


    public static void main(String[] args){
        DBQueries dbQueries =new DBQueries();
        String cityName=dbQueries.getCityName("35");
        System.out.println(cityName);

        EwsAPIOperations ewsAPIOperations =new EwsAPIOperations();
        ewsAPIOperations.listFirstTenItems();
        


    }




}
