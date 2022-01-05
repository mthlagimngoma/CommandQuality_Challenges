import java.util.Scanner;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.io.*;

public class Main{
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ArrayList<String> userNames = new ArrayList<String>();
        ArrayList<String> userSurnames = new ArrayList<String>();
        ArrayList<String> dateOfBirth = new ArrayList<String>();
        ArrayList<String> userEmails = new ArrayList<String>();
        String filePath = "C:\\Users\\nmowa\\IdeaProjects\\UserDatabaseJSON\\src\\main\\java\\UserDatabase.txt";

		/*While loop ensure that the program doesn't terminate but keeps
		showing the user the menu*/
        while(true){

            int menuOption = getMenuOption();
            if(menuOption == 1){ 		//Add option
                addUser(userNames, userSurnames, userEmails, dateOfBirth, filePath);
            }else if(menuOption == 2){ 	//Update option
                updateUserDetails(userNames, userSurnames, userEmails,
                        dateOfBirth, filePath);
            }else if(menuOption == 3){ 	//Delete option
                deleteUserDetails(userNames, userSurnames, userEmails,
                        dateOfBirth, filePath);
            }
            else if(menuOption == 4){	// List Option
                listUsers(userNames, userSurnames, userEmails,
                        dateOfBirth, filePath);
            }else if(menuOption == 5){  	//Terminate the program
                break;
            }
        }
    }


    /*Displays the menu to user and prompt to choose an option
    return a number for a certain menu option.
    */
    static int getMenuOption(){
        Scanner scannerMenu = new Scanner(System.in);

        int option;
        //Display menu
        while(true){
            System.out.println("---------------Menu----------------");
            System.out.println("(1) Add \t\t (2) Update \n");
            System.out.println("(3) Delete \t\t (4) List \n");
            System.out.println("(5) Exit \n");
            System.out.println("-----------------------------------");
            System.out.print("Choose an option(1-5) : ");
            try{
                option = scannerMenu.nextInt();
                break;

            }catch(Exception e){
                System.out.println("Option not valid");
                scannerMenu.next();
            }
        }
        return option;
    }

    /*Displays options to user when they want to update
    and returns the option*/
    static int updateMenuOption(){
        Scanner scannerMenu = new Scanner(System.in);

        int option;
        while(true){
            System.out.println("---------------Update Details----------------");
            System.out.println("(1) Name \t\t (2) Surname \n");
            System.out.println("(3) Email \t\t (4) Date of birth \n");
            System.out.println("(5) All Details \n");
            System.out.println("-----------------------------------");
            System.out.print("Choose an option(1-5) : ");
            try{
                option = scannerMenu.nextInt();
                if(option >= 1 && option <= 5){
                    break;
                }

            }catch(Exception e){
                System.out.println("Option not valid");
                scannerMenu.next();
            }
        }
        return option;
    }


    //Menu Options methods
    //Add a user
    static void addUser(ArrayList<String> name, ArrayList<String> surname,
                        ArrayList<String> emails,
                        ArrayList<String> dateOfBirth, String filePath) throws IOException, ClassNotFoundException {
        ArrayList<ArrayList<String>> userDatabase = new ArrayList<ArrayList<String>>();
        try {
            userDatabase = JSONSerializationHelper.deserializeFromFile(filePath, ArrayList.class);
        }catch (Exception e){
            System.out.print("");
        }finally {
            if (userDatabase.size() == 0) {
                userDatabase.add(name);
                userDatabase.add(surname);
                userDatabase.add(emails);
                userDatabase.add(dateOfBirth);
            } else {
                name = userDatabase.get(0);
                surname = userDatabase.get(1);
                emails = userDatabase.get(2);
                dateOfBirth = userDatabase.get(3);
            }
            String userName = getUserName();
            name.add(userName);
            String userSurname = getUserSurname();
            surname.add(userSurname);
            String userEmail = getUserEmail();
            emails.add(userEmail);
            String dob = getUserDOB();
            dateOfBirth.add(dob);
            JSONSerializationHelper.serialiseToFile(userDatabase, filePath);
            int year = Integer.parseInt(dob.split("/")[2]);
            int age = 2021 - year;            //Calculate age
            System.out.println("You are " + age + " years old");
            System.out.println("Hello " + userName + " " + userSurname +
                    " your details have been saved. \n");
        }
    }

    /*Takes an arraylist of emails and first checks if it's not empty
    then check if the user exists and updates the details.
    */
    static void updateUserDetails(ArrayList<String> name, ArrayList<String> surname,
                                  ArrayList<String> emails,
                                  ArrayList<String> dateOfBirth, String filePath) throws IOException, ClassNotFoundException {
        ArrayList<ArrayList<String> > userDatabase;
        int updateOption;
        String userName;
        String userSurname;
        String userEmail;
        String dob;
        try{
            userDatabase = JSONSerializationHelper.deserializeFromFile(filePath, ArrayList.class);
            name = userDatabase.get(0);
            surname = userDatabase.get(1);
            emails = userDatabase.get(2);
            dateOfBirth = userDatabase.get(3);
            lengthOfList(emails);
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();
            int check = emails.size();
            for(int i = 0; i < emails.size(); i++){
                // check if email is in database
                if(email.equalsIgnoreCase(emails.get(i))){
                    System.out.println("Name: "+ name.get(i) + "\tSurname: "
                            + surname.get(i) + "\tEmail: " +
                            emails.get(i) + "\tDOB " +
                            dateOfBirth.get(i));
                    check--;
                    updateOption = updateMenuOption();
                    if(updateOption == 1){ //update name
                        userName = getUserName();
                        name.set(i, userName);
                    }else if(updateOption == 2){  //update surname
                        userSurname = getUserSurname();
                        surname.set(i, userSurname);
                    }else if(updateOption == 3){  //update email
                        userEmail = getUserEmail();
                        emails.set(i, userEmail);
                    }else if(updateOption == 4){  //update date of birth
                        dob = getUserDOB();
                        dateOfBirth.set(i, dob);
                    }else if(updateOption == 5){ //  //update all details
                        userName = getUserName();
                        name.set(i, userName);
                        userSurname = getUserSurname();
                        surname.set(i, userSurname);
                        userEmail = getUserEmail();
                        emails.set(i, userEmail);
                        dob = getUserDOB();
                        dateOfBirth.set(i, dob);
                    }
                    JSONSerializationHelper.serialiseToFile(userDatabase, filePath);
                }
            }
            if(check == emails.size()){
                System.out.println("User not found");
            }

        }catch(Exception e){
            System.out.println("No users available");
        }
    }

    /*Takes an arraylist of emails and first checks if it's not empty
    then check if the user exists and deletes user if email matches ones
    stored in database.
    */
    static void deleteUserDetails(ArrayList<String> name, ArrayList<String> surname,
                                  ArrayList<String> emails,
                                  ArrayList<String> dateOfBirth, String filePath) throws IOException, ClassNotFoundException {
        ArrayList<ArrayList<String> > userDatabase;
        try{
            userDatabase = JSONSerializationHelper.deserializeFromFile(filePath, ArrayList.class);
            name = userDatabase.get(0);
            surname = userDatabase.get(1);
            emails = userDatabase.get(2);
            dateOfBirth = userDatabase.get(3);
            lengthOfList(emails);  	//Check size of arraylist
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();
            int temp = emails.size();
            for(int i = 0; i < emails.size(); i++){
                if(email.equalsIgnoreCase(emails.get(i))){
                    String tempName = name.get(i);
                    String tempsurname = surname.get(i);
                    name.remove(i);
                    surname.remove(i);
                    emails.remove(i);
                    dateOfBirth.remove(i);
                    System.out.println("User "+ tempName + " " + tempsurname+
                            " has been deleted");
                    i--;
                }
                JSONSerializationHelper.serialiseToFile(userDatabase, filePath);
            }if(temp == emails.size()){
                System.out.println("User not found!");
            }
        }catch(Exception e){
            System.out.println("No users available");
        }
    }

    // List all users
    // If list is empty System.out.println("No Users available");
    static void listUsers(ArrayList<String> name, ArrayList<String> surname,
                          ArrayList<String> emails,
                          ArrayList<String> dateOfBirth, String filePath) throws IOException, ClassNotFoundException {
        ArrayList<ArrayList<String> > userDatabase;
        try{
            userDatabase = JSONSerializationHelper.deserializeFromFile(filePath, ArrayList.class);
            name = userDatabase.get(0);
            surname = userDatabase.get(1);
            emails = userDatabase.get(2);
            dateOfBirth = userDatabase.get(3);
            lengthOfList(emails);  	//Check size of arraylist
            for(int i = 0; i < name.size(); i++){
                System.out.println(name.get(i) + " " + surname.get(i) + " " +
                        emails.get(i) + " " + dateOfBirth.get(i));
            }

        }catch (Exception e){
            System.out.println("No users available");
        }
    }
    //End of menu options methods


    //Start of user details validation methods

    /*Takes user name input and check if it's valid and
    returns the name if it's valid
    */
    static String getUserName(){
        while(true){
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            try{
                validateName(name);
                return name;
            }catch(Exception e){
                System.out.println("Name Invalid!");
            }
        }
    }

    /*Takes user surname input and check if it's valid and returns
    the surname if it's valid.
    */
    static String getUserSurname(){
        while(true){
            System.out.print("Enter your surname: ");
            String surname = scanner.nextLine();
            try{
                validateSurname(surname);
                return surname;
            }catch(Exception e){
                System.out.println("Surname Invalid!");
            }
        }
    }


    /*Takes user email input and check if it's valid and returns
    the email if it's valid.
    */
    static String getUserEmail(){
        while(true){
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();
            try{
                validateEmail(email);
                return email;
            }catch(Exception e){
                System.out.println("Email Invalid!");
            }
        }
    }

    /*Take user date of birth input and check if it's valid and returns
    the date of birth if it's valid.
    */
    static String getUserDOB(){
        String dob;
        int yearCheck;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        while(true){
            System.out.print("Enter your date of birth(dd/MM/yyyy): ");
            dob = scanner.nextLine();
            try{
                Date date = dateFormat.parse(dob);
                if(dob.length() == 10){
                    yearCheck = Integer.parseInt(dob.split("/")[2]);
                    if(yearCheck < 2021){
                        return dob;
                    }else{
                        System.out.println("Can't be born in the future");
                    }
                }else{
                    System.out.println("Date format not valid");
                }
            }catch(Exception e){
                System.out.println("Date of birth is not valid");
            }
        }
    }
    //End of user details methods


    //Throw this exception if name format is not valid
    static void validateName(String nameCheck) throws ThrowException{
        if(!Pattern.matches("^[a-zA-Z]+$",nameCheck)){
            throw new ThrowException("");
        }
    }

    //Throw this exception if surname format is not valid
    static void validateSurname(String surnameCheck) throws ThrowException{
        if(!Pattern.matches("^[a-zA-Z]+$",surnameCheck)){
            throw new ThrowException("");
        }
    }

    //Throw this exception if email format is not valid
    static void validateEmail(String emailCheck) throws ThrowException{
        if(!Pattern.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
                emailCheck)){
            throw new ThrowException("");
        }
    }

    //Throw this exception if there are no users(UPDATE, DELETE, LIST)
    static void lengthOfList(ArrayList<String> arrayList) throws ThrowException{
        ArrayList<String> userNames = arrayList;
        if(userNames.size() == 0){
            throw new ThrowException("");
        }
    }

}