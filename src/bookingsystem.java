import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class bookingsystem {
    filemanager filemanager=new filemanager();
    private passengerlinkedlist systempassengers =new passengerlinkedlist();
    private flightlinkedlist systemflights=new flightlinkedlist();
    private ArrayList<customer>cust=new ArrayList<customer>();
    private ArrayList<user>users=new ArrayList<user>();
    private ArrayList<booking>boo=new ArrayList<booking>();
    private ArrayList<agent>ag=new ArrayList<agent>();
    private ArrayList<Integer>flightnums=new ArrayList<Integer>();
    private agent agent;
    customer customer;
    private agent agen=new agent("anas", "anas19", "myemail@gmail.com", "111", "create flight and manage customer booking", "flight management");
    private administrator admin=new administrator(5,3,"ahmed","ahmed19","study1@gmail.com","01119211369");
    private administrator sysadmin;
    public boolean login(int us,String name,String password){
        ag.add(agen);
        switch(us){
            case 1:
                if(admin.login(name,password)){
                    sysadmin=admin;
                    return true;
                }
                return false;
            case 2:
                for(int i=0;i<ag.size();i++){
                    if(ag.get(i).login(name,password)){
                        System.out.println("welcome "+name);
                        agent=ag.get(i);
                        return true;
                    }
                }
                System.out.println("wrong name or password");
                return false;
            case 3:
                for(int i=0;i<cust.size();i++){
                    if(cust.get(i).login(name,password)){
                        System.out.println("welcome "+name);
                        customer=cust.get(i);
                        return true;
                    }
                }
                System.out.println("wrong name or password");
                return false;
            default:
                System.out.println("wrong choise");
                return false;
        }
    }
    public void logout(){
        customer=null;
        agent=null;
        sysadmin=null;
    }
    public customer searchforcustomer(int customerid){
        for(int i=0;i<cust.size();i++){
            if(cust.get(i).returcustomerid()==customerid){
                return cust.get(i);
            }
        }
        return null;
    }
    public void createagent(String username,String password, String email, String contactinfo,String commission,String department){
        users.add(admin);
        agent=new agent(username,password,email,contactinfo,commission,department);
        ag.add(agent);
        users.add(agent);
        filemanager.saveuser(users);
    }
    public boolean notduplicateflightnum(int flightnum){
        for(int i=0;i<flightnums.size();i++){
            if(flightnum==flightnums.get(i)){
                return false;
            }
        }
        return true;
    }
    //------------------------------------------------------------------------
    //agent
    public void createflight(int flightnum,int price,String depaturetime,String arrivailtime,String airl,String dest,String ori){
            flightnums.add(flightnum);
            agent.createflight(flightnum,price,depaturetime,arrivailtime,airl,dest,ori);
            systemflights.addflight(agent.flight);
            System.out.println("flight has been successfully added");
            filemanager.saveflight(systemflights);
    }
    public void createbookingforcustomer(int customerid,int flightnum,String seatclass,int passportid){
        agent.createbookingforcustomer(searchforcustomer(customerid),systemflights.searchflightbynum(flightnum).info,seatclass,passportid);
    }
    public void modifybookin(int customerid,int passportnum,int flightnum,String bookingstatus,String paymentstatus){
        if(searchforcustomer(customerid)==null){
            System.out.println("wrong customer id");
            return;
        }
        if(searchforcustomer(customerid).searchforbooking(passportnum,flightnum)==null){
            System.out.println("wrong passport number or flight number");
            return;
        }
        agent.modifybookin(searchforcustomer(customerid).searchforbooking(passportnum,flightnum),bookingstatus,paymentstatus);
    }
    public void manageflight(int flightnum,int price,String depaturetime,String arrivailtime,String airl,String dest,String ori){
        if(systemflights.searchflightbynum(flightnum)==null){
            System.out.println("wrong flight number");
            return;
        }
        agent.manageflight(systemflights.searchflightbynum(flightnum).info,price,depaturetime,arrivailtime,airl,dest,ori);
    }
    public void generateflightreport(int flightnum){
        if(systemflights.searchflightbynum(flightnum)==null){
            System.out.println("wrong flight number");
            return;
        }
        agent.generateflightreport(systemflights.searchflightbynum(flightnum).info);
    }
    public void generatecustomerreport(int customerid){
        if(searchforcustomer(customerid)==null){
            System.out.println("wrong customer id");
            return;
        }
        agent.generatecustomerreport(searchforcustomer(customerid));
    }
    public void seeflights(){
        systemflights.printall();
    }
    public void seecustomers(){
        if(cust.getFirst()==null){
            System.out.println("there is no customer yet");
            return;
        }
        for(int i=0;i<cust.size();i++){
            System.out.println(cust.get(i).cutomerandbookdetail());
        }

    }
    //------------------------------------------------------------------------
    //customer
    public void createcustomeraccount(String dateofbirth,String username, String password, String email, String contactinfo,String preferince,String address){
        customer=new customer(dateofbirth,username,password,email,contactinfo,preferince,address);
        customer.createcustomeraccount(username,password);
        cust.add(customer);
        users.add(customer);
        filemanager.saveuser(users);
        System.out.println("your account\n"+customer.returndetail());
    }
    public void searchforaflght(){
        systemflights.printall();
    }
    public void createbooking(int flightnum,String seatclass,int passnum){
        if(systemflights.searchflightbynum(flightnum).info==null){
            System.out.println("there is no flight with such number");
            return;
        }
        customer.createbooking(systemflights.searchflightbynum(flightnum).info,seatclass,passnum);
        systempassengers.addpassenger(customer.booking.passenger);
        filemanager.savepassenger(systempassengers);
        boo.add(customer.booking);
        filemanager.savebooking(boo);
    }
    public void viewbooking(){
        customer.viewbooking();
    }
    public void cancelbooking(int passport,int flightnum){
        customer.canselbooking(passport,flightnum);
    }
    public boolean confirmbooking(String method,int passport,int flightnum){
        return customer.confirmbooking(method,passport,flightnum);
    }
    public boolean itinerary(int passport,int flightnum){
        for(int i=0;i<customer.bookinghistory.size();i++){
            if((customer.bookinghistory.get(i).passenger.getPassportid()==passport)&&(customer.bookinghistory.get(i).thisbookflight.getFlightnum()==flightnum)){
                customer.bookinghistory.get(i).generateitinerary();
                return true;
            }
        }
        System.out.println("couldn`t find the booking\nyou probably entered a wrong passport id or flight number");
        return false;
    }
    public void generateticket(int passport,int flightnum){
        customer.generateticket(passport, flightnum);
    }
    //------------------------------------------------------------------------
    //flight
    public boolean seeifflightexist(int flightnumber){
        if(systemflights.searchflightbynum(flightnumber)==null){
            System.out.println("there isn`t any flight with such a number");
            return false;
        }else{
            return true;
        }
    }
    //------------------------------------------------------------------------
    //administrator
    public void createuser(String username,String password,String email,String contactinfo,String commission,String department){
        sysadmin.createuser(username,password,email,contactinfo,commission,department);
        users.add(sysadmin.ag);
        ag.add(sysadmin.ag);
        filemanager.saveuser(users);
    }
    public void viewsystemlogs(){
        sysadmin.viewsystemlogs();
    }
    public void showallpassengers(){
        filemanager.loadpassenger();
    }
    public void showallbooking(){
        filemanager.loadbooking();
    }
    public void showallflights(){
        filemanager.loadflight();
    }
}

abstract class user {
    protected String username,password,email,contactinfo;
    private static int user;
    protected int userid;
    public user(String username, String password, String email, String contactinfo) {
        this.username = username;
        userid=user;
        user++;
        this.password = password;
        this.email = email;
        this.contactinfo = contactinfo;
    }
    public abstract boolean login(String username,String Password);
    public abstract void logout();
    public abstract void updateprofile(String username, String password, String email, String contactinfo);
    public abstract String returndetail();
}

class administrator extends user {
    private int securitylvl,adminid;
    agent ag;
    private filemanager file=new filemanager();
    public administrator(int adminid,int securitylvl,String username,String password,String email,String contactinfo) {
        super(username,password,email,contactinfo);
        this.adminid = adminid;
        this.securitylvl = securitylvl;
    }
    @Override
    public boolean login(String name,String password){
        return ((this.username.equals(name))&&(this.password.equals(password)));
    }
    @Override
    public void logout(){}
    @Override
    public void updateprofile(String username,String password,String email,String contactinfo){
        this.username=username;
        this.password=password;
        this.email=email;
        this.contactinfo=contactinfo;
    }
    @Override
    public String returndetail(){
        return ("administrator name:"+username+"\nadministrator password:"+password+"\nadministrator ID:"+adminid+"\nemail:"+email+"\ncontact information:"+contactinfo+"\nuser ID:"+userid+"\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }
    public void createuser(String username,String password, String email, String contactinfo,String commission,String department){
        ag=new agent(username,password,email,contactinfo,commission,department);
        System.out.println("agent has been added successfully");
    }
    public void viewsystemlogs(){
        file.loaduser();
    }
    public void modifysystemsitting(){}
    public void manageuseraccess(){}
}

class agent extends user {
    private int agentid;
    private static int aid=240;
    private String department,commission;
    flight flight;
    public agent(String username,String password, String email, String contactinfo,String commission,String department){
        super(username,password,email,contactinfo);
        this.agentid=aid;
        aid++;
        this.department = department;
        this.commission = commission;
    }
    @Override
    public boolean login(String username,String Password){
        return ((this.username.equals(username))&&(this.password.equals(password)));
    }
    @Override
    public void logout(){

    }
    @Override
    public void updateprofile(String username, String password, String email, String contactinfo){
        this.username=username;
        this.userid=userid;
        this.password=password;
        this.email=email;
        this.contactinfo=contactinfo;
    }
    public void updateagent(int agentid,String commission,String department){
        this.agentid = agentid;
        this.department = department;
        this.commission = commission;
    }
    @Override
    public String returndetail(){
        String detail=("agent name:"+username+"\npassword:"+password+"\nemail"+email+"\ncontact information:"+contactinfo+"\nagent id:"+agentid+"\nagent commission:"+commission+"\nagent department:"+department+"\n~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        return detail;
    }
    public flight createflight(int flightnum,int price,String depaturetime,String arrivailtime,String airl,String dest,String ori){
        flight=new flight(flightnum,price,depaturetime,arrivailtime,airl,dest,ori);
        return flight;
    }
    public void createbookingforcustomer(customer cust,flight cflight,String seatclass,int passportid){
        if(cust==null){
            System.out.println("wrong customer id");
            return;
        }
        if(flight==null){
            System.out.println("wrong flight id");
            return;
        }
        cust.createbooking(cflight,seatclass,passportid);
    }
    public void modifybookin(booking book,String bookingstatus,String paymentstatus){
        book.confirmbooking(bookingstatus,paymentstatus);
    }
    public void manageflight(flight flight,int price,String depaturetime,String arrivailtime,String airl,String dest,String ori){
        flight.editflightinfo(price,depaturetime,arrivailtime,airl,dest,ori);
    }
    public void generateflightreport(flight flight){
        flight.flightdetail();
    }
    public void generatecustomerreport(customer customer){
        System.out.println(customer.cutomerandbookdetail());
    }
}

class customer extends user{
    private static int id=240124;
    booking booking;
    ArrayList<booking> bookinghistory =new ArrayList<booking>();
    private int customerid;
    private String preferince,address,dateofbirth;
    public customer(String dateofbirth,String username, String password, String email, String contactinfo,String preferince,String address){
        super(username,password,email,contactinfo);
        this.customerid=id;
        id++;
        this.address=address;
        this.preferince=preferince;
        this.dateofbirth=dateofbirth;
    }
    public void createcustomeraccount(String name,String password){
        this.username=name;
        this.password=password;
    }
    @Override
    public boolean login(String name,String password){
        return (this.username.equals(name))&&(this.password.equals(password));
    }
    @Override
    public void logout(){}
    @Override
    public void updateprofile(String username, String password, String email, String contactinfo){
        this.username=username;
        this.password=password;
        this.email=email;
        this.contactinfo=contactinfo;
    }
    public void updatecustomerinfo(String preferince,String address,String dof){
        this.address=address;
        this.preferince=preferince;
        this.dateofbirth=dof;
    }
    public void createbooking(flight flight,String seatclass,int passportnum){
        if(flight==null){
            System.out.println("wrong flight number");
            return;
        }
        booking=new booking(flight,seatclass,username,dateofbirth,customerid,passportnum,preferince);
        bookinghistory.add(booking);
    }
    public void searchflight(flightlinkedlist fli,int flightnum){
        flightnode current=fli.head;
        while(current!=null){
            if(current.info.getFlightnum()==flightnum){
                current.info.flightdetail();
            }
            current=current.next;
        }
    }
    public void viewbooking(){
        if(bookinghistory.get(0)!=null){
            for(int i=0;i<bookinghistory.size();i++){
                booking current=bookinghistory.get(i);
                current.bookingdetail();
            }
        }else{
            System.out.println("tere isn`t ant booking yet");
        }
    }
    public void canselbooking(int passportid,int flightnum){
        for(int i=0;i<bookinghistory.size();i++){
            if((bookinghistory.get(i).passenger.getPassportid()== passportid)&&(bookinghistory.get(i).thisbookflight.getFlightnum()==flightnum)){
                bookinghistory.remove(i);
                booking.cancelbooking();
                System.out.println("the booking has been deleted successfully");
                return;
            }
        }
        System.out.println("couldn`t find the booking\nmaybe you entered a wrong Passport Id or Flight Number");
    }
    public boolean confirmbooking(String methodofpay,int passportid,int flightnum){
        for(int i=0;i<bookinghistory.size();i++){
            if((bookinghistory.get(i).passenger.getPassportid()== passportid)&&(bookinghistory.get(i).thisbookflight.getFlightnum()==flightnum)){
                bookinghistory.get(i).pay(methodofpay);
                System.out.println("the booking has been confirmed successfully");
                bookinghistory.get(i).generateticket();
                return true;
            }
        }
        System.out.println("couldn`t find the booking\nyou probably entered a wrong passport id or flight number");
        return false;
    }
    public String cutomerandbookdetail(){
        String custdetail="";
        custdetail+=("Customer Name:"+username+"\nPassword:"+password+"\nEmail:"+email+"\nContact Information:"+contactinfo+"\nUserid:"+userid+"\nCustomer Id:"+customerid+"\nPreferince:"+preferince+"\nAddress:"+address+"\nDate of Birth:"+dateofbirth);
        custdetail+=booking.returnbookingdetail();
        return custdetail;
    }
    @Override
    public String returndetail(){
        String custdetail=("Customer Name:"+username+"\nPassword:"+password+"\nEmail:"+email+"\nContact Information:"+contactinfo+"\nUserid:"+userid+"\nCustomer Id:"+customerid+"\nPreferince:"+preferince+"\nAddress:"+address+"\nDate of Birth:"+dateofbirth+"\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        return custdetail;
    }
    public int returcustomerid(){
        return customerid;
    }
    public booking searchforbooking(int passportid,int flightnum){
        for(int i=0;i<bookinghistory.size();i++){
            if((bookinghistory.get(i).passenger.getPassportid()== passportid)&&(bookinghistory.get(i).thisbookflight.getFlightnum()==flightnum)){
                return bookinghistory.get(i);
            }
        }
        return null;
    }
    public void generateticket(int passportid,int flightnum){
        booking=searchforbooking(passportid,flightnum);
        booking.pay.validatepaymentdetail(booking.seatclass);
    }
}

class seat{
    protected String seatclass;
    public seat(){}
    public seat(String choise){
        seatclass=choise;
    }
}
class flight extends seat{
    private seat seat;
    ArrayList<seat> seats=new ArrayList<seat>(80);
    private int flightnum,price,avilableseats=80,econum=50,businnum=20,fistnum=10;
    private String depaturetime,arrivailtime;
    private String origin,airline,destination;
    public flight(){}
    public flight(int flightnum,int price,String depaturetime,String arrivailtime,String airl,String dest,String ori){
        this.flightnum=flightnum;
        this.price=price;
        this.depaturetime=depaturetime;
        this.arrivailtime=arrivailtime;
        this.origin=ori;
        this.airline=airl;
        this.destination=dest;
    }
    public void updateschedule(String depaturetime,String arrivailtime){
        this.depaturetime=depaturetime;
        this.arrivailtime=arrivailtime;
    }
    public int getFlightnum(){
        return flightnum;
    }
    public int calculateprice(String seatclass){
        switch(seatclass){
            case "economy":
                return price;
            case "business":
                price+=(price/2);
                return price;
            case "firstclass":
                price*=2;
                return price;
            default:
                return 0;
        }

    }
    public void checkavilbilaty(){
        if(avilableseats==0){
            System.out.println("there isn`t any seat Available");
            return;
        }
        if(isstillEconum())
            System.out.println("there is still "+econum+" seat/s Available in economy class");
        else
            System.out.println("there is no economy class seats Available");
        if(isstillBusinnum())
            System.out.println("there is still "+businnum+" seat/s Available in business class");
        else
            System.out.println("there is no business class seats Available");
        if(isstillFistnum())
            System.out.println("there is still "+flightnum+" seat/s Available in First class");
        else
            System.out.println("there is no First class seats Available");
    }
    public void flightdetail(){
        System.out.println("Flight Number:"+flightnum+"\nPrice:"+price+"\nAirline:"+airline+"\nDestination:"+destination+"\nFlight Origin:"+origin+"\nDepature Time:"+depaturetime+"\nArrival Time:"+arrivailtime+"\n~~~~~~Availability~~~~~~");
        checkavilbilaty();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
    }
    public void addseat(seat s){
        seats.add(s);
        avilableseats--;
        switch(s.seatclass){
            case "economy":
                econum--;
                break;
            case "business":
                businnum--;
                break;
            case "firstClass":
                fistnum--;
                break;
            }
    }
    public boolean isstillEconum(){
        if(econum<=50){
            return true;
        }else{
            return false;
        }
    }
    public boolean isstillBusinnum() {
        if(businnum<=20){
            return true;
        }else{
            return false;
        }
    }
    public boolean isstillFistnum(){
        if(fistnum<=10){
            return true;
        }else{
            return false;
        }
    }
    public void deletseat(seat s){
        for(int i=0;i<seats.size();i++){
            if(seats.get(i).equals(s.seatclass)){
                seats.remove(i);
                avilableseats++;
                switch(s.seatclass){
                    case "economy":
                        econum--;
                        break;
                    case "business":
                        businnum--;
                        break;
                    case "firstClass":
                        fistnum--;
                        break;
                }



            }

        }


    }
    public void generateitinerary(){
        System.out.println("the plane trip will move at "+depaturetime+" and will arrive in "+destination+" at "+arrivailtime+" on "+airline);
    }
    public int returnprice(){
        return price;
    }
    public String returnflightdetail(){
        String flightdetail="";
        flightdetail+=("Flight Number:"+flightnum+"\nPrice:"+price+"\nAirline:"+airline+"\nDestination:"+destination+"\nFlight Origin:"+origin+"\nDeparture Time:"+depaturetime+"\nArrival Time:"+arrivailtime);
        flightdetail+=("\n~~~~~~~~~~~~~~~~~~~~~~");
        return flightdetail;
    }
    public void editflightinfo(int price,String depaturetime,String arrivailtime,String airl,String dest,String ori){
        this.price=price;
        updateschedule(depaturetime,arrivailtime);
        this.origin=ori;
        this.airline=airl;
        this.destination=dest;
    }
    public void reserveseat(){}
}

class passenger {
    private int passportid,passportnum;
    private String name,dateofbirth,specialrequest;
    public int getPassportid(){
        return passportnum;
    }
    public passenger(){}
    public passenger(String name,String dateofbirth,int passid,int passnum){
    this.passportid=passid;
    this.passportnum=passnum;
    this.name=name;
    this.dateofbirth=dateofbirth;
    this.specialrequest="No Special Requests";
    }
    public passenger(String name,String dateofbirth,int passid,int passnum,String specialrequest){
        this.passportid=passid;
        this.passportnum=passnum;
        this.name=name;
        this.dateofbirth=dateofbirth;
        this.specialrequest=specialrequest;
    }
    public void updateinfo(String name,String dateofbirth,String specialrequest,int passid,int passnum){
        this.passportid=passid;
        this.passportnum=passnum;
        this.name=name;
        this.dateofbirth=dateofbirth;
        this.specialrequest=specialrequest;
    }
    public void getpassengerdetail(){
        System.out.println("Passenger Name:"+name+"\nPassenger ID:"+passportid+"\nPassport Number:"+passportnum+"\nPassenger Birth Date:"+dateofbirth+"\nPassenger Special Requests:"+specialrequest);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
    }
    public String returnpassengerdetail(){
        return ("Passenger Name:"+name+"\nPassenger ID:"+passportid+"\nPassport Number:"+passportnum+"\nPassenger Birth Date:"+dateofbirth+"\nPassenger Special Requests:"+specialrequest+"\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }
}

class payment{
    private int paymentid,amount;
    private String transicationdate,method,status;
    private int bookingreference;
    public payment(){
        status="Reserved";
    }
    public payment(int id,int amount,String method,String date){
        paymentid=id;
        this.amount=amount;
        this.method=method;
        transicationdate=date;
        bookingreference=id*amount%24;

    }
    public void payproccess(int id,int amount,String method,String date){
        updatestatus(id,amount,method,date);
        status="Confirmed";
    }
    public void validatepaymentdetail(String seatClass){
        System.out.println("\n---------- TICKET ----------");
        switch (seatClass) {
            case "economy":
                System.out.println("====== ECONOMY TICKET ======");
                System.out.println("|        * ECONOMY *       |");
                System.out.println("| You paid: "+amount+" pounds     |");
                System.out.println("| Date: "+transicationdate+ "  |");
                System.out.println("| Payment Method: "+method+"     |");
                System.out.println("| Payment ID: "+paymentid+ "           |");
                System.out.println("|***************************|");
                System.out.println("============================");
                break;
            case "business":
                System.out.println("~~~~~~ BUSINESS CLASS ~~~~~~");
                System.out.println("#      * BUSINESS *        #");
                System.out.println("# You paid: "+amount+" pounds  #");
                System.out.println("# Date: "+transicationdate+"  #");
                System.out.println("# Payment Method: "+method+"  #");
                System.out.println("# Payment ID: "+paymentid+"  #");
                System.out.println("#############################");
                break;
            case "first class":
                System.out.println("***** FIRST CLASS *****");
                System.out.println("||       * VIP *      ||");
                System.out.println("|| You paid: "+amount+" pounds ||");
                System.out.println("|| Date: "+transicationdate+" ||");
                System.out.println("|| Payment Method: "+method+" ||");
                System.out.println("|| Payment ID: "+paymentid+ " ||");
                System.out.println("************************");
                break;
            default:
                System.out.println("Invalid seat class.");
        }
    }
    public void updatestatus(int id,int amount,String method,String date){
        paymentid=id;
        this.amount=amount;
        this.method=method;
        transicationdate=date;
        this.bookingreference=id*amount%24;
    }
}

class booking extends seat{
    //status بيوريك المكان محجوز ولا لا
    private String status,paymentstatus;
    private int bookingreference;
    passenger passenger=new passenger();
    flight thisbookflight=new flight();
    seat seat=new seat();
    payment pay=new payment();
    public booking(flight flight,String seatclass,String name,String dateofbirth,int passid,int passnum,String pref){
        regestinflight(flight,seatclass);
        addpassenger(name,dateofbirth,passid,passnum,pref);
        this.status="Reserved";
        this.paymentstatus="not confirmed yet";

    }
    public void regestinflight(flight flight,String seatclass){
        seat=new seat(seatclass);
        thisbookflight=flight;
        flight.addseat(seat);
    }
    public void bookingdetail(){
        System.out.println("Booking Status:"+status+"\nbooking reference number:"+bookingreference+"\npayment status:"+paymentstatus+"\nPassenger Details:\n");
        passenger.getpassengerdetail();
        System.out.println("\nFlight Details:\n");
        thisbookflight.flightdetail();
    }
    public void addpassenger(String name,String dateofbirth,int passid,int passnum,String pref){
        passenger=new passenger(name,dateofbirth,passid,passnum,pref);
    }
    public int calculateprice(String seatclass){
        int price=thisbookflight.calculateprice(seatclass);
        return price;
    }
    public void confirmbooking(String status,String paymentstatus){
        this.status=status;
        this.paymentstatus=paymentstatus;
    }
    public void cancelbooking(){
        thisbookflight.deletseat(seat);
    }
    public void generateitinerary(){
        thisbookflight.generateitinerary();
    }
    public void pay(String methodofpay){
        pay.payproccess(passenger.getPassportid(),calculateprice(seat.seatclass),methodofpay,"friday,May 9,2025");
        this.paymentstatus="Confirmed";
        this.bookingreference=(passenger.getPassportid()*thisbookflight.returnprice())%24;
        this.status="Confirmed";
    }
    public String returnbookingdetail(){
        String returndet="";
        returndet+=("Booking Status:"+status+"\nbooking reference number:"+bookingreference+"\npayment status:"+paymentstatus+"\nPassenger Details:\n");
        returndet+=passenger.returnpassengerdetail();
        returndet+=("\nFlight Details:\n");
        returndet+=thisbookflight.returnflightdetail();
        return returndet;
    }
    public void generateticket(){
        pay.validatepaymentdetail(seat.seatclass);
    }
}

class filemanager {
    public void savepassenger(passengerlinkedlist p){
        try {
            FileWriter write = new FileWriter("passenger.txt");
            passengernode current= p.head;
            String all="";
            while (current!=null){
             all+=current.info.returnpassengerdetail();
             current=current.next;
            }
            write.write(all);
            write.close();
        } catch (IOException e) {
            System.out.println("couldn`t write");
        }
    }
    public void saveuser(ArrayList<user> as){
        try {
            FileWriter write = new FileWriter("user.txt");
            String all="";
            for(int i=0;i<as.size();i++){
                all+=as.get(i).returndetail();
            }
            write.write(all);
            write.close();
        } catch (IOException e) {
            System.out.println("couldn`t write");
        }

    }
    public void saveflight(flightlinkedlist f){
        try {
            FileWriter write = new FileWriter("flight.txt");
            flightnode current= f.head;
            String all="";
            while (current!=null){
                all+=current.info.returnflightdetail();
                current=current.next;
            }
            write.write(all);
            write.close();
        } catch (IOException e) {
            System.out.println("couldn`t write");
        }
    }
    public void savebooking(ArrayList<booking> b){
        try {
            FileWriter write=new FileWriter("booking.txt");
            String all="";
            for(int i=0;i<b.size();i++){
                all+=b.get(i).returnbookingdetail();
            }
            write.write(all);
            write.close();
        } catch (IOException e) {
            System.out.println("couldn`t write");
        }
    }
    public void loadpassenger(){
        String line,txt="";
        try{
            BufferedReader reader = new BufferedReader(new FileReader("passenger.txt"));
            while ((line = reader.readLine()) != null) {
                txt+=line;
                txt+="\n";
            }
        } catch (IOException e) {
            System.out.println("couldn`t read");
        }
        System.out.println(txt);
    }
    public void loaduser(){
        String line,txt="";
        try{
            BufferedReader reader = new BufferedReader(new FileReader("user.txt"));
            while ((line = reader.readLine()) != null) {
                txt+=line;
                txt+="\n";
            }
        } catch (IOException e) {
            System.out.println("couldn`t read");
        }
        System.out.println(txt);
    }
    public void loadflight(){
        String line,txt="";
        try{
            BufferedReader reader = new BufferedReader(new FileReader("flight.txt"));
            while ((line = reader.readLine()) != null) {
                txt+=line;
                txt+="\n";
            }
        } catch (IOException e) {
            System.out.println("couldn`t read");
        }
        System.out.println(txt);
    }
    public void loadbooking(){
        String line,txt="";
        try{
            BufferedReader reader = new BufferedReader(new FileReader("booking.txt"));
            while ((line = reader.readLine()) != null) {
                txt+=line;
                txt+="\n";
            }
        } catch (IOException e) {
            System.out.println("couldn`t read");
        }
        System.out.println(txt);
    }
}