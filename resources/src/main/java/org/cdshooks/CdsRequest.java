package org.cdshooks;

import javax.validation.constraints.NotNull;
import org.hl7.davinci.EncounterBasedServiceContext;

public abstract class CdsRequest<prefetchTypeT, serviceContextTypeT extends EncounterBasedServiceContext> {
  @NotNull(message = "unsupported hook")
  private Hook hook = null;

  @NotNull
  private String hookInstance = null;

  private String fhirServer = null;

  private FhirAuthorization fhirAuthorization = null;

  @NotNull
  private serviceContextTypeT context = null;

  private prefetchTypeT prefetch = null;

  private Extension extension = null;

  private RequirementsLookup RequirementsLookup = null;

  public prefetchTypeT getPrefetch() {
    return prefetch;
  }

  public void setPrefetch(prefetchTypeT prefetch) {
    this.prefetch = prefetch;
  }

  public Hook getHook() {
    return hook;
  }

  public void setHook(Hook hook) {
    this.hook = hook;
  }

  public String getHookInstance() {
    return hookInstance;
  }

  public void setHookInstance(String hookInstance) {
    this.hookInstance = hookInstance;
  }

  public String getFhirServer() {
    return fhirServer;
  }

  public void setFhirServer(String fhirServer) {
    this.fhirServer = fhirServer;
  }

  public FhirAuthorization getFhirAuthorization() {
    return fhirAuthorization;
  }

  public void setFhirAuthorization(FhirAuthorization oauth) {
    this.fhirAuthorization = oauth;
  }

  public serviceContextTypeT getContext() {
    return context;
  }

  public void setContext(serviceContextTypeT context) {
    this.context = context;
  }

  public RequirementsLookup getRequirementsLookup() {
    return RequirementsLookup;
  }

  public void setRequirementsLookup(RequirementsLookup requirementsLookup) {
    this.RequirementsLookup = requirementsLookup;
  }
  public Extension getExtension() { return extension; }

  public void setExtension(Extension extension) { this.extension = extension; }

  /**
   * This should return a traversible structure that can be used to resolve prefetch tokens.
   * It is abstract since different hooks have different elements as prefetch tokens.
   *
   * @return A traversable object (traversable with PropertyUtils)
   */
  public abstract Object getDataForPrefetchToken();

  @Override
  public String toString(){
    StringBuilder sb = new StringBuilder();
    sb.append("[hook: " + hook + "]");
    sb.append("[hookInstance: " + hookInstance + "]");
    sb.append("[fhirServer: " + fhirServer + "]");
    sb.append("[fhirAuthorization: " + fhirAuthorization + "]");
    sb.append("[context: " + context + "]");
    sb.append("[extension: " + extension + "]");
    sb.append("[prefetch: " + prefetch + "]");
    return sb.toString();
  }


}
