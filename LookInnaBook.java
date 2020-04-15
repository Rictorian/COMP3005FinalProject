package lookinnabook;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Ben Campbell
 */
public class LookInnaBook {

        static Scanner menu_input = new Scanner(System.in);
        static Scanner int_input = new Scanner(System.in);
        static Scanner str_input = new Scanner(System.in);
        static Scanner flt_input = new Scanner(System.in);
        static Scanner lng_input = new Scanner(System.in);
        
        static ArrayList<String> returnList = new ArrayList<String>();
        static ArrayList<String> returnList2 = new ArrayList<String>();
        static ArrayList<String> returnList3 = new ArrayList<String>();
        static ArrayList<String> bookNameList = new ArrayList<String>();
        static ArrayList<String> bookAuthorList = new ArrayList<String>();
        static ArrayList<String> bookGenreList = new ArrayList<String>();
        static ArrayList<String> bookISBNList = new ArrayList<String>();
        static ArrayList<String> bookPageList = new ArrayList<String>();
        static ArrayList<String> bookRatingList = new ArrayList<String>();
        static ArrayList<String> bookCostList = new ArrayList<String>();
        static ArrayList<String> bookQuantityList = new ArrayList<String>();
        
        static int int_choice = -1, menu_choice = -1;
        static String str_choice = "";
        static float flt_choice = -1.0f;
        static long lng_choice = -1;
        static int lastOrderNum;
        static int currOrderNum;
        
        static boolean retry = false;
        static boolean valid = true;
        static boolean quit = false;
        static Statement statement;
        static int activeID = -1;
        static String userName = "";
        static boolean loggedIn = false;
     
    public static void clean()
    {
        menu_choice = -1;
        int_choice = -1;
        str_choice = "";
        flt_choice = -1.0f;
        retry = false;
        valid = true;
    }
    
    public static void cleanList()
    {
        returnList.clear();
        returnList2.clear();
        returnList3.clear();
        bookNameList.clear();
        bookAuthorList.clear();
        bookGenreList.clear();
        bookISBNList.clear();
        bookPageList.clear();
        bookRatingList.clear();
        bookCostList.clear();
        bookQuantityList.clear();
    }
        
    
    public static void query(String inQuery, String column)
    {
     try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookStore", "postgres", "password")) {
 
            System.out.println("Java JDBC PostgreSQL Example");
 
            System.out.println("Connected to PostgreSQL database!");
            System.out.println();
            System.out.println();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(inQuery);
           
            while (resultSet.next()) {
                returnList.add(resultSet.getString(column));
            }
        }
        catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }   
    }
    
    public static void query(String inQuery, String column, String column2)
    {
     try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookStore", "postgres", "password")) {
 
            System.out.println("Java JDBC PostgreSQL Example");
 
            System.out.println("Connected to PostgreSQL database!");
            System.out.println();
            System.out.println();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(inQuery);
           
            while (resultSet.next()) {
                returnList.add(resultSet.getString(column));
                returnList2.add(resultSet.getString(column2));
            }
        }
        catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }   
    }
    
    public static void registerQuery(String name, String address, Long creditCard, Long phoneNum)
    {
     try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookStore", "postgres", "password")) {

            System.out.println();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customer");
            boolean atEnd = false;
            while(!atEnd)
            {
                if(resultSet.isLast())
                {
                    atEnd = true;
                }
                else
                    resultSet.next();
            }
            int lastUID = resultSet.getInt("UID");
            int newID = lastUID + 1;
            
            statement.executeUpdate("INSERT INTO customer VALUES(" + newID + ", '" + name + "', '" + address + "', " + phoneNum + ", " + creditCard + ")");
            
            activeID = newID;
            loggedIn = true;
            userName = name;
        }
        catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
    }
    
    public static void bookQuery(String inQuery)
    {
     try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookStore", "postgres", "password")) {

            System.out.println();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(inQuery);
           
            while (resultSet.next()) {
                bookNameList.add(resultSet.getString("Name"));
                bookAuthorList.add(resultSet.getString("Author"));
                bookGenreList.add(resultSet.getString("Genre"));
                bookISBNList.add(resultSet.getString("ISBN"));
                bookPageList.add(resultSet.getString("Page_Count"));
                bookRatingList.add(resultSet.getString("Rating"));
                bookCostList.add(resultSet.getString("Cost"));
            }
        }
        catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }   
    }
    
    public static void bookBasketAdd(String inQuery)
    {
     try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookStore", "postgres", "password")) {

            System.out.println();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(inQuery);
            resultSet.next();
            if(resultSet.getBoolean(1))
            {
                statement.executeUpdate("UPDATE basket SET quantity=quantity+1 WHERE ISBN='" + bookISBNList.get(int_choice-1) + "' AND UID=" + activeID);
            }
            else
            {
                statement.executeUpdate("INSERT INTO basket VALUES (" + activeID + ", '" + bookISBNList.get(int_choice-1) + "', " + 1 +")");
            }
        }
        catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }   
    }
    
    public static void bookBasketRemove(String inQuery)
    {
     try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookStore", "postgres", "password")) {

            System.out.println();
            statement = connection.createStatement();
            statement.executeUpdate(inQuery);
        }
        catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }   
    }
    
    public static void basketQuery(String inQuery)
    {
     try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookStore", "postgres", "password")) {
            cleanList();
            System.out.println();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(inQuery);
            
            while (resultSet.next()) {
                bookNameList.add(resultSet.getString("Name"));
                bookISBNList.add(resultSet.getString("ISBN"));
                bookAuthorList.add(resultSet.getString("Author"));
                bookGenreList.add(resultSet.getString("Genre"));
                bookCostList.add(resultSet.getString("Cost"));
                bookQuantityList.add(resultSet.getString("Quantity"));
            }
        }
        catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }   
    }
    
    public static void checkoutQuery()
    {
     try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookStore", "postgres", "password")) {
            cleanList();
            System.out.println();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT order_number FROM orders");
            boolean atEnd = false;
            while(!atEnd)
            {
                if(resultSet.isLast())
                {
                    atEnd = true;
                }
                else
                    resultSet.next();
            }
            lastOrderNum = resultSet.getInt(1);
            currOrderNum = lastOrderNum + 1;
            
            ResultSet resultSet3 = statement.executeQuery("SELECT address, credit_card_number FROM customer WHERE uid=" + activeID);
            resultSet3.next();
            statement.executeUpdate("INSERT INTO orders VALUES (" + currOrderNum + ", " + activeID + ", 0000000, '" + resultSet3.getString("address") + "', " + resultSet3.getLong("credit_card_number") + ")");
            ResultSet resultSet2 = statement.executeQuery("SELECT * FROM basket WHERE uid=" + activeID);
            while (resultSet2.next()) {
                statement = connection.createStatement();
                statement.executeUpdate("INSERT INTO orderbasket VALUES (" + resultSet2.getString("ISBN") + ", " + resultSet2.getInt("quantity") + ", " + currOrderNum + ");");
            };
            
            statement.executeUpdate("DELETE FROM basket WHERE uid=" + activeID);
        }
        catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }   
    }
    
        public static void orderQuery(String inQuery)
    {
     try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookStore", "postgres", "password")) {
            cleanList();
            System.out.println();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(inQuery);
            resultSet.next();
            returnList.add(resultSet.getString("delivery_date"));
            returnList2.add(resultSet.getString("address"));
            returnList3.add(resultSet.getString("credit_card_number"));
        }
        catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }   
    }
        
    public static void addBookQuery(String inQuery)
    {
     try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookStore", "postgres", "password")) {
            cleanList();
            System.out.println();
            statement = connection.createStatement();
            statement.executeUpdate(inQuery);
            }
        catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }   
    }
    
    public static void removeBookQuery(String inQuery)
    {
     try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookStore", "postgres", "password")) {
            cleanList();
            System.out.println();
            statement = connection.createStatement();
            statement.executeUpdate(inQuery);
            }
        catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }   
    }
    
    
    public static void menu_input()
    {
        if (menu_input.hasNextInt())
            {
                menu_choice = menu_input.nextInt();
            }
    }
    
    public static void int_input()
    {
        if (int_input.hasNextInt())
            {
                int_choice = int_input.nextInt();
            }
    }
    
    public static void str_input()
    {
        if (str_input.hasNext())
        {
            str_choice = str_input.nextLine();
        }
    }
        
    public static void flt_input()
    {
        if (flt_input.hasNextFloat())
        {
            flt_choice = flt_input.nextFloat();
        }
    }
    
    public static void lng_input()
    {
        if (lng_input.hasNextFloat())
        {
            lng_choice = lng_input.nextLong();
        }
    }
        
    
    public static void main(String[] args)
    {
        while(!quit)
        {
            while((menu_choice > 4 || menu_choice < 1) && loggedIn && (activeID > 9999 && activeID < 99999)) // Logged In as Admin
            {
                System.out.println("******************************");
                System.out.println("* Welcome to Look Inna Book! *");
                System.out.println("******************************");
                System.out.println();
                System.out.println("What would you like to do " + userName + "? (ADMIN)");
                System.out.println("(1) Reports");
                System.out.println("(2) Add Book");
                System.out.println("(3) Remove Book");
                System.out.println("(4) Logout");
                System.out.print("Choice: ");
                menu_input();
                
                switch(menu_choice)
                {
                    case 2:
                        addBook();
                        clean();
                        break;
                    case 3:
                        removeBook();
                        clean();
                        break;
                    case 4:
                        activeID = -1;
                        userName = "";
                        loggedIn = false;
                        break;
                }
            }
            
            while((menu_choice > 4 || menu_choice < 1) && loggedIn && (activeID > 999 && activeID < 9999)) // Logged In as User
            {
                System.out.println("******************************");
                System.out.println("* Welcome to Look Inna Book! *");
                System.out.println("******************************");
                System.out.println();
                System.out.println("What would you like to do " + userName + "?");
                System.out.println("(1) Search Books");
                System.out.println("(2) View Basket/Checkout");
                System.out.println("(3) Delivery Lookup");
                System.out.println("(4) Logout");
                System.out.print("Choice: ");
                menu_input();
                
                switch(menu_choice)
                {
                    case 1:
                        search();
                        clean();
                        break;
                    case 2:
                        viewBasket();
                        clean();
                        break;
                    case 3:
                        checkOrder();
                        clean();
                        break;
                    case 4:
                        activeID = -1;
                        userName = "";
                        loggedIn = false;
                        clean();
                        break;
                }
            }

            while((menu_choice > 3 || menu_choice < 1) && !loggedIn) // Not Logged In
            {
                System.out.println("******************************");
                System.out.println("* Welcome to Look Inna Book! *");
                System.out.println("******************************");
                System.out.println();
                System.out.println("What would you like to do?");
                System.out.println("(1) Login");
                System.out.println("(2) Register");
                System.out.println("(3) Quit");
                System.out.print("Choice: ");
                menu_input();


                switch(menu_choice)
                {
                    case 1:
                        login();
                        clean();
                        break;
                    case 2:
                        register();
                        clean();
                        break;
                    case 3:
                        clean();
                        return;
                }
            }
        }
    }
    
    public static void login()
    {
        clean();
        retry = true;
        while(retry)
        {
            System.out.println("Please enter your user ID");
            System.out.print("Choice: ");
            int_input();
            query("SELECT * FROM public.customer", "UID", "Name");
            
            boolean valid = false;
            for (int i = 0; i < returnList.size(); i++)
            {
                if(Integer.parseInt(returnList.get(i)) == int_choice)
                {
                    valid = true;
                    userName = returnList2.get(i);
                }
            }
            if(valid)
            {
                activeID = int_choice;
                loggedIn = true;
                retry = false;
                System.out.println("Successfully logged in!");
                System.out.println("Welcome back " + userName);
            }
            else
            {
                System.out.println("User ID not found, would you like to: ");
                System.out.println("(1) Try Again");
                System.out.println("(2) Register");
                System.out.print("Choice: ");
                menu_input();
                if(menu_choice == 2)
                {
                    register();
                    retry = false;
                }
            }
        }
    }
    
    public static void register()
    {
        valid = false;
        String name = "", address = "";
        long creditCard = -1, phoneNum = -1;
        while(!valid)
        {
            clean();
            System.out.println("Please Enter Your Name");
            System.out.print("Choice: ");
            str_input();
            name = str_choice;
            System.out.println("Please Enter Your Address");
            System.out.print("Choice: ");
            str_input();
            address = str_choice;
            System.out.println("Please Enter Your 10 digit Phone Number");
            System.out.print("Choice: ");
            lng_input();
            phoneNum = lng_choice;
            System.out.println("Please Enter Your Credit Card Number For Purchases");
            System.out.print("Choice: ");
            lng_input();
            creditCard = lng_choice;
            System.out.println();
            if(name == "")
            {
                System.out.println("Please Enter a Valid Name");
                valid = false;
            }
            if(address == "")
            {
                System.out.println("Please Enter a Valid Address");
                valid = false;
            }
            if(creditCard == -1.0f || creditCard < 10000000 || creditCard > 9999999999999999f)
            {
                System.out.println("Please Enter a Valid Credit Card");
                valid = false;
            }
            if(phoneNum == -1.0f || phoneNum < 999999999 || phoneNum > 9999999999f)
            {
                System.out.println("Please Enter a Valid Phone Number");
                valid = false;
            }
            System.out.println();
        }
        
        userName = name;
        registerQuery(name, address, creditCard, phoneNum);
        
        System.out.println();
        System.out.println(name);
        System.out.println(address);
        System.out.println(phoneNum);
        System.out.println(creditCard);
        
        
    }
    
    public static void search()
    {
        clean();
        String searchterm = "";
        retry = true;
        while(retry)
        {
            cleanList();
            System.out.println("What would you like to search by?");
            System.out.println("(1) Title");
            System.out.println("(2) Author");
            System.out.println("(3) Genre");
            System.out.println("(4) ISBN");
            System.out.println("(5) Exit");
            System.out.print("Choice: ");
            menu_input();
            if(menu_choice == 5)
                return;
            
            System.out.println("What would you like to search for?");
            System.out.print("Choice: ");
            str_input();
            
            switch(menu_choice)
            {
                case 1:
                    bookQuery("SELECT * FROM public.book WHERE Name ILIKE '%" + str_choice + "%'");
                    break;
                case 2:
                    bookQuery("SELECT * FROM public.book WHERE Author ILIKE '%" + str_choice + "%'");
                    break;
                case 3:
                    bookQuery("SELECT * FROM public.book WHERE Genre ILIKE '%" + str_choice + "%'");
                    break;
                case 4:
                    bookQuery("SELECT * FROM public.book WHERE ISBN ILIKE '%" + str_choice + "%'");
                    break;
            }
            for (int i = 0; i < bookNameList.size(); i++)
            {
                System.out.println("(" + (i+1) + ") " + bookNameList.get(i) + " By: " + bookAuthorList.get(i) + ", Genre: " + bookGenreList.get(i) + ", ISBN: " + bookISBNList.get(i) + ", Pages: " + bookPageList.get(i) + ", Rating: " + bookRatingList.get(i) + ", Cost: $" + bookCostList.get(i));
            }
            System.out.println();
            
            System.out.println("What would you like to do?");
            System.out.println("(1) Search Again");
            System.out.println("(2) Add Book From Search to Basket");
            System.out.println("(3) Exit");
            System.out.print("Choice: ");
            menu_input();
            System.out.println();
            switch(menu_choice)
            {
                case 2:
                    addToBasket();
                    break;
                case 3:
                    retry = false;
                    break;
            }
        }
    }
    
    public static void addToBasket()
    {
        clean();
        System.out.println("What book would you like to add? (by number)");
        System.out.print("Choice: ");
        int_input();
        if(int_choice > 0 && int_choice < bookNameList.size())
        {
            bookBasketAdd("SELECT EXISTS(SELECT * from public.basket WHERE ISBN='" + bookISBNList.get(int_choice-1) + "')");
            System.out.println("Book Added Successfully!");
        }
            
        else
            System.out.println("Error: Book doesn't exist");
    }
    
    public static void viewBasket()
    {
        clean();
        retry = true;
        while(retry)
        {
            cleanList();
            basketQuery("SELECT Basket.quantity, Basket.isbn, Book.Name, Book.author, Book.genre, book.cost FROM Basket INNER JOIN Book ON Basket.ISBN=BOOK.ISBN WHERE uid=" + activeID);
            for (int i = 0; i < bookNameList.size(); i++)
            {
                System.out.println("(" + (i+1) + ") x" + bookQuantityList.get(i) + " " + bookNameList.get(i) + " By: " + bookAuthorList.get(i) + ", Genre: " + bookGenreList.get(i) + ", Cost: $" + bookCostList.get(i));
            }
            System.out.println();
            System.out.println("What would you like to do?");
            System.out.println("(1) Delete a book");
            System.out.println("(2) Checkout");
            System.out.println("(3) Go to Menu");
            System.out.print("Choice: ");
            menu_input();
            switch(menu_choice)
            {
                case 1:
                    System.out.println("What book would you like to remove? (by number)");
                    System.out.print("Choice: ");
                    int_input();
                    if(int_choice > 0 && int_choice < bookNameList.size())
                    {
                        bookBasketRemove("DELETE FROM basket WHERE ISBN='" + bookISBNList.get(int_choice - 1)+"'");
                        System.out.println("Book Removed Successfully!");
                    }
                    else
                        System.out.println("Error: Book doesn't exist");
                    clean();
                    break;
                case 2:
                    checkout();
                    retry = false;
                    clean();
                    break;
                case 3:
                    retry = false;
                    clean();
                    break;
            }

        }
        
    }
    
    public static void checkout()
    {
        checkoutQuery();
        System.out.println("Thanks for your order! Your order number is: " + currOrderNum);
    }
    
    public static void checkOrder()
    {
        clean();
        System.out.println("Please enter your order number.");
        System.out.print("Choice: ");
        int_input();
        orderQuery("SELECT * FROM orders WHERE order_number=" + int_choice);
        System.out.println("Order " + int_choice + " will be delivered " + returnList.get(0) + " to " + returnList2.get(0) + ". Credit Card Used: " + returnList3.get(0));
        System.out.println();
    }
    
    public static void addBook()
    {
        String ISBN, name, author, genre, publisher;
        int quantity, pageCount, rating;
        float cost, returnValue;
        
        System.out.println("Please enter the book's ISBN.");
        System.out.print("Choice: ");
        str_input();
        ISBN = str_choice;
        System.out.println();
        System.out.println("Please enter the book's name.");
        System.out.print("Choice: ");
        str_input();
        name = str_choice;
        System.out.println("Please enter the number of books in stock.");
        System.out.print("Choice: ");
        int_input();
        quantity = int_choice;
        System.out.println("Please enter the book's author.");
        System.out.print("Choice: ");
        str_input();
        author = str_choice;
        System.out.println("Please enter the book's genre.");
        System.out.print("Choice: ");
        str_input();
        genre = str_choice;
        System.out.println("Please enter the book's publisher.");
        System.out.print("Choice: ");
        str_input();
        publisher = str_choice;
        System.out.println("Please enter the book's page count.");
        System.out.print("Choice: ");
        int_input();
        pageCount = int_choice;
        System.out.println("Please enter the book's rating.");
        System.out.print("Choice: ");
        int_input();
        rating = int_choice;
        System.out.println("Please enter the book's cost.");
        System.out.print("Choice: ");
        flt_input();
        cost = flt_choice;
        System.out.println("Please enter the publisher's revenue cut for the book.");
        System.out.print("Choice: ");
        flt_input();
        returnValue = flt_choice;
        
        addBookQuery("INSERT INTO book VALUES ('" + ISBN + "', '" + name + "', " + quantity + ", '" + author + "', '" + genre + "', '" + publisher + "', " + pageCount + ", " + rating + ", " + cost + ", " + returnValue + ")");
        System.out.println("Book added successfully!");
        System.out.println();
    }
    
    public static void removeBook()
    {
        System.out.println("Please enter the book to be removed's ISBN.");
        System.out.print("Choice: ");
        str_input();
        removeBookQuery("DELETE FROM book WHERE isbn='" + str_choice + "'");
        System.out.println("Book removed succesfully!");
        System.out.println();
    }
    
}