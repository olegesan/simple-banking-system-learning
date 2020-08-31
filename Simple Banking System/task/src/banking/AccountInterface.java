package banking;

import java.util.ArrayList;

public class AccountInterface {
    public AccountInterface(){

    }
    public void printExitMessage(){
        System.out.println();
        System.out.println("Bye!");
    }
    public void displayBalance(Card card){
        System.out.println();
        int balance = card.getBalance();
        System.out.println("Balance: "+balance);
    }
    public void displayNewCard(Card card){
        System.out.println();
        System.out.println("Your card has been created");
        System.out.println("Your card number: ");
        System.out.println(card.getCardNumber());
        System.out.println("Your card PIN: ");
        System.out.println(card.getPin());
    }
    public void displayActions(ArrayList<String> actions){
        System.out.println();
        for(int i = 0; i < actions.size()-1; i++){
            System.out.println(i+1+". "+actions.get(i));
        }
        System.out.println("0. Exit");
    }
    public void displaySuccessfulLogin(){
        System.out.println();
        System.out.println("You have successfully logged in!");
    }

    public void displayFailedLogin(){
        System.out.println();
        System.out.println("Wrong card number or PIN!");
    }

    public void displaySuccessfulIncomeAdd(){
        System.out.println("Income was added!");
    }
    public void displayWrongCardNumErr(){
        System.out.println("Probably you made mistake in the card number. Please try again!");
    }
    public void noSuchCardError(){
        System.out.println("Such a card does not exist.");
    }
}
