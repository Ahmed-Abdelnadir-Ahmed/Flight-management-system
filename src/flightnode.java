public class flightnode {
    flight info;
    flightnode next;
    flightnode(flight i){
        info=i;
        this.next=null;
    }
}
class flightlinkedlist {
    flightnode head,tail;
    public flightlinkedlist() {
        head=tail=null;
    }
    public void addflight(flight info){
        flightnode current =new flightnode(info);
        if(isempty()){
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
    public void deletpassengerbyid(int num){
        if(isempty()){
            System.out.println("there is no flight registered yet.");
            return;
        }
        if(searchflightbynum(num)!=null){
            flightnode current=searchflightbynum(num);
            current=current.next;
            System.out.println("flight has been deleted successfully");
        }else{
            System.out.println("there is no flight with such a number");
            return;
        }

    }
    public flightnode searchflightbynum(int num){
        flightnode tmp=head;
        while((tmp!=null)){
            if(tmp.info.getFlightnum()==num){
                return tmp;
            }
            tmp=tmp.next;
        }
        return null;
    }
    public void printall(){
        if(head!=null) {
            flightnode current = head;
            while (current != null) {
                current.info.flightdetail();
                current = current.next;
            }
        }else{
            System.out.println("there isn`t any flights yet");
        }
    }
}