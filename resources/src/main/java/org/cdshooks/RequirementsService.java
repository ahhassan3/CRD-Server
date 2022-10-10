package org.cdshooks;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.*;



public class RequirementsService {
  private static Logger logger = Logger.getLogger(RequirementsService.class.getName());

  public static List<RequirementsLookup> LookupResults;
  
  
  public static boolean IsPriorAuthRequired(int InsuranceCompanyId, int InsurancePlanId, 
  int inpatientStatus, String stempCode){
    if(LookupResults == null || LookupResults.isEmpty())
    {
      try {
        LoadLookup();
      } catch (IOException e) {
        logger.warning("Unable to load Lookup Requirements");
      }
    }
    try{
    boolean isPriorAuthRequired = LookupResults.stream().anyMatch(x -> (x.InOutPatientStatus == inpatientStatus &&
                                                                                        x.InsuranceCompanyId == InsuranceCompanyId &&
                                                                                        (x.InsurancePlanId == null || x.InsurancePlanId == InsurancePlanId) &&
                                                                                        x.StempCode.equals(stempCode)));
    return isPriorAuthRequired;
    }
    catch(Exception ex){
      return false;
    }
  }

  public static void LoadLookup() throws IOException{

    if(LookupResults == null || LookupResults.isEmpty()){
      LookupResults = new ArrayList<RequirementsLookup>();
      ClassPathResource resource = new ClassPathResource("bluemassparequirement.csv");
      try (
            
            Reader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        ) {
            for (CSVRecord csvRecord : csvParser) {
                // Accessing Values by Column Index
                RequirementsLookup lookupEntry = new RequirementsLookup();
                lookupEntry.InsuranceCompanyId = parseIntOrNull(csvRecord.get(0)) ;
                lookupEntry.InOutPatientStatus = Short.parseShort(csvRecord.get(1)) ;
                lookupEntry.StempCode = csvRecord.get(2);
                lookupEntry.InsurancePlanId = parseIntOrNull(csvRecord.get(3));
                LookupResults.add(lookupEntry);
            }
        }
    }
  }

  public static Integer parseIntOrNull(String value) {
    try {
        return Integer.parseInt(value);
    } catch (NumberFormatException e) {
        return null;
    }
  }
}
