package utils;

import net.bytebuddy.utility.RandomString;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


public class RandomData {

    private static final String mobileNumberDigitsJsonFilePath = System.getProperty("user.dir") + "/src/test/resources/conf/mobileNumberDigits.json";
    private static StringBuffer currentGeneratedString;
    private static HashSet<String> lastThreeNumberSet = new HashSet<String>();
    private static HashSet<String> emailIdSuffixStringSet = new HashSet<String>();

    public static HashSet<String> getLastThreeNumberSet() {
        return lastThreeNumberSet;
    }

    public static StringBuffer getCurrentGeneratedString() {
        return currentGeneratedString;
    }

    public static String alpha_numeric_string(int length) {
        return RandomString.make(length);
    }

    public static String email() {
        String address = RandomString.make(10);
        String domain = RandomString.make(5);
        return address + "@" + domain + ".com";
    }

    public static String dateTime_yyyyMMddHHmmss() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }

    public static StringBuffer generateString(int length) {
        StringBuffer output = new StringBuffer();
        String characterSet = "";
        characterSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < length; i++) {
            double index = Math.random() * characterSet.length();
            output.append(characterSet.charAt((int) index));
        }
        currentGeneratedString = output;
        return (output);
    }

    // Generate last 3 digits mobile number
    public static String generateLastThreeNumbersOfMobileNumber(int lowerBoundary, int upperBoundary) {
        Random rand = new Random();
        int low = lowerBoundary; //inclusive of 501
        int high = upperBoundary; //exclusive of 999
        String n = String.valueOf(Math.abs(rand.nextInt(high - low) + low));
        if (n.length() == 1) {
            return n = "00" + n;
        } else if (n.length() == 2) {
            return n = "0" + n;
        } else {
            return n;
        }
    }

    // generates a unique last three digit number
    public static String getUniqueMobileNumber(int lowerBoundary, int upperBoundary) throws Exception {
        HashSet<String> alreadySignedUpNumbers = getUsedNumbersFromFile();
        // intializing an arrayList of the all numbers in the given range
        ArrayList<Integer> numbersArr = new ArrayList<>();
        for(int i = lowerBoundary; i < upperBoundary; i++){
            numbersArr.add(i);
        }
        if(alreadySignedUpNumbers.containsAll(numbersArr)){
            throw new Exception("There are new numbers available in between"+lowerBoundary+" and "+upperBoundary);
        }
        HashSet<UniqueThreeDigitNumber> uNumSet = new HashSet<>();
        Iterator itr = alreadySignedUpNumbers.iterator();


        /*
        * why i am copying the strings of alreadySignedUpNumbers hashset to new Hashset of generic type UniqueThreeDigitNumber
          is because hashset equals method compares the hashcodes of the strings and if the hash codes of two same strings
          are different then duplicate string will be added to the hashset.
          To avoid above problem I created UniqueThreeDigitNumber class which has one string type field.
          the 'equals' and 'hashcode' methods are overridden therefore the hashcode of same UniqueThreeDigitNumber will have same hashcode
         * */
        while (itr.hasNext()){
            uNumSet.add(new UniqueThreeDigitNumber(itr.next().toString()));
        }

        String uniqueNum = generateLastThreeNumbersOfMobileNumber(lowerBoundary, upperBoundary);
        while (!uNumSet.add(new UniqueThreeDigitNumber(uniqueNum))) {
            uniqueNum = generateLastThreeNumbersOfMobileNumber(lowerBoundary, upperBoundary);
        }
        HashSet<String> updatedUniqueNumberSet = new HashSet<>();
        for (UniqueThreeDigitNumber uNum : uNumSet) {
            updatedUniqueNumberSet.add(uNum.getUniqueNumber());
        }
        System.out.println("hashset after new unique number is added : " + updatedUniqueNumberSet);
        addNumberToJsonFile(updatedUniqueNumberSet);
        /*System.out.println("hash set size after adding new number : " + updatedUniqueNumberSet.size());*/
        return uniqueNum;
    }

    private static void addNumberToJsonFile(HashSet<String> alreadySignedUpNumbers) {
        try {
            FileWriter fileWriter = new FileWriter(mobileNumberDigitsJsonFilePath);
            JSONObject jsonObject = new JSONObject();
            ArrayList<String> numArray = new ArrayList<>(alreadySignedUpNumbers);
            jsonObject.put("numbers", alreadySignedUpNumbers);
            fileWriter.write(jsonObject.toJSONString());
            /*System.out.println("Writed new json value : " + jsonObject);*/
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static HashSet<String> getUsedNumbersFromFile() {
        HashSet<String> alreadySignedUpNumbers = null;
        JSONParser jsonParser = new JSONParser();
        File file = new File(mobileNumberDigitsJsonFilePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(mobileNumberDigitsJsonFilePath);
                JSONObject jsonObject = new JSONObject();
                ArrayList<String> numArray = new ArrayList<>();
                jsonObject.put("numbers", numArray);
                fileWriter.write(jsonObject.toJSONString());
                /*System.out.println("new JSON file created: " + jsonObject);*/
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        try {
            FileReader reader = new FileReader(mobileNumberDigitsJsonFilePath);
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            List<String> numbersList = (List<String>) jsonObject.get("numbers");
            alreadySignedUpNumbers = new HashSet<>(numbersList);
            /*System.out.println("array list read from json : " + numbersList);*/
            /*System.out.println("hashset converted from ArrayList : " + alreadySignedUpNumbers);*/
            reader.close();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return alreadySignedUpNumbers;
    }

    public static StringBuffer generateRandomNumber(int numLength) {
        StringBuffer output = new StringBuffer();
        String characterSet = "";
        characterSet = "1234567890";
        for (int i = 0; i < numLength; i++) {
            double index = Math.random() * characterSet.length();
            output.append(characterSet.charAt((int) index));
        }
        return (output);
    }
}
