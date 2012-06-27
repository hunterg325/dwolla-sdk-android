package com.client;

/**
 * Created with IntelliJ IDEA.
 * User: nschulze
 * Date: 6/19/12
 * Time: 9:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class DwollaTransaction
{
    private String notes;
    private String type;
    private String status;
    private String amount;
    private String clearingDate;
    private String source;
    private String destinationID;
    private String date;
    private String transactionType;
    private String id;
    private String sourceID;
    private String name;
    private Boolean success;


    DwollaTransaction(String notes, String type, String status, String amount, String clearingDate, String source, String destinationID,
                      String date, String transactionType, String id, String sourceID, String name)
    {
        this.notes = notes;
        this.type = type;
        this.status = status;
        this.amount = amount;
        this.clearingDate = clearingDate;
        this.source = source;
        this.destinationID = destinationID;
        this.date = date;
        this.transactionType = transactionType;
        this.id = id;
        this.sourceID = sourceID;
        this.name = name;
        this.success = true;
    }

    DwollaTransaction(Boolean success)
    {
        this.success = success;
    }

    public String getNotes()
    {
        return notes;
    }

    public String getType()
    {
        return type;
    }

    public String getStatus()
    {
        return status;
    }

    public String getAmount()
    {
        return amount;
    }

    public String getClearingDate()
    {
        return clearingDate;
    }

    public String getSource()
    {
        return source;
    }

    public String getDestinationID()
    {
        return  destinationID;
    }

    public String getDate()
    {
        return date;
    }

    public String getTransactionType()
    {
        return  transactionType;
    }

    public String getId()
    {
        return id;
    }

    public String getSourceID()
    {
        return sourceID;
    }

    public String getName()
    {
        return name;
    }

    public Boolean getSuccess()
    {
        return success;
    }

    public Boolean equals(DwollaTransaction transaction)
    {
        return (this.amount.equals(transaction.amount) && this.date.equals(transaction.date) && this.id.equals(transaction.id) &&
        this.status.equals(transaction.status) && this.transactionType.equals(transaction.transactionType) && this.type.equals(transaction.type));
    }
}
