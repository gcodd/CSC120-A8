import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.lang.Math;

/**
 * Fairy class allows objects represeting a fairy to be created.
 * @author Grace Codd
 * References:
 * https://stackoverflow.com/questions/10205437/compare-one-string-with-multiple-values-in-one-expression
 */


public class Fairy implements Contract {

    /** Height of fairy (in cm) at any given time*/
    private Integer height;
    /** A fairy's "normal" height before they have shrunk or grown */
    private Integer normalHeight;
    /** The maximum height of a fairy in cm, initialized to 20 */
    private static final int MAX_HEIGHT = 20;
    /** The minimum height of a fairy in cm, inintialized to 1 */
    private static final int MIN_HEIGHT = 1;
    /** The name of a fairy */
    private String name;
    /** The level of energy a fairy has */
    private int energy;
    /** A list of items a fairy has in their collection */
    private ArrayList<String> collection;
    /** A list of the possible directions a fairy can move */
    private final HashSet<String> directions;
    /** The maximum amount of items a fairy can have in their collection, initialized to 15 */
    private static final int MAX_COLLECT = 15;
    /** The maximum level of energy, initialized to 100 */
    private static final int MAX_ENERGY = 100;
    /** The minimum amount of energy, initialized to 0 */
    private static final int MIN_ENERGY = 0;

    /** Default Constructor. Sets height to minimum value */
    public Fairy(){
        //Call full constructor
        this("<Name Unknown>", MIN_HEIGHT);
    }

    /** 
     * Overloaded constructor with name only
     * @param name the name of the fairy */
    public Fairy(String name){
        this(); //Call default constructor
        this.name = name;   //Set name as name passed into constructor
    }

    /** 
     * Overloaded constructor with height only
     * @param height the height of the fairy in cm */
    public Fairy(int height){
        this(); //Call default constructor
        this.height = height;   //Set height as height passed into constructor
    }

    /**
     * Constructor takes in value for name and height and initializes all 
     * class fields. Throws error if height passed as arg is not within range.
     * @param name the name of the fairy
     * @param height the height of the fairy in cm
     */
    public Fairy(String name, int height){
        this.name = name;
        //Set energy level to maximum energy when a fairy is created
        this.energy = MAX_ENERGY;
        //Throw error if height pass in is not in range
        if(height > MAX_HEIGHT || height < MIN_HEIGHT){
            throw new RuntimeException("Error! Your fairy cannot be taller than 20 cm or shorter than 1 cm.");
        }
        this.height = height;
        //Set normalHeight as value stored in height
        this.normalHeight = height;
        //Initialize collection ArrayList.
        //Using an ArrayList instead of HashSet to allow for duplicate items
        collection = new ArrayList<String>();
        //Initialized directions HashSet
        directions = new HashSet<String>();
        //Add string objects to directions HashSet. 
        /*Since this is set in the constructor the possible directions will be the same
        * for all fairies created */
        directions.add("north");
        directions.add("south");
        directions.add("east");
        directions.add("west");
        directions.add("left");
        directions.add("right");
        directions.add("straight");
        directions.add("backwards");
    }

    /** Accessor for height class field
     * @return the value stored in height field
     */
    public int getHeight(){
        return this.height;
    }

    /** Accessor for name class field
     * @return the String stored in name
     */
    public String getName(){
        return this.name;
    }

    /** Accessor for size of collection */
    public int nCollection(){
        return collection.size();
    }

    /** Accessor for energy class field
     * @return the value stored in energy
     */
    public int getEnergy(){
        return this.energy;
    }

    /** Manipulator for name class field
     * @param name the String to be stored in name field
     */
    /*This is the only manipulator I'm including for simplicity's sake since 
    a height setter would have to check for range compliancy, which the constructor already does.*/
    public void setName(String name){
        this.name = name;
    }

    /**
     * Adds an item to the collection
     * @param item the item a fairy would like to add to their collection
     */
    public void grab(String item){
        //If the fairy already has the maximum amount of items in their collection, throw an error
        if(nCollection() == MAX_COLLECT){
            throw new RuntimeException("Error! You cannot add any more items to your collection.");
        }
        //Call add method, passing item as argument. This will add the item to the collection ArrayList
        collection.add(item);
        //Print message
        System.out.println("You have added " + item + " to your collection!");
    }

    /**
     * Removes an item from the collection
     * @param item the item to be removed from the collection
     */
    public String drop(String item){
        //If item cannot be found in the colllection, throw error
        if(!hasItem(item)){
            throw new RuntimeException("Error! Item does not exist in this fairy's collection.");
        }
        //Call remove method, passing item as arg. This will remove 
        collection.remove(item);
        System.out.println("You have removed " + item + " from your collection.");
        return item;
    }

    /**
     * Checks if an item is present in the collection
     * @param item the item to search for
     * @return true if item is found in collection, false if not
     */
    public boolean hasItem(String item){
        return collection.contains(item);
    }

    /**
     * Asks user if they would like to add an item to the Fairy's collection.
     * @param item the item to be examined
     */
    public void examine(String item){
        //Create Scanner object for user input
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Do you want to add " + item + " to your collection?");
        //Store response in answer variable
        String answer = keyboard.nextLine();

        //If user respondes yes, call grab method to add the item to the collection
        if(answer.equalsIgnoreCase("yes")){
            grab(item);
        }
        else{
            System.out.println("OK! " + item + " will not be added to your collection.");
        }
    }

    /** 
     * Uses item in fairy's collection by deducting from energy levels
     * @param item the item to be used
     */
    public void use(String item){
        //If item is not found in collection, throw error
        if(!hasItem(item)){
            throw new RuntimeException("Error! Item does not exist in fairy's collection.");
        }
        //Amount of energy to use any item is 2
        int energySpent = 2;
        //If fairy does not have energy to use item, throw error 
        if(this.energy - energySpent < MIN_ENERGY){
            throw new RuntimeException("You do not have enough energy to use " + item + ".");
        }
        //Deduct value stored in energy by value in energySpent
        this.energy -= energySpent;
        System.out.println("You successfully used " + item + ".");
    }

    /**
     * Deducts from energy levels to walk 1 m in any direction. 
     * @param direction the direction the fairy will walk in
     * @return true
     */
    public boolean walk(String direction){
        if(!directions.contains(direction.toLowerCase())){
            throw new RuntimeException("Error! Invalid direction.");
        }

        int energySpent = 5;
        if(this.energy - energySpent < MIN_ENERGY){
            throw new RuntimeException("Error! Not enough energy to walk.");
        }
        this.energy -= energySpent;
        System.out.println("You have walked 1 m " + direction);
        return true;
    }

    /**
     * Calculates distance flown, deducts from energy levels to fly.
     * @param x distance in meters fairy will fly forwards
     * @param y distance in meters fairly will fly upwards
     * @return true
     */
    public boolean fly(int x, int y){
        if(x >= 0 && y >= 0){
            /*Distance traveled determined by calculating the hypotenuse of a right triangle if a line was drawn 
            between distance x and distance y. This calculation will still work even if x = 0 or y = 0.
            Explicit cast to int, store in variable distanceTraveled*/
            int distanceTraveled = (int)(Math.sqrt((x * x) + (y * y)));
            //Energy spent will be distance travled * 5
            int energySpent = distanceTraveled * 5;
            //If fairy does not have enough energy to fly this distance, throw an error
            if(this.energy - energySpent < MIN_ENERGY){
                throw new RuntimeException("Error! Not enough energy to fly.");
            }
            //Subtract energySpent from value stored in energy, store new value in energy
            this.energy -= energySpent;
            System.out.println("You have flown " + x + " m forward and " + y + " m up, a total of " + distanceTraveled + " m.");
            //Return true, method will always return true unless errors are thrown and program stops.
            return true;
        }
        //If values passed into x or y are less than 0, throw error
        else
            throw new RuntimeException("Error! Distance values must be positive integers.");
    }

    /**
     * Sets height to minimum possible height in order to shrink
     * @return height of fairy after shrinking
     */
    public Number shrink(){
        //Check if fairy is taller than minimum height
        if(height > MIN_HEIGHT){
            //If energy levels are less than 20, throw error
            if(this.energy < 20){
                throw new RuntimeException("Error! You do not have enough energy to shrink.");
            }
            //If fairy has enough energy, subtract 20 from energy, set height to minimum height and return height
            else{
                this.energy -= 20;
                this.height = MIN_HEIGHT;
                return height;
            }
        }
        //If fairy is already at minimum height, throw error
        else{
            throw new RuntimeException("Error! Cannot shrink. Already at minimum height");
        }
    }

    /**
     * Sets height to maximum possible height in order to grow
     * @return height of fairy after growing
     */
    public Number grow(){
        //Check if fairy is shorter than maximum height
        if(height < MAX_HEIGHT){
            //If fairy does not have enough energy, throw error
            if(this.energy < 20){
                throw new RuntimeException("Error! You do not have enough energy to grow.");
            }
            //If fairy does have enough energy, subtract 20 from energy, set height to maximum height, return height
            else{
                this.energy -= 20;
                this.height = MAX_HEIGHT;
                return height;
            }
        }
        //If fairy is already at maximum height, throw error
        else{
            throw new RuntimeException("Error! Cannot grow. Already at maximum height.");
        }
    }

    /**
     * Sets energy back to maximum energy level
     */
    public void rest(){
        this.energy = MAX_ENERGY; //Store value in MAX_ENERGY in energy
    }

    /**
     * Sets height to fairy's normal height to undo shrinking or growing
     */
    public void undo(){
        this.height = normalHeight; //Store value in normalHeight in height
    }

    /**
     * Displays interactive method options to user
     */
    public void showOptions(){
        System.out.println("Actions your fairy can take: \n + grab(item) \n + drop(item) \n + examine (item) \n + use(item) \n + walk(direction) \n + fly(x, y) \n + shrink() \n + grow() \n + rest() \n + undo()");
    }

    /**
     * Displays all items in the collection
     */
    public void printCollection(){
        //Enhanced for loop prints each element in collection ArrayList
        for(String item:this.collection){
            System.out.println(item);
        }
    }

    //Main for testing
    public static void main(String[] args) {
        String answer;
        char letter;
        Scanner keyboard = new Scanner(System.in);

        System.out.println("Hello! We are going to make a fairy today.");

        do{
            System.out.print("What is your fairy's name? ");
            String name = keyboard.nextLine();
            System.out.print("How many cm tall is " + name + "? ");
            int height = keyboard.nextInt();
            keyboard.nextLine();

            Fairy myFairy = new Fairy(name, height);
            myFairy.showOptions();

            myFairy.grab("Acorn");
            myFairy.examine("Pinecone");
            myFairy.examine("Snail");
            myFairy.grab("Leaf");
            myFairy.drop("Acorn");
            myFairy.printCollection();

            myFairy.use("Leaf");

            myFairy.walk("right");
            myFairy.walk("straight");
            myFairy.walk("straight");

            myFairy.fly(10, 10);

            myFairy.rest();

            myFairy.shrink();
            System.out.println(myFairy.getHeight());
            myFairy.undo();
            System.out.println(myFairy.getHeight());


            System.out.print("Would you like to make another fairy? ");

            answer = keyboard.nextLine();
            letter = answer.charAt(0);

        }while(letter == 'Y' || letter == 'y');
    }
}