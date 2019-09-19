/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java2.ip;
//import java.util.Scanner;
//import java.util.Random;
import java.util.*;
import java.util.stream.*;
import java.util.stream.IntStream;
import java.util.Optional;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author Stefan
 */
public class Java2IP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        Survey survey = new Survey("Student Survey");
        
        //survey.executeSurvey();
        survey.executeSurveyAuto();
        
        
        
        
    }
    
    
    public static class Survey {
        //Variable Declaration
        public String title;
        public static int respondentID;
        public int n = 10;
        public int numRespondents = 0;
        public String[] surveyQuestions = new String[n];
        public int[][] surveyMatrix = new int[n][n];
        public int textLength = 0;
        public Random rnd = new Random();
        
        public Survey() {
            this.title = "Customer Survey";
        }
        
        public Survey(String inputTitle) {
            String output = String.format("Generating survey: %s", inputTitle);
            System.out.println(output);
            this.title = inputTitle;
        }
       
        //Function requested in assignment
        private int generateRespondentId() {
            return ++respondentID;
        }
        
        private int calculateText() {
            // This function finds the length of the longest survey question for formatting the summary tables
            int maxTextLength = 0;
           
            
            for(int i = 0; i<n; i++) {
                if(maxTextLength < surveyQuestions[i].length()) {
                    maxTextLength = surveyQuestions[i].length();
                }
            }
            
            return maxTextLength;
        }
        
        private void displaySurveyResults() {
            
            //This function displays a table containing the results of each question and respondent
            String singleOutput;
            String rowStart;
            String tableStart;
            String respondents;
            
            //Unusual calculation is due to the number of questions not being assigned as a variable. 
            String border = new String(new char[textLength+6 + 4 * n]).replace("\0", "-");
            
            respondents = String.format("| %-" + (textLength + 2) + "." + (textLength + 2) + "s | %-" +(4*n -3) + "." + (4*n - 3) + "s |", " ", "Respondent");
            tableStart = String.format("| %-" + (textLength + 2) + "." + (textLength + 2) + "s |", "Questions");
            System.out.println(border);
            System.out.println(respondents);
            System.out.println(border);
            System.out.print(tableStart);
            
            for(int j = 0; j<numRespondents; j++) {
                    singleOutput = String.format("%2d |", j);
                    System.out.print(singleOutput);
                }
            System.out.print("\n");
            System.out.println(border);
            for(int i = 0; i < n; i++) {
                rowStart = String.format("|%d. %-" + textLength + "s |", i, surveyQuestions[i]);
                System.out.print(rowStart);
                for(int j = 0; j<numRespondents; j++) {
                    singleOutput = String.format("%2d |", surveyMatrix[i][j]);
                    System.out.print(singleOutput);
                }
                System.out.print("\n" + border + "\n");
            }
        }
        
        private void displayQuestionStats(int q) {
            //This function displays the results from a single question
            String formatText = String.format("Printing Results for Question : %d", q);
            String formatColumns = String.format("%15s|%15s", "Respondnent", "Response");
            String border = new String(new char[31]).replace("\0", "-");
            System.out.println(formatText);
            System.out.print("Question: ");
            System.out.print(surveyQuestions[q]);
            System.out.print("\n");
            System.out.println(formatColumns);
            System.out.println(border);
            for(int i = 0; i < numRespondents ; i++) {
                formatColumns = String.format("%15d|%15d", i, surveyMatrix[q][i]);
                System.out.println(formatColumns);
            }
            
        }
        
        private void enterQuestions() {
            //This function allows the user to input questions to the survey
            Scanner inputStream = new Scanner(System.in);
            String userInput = "null";
            String formatText;
            int i = 0;
            while(i < n) {
                formatText = String.format("Enter Question %d and then press enter : ", i);
                System.out.println(formatText);
                userInput = inputStream.nextLine();
                surveyQuestions[i] = userInput;
                i++;
            }
            
            textLength = calculateText();
        }
        
        private void logResponse(int rID, int qNumber, int response) {
            //Thsi function was requested by the assignment, but the brevity of it may make it not necessary.
            surveyMatrix[rID][qNumber] = response;
            
                    
            
        }
        private void elicitResponse(int rID, int qNumber) {
            //This function prompts the user to input an answer for a single question
            String formatText = String.format("Respondent %d, Question %d : %" + textLength+ "s >> ", rID, qNumber, surveyQuestions[qNumber]);
            String userOutput;
            
            Scanner userInput = new Scanner(System.in);
            System.out.print(formatText);
            
            userOutput = validateResponse();
            logResponse(rID, qNumber, Integer.parseInt(userOutput));
            
        }
        
        private void executeSurveyAuto() {
            //This function creates a prepopulated list of survey results so that manual entry is not required each time.
            surveyQuestions[0] = "Rate your school from 1 - 5";
            surveyQuestions[1] = "Rate your teacher from 1 - 5";
            surveyQuestions[2] = "Rate your math class from 1 - 5";
            surveyQuestions[3] = "Rate your science class from 1 - 5";
            surveyQuestions[4] = "Rate your history class from 1 - 5";
            surveyQuestions[5] = "Rate your music class from 1 - 5";
            surveyQuestions[6] = "Rate your food from 1 - 5";
            surveyQuestions[7] = "Rate your dorms from 1 - 5";
            surveyQuestions[8] = "Rate your car from 1 - 5";
            surveyQuestions[9] = "Rate your support desk from 1 - 5";
            textLength = calculateText();
            numRespondents = 10;
            for(int i = 0; i < 10; i ++ ) {
                for(int j = 0; j < n ; j++) {
                    logResponse(i, j, (rnd.nextInt(5)+1));
                }
            }
            for(int i = 0; i < n; i++ )  {
                displayQuestionStats(i);
            }
            
            displaySurveyResults();
            topRatedQuestion();
            lowRatedQuestion();
            System.out.println(presentQuestion(5));
            System.out.println(presentQuestion(5,3));
        }
        
        public void topRatedQuestion() {
            //This function finds the question with the highest rating through summation
            int[] questionSums = new int[10];
            int[] questionRow;
            int[] output;
            
            for(int i = 0; i < 10; i++) {
                questionRow = getColumn(surveyMatrix,i);
                questionSums[i] = Arrays.stream(questionRow).sum();
            }
            
            output = findMax(questionSums);
            
            System.out.println("\nReporting top rated questions: \n");
            
            if(output.length == 1) {
              System.out.println("There is 1 top rated question : Question " + 
                    output[0] + " with a sum of " + questionSums[output[0]] +"\n");
            } else {
                System.out.println("There are " + output.length + " top rated questions: \n");
                for(int i = 0; i < output.length; i++) {
                    System.out.println("Question " + output[i] + " with a sum of " + questionSums[output[i]]);
                }
            }
            
            
            
            
        }
        
        public void lowRatedQuestion() {
            //This function finds the question with the lowest rating through summation
            int[] questionSums = new int[10];
            int[] questionRow;
            int[] output;
            
            for(int i = 0; i < 10; i++) {
                questionRow = getColumn(surveyMatrix,i);
                questionSums[i] = Arrays.stream(questionRow).sum();
            }
            
            output = findMin(questionSums);
            
            System.out.println("\nReporting low rated questions: ");
            
            if(output.length == 1) {
              System.out.println("There is 1 low rated question : Question " + 
                    output[0] + " with a sum of " + questionSums[output[0]] +"\n");
            } else {
                System.out.println("There are " + output.length + " low rated questions: \n");
                for(int i = 0; i < output.length; i++) {
                    System.out.println("Question " + output[i] + " with a sum of " + questionSums[output[i]]);
                }
            }
            
            
            
            
        }
        
       
        
        public String presentQuestion(int qNumber) {
            //This function presents the question 
            
            return "Please respond to the following question :\n " + qNumber + ") " + surveyQuestions[qNumber];
        }
        
        public String presentQuestion(int qNumber, int rID) {
            //This function presents the question using an overload
            return "Respondant " + rID + " Please respond to the following question :\n " + qNumber + ") " + surveyQuestions[qNumber];
        }
        
        public void executeSurvey() {
            //This function executes the survey including manual entry for questions and responses
            int proceed = 0;
            String prompt = "Is there another respondent? Enter y or n >> ";
            String answer = "";
            Scanner response = new Scanner(System.in);
    
            
            
            enterQuestions();
            
            for(int i = 0; i < n ; i++) {
                System.out.print(prompt);
                 answer = response.next();
                 if("y".equals(answer)) {
                 
                    if(i == 9) {
                        numRespondents = 10;
                    }
                    for(int j = 0; j < n ; j++) {
                        elicitResponse(i,j);
                    }
                 }
                 
                 else {
                     numRespondents = i;
                     break;
                 }
            }
            
            for(int i = 0; i < n; i++ )  {
                displayQuestionStats(i);
            }
            
            displaySurveyResults();
            topRatedQuestion();
            lowRatedQuestion();
            System.out.println(presentQuestion(5));
            System.out.println(presentQuestion(5,1));
        }
        
        private int[] getColumn(int[][] matrix, int column) {
            //This function extracts a column representing all responses from a single question from the survey matrix
            return IntStream.range(0, matrix[0].length).map(i -> matrix[column][i]).toArray();
            
        }
        
        private int[] findMax(int[] numbers) {
            //This function finds the biggest number in a list of integers
            //This function will not work for numbers less than 0
            int maxSum = 0;
            int j = 0;
            
            List<Integer> answers = new ArrayList<>();
            
            //Search through array to find max
            for(int i : numbers) {
                if(i >= maxSum) {
                    maxSum = i;
                }
            }
            
            //Find index of max/s
            
            for(int i = 0; i < 10; i++) {
                if(numbers[i] == maxSum) {
                    answers.add(i);
                }
            }
            
            // convert to int array
            int[] ans = new int[answers.size()];
            
            for(int i: answers) {
                ans[j++] = i;
                
            }
            return ans;
        }
        
        private int[] findMin(int[] numbers) {
            //This function finds the smallest number in a list of integers. 
            //This function will not work for numbers that are greater than 100.
            int minSum = 100;
            int j = 0;
            
            List<Integer> answers = new ArrayList<>();
            
            //Search through array to find max
            for(int i : numbers) {
                if(i <= minSum) {
                    minSum = i;
                }
            }
            
            //Find index of max/s
            
            for(int i = 0; i < 10; i++) {
                if(numbers[i] == minSum) {
                    answers.add(i);
                }
            }
            
            // convert to int array
            int[] ans = new int[answers.size()];
            
            for(int i: answers) {
                ans[j++] = i;
                
            }
            return ans;
        }
        
        public String validateResponse() {
            //This function uses regular expressions to accept user input, only accepting a number between 1 and 5
            Scanner readString = new Scanner(System.in);
            boolean parseFlag;
            String returnValue;
            String outText;
            Pattern p = Pattern.compile("[1-5]");
            Matcher m;
            returnValue = readString.nextLine();



            parseFlag = false;
            while(parseFlag == false) {
                    //Check if user entered any alphabetic (non-numeric) text

                    m = p.matcher(returnValue);

                    parseFlag = m.matches();

                    if(!parseFlag) {
                            System.out.print("Error: Please enter a number between 1 and 5 >>.\n");
                            
                            returnValue = readString.nextLine();
                            System.out.print("\n");
                    }

            }
            System.out.print("\n");
            //readString.close();
            return returnValue;
			
			
		}
    }
}
