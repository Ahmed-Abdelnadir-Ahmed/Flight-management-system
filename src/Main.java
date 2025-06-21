import java.io.IOException;
import java.util.Scanner;
import java.io.File;
//---------------------------------------------------
public class Main{
    public static void main(String[] args) {
        File file=new File("passenger.txt");
        try{
            file.createNewFile();
        }catch(IOException e){
            System.out.println("1");
        }
        file=new File("user.txt");
        try{
            file.createNewFile();
        }catch(IOException e){
            System.out.println("1");
        }
        file=new File("booking.txt");
        try{
            file.createNewFile();
        }catch(IOException e){
            System.out.println("1");
        }
        file = new File("flight.txt");
        try{
            file.createNewFile();
        }catch(IOException e){
            System.out.println("1");
        }
        //--------------------------------------
        bookingsystem sys = new bookingsystem();
        Scanner input = new Scanner(System.in);
        System.out.println("~~~~~~~~~~~~Welcome To Our Flight Management System~~~~~~~~~~~~");
        while (true) {
            System.out.println("what do you want to do ?\n1.log in\n2.create account\n3.exit from the system");
            int choise;
            try{
                choise = input.nextInt();
                input.nextLine();
            }catch(Exception e){
                System.out.println("wrong choise");
                input.nextLine();
                continue;
            }
            if(choise==3){
                break;
            }
            else if(choise==2){
                System.out.println("enter the Required information\n");
                System.out.print("name:");
                String name = input.nextLine();
                boolean pas = true;
                String password = "";
                while (pas) {
                    System.out.print("\npassword:{at least 6}");
                    password = input.nextLine();
                    if (password.length() < 6) {
                        System.out.println("the password should be at least 6 characters and letters");
                    } else {
                        pas = false;
                    }
                }
                System.out.println("\nDate of Birth:");
                String dof = input.nextLine();
                System.out.println("\nemail");
                String email = input.nextLine();
                System.out.println("\ncontact information:");
                String coin = input.nextLine();
                System.out.println("\npreference(you don`t have to enter it if you don`t want to):");
                String prefe = input.nextLine();
                if (prefe.isEmpty()) {
                    prefe = "no preference";
                }
                System.out.println("\naddress");
                String address = input.nextLine();
                sys.createcustomeraccount(dof, name, password, email, coin, prefe, address);
            }
            else if(choise==1){
                System.out.println("Are you\n1.Administrator\n2.Agent\n3.Customer");
                try{
                    choise = input.nextInt();
                    input.nextLine();
                }catch(Exception e){
                    System.out.println("wrong input please try again");
                    input.nextLine();
                    continue;
                }
                System.out.println("Please enter your account name:");
                String name = input.nextLine();
                System.out.println("Please enter your account password:");
                String password = input.nextLine();
                if (!sys.login(choise, name, password)) {
                    continue;
                }
                boolean aftlog = true;
                while (aftlog) {
                    if(choise==3){
                        System.out.println("what do you want to do ?" +
                                "\n1.Search for flights" +
                                "\n2.create a booking" +
                                "\n3.view your booking" +
                                "\n4.cancel a booking" +
                                "\n5.edit your account information" +
                                "\n6.confirm booking" + "\n7.itinerary" +"\n8.Generate Ticket"+ "\n9.log out");
                        int whatto;
                        try{
                            whatto = input.nextInt();
                            input.nextLine();
                        }catch(Exception e){
                            System.out.println("wrong input");
                            input.nextLine();
                            continue;
                        }
                        switch (whatto) {
                            case 1:
                                sys.searchforaflght();
                                break;
                            case 2:
                                while (true) {
                                    System.out.println("enter Flight Number\n{enter -1 if you want to exit this stage}");
                                    int num;
                                    try {
                                        num = input.nextInt();
                                        input.nextLine();
                                        if(!sys.seeifflightexist(num)){
                                            continue;
                                        }else if (num==-1){
                                            break;
                                        }
                                    } catch (Exception e) {
                                        System.out.println("wrong input");
                                        input.nextLine();
                                        continue;
                                    }
                                    String seat;
                                    while (true) {
                                        System.out.println("choose your seat class\n1.Economy\n2.Business\n3.First Class");
                                        int clas;
                                        try {
                                            clas = input.nextInt();
                                            input.nextLine();
                                        } catch (Exception e) {
                                            System.out.println("wrong input");
                                            input.nextLine();
                                            continue;
                                        }
                                        if (clas == 1) {
                                            seat = "economy";
                                        } else if (clas == 2) {
                                            seat = "business";
                                        } else if (clas == 3) {
                                            seat = "first class";
                                        } else{
                                            System.out.println("wrong choise");
                                            continue;
                                        }
                                        break;
                                    }
                                    System.out.println("enter your passport number");
                                    int pass;
                                    try {
                                        pass = input.nextInt();
                                        input.nextLine();
                                    } catch (Exception e) {
                                        System.out.println("wrong input");
                                        input.nextLine();
                                        continue;
                                    }
                                    sys.createbooking(num, seat, pass);
                                    System.out.println("your booking has been created and saved");
                                    break;
                                }
                                break;
                            case 3:
                                sys.customer.viewbooking();
                                break;
                            case 4:
                                while (true) {
                                    System.out.println("enter your passport id:");
                                    int passid;
                                    try {
                                        passid = input.nextInt();
                                        input.nextLine();
                                    } catch (Exception e) {
                                        System.out.println("wrong input");
                                        input.nextLine();
                                        continue;
                                    }
                                    System.out.println("enter the flight number:");
                                    int flightnum;
                                    try {
                                        flightnum = input.nextInt();
                                        input.nextLine();
                                    } catch (Exception e) {
                                        System.out.println("wrong input");
                                        input.nextLine();
                                        continue;
                                    }
                                    sys.cancelbooking(passid,flightnum);
                                    break;
                                }
                                break;
                            case 5:
                                System.out.println("your account information:");
                                System.out.println(sys.customer.returndetail());
                                System.out.println("enter your new detail\n new name:");
                                name = input.nextLine();
                                //password="";
                                while (true) {
                                    System.out.print("\nnew password:{at least 6}");
                                    password = input.nextLine();
                                    if (password.length() < 6) {
                                        System.out.println("the password should be at least 6 characters and letters");
                                    } else {
                                        break;
                                    }
                                }
                                System.out.println("\nDate of Birth:");
                                String dof = input.nextLine();
                                System.out.println("\nemail");
                                String email = input.nextLine();
                                System.out.println("\ncontact information:");
                                String coin = input.nextLine();
                                System.out.println("\npreference(you don`t have to enter it if you don`t want to):");
                                String prefe = input.nextLine();
                                if (prefe.isEmpty()) {
                                    prefe = "no preference";
                                }
                                System.out.println("\naddress");
                                String address = input.nextLine();
                                sys.customer.updateprofile(name, password, email, coin);
                                sys.customer.updatecustomerinfo(prefe, address, dof);
                                System.out.println("your account information has been successfully edited");
                                System.out.println(sys.customer.returndetail());
                                break;
                            case 6:
                                System.out.println("please enter the method you want to use for paying:\n1.credit card\n2.bank transfer\n3.cash\n4.visa");
                                String method = input.nextLine();
                                while (true){
                                    int flightnum;
                                    while (true){
                                        System.out.println("enter the flight number\n{enter -1 to exit this stage}");
                                        try {
                                            flightnum = input.nextInt();
                                            input.nextLine();
                                        } catch (Exception e) {
                                            System.out.println("wrong input");
                                            input.nextLine();
                                            continue;
                                        }
                                        if(flightnum==-1){
                                            break;
                                        }
                                        if (!sys.seeifflightexist(flightnum)) {
                                            System.out.println("please try again");
                                        }
                                        else{
                                            break;
                                        }
                                    }
                                    int passportid;
                                    while (true){
                                        System.out.println("enter your passport id");
                                        try {
                                            passportid = input.nextInt();
                                            input.nextLine();
                                            break;
                                        } catch (Exception e) {
                                            System.out.println("wrong input");
                                            input.nextLine();
                                        }
                                    }
                                    sys.confirmbooking(method,passportid,flightnum);
                                    break;
                                }
                                break;
                            case 7:
                                while (true) {
                                    int flightnum;
                                    while (true) {
                                        System.out.println("enter the flight number");
                                        try {
                                            flightnum = input.nextInt();
                                            input.nextLine();
                                        } catch (Exception e) {
                                            System.out.println("wrong input");
                                            input.nextLine();
                                            continue;
                                        }
                                        if (!sys.seeifflightexist(flightnum)) {
                                            System.out.println("please try again");
                                        } else {
                                            break;
                                        }
                                    }
                                    int passportid;
                                    while (true) {
                                        System.out.println("enter your passport id");
                                        try {
                                            passportid = input.nextInt();
                                            input.nextLine();
                                            break;
                                        } catch (Exception e) {
                                            System.out.println("wrong input");
                                            input.nextLine();
                                        }
                                    }
                                    try {
                                        if (sys.itinerary(passportid, flightnum)) {
                                            break;
                                        }
                                    }catch(Exception o){
                                        System.out.println("wrong flight number or passport ID");
                                        break;
                                    }
                                }
                                break;
                            case 8:
                                while (true) {
                                    int flightnum;
                                    while (true){
                                        System.out.println("enter the flight number\n{enter -1 to exit this stage}");
                                        try {
                                            flightnum = input.nextInt();
                                            input.nextLine();
                                        } catch (Exception e) {
                                            System.out.println("wrong input");
                                            input.nextLine();
                                            continue;
                                        }
                                        if(flightnum==-1){
                                            break;
                                        }
                                        if (!sys.seeifflightexist(flightnum)) {
                                            System.out.println("please try again");
                                        }
                                        else{
                                            break;
                                        }
                                    }
                                    int passportid;
                                    while (true) {
                                        System.out.println("enter your passport id");
                                        try {
                                            passportid = input.nextInt();
                                            input.nextLine();
                                            break;
                                        } catch (Exception e) {
                                            System.out.println("wrong input");
                                            input.nextLine();
                                        }
                                    }
                                    sys.generateticket(passportid,flightnum);
                                    break;
                                }
                                break;
                            case 9:
                                sys.logout();
                                aftlog = false;
                                break;
                            default:
                                System.out.println("wrong choise");
                        }
                    }
                    else if(choise==2){
                        System.out.println("what do you want to do ?" +
                                "\n1.createflight" +
                                "\n2.createbookingforcustomer" +
                                "\n3.modifybookin" +
                                "\n4.manageflight" +
                                "\n5.generateflightreport" +
                                "\n6.generatecustomerreport" + "\n7.seeflights" + "\n8.seecustomers" + "\n9.logout");
                        int whatagentdo;
                        try {
                            whatagentdo = input.nextInt();
                            input.nextLine();
                        } catch (Exception e) {
                            System.out.println("wrong input");
                            input.nextLine();
                            continue;
                        }
                        switch (whatagentdo) {
                            case 1:
                                int flightnum;
                                while (true) {
                                    System.out.println("enter flight number:\n(don`t make it 0)");
                                    try {
                                        flightnum = input.nextInt();
                                        input.nextLine();
                                        if (!sys.notduplicateflightnum(flightnum)) {
                                            System.out.println("this flight number already exists try another one");
                                            continue;
                                        }
                                    } catch (Exception e) {
                                        System.out.println("wrong input");
                                        input.nextLine();
                                        continue;
                                    }
                                    break;
                                }
                                int price;
                                while (true) {
                                    System.out.println("enter the flight price:");
                                    try {
                                        price = input.nextInt();
                                        input.nextLine();
                                    } catch (Exception e) {
                                        System.out.println("wrong input");
                                        input.nextLine();
                                        continue;
                                    }
                                    break;
                                }
                                System.out.println("enter the flight depaturetime:");
                                String depature = input.nextLine();
                                System.out.println("enter the flight arrival time:");
                                String arriv = input.nextLine();
                                System.out.println("choose and write the airline:\n1.egypt airline\n2.syria airline\n3.qatar airline\n4.mahrosa airline\n(you can write other airline)");
                                String airl = input.nextLine();
                                System.out.println("choose and write the destination\n1.egypt\n2.sudan\n3.lybia\n4.syria\n(other countries or cites)");
                                String dest = input.nextLine();
                                System.out.println("write the plane origin\n1.domestic\n2.international");
                                String ori = input.nextLine();
                                sys.createflight(flightnum, price, depature, arriv, airl, dest, ori);
                                break;
                            case 2:
                                int id;
                                while (true) {
                                    System.out.println("enter the customer id");
                                    try {
                                        id = input.nextInt();
                                        input.nextLine();
                                        if (sys.searchforcustomer(id) == null) {
                                            System.out.println("there isn`t any customer with such a number");
                                            continue;
                                        }
                                    } catch (Exception e) {
                                        System.out.println("wrong input");
                                        input.nextLine();
                                        continue;
                                    }
                                    break;
                                }
                                while (true) {
                                    System.out.println("enter the flight number:");
                                    try {
                                        flightnum = input.nextInt();
                                        input.nextLine();
                                        if (!sys.seeifflightexist(flightnum)) {
                                            continue;
                                        }
                                    } catch (Exception e) {
                                        System.out.println("wrong input");
                                        input.nextLine();
                                        continue;
                                    }
                                    break;
                                }
                                String seat;
                                while (true) {
                                    System.out.println("choose your seat class\n1.Economy\n2.Business\n3.First Class");
                                    int clas;
                                    try {
                                        clas = input.nextInt();
                                        input.nextLine();
                                    } catch (Exception e) {
                                        System.out.println("wrong input");
                                        input.nextLine();
                                        continue;
                                    }
                                    if (clas == 1) {
                                        seat = "economy";
                                    } else if (clas == 2) {
                                        seat = "business";
                                    } else if (clas == 3) {
                                        seat = "firstclass";
                                    } else {
                                        System.out.println("wrong choise");
                                        continue;
                                    }
                                    break;
                                }
                                int passid;
                                while (true) {
                                    System.out.println("enter the passport id");
                                    try {
                                        passid = input.nextInt();
                                        input.nextLine();
                                    } catch (Exception e) {
                                        System.out.println("wrong input");
                                        input.nextLine();
                                        continue;
                                    }
                                    break;
                                }
                                sys.createbookingforcustomer(id, flightnum, seat, passid);
                                break;
                            case 3:
                                while (true) {
                                    System.out.println("enter Flight Number");
                                    int num;
                                    try {
                                        num = input.nextInt();
                                        input.nextLine();
                                    } catch (Exception e) {
                                        System.out.println("wrong input");
                                        input.nextLine();
                                        continue;
                                    }
                                    while (true) {
                                        System.out.println("choose your seat class\n1.Economy\n2.Business\n3.First Class");
                                        int clas;
                                        try {
                                            clas = input.nextInt();
                                            input.nextLine();
                                        } catch (Exception e) {
                                            System.out.println("wrong input");
                                            input.nextLine();
                                            continue;
                                        }
                                        if (clas == 1) {
                                            seat = "economy";
                                        } else if (clas == 2) {
                                            seat = "business";
                                        } else if (clas == 3) {
                                            seat = "firstclass";
                                        } else {
                                            System.out.println("wrong choise");
                                            continue;
                                        }
                                        break;
                                    }
                                    System.out.println("enter your passport number");
                                    int pass;
                                    try{
                                        pass = input.nextInt();
                                        input.nextLine();
                                    }catch(Exception e){
                                        System.out.println("wrong input");
                                        input.nextLine();
                                        continue;
                                    }
                                    while(true){
                                        System.out.println("enter the customer id");
                                        try {
                                            id = input.nextInt();
                                            input.nextLine();
                                            if (sys.searchforcustomer(id) == null) {
                                                System.out.println("there isn`t any customer with such a number");
                                                continue;
                                            }
                                        } catch (Exception e) {
                                            System.out.println("wrong input");
                                            input.nextLine();
                                            continue;
                                        }
                                        break;
                                    }
                                    System.out.println("enter the booking status:\n{Confirmed,Reserved}");
                                    String bookstat = input.nextLine();
                                    System.out.println("enter the payment status:\n{Confirmed,not confirmed yet,Reserved}");
                                    String paystat = input.nextLine();
                                    sys.modifybookin(id, pass, num, bookstat, paystat);
                                    System.out.println("the booking has been modified");
                                    break;
                                }
                                break;
                            case 4:
                                while (true) {
                                    System.out.println("enter the flight number:");
                                    try {
                                        flightnum = input.nextInt();
                                        input.nextLine();
                                        if (!sys.seeifflightexist(flightnum)) {
                                            continue;
                                        }
                                    } catch (Exception e) {
                                        System.out.println("wrong input");
                                        input.nextLine();
                                        continue;
                                    }
                                    break;
                                }
                                while (true) {
                                    System.out.println("enter the flight price:");
                                    try {
                                        price = input.nextInt();
                                        input.nextLine();
                                    } catch (Exception e) {
                                        System.out.println("wrong input");
                                        input.nextLine();
                                        continue;
                                    }
                                    break;
                                }
                                System.out.println("enter the flight depaturetime:");
                                depature = input.nextLine();
                                System.out.println("enter the flight arrival time:");
                                arriv = input.nextLine();
                                System.out.println("choose and write the airline:\n1.egypt airline\n2.syria airline\n3.qatar airline\n4.mahrosa airline\n(you can write other airline)");
                                airl = input.nextLine();
                                System.out.println("choose and write the destination\n1.egypt\n2.sudan\n3.lybia\nstria\n(other countries or cites)");
                                dest = input.nextLine();
                                System.out.println("write the plane origin\n1.domestic\n2.international");
                                ori = input.nextLine();
                                sys.manageflight(flightnum, price, depature, arriv, airl, dest, ori);
                                break;
                            case 5:
                                while (true) {
                                    System.out.println("enter the flight number:\n{if you want to leave this operation enter -1}");
                                    try {
                                        flightnum = input.nextInt();
                                        input.nextLine();
                                        if (flightnum == -1) {
                                            break;
                                        }
                                        if (!sys.seeifflightexist(flightnum)) {
                                            continue;
                                        }
                                    } catch (Exception e) {
                                        System.out.println("wrong input");
                                        input.nextLine();
                                        continue;
                                    }
                                    break;
                                }
                                sys.generateflightreport(flightnum);
                                break;
                            case 6:
                                while (true) {
                                    System.out.println("enter the customer id\n{if you want to leave this operation enter -1}");
                                    try {
                                        id = input.nextInt();
                                        input.nextLine();
                                        if (id == (-1)) {
                                            break;
                                        }
                                        if (sys.searchforcustomer(id) == null) {
                                            System.out.println("there isn`t any customer with such a number");
                                            continue;
                                        }
                                    } catch (Exception e) {
                                        System.out.println("wrong input");
                                        input.nextLine();
                                        continue;
                                    }
                                    break;
                                }
                                sys.generatecustomerreport(id);
                                break;
                            case 7:
                                sys.seeflights();
                                break;
                            case 8:
                                sys.seecustomers();
                                break;
                            case 9:
                                sys.logout();
                                aftlog = false;
                                break;
                            default:
                                System.out.println("wrong choise");
                        }
                    }
                    else if(choise==1){
                        System.out.println("what do you want to do ?"
                        +"\n1.create an agent account"
                        +"\n2.view all logs on the system"
                        +"\n3.show all passengers on the system"
                        +"\n4.show all flights on the system"
                        +"\n5.show all booking on the system"
                        +"\n6.log out");
                        int whatagentdo;
                        try{
                            whatagentdo = input.nextInt();
                            input.nextLine();
                        }catch(Exception e){
                            System.out.println("wrong input");
                            input.nextLine();
                            continue;
                        }
                        switch(whatagentdo){
                            case 1:
                                System.out.println("enter the Required information\n");
                                System.out.print("name:");
                                name = input.nextLine();
                                boolean pas = true;
                                password = "";
                                while (pas) {
                                    System.out.print("\npassword:{at least 6}");
                                    password = input.nextLine();
                                    if (password.length() < 6) {
                                        System.out.println("the password should be at least 6 characters and letters");
                                    } else {
                                        pas = false;
                                    }
                                }
                                System.out.println("\nemail");
                                String email = input.nextLine();
                                System.out.println("\ncontact information:");
                                String coin = input.nextLine();
                                System.out.println("\ndepartment:");
                                String dep = input.nextLine();
                                System.out.println("\ncommission:");
                                String com = input.nextLine();
                                sys.createagent(name,password,email,coin,com,dep);
                                break;
                            case 2:
                                sys.viewsystemlogs();
                                break;
                            case 3:
                                sys.showallpassengers();
                                break;
                            case 4:
                                sys.showallflights();
                                break;
                            case 5:
                                sys.showallbooking();
                                break;
                            case 6:
                                sys.logout();
                                aftlog=false;
                                break;
                            default:
                                System.out.println("wrong choise");
                                break;
                        }
                    }
                    else {
                        aftlog = false;
                    }
                }
            }
        }
        input.close();
        System.out.println("by");
    }
}