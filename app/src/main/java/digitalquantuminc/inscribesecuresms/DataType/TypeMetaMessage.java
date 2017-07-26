package digitalquantuminc.inscribesecuresms.DataType;

import digitalquantuminc.inscribesecuresms.UserInterface.ByteConverter;

/**
 * Created by Bagus Hanindhito on 23/07/2017.
 */

public class TypeMetaMessage {
    public static final int MessageTypeNormalEncryptedUncompressed = 0x00;
    public static final int MessageTypeNormalEncryptedCompressedBLZ4 = 0x01;
    public static final int MessageTypeNormalEncryptedCompressedDeflate = 0x02;
    public static final int MessageTypeHandshakeRequestDS = 0xC0;
    public static final int MessageTypeHandshakeReplyDS = 0xC1;
    public static final int MessageTypeHandshakeSuccessDS = 0xC2;
    public static final int MessageTypeEndSessionRequest = 0xC3;
    public static final int MessageTypeEndSessionSuccess = 0xC4;
    public static final int MessageTypeErrorHandshakeRequestDSNotValid = 0xE0;
    public static final int MessageTypeErrorHandshakeReplyDSNotValid = 0xE1;
    public static final int MessageTypeErrorHandshakeSuccessDSNotValid = 0xE2;
    public static final int MessageTypeErrorNoSecureSessionActive = 0xE3;
    public static final int MessageTypeErrorHandshakeDeclined = 0xE4;

    public static final int MessageTypeLength = 1;
    public static final int MessageHeadIDLength = 3;
    public static final int MessageTailIDLength = 3;
    public static final int IntegerLength = 4;
    public static final int SMSStandardLength = 160;
    public static final int SMSExtendedLength = 145;
    public static final int SMSUltraExtendedLength = 152;

    public static final int MessageHeadIDVersion0 = 0x33343C;
    public static final int MessageTailIDVersion0 = 0x3C3433;

    private int MessageType;
    private int MessageHeadID;
    private int MessageTailID;

    public TypeMetaMessage()
    {

    }

    public TypeMetaMessage(int MessageType, int MessageHeadID, int MessageTailID)
    {
        this.MessageType = MessageType;
        this.MessageHeadID = MessageHeadID;
        this.MessageTailID = MessageTailID;
    }

    public int getMessageType() {
        return MessageType;
    }

    public int getMessageHeadID() {
        return MessageHeadID;
    }

    public int getMessageTailID() {
        return MessageTailID;
    }

    public static byte[] EmbedMetaData(TypeMetaMessage meta, byte[] OriginalMessage)
    {
        byte[] MessageType = ByteConverter.convertToByteArray(meta.getMessageType());
        byte[] MessageHeadID = ByteConverter.convertToByteArray(meta.getMessageHeadID());
        byte[] MessageTailID = ByteConverter.convertToByteArray(meta.getMessageTailID());


        int newmessagelength = OriginalMessage.length + MessageTypeLength + MessageHeadIDLength + MessageTailIDLength;
        byte[] newmessage = new byte[newmessagelength];
        // Because integer has 4 bytes and we only use 3 LSB Bytes, we need to start copying from IntegerLength-DATALength
        System.arraycopy(MessageHeadID,IntegerLength-MessageHeadIDLength,newmessage,0,MessageHeadIDLength);
        System.arraycopy(MessageType,IntegerLength-MessageTypeLength,newmessage,MessageHeadIDLength,MessageTypeLength);
        System.arraycopy(OriginalMessage,0,newmessage,MessageHeadIDLength+MessageTypeLength,OriginalMessage.length);
        System.arraycopy(MessageTailID,IntegerLength-MessageTailIDLength,newmessage,MessageHeadIDLength+MessageTypeLength+OriginalMessage.length,MessageTailIDLength);
        byte[] MessageHeadIDRet = new byte[4];
        System.arraycopy(newmessage,0,MessageHeadIDRet,1,3);

        return newmessage;
    }

    public static TypeMetaMessage ExtractMetaData(byte[] CompleteText)
    {
        byte[] MessageType =  new byte[IntegerLength];
        byte[] MessageHeadID = new byte[IntegerLength];
        byte[] MessageTailID = new byte[IntegerLength];
        // Because integer has 4 bytes and we only retrieve 3 LSB Bytes, we need to start copying from IntegerLength-DATALength
        System.arraycopy(CompleteText,0,MessageHeadID,IntegerLength-MessageHeadIDLength,MessageHeadIDLength);
        System.arraycopy(CompleteText,MessageHeadIDLength,MessageType,IntegerLength-MessageTypeLength,MessageTypeLength);
        System.arraycopy(CompleteText,CompleteText.length-MessageTailIDLength,MessageTailID,IntegerLength-MessageTailIDLength,MessageTailIDLength);

        int MessageTypeInt = ByteConverter.convertToInt(MessageType);
        int MessageHeadIDInt = ByteConverter.convertToInt(MessageHeadID);
        int MessageTailIDInt = ByteConverter.convertToInt(MessageTailID);
        TypeMetaMessage meta = new TypeMetaMessage(MessageTypeInt, MessageHeadIDInt, MessageTailIDInt);
        return meta;
    }

    public static byte[] ExtractOriginalMessage(byte[] CompleteText)
    {
        int originalmessagelength = CompleteText.length - MessageTypeLength - MessageHeadIDLength - MessageTailIDLength;
        byte[] OriginalMessage = new byte[originalmessagelength];
        System.arraycopy(CompleteText,MessageHeadIDLength+MessageTypeLength,OriginalMessage,0,originalmessagelength);
        return OriginalMessage;
    }
}
