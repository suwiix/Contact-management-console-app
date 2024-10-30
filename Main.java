//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        Operations op=new Operations();
        boolean exit=true;
        do{
            System.out.println("LIST OF OPERATIONS:");
            System.out.println("1 - Create contact");
            System.out.println("2 - Edit contact");
            System.out.println("3 - Search contact");
            System.out.println("4 - Delete contact");
            System.out.println("5 - See all contacts");
            System.out.println("0 - Exit");
            System.out.print("Enter your choice of operation: ");
            int choice=Integer.parseInt(sc.nextLine());
            String mobileNumber,contactName,countryName;
            switch(choice){
                case 1:
                    System.out.print("Enter Mobile Number: ");
                    mobileNumber=sc.nextLine();
                    System.out.print("Enter Name: ");
                    contactName=sc.nextLine();
                    System.out.print("Enter Country: ");
                    countryName=sc.nextLine();
                    op.createContact(contactName,mobileNumber,countryName);
                    break;
                case 2:
                    System.out.print("Enter the contact name you want to edit: ");
                    contactName=sc.nextLine();
                    op.editContact(contactName);
                    break;
                case 3:
                    System.out.print("Enter the contact name you want to search: ");
                    contactName=sc.nextLine();
                    op.fetchContact(contactName);
                    break;
                case 4:
                    System.out.print("Enter the contact name you want to delete: ");
                    contactName=sc.nextLine();
                    op.deleteContact(contactName);
                    break;
                case 5:
                    op.fetchAllContacts();
                    break;
                case 0:
                    exit=false;
                    op.closeConnection();
                    break;
                default:
                    System.out.println("Invalid Choice! Enter your choice of operation correctly");
            }
            System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        }while(exit);

    }
}