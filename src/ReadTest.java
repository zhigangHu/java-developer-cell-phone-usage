import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

public class ReadTest {
    public static void main(String[] args) {
        CsvReader reader1 = new CsvReader("data\\CellPhone.csv");
        CsvReader reader2 = new CsvReader("data\\CellPhoneUsageByMonth.csv");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<String[]> cellPhone = reader1.parse();
        List<String[]> cellPhoneUsage=reader2.parse();

        List reportList=new ArrayList();
        String[] reportHeader={"Report Run Date","EmployeeId","Number of Phones","Total Minutes","Total Data","Average Minutes","Average Data"};
        reportList.add(reportHeader);

        for (int i=1;i<cellPhone.size();i++){
            int phoneNum=0;
            float totalMin=0;
            float totalData=0;
            String[] reportLine=new String[7];


            for (int j=1;j<cellPhoneUsage.size();j++){

                if(cellPhone.get(i)[0].equals(cellPhoneUsage.get(j)[0]))
                {

                    phoneNum++;
                    totalMin+=Float.parseFloat(cellPhoneUsage.get(j)[2]);
                    totalData+=Float.parseFloat(cellPhoneUsage.get(j)[3]);
                }


            }
            reportLine[0]=df.format(new Date());
            reportLine[1]=cellPhone.get(i)[0];
            reportLine[2]=String.valueOf(phoneNum);
            reportLine[3]=String.valueOf(totalMin);
            reportLine[4]=String.valueOf(totalData);
            reportLine[5]=String.valueOf(totalMin/phoneNum);
            reportLine[6]=String.valueOf(totalData/phoneNum);

            reportList.add(reportLine);

        }

        CsvReader.print(reportList);


    }
}
