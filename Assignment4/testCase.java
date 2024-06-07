import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class testCase {

    private String length10 = "examTitle1";
    private String length250 = "This has a length of 250. IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII";
    private String length300 = "This has a length of 300. IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII";
    private String[] keySize1 = {"hello"};                                              // key elements with a size of one    
    private String[] keySize2 = {"hello", "hi"};                                        // key elements with a size of two
    private String[] keySize4 = {"hello", "hi", "Greetings", "hey"};                    // key elements with a size of four
    private String[] keySize5 = {"hello", "hi", "Greetings", "hey", "yo"};              // key elements with a size of five
    private String[] keySize6 = {"hello", "hi", "Greetings", "hey", "yo", "wrong"};     // key elements with a size of six

    //Tests for addPost() function

    @Test
    void testAddPost_testCase1() {
        //Test Case 1: Check the function with valid inputs
        PostTest case1Data1 = new PostTest("Easy", "Ordinary", length10, length250, keySize2);

        PostTest case1Data2 = new PostTest("Easy", "Ordinary", length250, length250, keySize2);

        PostTest case1Data3 = new PostTest("Difficult", "Highly Needed", length10, length300, keySize2);

        PostTest case1Data4 = new PostTest("Very Difficult", "Highly Needed", length10, length300, keySize4);


        assertAll ("", ()->assertEquals(true, case1Data1.addPost()), ()->assertEquals(true, case1Data2.addPost()), ()->assertEquals(true, case1Data3.addPost()), ()->assertEquals(true, case1Data4.addPost()));
    }

    @Test
    void testAddPost_testCase2() {
        //Test Case 2: Check the function where title is invalid
        //title has length 7 characters
        PostTest case2Data1 = new PostTest("Easy", "Ordinary", "length7", length250, keySize2);
        //title has length 300 characters
        PostTest case2Data2 = new PostTest("Easy", "Ordinary", length300, length250, keySize2);
    
        assertAll ("", ()->assertEquals(false, case2Data1.addPost()), ()->assertEquals(false, case2Data2.addPost()));
    }

    @Test
    void testAddPost_testCase3() {
        //Test Case 3: Check the function where the body is invalid when the difficulty changes
        //Difficult with body length 250
        PostTest case3Data1 = new PostTest("Normal", "Ordinary", length10, length250, keySize2);
        //Very Difficult with body length 250
        PostTest case3Data2 = new PostTest("Very Difficult", "Ordinary", length10, length250, keySize2);

        assertAll ("", ()->assertEquals(false, case3Data1.addPost()), ()->assertEquals(false, case3Data2.addPost()));
    }

    @Test
    void testAddPost_testCase4() {
        //Test Case 4: Check the function where the tags are invalid when the difficulty changes
        //Easy with 4 tags
        PostTest case4Data1 = new PostTest("Easy", "Ordinary", length10, length250, keySize4);
        //Easy with 5 tags
        PostTest case4Data2 = new PostTest("Easy", "Ordinary", length10, length250, keySize5);

        assertAll ("", ()->assertEquals(false, case4Data1.addPost()), ()->assertEquals(false, case4Data2.addPost()));
    }

    @Test
    void testAddPost_testCase5() {
        //Test Cast 5: check the function where number of tags is invalid
        //1 tag
        PostTest case5Data1 = new PostTest("Difficult", "Ordinary", length10, length250, keySize1);
        //6 tags
        PostTest case5Data2 = new PostTest("Easy", "Ordinary", length10, length250, keySize6);

        assertAll ("", ()->assertEquals(false, case5Data1.addPost()), ()->assertEquals(false, case5Data2.addPost()));
    }

    @Test
    void testAddPost_testCase6() {
        //Check the state where the emergency of the post is invalid
        //Easy with Ordinary emergency
        PostTest case6Data1 = new PostTest("Easy", "Highly Needed", length10, length300, keySize2);
        //Difficult with Highly Needed emergency
        PostTest case6Data2 = new PostTest("Easy", "Immediately Needed", length10, length300, keySize2);
        //Very Difficuly with Immediately Needed emergency
        PostTest case6Data3 = new PostTest("Very Difficult", "Ordinary", length10, length300, keySize2);

        assertAll ("", ()->assertEquals(false, case6Data1.addPost()), ()->assertEquals(false, case6Data2.addPost()), ()->assertEquals(false, case6Data3.addPost()));
    }


    //Tests for addComment function

    @Test
    void testAddComment_testCase1() {
        // Test case 1: Check if the chosen postID is invalid
        //PostID = 1000
        PostTest commentCase1Data1 = new PostTest(1000, "Test");
        //PostID = 2000
        PostTest commentCase1Data2 = new PostTest(2000, "Test");

        assertAll ("", ()->assertEquals(false, commentCase1Data1.addComment()), ()->assertEquals(false, commentCase1Data2.addComment()));

    }

    @Test
    void testAddComment_testCase2() {
        // Test case 2: Check when comment length is invalid
        //comment length = 2
        PostTest commentCase2Data1 = new PostTest(1, "No");
        //comment length = 12
        PostTest commentCase2Data2 = new PostTest(1, "Long Comment");

        assertAll ("", ()->assertEquals(false, commentCase2Data1.addComment()), ()->assertEquals(false, commentCase2Data2.addComment()));

    }

    @Test
    void testAddComment_testCase3() {
        // Test case 3: Check the case when there are already more than 5 comments for a very difficult post
        //ID 3, has 5 comments
        PostTest commentCase3Data1 = new PostTest(3, "Test");
        //ID 3, has 5 comments
        PostTest commentCase3Data2 = new PostTest(3, "Maxlength1");

        assertAll ("", ()->assertEquals(false, commentCase3Data1.addComment()), ()->assertEquals(false, commentCase3Data2.addComment()));

    }

    @Test
    void testAddComment_testCase4() {
        // Test case 4: Check for when easy post cannot have more than 3 comments
        //ID 1, has 3 comments, easy post
        PostTest commentCase4Data1 = new PostTest(1, "Test");
        //ID 1, has 3 comments, easy post
        PostTest commentCase4Data2 = new PostTest(1, "Testing");

        assertAll ("", ()->assertEquals(false, commentCase4Data1.addComment()), ()->assertEquals(false, commentCase4Data2.addComment()));
    }

    @Test
    void testAddComment_testCase5() {
        // Test case 5: check that the first character of the comment is not uppercase
        //lower case as first character
        PostTest commentCase5Data1 = new PostTest(1, "wrong");
        //integer as first character
        PostTest commentCase5Data2 = new PostTest(1, "2late");

        assertAll ("", ()->assertEquals(false, commentCase5Data1.addComment()), ()->assertEquals(false, commentCase5Data2.addComment()));
    }

    @Test
    void testAddComment_testCase6() {
        // Test case 6: all inputs are valid
        PostTest commentCase6Data1 = new PostTest(2, "Test");
        PostTest commentCase6Data2 = new PostTest(2, "Maxlength1");

        assertAll ("", ()->assertEquals(true, commentCase6Data1.addComment()), ()->assertEquals(true, commentCase6Data2.addComment()));

    }

}