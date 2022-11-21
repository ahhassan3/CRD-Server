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
  int inpatientStatus, String stempCode, String payerPlanName){
    if(LookupResults == null || LookupResults.isEmpty())
    {
      try {
        LoadLookup();
      } catch (IOException e) {
        logger.warning("Unable to load Lookup Requirements");
      }
    }
    try{
    RequirementsLookup requirementFound = LookupResults.stream().filter(x -> (x.InOutPatientStatus == inpatientStatus &&
                                                                                        x.InsuranceCompanyId == InsuranceCompanyId &&
                                                                                        (x.InsurancePlanId == null || x.InsurancePlanId == InsurancePlanId) &&
                                                                                        x.StempCode.equals(stempCode) &&
                                                                                        x.PayerPlanName.trim().toUpperCase().equals(payerPlanName.trim().toUpperCase()))).findFirst().get();
    if(requirementFound != null)
    {
      return requirementFound.IsPaRequired;
    }
    return true;
    }
    catch(Exception ex){
      return true;
    }
  }

  public static void LoadLookup() throws IOException{

    if(LookupResults == null || LookupResults.isEmpty()){
      LookupResults = new ArrayList<RequirementsLookup>();
      ClassPathResource resource = new ClassPathResource("bluemassparequirements.csv");
      try (
            
            Reader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        ) {
            for (CSVRecord csvRecord : csvParser) {
                // Accessing Values by Column Index
                RequirementsLookup lookupEntry = new RequirementsLookup();
                lookupEntry.StempCode = csvRecord.get(1);
                lookupEntry.InsuranceCompanyId = parseIntOrNull(csvRecord.get(2));
                lookupEntry.InsurancePlanId = parseIntOrNull(csvRecord.get(3));
                lookupEntry.InOutPatientStatus = Short.parseShort(csvRecord.get(4));
                lookupEntry.IsPaRequired = "1".equals(csvRecord.get(5));
                lookupEntry.PayerPlanName = csvRecord.get(6);
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
