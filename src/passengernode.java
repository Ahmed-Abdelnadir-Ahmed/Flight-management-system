public class passengernode {
    passenger info;
    passengernode next;
    passengernode(passenger i){
        info=i;
        this.next=null;
    }
}
class passengerlinkedlist {
    passengernode head,tail;
    public passengerlinkedlist() {
        head=tail=null;
    }
    public void addpassenger(passenger info){
        passengernode current =new passengernode(info);
        if(head==null){
            head=tail=current;
        }else{
            tail.next=current;
            tail=tail.next;
        }
    }
    public boolean isempty(){
        if((head==null)&&(tail==null)) {
            return true;
        } else
            return false;
    }
    public void deletpassengerbyid(int id){
        if(isempty()){
            System.out.println("there is no passengers registered yet.");
            return;
        }
        if(searchforpassengerbyid(id)!=null){
            passengernode current=searchforpassengerbyid(id);
            current=current.next;
            System.out.println("the passenger has been deleted successfully");
        }else{
            System.out.println("there is no passenger with such ID");
            return;
        }

    }
    public boolean seeifpassengerexistbyid(int id){
        passengernode tmp=head;
        while((tmp!=null)){
            if(tmp.info.getPassportid()==id){
             return true;
            }
            tmp=tmp.next;
        }
        return false;
    }
    public passengernode searchforpassengerbyid(int id){
        passengernode tmp=head;
        while((tmp!=null)){
            if(tmp.info.getPassportid()==id){
                return tmp;
            }
            tmp=tmp.next;
        }
        return null;
    }
    public void printall(){
        passengernode current=head;
        while (current!=null){
            current.info.getpassengerdetail();
            current=current.next;
        }

    }
}