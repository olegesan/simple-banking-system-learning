package banking;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class AccountController {
    private Card card;
    private ArrayList<String> actions;
    private AccountInterface gui;
    private String state;
    private DBController dbController;
    public AccountController(String[] args){
        gui = new AccountInterface();
        dbController = new DBController(getDBName(args));
        setState("Main Menu");

        while(true){
            updateGui();
            handleMenuInput();
            if(getState().equals("Exit")) {
                break;
            }
        }
    }
    public String getDBName(String[] args){
        for(int i = 0; i < args.length-1; i++){
            if(args[i].equals("-fileName")){
                return args[i+1];
            }
        }
        return "";
    }
    public void addIncome(){
        System.out.println("Enter income:");
        String input = getUserInput();
        int value = 0;
        while(true){
            try{
                value = Integer.valueOf(input);
                break;
            }catch(NumberFormatException e){
                System.out.println(e);
                return;
            }
        }
        boolean resultAdd = dbController.updateBalance(card.getCardNumber(), value);

        if(resultAdd){
            card.setBalance(card.getBalance()+value);
            gui.displaySuccessfulIncomeAdd();
        }

    }
    public void closeAccount(){
        boolean status = dbController.closeAccount(card.getCardNumber());
        if(status){
            System.out.println("The account has been closed!");
            setState("Main Menu");
            return;
        }
    }
    public void transfer(){
        System.out.println("Transfer");
        System.out.println("Enter card number:");
        String cardNumber = getUserInput();
        if(!Card.isValidCardNumber(cardNumber)){
            gui.displayWrongCardNumErr();
            return;
        }
        if(dbController.getCardInfo(cardNumber)[0].equals("error")) {
            gui.noSuchCardError();
            return;
        }
        System.out.println("Enter how much money you want to transfer:");
        String amountStr = getUserInput();
        int amount = 0;
        try{
            amount = Integer.valueOf(amountStr);
        }catch(Exception e){
            System.out.println(e);
        }
        if(card.getBalance()-amount < 0){
            System.out.println("Not enough money!");
        }
        dbController.transfer(card.getCardNumber(), amount, cardNumber);
        card.setBalance(card.getBalance()-amount);
        System.out.println("Success!");
    }
    public void login(){
        System.out.println("Enter your card number:");
        String cardNumber = getUserInput();
            System.out.println("Enter your PIN:");
            String pin = getUserInput();
            if(card.isValidCardNumber(cardNumber)){
                String cardStringArr[] = dbController.getCardInfo(cardNumber);
                if(cardStringArr[1].equals(pin)){
                    Card card = new Card(cardStringArr[0], cardStringArr[1], cardStringArr[2]);
                    setCard(card);
                    setState("Account Menu");
                    gui.displaySuccessfulLogin();
                    return;
                }
            }
        gui.displayFailedLogin();

    }
    public void exit(){
        setState("Exit");
        gui.printExitMessage();
    }

    public void logout(){
        setCard(null);
        setState("Main Menu");
    }

    private void handleMenuInput(){
        String userInput = getUserInput();
        int menuItem = Integer.parseInt(userInput);
        if (menuItem==0){
            actionToMethod("Exit");
        }else{
            actionToMethod(getActions().get(menuItem-1));
        }

    }
    private ArrayList<String> getStateAvailableActions(String state){
        ArrayList<String> allActions = new ArrayList<>(Arrays.asList("Exit",
                "Create an account",
                "Log out",
                "Log into account",
                "Balance",
                "Add income",
                "Close account",
                "Do transfer"));
        switch(state){
            case "Main Menu":
                return new ArrayList<> (Arrays.asList(allActions.get(1),
                        allActions.get(3),
                        allActions.get(0)));
            case "Account Menu":
                return new ArrayList<> (Arrays.asList(allActions.get(4),
                        allActions.get(5),
                        allActions.get(7),
                        allActions.get(6),
                        allActions.get(2),
                        allActions.get(0)));
        }
        return null;
    }

    public void actionToMethod(String action){
        switch(action){
            case "Exit":
                exit();
                break;
            case "Create an account":
                createAccount();
                break;
            case "Log out":
                logout();
                break;
            case "Log into account":
                login();
                break;
            case "Balance":
                displayBalance();
                break;
            case "Add income":
                addIncome();
                break;
            case "Do transfer":
                transfer();
                break;
            case "Close account":
                closeAccount();
                break;
        }
    }
    public void displayBalance(){

        gui.displayBalance(card);
    }
    public void createAccount(){
        while(true){
            Card card = new Card();
            setCard(card);
            if(dbController.insertCard(card.getCardNumber(),card.getPin())){
                break;
            }
        }
        gui.displayNewCard(card);
    }

    private String getUserInput(){
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }


    private void updateGui(){
        setActions(getStateAvailableActions(getState()));
        gui.displayActions(getActions());
    }

    private ArrayList<String> getActions(){
        return actions;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setActions(ArrayList<String> actions) {
        this.actions = actions;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
