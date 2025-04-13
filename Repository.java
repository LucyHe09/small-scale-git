// A repository represents the series of ordered commits (revisions within a repository) 
    // added to it 

    import java.util.*;
    import java.text.SimpleDateFormat;
    
    public class Repository {
       private Commit front;
       private String repoName;
    
        // Behavior:
            //   Constructor to create a new, empty repository
        // Parameters:
            //   String name given by the user
        // Exceptions:
            // throws an IllegalArgumentException when given name is null or empty
       public Repository(String name){
           if(name == null || name.equals("")){
               throw new IllegalArgumentException("name is null or empty");
           }
           front = null;
           repoName = name;
       }
    
       // Behavior:
           //   gets the ID of the current head of this repository
       // Returns:
               // Returns the ID of the current head of this repository
               // If the head is null, null is returned
       public String getRepoHead(){
           if(front == null){ //how to access the node
               return null;
           }
           return front.id;
       }
    
        // Behavior:
           //   gets the size (number of commits) in this repository
       // Returns:
               // Returns the size (number of commits) of this repository
       public int getRepoSize(){ 
           int size = 0;
           Commit curr = front;
           while (curr != null) {
               size++;
               curr = curr.past;
           }
           return size;
       }
    
       // Behavior:
           //   Creates a string representation of this repository
                // the format "<name> - Current head: <head>" is used
                // if there are no commits, the format "<name> - No commits" is used
       // Returns:
               // Returns a string representation of this repository
       public String toString(){
           if(this.getRepoSize() == 0){
               return this.repoName + " - No commits";
           }
           return this.repoName + " - Current head: " + front.toString();
       }
    
        // Behavior:
            //   contains checks if the given targetId of a commit is 
                // contained within the repository - the commit is part of the repository
        // Parameters:
            //   String targetId to check if the repository contains
        // Exceptions:
            // throws an IllegalArgumentException when given targetId is null
        // Returns:
            // Return true if the commit with ID targetId is in the repository, false if not
       public boolean contains(String targetId){
           if(targetId == null){
               throw new IllegalArgumentException("target ID is null");
           }
    
           Commit curr = this.front;
           while (curr != null){
               if(curr.id.equals(targetId)){
                   return true;
               }
               curr = curr.past;
           }
           return false;
       }
    
        // Behavior:
            //   getHistory creates a String with representations of n most recent commits
                // most recent commits are first
        // Parameters:
            //  int n representing how many commits back the user would 
                // like to see repository history
        // Exceptions:
            // throws an IllegalArgumentException when given int is non positive
        // Returns:
            // Return a string consisting of the String representations of the most recent n
                // commits in this repository, with the most recent first
                // sequential commits are separated by a new line
        public String getHistory(int n){ 
            if(n <= 0){
                throw new IllegalArgumentException("n is non positive");
            } else {
                String result = "";
                Commit curr = this.front;
                if(n < this.getRepoSize()){
                    for(int i = 0; i < n; i++){
                        result += curr.toString() + "\n";
                        curr = curr.past;
                    }
                } else{
                    while(curr != null){ //print all
                        result += curr.toString() + "\n";
                        curr = curr.past;
                    }
                }
                return result;
            }
        }
    
        // Behavior:
            //   commit creates a new commit with the given message, adding it to this repository
        // Parameters:
            //  String message representing the message the user chooses for its respective commit
        // Exceptions:
            // throws an IllegalArgumentException when message is null
        // Returns:
            // Returns the ID of the new commit
       public String commit(String message){
           if(message == null){
               throw new IllegalArgumentException("message is null");
           }
          
           Commit curr = this.front;
           Commit curr2 = new Commit(message, curr) ;
           front = curr2;
           return curr2.id;
       }
    
        // Behavior:
            //   drop removes the commit with ID targetId from this repository,
                // maintaining the rest of the history
        // Parameters:
            //  String targetId representing the ID of the commit the user would like to remove
        // Exceptions:
            // throws an IllegalArgumentException when targetId is null
        // Returns:
            // Returns true if the commit was successfully dropped, 
                // and false if there is no commit that matches the given ID in the repository
        public boolean drop(String targetId){ 
            if(targetId == null){
                throw new IllegalArgumentException("target ID is null");
            } else if(!this.contains(targetId)){
                return false;
            }
            
            Commit curr = front;
            if (front.id.equals(targetId)) { //if its the front
                this.front = curr.past;
                return true;
            }
            while (curr.past != null && !curr.past.id.equals(targetId)){
                curr = curr.past;
            }
            
            curr.past = curr.past.past;
            return true;
        }
    
        // Behavior:
            //  synchronize takes all the commits in the other repository and 
                // moves them into this repository, 
                // combining the two repository histories so chronological order is preserved
                // If the user calls this method with the other Repository, other's remaining commits
                // are completely emptied into this repository when the method is finished
        // Parameters:
            //  Repository other in which the user would like to 
                // synchronize Repository other with this
        // Exceptions:
            // throws an IllegalArgumentException if Repository other is null
       public void synchronize(Repository other){
        if(other == null){
            throw new IllegalArgumentException("other repository is null");
        }
    
        if (front == null) { //good to go
            front = other.front;
            other.front = null;
        } else if (other.front != null) {
            // front case should check if other has more recent cases
            if (other.front != null && this.front.timeStamp < other.front.timeStamp){
                //if front node of other is earlier than front of this
               Commit temp = other.front;
               other.front = other.front.past;
               temp.past = this.front;
               this.front = temp;
            }
    
            Commit curr = front;
            // middle case:
            while (curr.past != null && other.front != null) {
                Commit temp = other.front;
                if(curr.past.timeStamp < other.front.timeStamp){ 
                    //if our repository has a less timestamp, then weave in the next commit
                    other.front = other.front.past;
                    temp.past = curr.past;
                    curr.past = temp;
                    curr = curr.past;
                } else {
                    curr = curr.past; //needs in all cases 
                }
            }
    
            if(curr.past == null && other.front != null){ 
                // if other has extra commits/no nodes left in repo1
                curr.past = other.front;
                other.front = null;
            }
        }
    }
    
       /**
        * DO NOT MODIFY
        * A class that represents a single commit in the repository.
        * Commits are characterized by an identifier, a commit message,
        * and the time that the commit was made. A commit also stores
        * a reference to the immediately previous commit if it exists.
        *
        * Staff Note: You may notice that the comments in this
        * class openly mention the fields of the class. This is fine
        * because the fields of the Commit class are public. In general,
        * be careful about revealing implementation details!
        */
       public static class Commit {
    
    
           private static int currentCommitID;
    
    
           /**
            * The time, in milliseconds, at which this commit was created.
            */
           public final long timeStamp;
    
    
           /**
            * A unique identifier for this commit.
            */
           public final String id;
    
    
           /**
            * A message describing the changes made in this commit.
            */
           public final String message;
    
    
           /**
            * A reference to the previous commit, if it exists. Otherwise, null.
            */
           public Commit past;
    
    
           /**
            * Constructs a commit object. The unique identifier and timestamp
            * are automatically generated.
            * @param message A message describing the changes made in this commit. Should be non-null.
            * @param past A reference to the commit made immediately before this
            *             commit.
            */
           public Commit(String message, Commit past) {
               this.id = "" + currentCommitID++;
               this.message = message;
               this.timeStamp = System.currentTimeMillis();
               this.past = past;
           }
    
    
           /**
            * Constructs a commit object with no previous commit. The unique
            * identifier and timestamp are automatically generated.
            * @param message A message describing the changes made in this commit. Should be non-null.
            */
           public Commit(String message) {
               this(message, null);
           }
    
    
           /**
            * Returns a string representation of this commit. The string
            * representation consists of this commit's unique identifier,
            * timestamp, and message, in the following form:
            *      "[identifier] at [timestamp]: [message]"
            * @return The string representation of this collection.
            */
           @Override
           public String toString() {
               SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
               Date date = new Date(timeStamp);
    
               return id + " at " + formatter.format(date) + ": " + message;
           }
    
           /**
           * Resets the IDs of the commit nodes such that they reset to 0.
           * Primarily for testing purposes.
           */
           public static void resetIds() {
               Commit.currentCommitID = 0;
           }
       }
    }
    