//-----------------------------------------------------------------------------
// Author:      Michal Bochnak, mbochn2
// Project:     Project #6, Can I Get There from Here?
// Class:       CS 211
// Professor:   Pat Troy
// Date:        April 10, 2017
//-----------------------------------------------------------------------------

//
//  Airport.java
//


// keeps information about airport
public class Airport {

    // airport ID that can be reached directly
    private int airportID;
    // indicates if airport was visited or not
    private boolean visited;
    // pointer to the next element of the Linked List
    private Airport nextAirport;


    // constructor, initialize the airport
    public Airport() {
        airportID = -1;         // -1 as empty
        visited = false;        // not visited
        nextAirport = null;     // no next airport ( null )
    }

    // getters

    // get airport ID
    public int getAirportID() {
        return this.airportID;
    }

    // get status if visited or not
    public boolean getVisited() {
        return this.visited;
    }

    // get next airport node
    public Airport getNextAirport() {
        return this.nextAirport;
    }


    // setters

    // set the airport ID
    public void setAirportID(int val) {
        this.airportID = val;
    }

    // set the status visited or not
    public void setVisited(boolean status) {
        this.visited = status;
    }

    // set pointer to the next airport
    public void setNextAirport(Airport next) {
        this.nextAirport = next;
    }

    // set the data inside the Airport Node
    public void setAirportData(Airport data) {
        this.airportID = data.airportID;
        this.visited = data.visited;
        this.nextAirport = data.nextAirport;
    }

    // Returns true is the list is empty, false otherwise is returned
    public int isEmpty() {
        return airportID = -1;
    }

    // insert new element into Linked List ( at the end of the list )
    public void insertNode(int val)
    {
        // check if already inserted
        if (this.findAdjacentAirport(val)) {        // already on the list
            System.out.println("Airport " + val + " is already on the list.");
            return;
        }
        // temp airport
        Airport temp = new Airport();
        // store data into temp
        temp.airportID = val;
        temp.nextAirport = null;
        // traverse to the end and insert
        Airport cur = this;
        while (cur.nextAirport != null) {
            cur = cur.nextAirport;  // forward the current
        }
        cur.nextAirport = temp;     // insert at the end
    }

    // delete from list
    public void deleteNode(int val)
    {
        // find the node to delete
        Airport cur = this;     // current node
        Airport prev = null;    // previous node
        while (cur != null) {
            // found
            if (cur.airportID == val) {
                // forward the pointer to skip node to be deleted
                prev.nextAirport = cur.nextAirport;
            }
            prev = cur;             // previous points to current now
            cur = cur.nextAirport;  // forward current
        }
    }

    // display airports that can be reached from specified airport
    public void displayAdjacentAirports()
    {
        // traverse the linked list
        Airport cur = this;
        // empty list, no flight available
        if (this.getAirportID() == -1) {
            System.out.println("None");
            return;
        }
        // traverse and display available flights
        while (cur != null) {
            System.out.print(cur.airportID + ", ");
            cur = cur.nextAirport;  // forward te current
        }
        System.out.print("\n");     // new line, for output formatting
    }

    // look up for adjacent airport, return true if found,
    // false is returned otherwise
    public boolean findAdjacentAirport(int id) {
        Airport cur = this;
        // traverse the list
        while (cur != null) {
            if (cur.airportID == id)
                return true;        // found
            // forward cur to next element
            cur = cur.nextAirport;
        }
        return false;               // not found
    }

    // counts the elements in the linked list and return number of elements
    public int numOfElemInLL() {
        Airport cur = this;
        int count = 0;
        while (cur != null){
            count++;
            cur = cur.nextAirport;
        }
        return count;
    }



}
