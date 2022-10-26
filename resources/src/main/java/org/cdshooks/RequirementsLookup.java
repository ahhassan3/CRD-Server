package org.cdshooks;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequirementsLookup implements Serializable {
    @JsonProperty("InsuranceCompanyId")
    public int InsuranceCompanyId;
    @JsonProperty("InsurancePlanId")
    public Integer InsurancePlanId;
    @JsonProperty("InOutPatientStatus")
    public short InOutPatientStatus; 
    @JsonProperty("StempCode")
    public String StempCode;
    @JsonProperty("IsPaRequired")
    public Boolean IsPaRequired;
    @JsonProperty("PayerPlanName")
    public String PayerPlanName;

}

