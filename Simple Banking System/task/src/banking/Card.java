package banking;

import java.util.Arrays;
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
    public Card(String cardNumber, String pin, String balance){
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = Integer.valueOf(balance);added
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public static boolean isValidCardNumber(String cardNumber){
        int controlNumber = calculateCardNumberControlNumber(cardNumber);
        int checkSum = 10 -(controlNumber%10);
        return Integer.valueOf(cardNumber.substring(cardNumber.length()-1)) == checkSum;
    }

    private static int calculateCardNumberControlNumber(String cardNumber){
        String[] numsString = cardNumber.split("");
        int[] nums = new int[15];
        for(int i = 0; i < nums.length && i<16; i++ ){
            nums[i] = Integer.valueOf(numsString[i]);
        }
        for(int i = 0; i < nums.length; i +=2  ){
            nums[i]*=2;
            if(nums[i]>9){
                nums[i]-=9;
            }
        }
        return Arrays.stream(nums).sum();
    }

    private String generateCardNumber(){
        String bik = "400000";
        String randomAccount = String.valueOf(random.nextInt(999999999
                +1));
        String accountIdentifier = ("000000000" + randomAccount).substring(randomAccount.length());
        String cardNumber =  bik + accountIdentifier;
        int controlNumber =  calculateCardNumberControlNumber(cardNumber);
        int checkSum = (10 - (controlNumber % 10)) % 10;
        return cardNumber + checkSum;
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
    public boolean validateCard(String cardNumber){
        if(isValidCardNumber(cardNumber)){
            return true;
        }else{
            return false;
        }
    }
}
