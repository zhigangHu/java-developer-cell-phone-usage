import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
    Read .csv file
 */
public class CsvReader {
    private File file;
    private List<String[]> resultList = new ArrayList<String[]>();
    private static Pattern pattern = Pattern
            .compile(",?\"([^\"]*(\"{2}[^\"]*\"{2}[^\"]*)*)\",?");

    public CsvReader(File file) {
        this.file = file;
    }

    public CsvReader(String filePath) {
        file = new File(filePath);
    }


// Method print the list content
    public static void print(List<String[]> list) {
        for (int i = 0; i < list.size(); i++)
            System.out.println(Arrays.toString(list.get(i)) + " Length: "
                    + list.get(i).length);
    }

    public List<String[]> parse() {
        try {
            BufferedReader br = getReader();
            String line = "";
            while ((line = br.readLine()) != null) {
                line = handleSpecial(line);
                String[] elements = line.split(",");
                revert(elements);
                store(elements);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultList;
    }

// If the line have the other character exclude ',', then reverse it
    public void revert(String[] elements) {
        for (int i = 0; i < elements.length; i++)
            elements[i] = elements[i].replace("|", ",");
    }

// Store the element into the list
    public void store(String[] elements) {
        resultList.add(elements);
    }

    private BufferedReader getReader() {
        BufferedReader br = null;
        try {
            //br = new BufferedReader(new InputStreamReader(new FileInputStream(
            //  file), Charset.forName("GB18030")));// ISO-8859-1,GB18030
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return br;
    }

    /**
     * Handling special characters for each given line.
     *
     * @param inputLine
     * @return
     */
    private String handleSpecial(String inputLine) {
        if (inputLine == null || inputLine.trim().length() == 0)
            return "";
        Matcher m = getMatcher(inputLine);
        String after = "";
        while (m.find()) {
            after = m.group(2);
            String before = after;
            System.out.println("Before: " + before);
            if (after.indexOf(",") > -1)
                // use '|' to replace ',' for later splitting of each field of
                // the line,this '|' needs to be revert to ',' after the whole line being
                // split
                after = after.replace(',', '|');
            if (after.indexOf("\"\"") > -1)
                after = after.replace("\"\"", "\"");
            System.out.println("After:" + after);
            inputLine = inputLine.replace(before, after);
        }

        return inputLine;
    }

    private static Matcher getMatcher(String sequence) {
        return pattern.matcher(sequence);
    }


}
