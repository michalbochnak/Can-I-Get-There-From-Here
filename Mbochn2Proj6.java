//-----------------------------------------------------------------------------
// Author:      Michal Bochnak, mbochn2
// Project:     Project #6, Can I Get There from Here?
// Class:       CS 211
// Professor:   Pat Troy
// Date:        April 10, 2017
//-----------------------------------------------------------------------------

//
//  Mbochn2Proj6.java
//


import java.io.*;
import java.util.*;

// main class
// set up the input buffer, create travel network, and process the command from user
public class Mbochn2Proj6
{
    public static void main (String[] args)
    {
        // set up an instance of the BufferedReader class to read from standard input
        BufferedReader br = new BufferedReader (new InputStreamReader (
                System.in));

        // set up the data needed for the airport adjacency list
        TravelNetwork airportData = new TravelNetwork();

        // call the method that reads and parses the input
        airportData.processCommandLoop(br);

        System.out.println ("Goodbye");
    }
}


// keeps information about the travel network
class TravelNetwork
{
    // Create the Data Members for the Travel Network here
    private Airport[] airports;     // array of Airports
    private int numOfAirports;      // number of Airports
    // Keeps track of used file names
    private FilesInUse filesInUse = new FilesInUse();

    // Constructor, initialization of data members for the Travel network
     public TravelNetwork() {
         numOfAirports = 10;    // num of Airports
         // array of Airports, size + 1, 0 index is not used for simplicity
         airports = new Airport[numOfAirports+1];
         // initialize each instance of the Airport
         int i = 0;
         for (; i <= numOfAirports; i++)
             airports[i] = new Airport();
     }


    // processes the commands entered by user
    public void processCommandLoop (BufferedReader br)
    {
        try {  //try-catch clauses are needed since BufferedReader and Scanner classes
            //  throw exceptions on errors
            String inline = br.readLine();   // get a line of input
            Scanner sc;

            // loop until all lines are read from the input
            while (inline != null)
            {
                if (inline != null && inline.isEmpty()) {
                    inline = br.readLine();   // get the next line of input
                    continue;
                }

                sc = new Scanner (inline);   // process each line of input using the Scanner iterators

                String command = sc.next();
                System.out.println ("*" + command + "*");

                if (command.equals("q") == true)
                    System.exit(1);

                else if (command.equals("?") == true)
                    showCommands();

                else if (command.equals("t") == true)
                    doTravel(sc);

                else if (command.equals("r") == true)
                    doResize(sc);

                else if (command.equals("i") == true)
                    doInsert(sc);

                else if (command.equals("d") == true)
                    doDelete(sc);

                else if (command.equals("l") == true)
                    doList(sc);

                else if (command.equals("f") == true)
                    doFile(sc);

                else if (command.equals("#") == true)
                    ;

                else
                    System.out.println ("Command is not known: " + command);

                inline = br.readLine();   // get the next line of input



            }
        }
        catch (IOException ioe)
        {
            System.out.println ("Error in Reading - Assuming End of File");
        }
    }

    // displays the available commands
    public void showCommands()
    {
        System.out.println ("The commands for this project are:");
        System.out.println ("  q ");
        System.out.println ("  ? ");
        System.out.println ("  # ");
        System.out.println ("  t <int1> <int2> ");
        System.out.println ("  r <int> ");
        System.out.println ("  i <int1> <int2> ");
        System.out.println ("  d <int1> <int2> ");
        System.out.println ("  l ");
        System.out.println ("  f <filename> ");
    }

    // verifies if travel soecified by user is available and displays the results
    public void doTravel(Scanner sc)
    {
        int val1 = 0;           // source airport
        int val2 = 0;           // destination airport

        // grab source airport ID
        if ( sc.hasNextInt() == true )
            val1 = sc.nextInt();
        else        // error
        {
            System.out.println ("Integer value expected");
            return;
        }

        // grab destination airport ID
        if ( sc.hasNextInt() == true )
            val2 = sc.nextInt();
        else        // error
        {
            System.out.println ("Integer value expected");
            return;
        }

        // verify if range of input is correct in respect to currect size of travel network
        if ((val1 > numOfAirports ) || (val1 < 1 ) || (val2 > numOfAirports ) || (val2 < 1 )) {
            System.out.println("Airports IDs must be between 1 and " + numOfAirports + ".");
            return;
        }

        // check if source airport has any flight available
        // if yes, calls the function to verify if user can reach
        // destination airport when starting from source airport
        if(!(airports[val1].getAirportID() == -1))
            depthFirstSearchHelper(val1, val2);
        else        // no flight available
            System.out.println("You can NOT get from airport " + val1 +
                " to airport " + val2 + " in one or more flights");
    }

    // adjust the size of travel network according to new value given by user
    public void doResize(Scanner sc)
    {
        int val1 = 0;       // new size

        // grab new size
        if ( sc.hasNextInt() == true )
            val1 = sc.nextInt();
        else        // error
        {
            System.out.println ("Integer value expected");
            return;
        }

        // check if new size is at least 1, display error message otherwise
        if (val1 < 1) {
            System.out.println("Value must be greater than 0");
            return;
        }

        // create new array
        airports = new Airport[val1+1];

        // initialize new elements
        int i = 0;
        for (; i <= val1; i++ ) {
            airports[i] = new Airport();
        }

        // update new size
        numOfAirports = val1;
    }

    // insert new flight into travel network
    public void doInsert(Scanner sc)
    {
        int val1 = 0;       // source airport ID
        int val2 = 0;       // destination airport ID

        // grab source airport ID
        if ( sc.hasNextInt() == true )
            val1 = sc.nextInt();
        else        // error
        {
            System.out.println ("Integer value expected");
            return;
        }

        // grab destination airport ID
        if ( sc.hasNextInt() == true )
            val2 = sc.nextInt();
        else        // error
        {
            System.out.println ("Integer value expected");
            return;
        }

        // verify if range of input values are correct, displays error message if not and return
        if ((val1 > numOfAirports ) || (val1 < 1 ) || (val2 > numOfAirports ) || (val2 < 1 )) {
            System.out.println("Airports IDs must be between 1 and " + numOfAirports + ".");
            return;
        }

        // insert into empty list
        if (airports[val1].getAirportID() == -1) {
            Airport temp = new Airport();   // temp Airport instance
            temp.setAirportID(val2);        // store airport ID
            temp.setNextAirport(null);      // set next pointer to null
            airports[val1] = temp;          // store as head of the linked list
        }
        // at least one element in the linked list
        else {
            airports[val1].insertNode(val2);    // insert
        }
    }

    // delete the flight from the travel network
    public void doDelete(Scanner sc)
    {
        int val1 = 0;       // source airport ID
        int val2 = 0;       // destination airport ID

        // grab source airport ID
        if ( sc.hasNextInt() == true )
            val1 = sc.nextInt();
        else        // error
        {
            System.out.println ("Integer value expected");
            return;
        }

        // grab destination airport ID
        if ( sc.hasNextInt() == true )
            val2 = sc.nextInt();
        else        // error
        {
            System.out.println ("Integer value expected");
            return;
        }

        // verify if range of input is correct in respect to currect size of travel network
        if ((val1 > numOfAirports ) || (val1 < 1 ) || (val2 > numOfAirports ) || (val2 < 1 )) {
            System.out.println("Airports IDs must be between 1 and " + numOfAirports + ".");
            return;
        }

        // check if flight exist
        if (airports[val1].findAdjacentAirport(val2)) {
            if (airports[val1].getAirportID() == val2) {
                // remove the head of the list if more than one element
                if (airports[val1].numOfElemInLL() > 1)
                    airports[val1] = airports[val1].getNextAirport();
                else
                    airports[val1] = new Airport();
            }
            else {  // find the node and remove from the list
                airports[val1].deleteNode(val2);
                System.out.println("Edge from " + val1 + " to " + val2 + " was removed.");
            }
        }
        // egde was not found
        else {
            System.out.println("There is no edge from " + val1 + " to " + val2 + ".");
        }
    }

    // display information about the whole list
    public void doList(Scanner sc)
    {
        int i = 1;      // loop counter
        // for every source airport, display his available flights
        for (; i <= numOfAirports; i++) {
            System.out.print("The fallowing Airports can be reached from Airport " + i + ": \n");
            // display available flights
            airports[i].displayAdjacentAirports();
        }
    }

    public void doFile(Scanner sc)
    {
        String fname;       // file name

        // grab the file name from the input
        if (sc.hasNext() == true)
            fname = sc.next();
        else {      // error
            System.out.println("Filename expected");
            return;
        }

        //  1. verify the file name is not currently in use
        if (filesInUse.exist(fname) == true ) {
            System.err.println("File already in use " + fname);
            return;
        }
        else {  // file not in use, insert to the list of files in use
            filesInUse.insert(fname);
        }


        BufferedReader br = null;
        try {
            //  2. open the file
            //  3. create a new instance of BufferedReader
            //        BufferedReader br = new BufferedReader (new FileReader ("MyFileReader.txt"));
            br = new BufferedReader(new FileReader(fname));
            //  4. recursively call processCommandLoop() with this new instance of BufferedReader as the parameter
            processCommandLoop(br);
            // remove from files in use
            filesInUse.remove(fname);
            //  5. close the file when processCommandLoop() returns
            br.close();

        // file not found
        } catch (FileNotFoundException fnfe) {
            System.out.println("File Not Found: " + fname);
            return;
         // input / output exception
        } catch (IOException ioe) {
            System.err.println("File could not be closed");
            return;
        }
    }

    // depth first search helper function, displays info about search for flight
    public void depthFirstSearchHelper(int val1, int val2) {
        // mark all airports as unvisited
        int i = 0;
        for (; i < numOfAirports; i++)
            airports[i].setVisited(false);  // mark as unvisited

        // if dfs return treu, flight exists, otherwise not
        if (dfs(val1, val2) == true) {
            System.out.println("You can get from airport " + val1 +
                    " to airport " + val2 + " in one or more flights");
        }
        else {      // no flight available
            System.out.println("You can NOT get from airport " + val1 +
                    " to airport " + val2 + " in one or more flights");
        }
    }

    // depth first search recursive function, return true if flight is available
    // false is returned otherwise
    public boolean dfs (int a, int b) {

        // current airport
        Airport cur = airports[a];

        // traverse the linked list and look up for available flight
        while ( cur != null ) {
            // flight found, return true
            if (cur.getAirportID() == b)
                return true;
            // if current airport is not visited, set as visited, then cal dfs
            if (!(cur.getVisited())){
                cur.setVisited(true);   // set as visited
               if (dfs(cur.getAirportID(), b) == true)
                   return true;         // flight is available, return true
            }
            cur = cur.getNextAirport(); // forward to the next airport
        }
        return false;       // not found
    }

}


// keeps track of filename currently in use
class FilesInUse
{
    private String filename;            // filename
    private FilesInUse nextFilename;    // next filename

    // constructor, initialize variables
    public FilesInUse() {
        filename = "No filename";
        nextFilename = null;
    }

    // getters
    public String getFilename() {
        return filename;
    }

    public FilesInUse getNextFilename() {
        return nextFilename;
    }

    // setters
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setNextFilename(FilesInUse nextFilename) {
        this.nextFilename = nextFilename;
    }

    // insert new filename
    public void insert(String name)
    {
        // temo filename
        FilesInUse temp= new FilesInUse();
        // store the data into temp
        temp.filename = name;
        temp.nextFilename = null;
        // traverse to the end and insert
        FilesInUse cur = this;
        while (cur.nextFilename != null) {
            cur = cur.nextFilename;     // forward pointer
        }
        // make last element point to new filename
        cur.nextFilename = temp;
    }

    // check if name is already in the list
    public boolean exist(String name)
    {
        FilesInUse cur = this;
        // traverse the list
        while (cur != null) {
            if (name.equals(cur.getFilename()))
                return true;        // exist
            // forward cur to next element
            cur = cur.nextFilename;
        }
        return false;   // does not exist
    }

    // delete the filename from the list
    public void remove(String name) {
        // traverse to the end and remove
        FilesInUse cur = this;      // keeps track of current node
        FilesInUse prev = null;     // keeps track of previous node
        while (cur != null) {
            // name are the same
            if (name.equals(cur.getFilename())) {
                // forward the orev pointer to skip he node to be deleted
                prev.nextFilename = cur.nextFilename;
            }
            prev = cur;                 // assign current to previous
            cur = cur.nextFilename;     // forward the currect
        }
    }
}