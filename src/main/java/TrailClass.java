import Util.Card;
import Util.CardExcelReader;

import java.io.IOException;
import java.util.ArrayList;

public class TrailClass {
    public static void main(String[] args) {
//        try {
//            CardExcelReader cardExcelReader = new CardExcelReader("src/main/resources/Data/cardDetails.xlsx");
//            ArrayList<Card> cardArrayList = cardExcelReader.readCardDetailsFile();
//            for(Card card: cardArrayList){
//                System.out.println("getCardNo " + card.getCardNo());
//                System.out.println("email " + card.getEmail());
//                System.out.println("getExpiryMonth " + card.getExpiryMonth());
//                System.out.println("getExpiryYear " + card.getExpiryYear());
//                System.out.println("getCvc " + card.getCvc());
//                System.out.println("getZipCode " + card.getZipCode());
//            }
//        }catch (IOException exception){
//            exception.printStackTrace();
//        }

        ArrayList<String> list = new ArrayList<>();
        list.add("Vishy La Shield Sunscreen SPF-30");
        String a = "spf-30";

        if(list.get(0).toLowerCase().contains(a.toLowerCase())){
            System.out.println("yes");
        }else {
            System.out.println("no");
        }
    }
}
