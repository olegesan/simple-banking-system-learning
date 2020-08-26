package banking;

import java.util.Random;

public class Card {
    private String cardNumber;
    private String pin;
    private int balance;
    private Random random = new Random();
    public Card(){
        setCardNumber(generateCardNumber());
        setPin(generatePin());
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    private String generateCardNumber(){
        String bik = "400000";
        String checkSum = "0";
        String randomAccount = String.valueOf(random.nextInt(999999999+1));
        String accountIdentifier = ("000000000" + randomAccount).substring(randomAccount.length());
        return bik + accountIdentifier + checkSum;
    }
    private String generatePin(){
        String randomPin = String.valueOf(random.nextInt(10000));
        String pin = ("0000" + randomPin).substring(randomPin.length());
        return pin;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
    public boolean validatePin(String cardNumber, String pin){
        if(cardNumber.equals(getCardNumber()) && pin.equals(getPin()) ){
            return true;
        }else{
            return false;
        }
    }
}
