import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PostTest {
    private int postID;
    private int counter;                //to keep track of number of comments
    private int maxCounter;             //the maximum number of comments that can be placed
    private String userDiff;            //difficulty of post
    private String userEmergency;       //emergency of post
    private String userComment;         //comment content for the post
    private String postTitle;
    private String postBody;
    private String fileLine;            //save the current line when reading file
    private String[] postTags;
    private String[] postTypes = {"Very Difficult", "Difficult", "Easy"};
    private String[] postEmergency = {"Immediately Needed", "Highly Needed", "Ordinary"};
    private ArrayList <String> postContent = new ArrayList<>();     //contains content of post.txt line for line
    private ArrayList <String> commentContent = new ArrayList<>();  //contains content of post.txt line for line
    private ArrayList <String> postComments = new ArrayList<>();
    
    Scanner input = new Scanner(System.in);

    //Constructor, will be used for making the post
    public PostTest(String diff, String emergency, String title, String body, String[] tags) {
        userDiff = diff;
        userEmergency = emergency;
        postTitle = title;
        postBody = body;
        postTags = tags;
    }

    // constructor, will be used for adding a comment
    public PostTest(int id, String comment) {
        postID = id;
        userComment = comment;

    }

    // check if the input for difficulty is valid
    public boolean booleanDiff(String diff) {

        // check through each of the types and compare if the user input for the type is the same
        for (int i = 0; i < postTypes.length ; i++) {
            if (diff.compareTo(postTypes[i]) == 0) {
                return true;
            }
        }
        return false;
    }

    // check if the input for emergency is valid
    public boolean checkEmergency(String emergency, String diff) {
        if (diff.compareTo("Easy") == 0 && emergency.compareTo("Ordinary") == 0) {
            return true;
        }
        if ((diff.compareTo("Difficult") == 0 || diff.compareTo("Very Difficult") == 0) && (emergency.compareTo("Highly Needed") == 0 || emergency.compareTo("Immediately Needed") == 0)) {
            return true;
        }

        return false;
    }

    //check if the input for title is valid
    public boolean checkTitle(String title) {
        if (title.length() < 10 || title.length() > 250) {
            return false;
        }
        return true;
    }

    // check if input for the body is valid
    public boolean checkBody(String body, String diff) {
        // diff == "Easy", body length lower than 250
        if (diff.compareTo("Easy") == 0 && body.length() < 250) {
            return false;
        }
        // diff != "Easy", body length lower than 300
        if (diff.compareTo("Easy") != 0 && body.length() < 300) {
            return false;
        }
        return true;
    }

    // check if input for the tag is valid, upper cases will be made lower cased
    public boolean checkTag(String[] tags, String diff) {
        //check if easy, between 2-3 tags
        if (diff.compareTo("Easy") == 0 && (tags.length < 2 || tags.length > 3)) {
            return false;
        }
        //check if not easy, between 2-3 tags
        else if (diff.compareTo("Easy") != 0 && (tags.length < 2 || tags.length > 5)) {
            return false;
        }


        //tag elements will all be made lower case
        for (int i = 0; i < tags.length; i++) {
            postTags[i] = tags[i].toLowerCase();
        }

        return true;
    }

    // check postID
    public boolean checkID(int id) {
        
        try {
            //open post.txt file
            File postFile = new File ("post.txt");
            Scanner postReader = new Scanner(postFile);

            //set default for maxCounter to 5
            maxCounter = 5;

            // read each line for and see if it is the specified ID
            // also read the difficulty of the post (next 3 lines)
            while (postReader.hasNextLine()) {
                fileLine = postReader.nextLine();        //postID
                if (fileLine.compareTo("PostID: " + id) == 0) {
                    fileLine = postReader.nextLine();               // title
                    fileLine = postReader.nextLine();               // tags
                    fileLine = postReader.nextLine();               // difficulty

                    //check difficulty, assign the max number of comments that can be posted under that post
                    if (fileLine.compareTo("Difficulty: Easy") == 0) {
                        maxCounter = 3;     //max 3 comments for easy posts
                    }
                    // else {
                    //     maxCounter = 5;
                    // }

                    //close reader and return true as ID is present
                    postReader.close();
                    return true;
                }
            }

            postReader.close();
        }
        catch(FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }
        

        return false;
    }

    //check of the comment is valid
    public boolean checkComment(String comment) {
        //Test if the first character is an uppercase letter
        if (Character.isUpperCase(comment.charAt(0))) {
            // check if length of the comment is correct
            if (comment.length() >= 4 && comment.length() <= 10) {
                return true;
            }

        }

        return false;
    }




    // method to add post
    public boolean addPost()
    {
        //performing all the checks, all must be correct for file to be created
        if (checkTitle(postTitle) && checkBody(postBody, userDiff) && checkTag(postTags, userDiff) && checkEmergency(userEmergency, userDiff)) {
            
            try {
                // Open file post.txt
                File postFile = new File ("post.txt");
                Scanner fileReader = new Scanner(postFile);
    
                //copy each line in the file and add it to the array list
                while (fileReader.hasNextLine()) {
                    postContent.add(fileReader.nextLine());
                }
                fileReader.close();
            }
            catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
                return false;
            }
            //Add new content
            //Each post takes up 7 lines, adding a new post will be number of lines divide 6 plus 1
            System.out.println(postContent.size());
            System.out.println((postContent.size() / 7) + 1);
            postID = (postContent.size() / 7) + 1;

            try {
                // write to file
                FileWriter poster = new FileWriter("post.txt");
                
                //add everything in postContent again
                for (int i = 0; i < postContent.size(); i++) {
                    poster.write(postContent.get(i) + "\n");
                }
                
                //add new content into post.txt
                poster.write("\nPostID: " + postID + "\n");
                poster.write("Title: " + postTitle + "\n");
                poster.write("Tags:");
                for (int i = 0; i < postTags.length; i++) {
                    poster.write(" " + postTags[i]);
                }
                poster.write("\n");
                poster.write("Difficulty: " + userDiff + "\n");
                poster.write("Emergency: " +userEmergency + "\n");
                poster.write("Body: " + postBody + "\n");
                poster.close();

            }
            catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
                return false;
            }

            
            // Add a section to comment.txt for the newly added post
            try {
                // Open file comment.txt
                File commentFile = new File ("comment.txt");
                Scanner commentReader = new Scanner(commentFile);
    
                //copy each line in the file and add it to the array list
                while (commentReader.hasNextLine()) {
                    commentContent.add(commentReader.nextLine());
                }
                commentReader.close();
            }
            catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
                return false;
            }

            try {
                // write to file
                FileWriter commenter = new FileWriter("comment.txt");
                
                //add everything in postContent again
                for (int i = 0; i < commentContent.size(); i++) {
                    commenter.write(commentContent.get(i) + "\n");
                }
                
                //add a section for the new ID 
                commenter.write("\nPostID: " + postID + "\n");
                commenter.close();

            }
            catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
                return false;
            }


            return true;

        }
        return false;

    }




    public boolean addComment() {

        //check if postID and the comment itself is valid
        if (checkID(postID) && checkComment(userComment)) {

            //Obtain all the lines in the comment.txt file
            try {
                //open comment.txt file
                File commentFile = new File ("comment.txt");
                Scanner commentReader = new Scanner(commentFile);
    
                // read each line for and see if it is the specified ID
                while (commentReader.hasNextLine()) {
                    //add the next line of the file
                    fileLine = commentReader.nextLine();
                    commentContent.add(fileLine);
                    //Once the line read is at the postID
                    if (fileLine.compareTo("PostID: " + postID) == 0) {

                        //
                        counter = 0;
                        //read the comments
                        while (commentReader.hasNextLine()) {
                            fileLine = commentReader.nextLine(); 
                            //if counter is at 5, return false as another comment cannot be added
                            if (counter == maxCounter) {
                                commentReader.close();
                                return false;
                            }
                            //if line is blank, add the userComment then a blank to the comment content array list then break while loop
                            else if (fileLine.equals("")) {
                                //if line is blank, add the userComment then a blank to the comment content array list then break while loop
                                commentContent.add(userComment);
                                commentContent.add(fileLine);
                                break;
                            }
                            //if the line currently being read is the last line of the file and it has not reached max counter
                            else if (!commentReader.hasNextLine()) {
                                //add current line to array list, increment counter
                                commentContent.add(fileLine);
                                counter++;
                                // check if maximum number of comments is reached
                                if (counter == maxCounter) {
                                    commentReader.close();
                                    return false;
                                }
                                else {
                                    commentContent.add(userComment);
                                }
                            }
                            //if line is another comment, add it to the array list
                            else {
                                commentContent.add(fileLine);
                                counter++;
                            }                                
                        }
                    }
                }
                commentReader.close();
            }
            catch(FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
                return false;
            }
            

            // write the comment.txt file again with the new comment appended
            try {
                // write to file
                FileWriter commenter = new FileWriter("comment.txt");
                
                //add everything in postContent again
                for (int i = 0; i < commentContent.size(); i++) {
                    commenter.write(commentContent.get(i) + "\n");
                }
                
                commenter.close();

            }
            catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
                return false;
            }



            return true;
        }


        return false;
    }

}

