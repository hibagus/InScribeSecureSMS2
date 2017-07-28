package digitalquantuminc.inscribesecuresms.Intent;

/**
 * Created by Bagus Hanindhito on 01/07/2017.
 * This class defines the variable name used for intent and feedback request as well as their associated value.
 */

public class IntentString {
    public static final String MainToContactsDetails_PhoneNum = "ContactPhoneNum";
    public static final String MainToContactsDetails_ColorTheme = "ColorTheme";
    public static final String MainToSessionDetails_PhoneNum = "PartnerNum";
    public static final String MainToSessionDetails_ColorTheme = "ColorTheme";
    public static final String MainToConversationListDetails_PhoneNum = "PartnerNum";
    public static final String MainToConversationListDetails_ColorTheme = "ColorTheme";
    public static final String MainToConversationListDetails_Timestamp = "TimeStamp";
    public static final String ConversationListDetailstoMessageDetails_PhoneNum = "PartnerNum";
    public static final String ConversationListDetailstoMessageDetails_Timestamp = "TimeStamp";
    public static final String ConversationListDetailstoMessageDetails_Direction = "Direction";

    public static final String MainFeedBackCode = "MainFeedbackCode";
    public static final String ConversationListDetailsFeedbackCode = "ConversationListDetailsFeedbackCode";
    public static final String ValidSessiontoMain_PhoneNum = "ValidPhoneNum";
    public static final String ValidSessiontoMain_Name = "ValidName";
    public static final int MainFeedbackCode_DoNothing = 0;
    public static final int MainFeedbackCode_RefreshContactList = 1;
    public static final int MainFeedbackCode_RefreshSessionList = 2;
    public static final int MainFeedbackCode_RefreshBothContactandSessionList = 3;
    public static final int MainFeedbackCode_LoadValidSession = 4;
    public static final int MainFeedbackCode_DiscardValidSession = 5;
    public static final int MainFeedBackCode_RefreshContactListSessionListCompose = 6;
    public static final int MainFeedBackCode_RefreshConversationList = 7;
    public static final int ConversationListDetailsFeedbackCode_DoNothing = 0;
    public static final int ConversationListDetailsFeedbackCode_RefreshList = 1;
}
