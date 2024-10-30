import java.sql.*;
import java.util.Scanner;
public class Operations {
    Connection con;
    PreparedStatement create;
    PreparedStatement fetchAll;
    PreparedStatement fetch;
    PreparedStatement update;
    PreparedStatement delete;
    PreparedStatement duplicate;

    Operations(){
        String username="postgres";
        String password="****"; //postgres password
        String url="jdbc:postgresql://localhost:5432/practice";

        try{
            con=DriverManager.getConnection(url,username,password);
            if(con!=null) System.out.println("connection established");
            else System.out.println("connection failed");
            create=con.prepareStatement("insert into contacts (name, contactinfo, country) values(?,?,?)");
            fetchAll=con.prepareStatement("select * from contacts order by name asc");
            fetch=con.prepareStatement("select * from contacts where name=?");
            update=con.prepareStatement("update contacts set name=?,contactinfo=?, country=? where id=?");
            delete=con.prepareStatement("delete from contacts where id=?");
            duplicate=con.prepareStatement("select * from contacts where contactinfo=?");

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public boolean checkContact(String contactName){
        int count=0;
        try{
            fetch.setString(1,contactName);
            ResultSet rs=fetch.executeQuery();

            while(rs.next()){
                System.out.println("Name: "+rs.getString(2)+" | Contact Info: "+rs.getString(3)+" | Country: "+rs.getString(4));
                count++;
            }

        }catch (Exception e) {
            System.out.println(e);
        }
        if(count==0) return  false;
        return true;
    }

    public void createContact(String contactName,String mobileNumber,String countryName){
        Scanner sc=new Scanner(System.in);
        try{
            duplicate.setString(1,mobileNumber);
            ResultSet rs=duplicate.executeQuery();
            int count=0;
            while(rs.next()){
                System.out.println("Name: "+rs.getString(2)+" | Contact Info: "+rs.getString(3)+" | Country: "+rs.getString(4));
                count++;
            }
            if(count!=0){
                System.out.println("Contact already exists");
                return;
            }
            while(checkContact(contactName)){
                System.out.println("Contact name already exists!");
                System.out.print("Enter a new contact name: ");
                contactName=sc.nextLine();
            }
            create.setString(1,contactName);
            create.setString(2,mobileNumber);
            create.setString(3,countryName);
            int i=0;
            i=create.executeUpdate();
            if(i==0) System.out.println("Failed to create contact");
            else System.out.println("contact created successfully");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void fetchContact(String contactName){
        try{
            if(!checkContact(contactName)){
                System.out.println("Contact name '"+contactName+ "' doesn't exist!");
            }
        }catch (Exception e) {
            System.out.println(e);
        }

    }
    public void fetchAllContacts(){
        try{
            ResultSet rs=fetchAll.executeQuery();
            while(rs.next()){
                System.out.println("Name: "+rs.getString(2)+" | Contact Info: "+rs.getString(3)+" | Country: "+rs.getString(4));
            }
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    public void editContact(String contactName){
        try{
            fetch.setString(1,contactName);
            ResultSet rs=fetch.executeQuery();
            int count=0;
            int idNumber=0;
            String newName="n";
            String newContactInfo="n";
            String newCountry="n";
            while(rs.next()){
                idNumber=rs.getInt(1);
                newName=rs.getString(2);
                newContactInfo=rs.getString(3);
                newCountry=rs.getString(4);
                System.out.println("Name: "+newName+" | Contact Info: "+newContactInfo+" | Country: "+newCountry);
                count++;
            }
            if(count==0){
                System.out.println("Contact name '"+contactName+ "' doesn't exist!");
                return;
            }
            Scanner sc= new Scanner(System.in);
            System.out.println("Enter the updated values (or) Type 'n' to have old value");
            String temp="n";
            System.out.print("New Name: ");
            temp=sc.nextLine();
            if(!temp.equals("n")){
                newName=temp;
                temp="n";
            }
            System.out.print("New Contact Info: ");
            temp=sc.nextLine();
            if(!temp.equals("n")){
                newContactInfo=temp;
                temp="n";
            }
            System.out.print("New Country: ");
            temp=sc.nextLine();
            if(!temp.equals("n")){
                newCountry=temp;
                temp="n";
            }
            System.out.println("Enter 'y' to save changes (or) 'n' to revert back to old values");
            System.out.print("Enter your choice: ");
            temp=sc.nextLine();
            if(temp.equals("y")){
                update.setInt(4,idNumber);
                update.setString(1,newName);
                update.setString(2,newContactInfo);
                update.setString(3,newCountry);
                int i=0;
                i=update.executeUpdate();
                if(i==0) System.out.println("Failed to update! Enter correct values.");
                else{
                    System.out.println("Records updated successfully!");
                    System.out.println("Updated records: ");
                    fetchContact(newName);
                }
            }
            else System.out.println("Records are not updated!");

        }catch (Exception e) {
            System.out.println(e);
        }
    }
    public void deleteContact(String contactName){
        try{
            fetch.setString(1,contactName);
            ResultSet rs=fetch.executeQuery();
            int count=0;
            int idNumber=0;
            while(rs.next()){
                idNumber=rs.getInt(1);
                System.out.println("Name: "+rs.getString(2)+" | Contact Info: "+rs.getString(3)+" | Country: "+rs.getString(4));
                count++;
            }
            if(count==0){
                System.out.println("Contact name '"+contactName+ "' doesn't exist!");
                return;
            }
            String temp="n";
            Scanner sc= new Scanner(System.in);
            System.out.println("Enter 'y' to delete the contact (or) 'n' to cancel");
            System.out.print("Enter your choice: ");
            temp=sc.nextLine();
            if(temp.equals("y")){
                delete.setInt(1,idNumber);
                delete.executeUpdate();
                System.out.println("contact "+contactName+" is deleted!");
            }
            else{
                System.out.println("contact "+contactName+" is not deleted");
            }
        }catch (Exception e) {
            System.out.println(e);
        }
    }
    public void closeConnection(){
        try {
            con.close();
        }catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Thank You!");
    }
}
