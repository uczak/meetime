package com.test.meetime.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HubspotContactCreateResponse {

    private String id;
    private Properties properties;

    private String createdAt;
    private String updatedAt;
    private boolean archived;

    @Data
    public static class Properties {

        @JsonProperty("createdate")
        private String createdDate;

        @JsonProperty("hs_all_contact_vids")
        private String allContactVids;

        @JsonProperty("hs_associated_target_accounts")
        private String associatedTargetAccounts;

        @JsonProperty("hs_currently_enrolled_in_prospecting_agent")
        private String currentlyEnrolledInProspectingAgent;

        @JsonProperty("hs_is_contact")
        private String isContact;

        @JsonProperty("hs_is_unworked")
        private String isUnworked;

        @JsonProperty("hs_lifecyclestage_lead_date")
        private String lifecycleStageLeadDate;

        @JsonProperty("hs_marketable_status")
        private String marketableStatus;

        @JsonProperty("hs_marketable_until_renewal")
        private String marketableUntilRenewal;

        @JsonProperty("hs_membership_has_accessed_private_content")
        private String membershipAccessedPrivateContent;

        @JsonProperty("hs_object_id")
        private String objectId;

        @JsonProperty("hs_object_source")
        private String objectSource;

        @JsonProperty("hs_object_source_id")
        private String objectSourceId;

        @JsonProperty("hs_object_source_label")
        private String objectSourceLabel;

        @JsonProperty("hs_pipeline")
        private String pipeline;

        @JsonProperty("hs_prospecting_agent_actively_enrolled_count")
        private String prospectingAgentActivelyEnrolledCount;

        @JsonProperty("hs_registered_member")
        private String registeredMember;

        @JsonProperty("hs_sequences_actively_enrolled_count")
        private String sequencesActivelyEnrolledCount;

        @JsonProperty("lastmodifieddate")
        private String lastModifiedDate;

        @JsonProperty("lifecyclestage")
        private String lifecycleStage;

        @JsonProperty("num_notes")
        private String numNotes;
    }
}
