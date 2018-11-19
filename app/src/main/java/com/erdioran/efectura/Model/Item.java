package com.erdioran.efectura.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class Item {


    private int Cur_ID;
    private int Cur_ParentID;
    private String Cur_Code;
    private String Cur_Abbreviation;
    private String Cur_Name;
    private String Cur_Name_Bel;
    private String Cur_Name_Eng;
    private String Cur_QuotName;
    private String Cur_QuotName_Bel;
    private String Cur_QuotName_Eng;
    private String Cur_NameMulti;
    private String Cur_Name_BelMulti;
    private String Cur_Name_EngMulti;
    private int Cur_Scale;
    private int Cur_Periodicity;
    private String Cur_DateStart;
    private String Cur_DateEnd;


//    public Item jsonParse(JSONObject jsonObject) throws JSONException {
//        if (!jsonObject.isNull("Cur_ID")) setCur_ID(jsonObject.getInt("Cur_ID"));
//        if (!jsonObject.isNull("Cur_ParentID")) setCur_ParentID(jsonObject.getInt("Cur_ParentID"));
//        if (!jsonObject.isNull("Cur_Code")) setCur_Code(jsonObject.getString("Cur_Code"));
//        if (!jsonObject.isNull("Cur_Abbreviation"))
//            setCur_Abbreviation(jsonObject.getString("Cur_Abbreviation"));
//        if (!jsonObject.isNull("Cur_Name")) setCur_Name(jsonObject.getString("Cur_Name"));
//        if (!jsonObject.isNull("Cur_Name_Bel"))
//            setCur_Name_Bel(jsonObject.getString("Cur_Name_Bel"));
//        if (!jsonObject.isNull("Cur_Name_Eng"))
//            setCur_Name_Eng(jsonObject.getString("Cur_Name_Eng"));
//        if (!jsonObject.isNull("Cur_QuotName"))
//            setCur_QuotName(jsonObject.getString("Cur_QuotName"));
//        if (!jsonObject.isNull("Cur_QuotName_Bel"))
//            setCur_QuotName_Bel(jsonObject.getString("Cur_QuotName_Bel"));
//        if (!jsonObject.isNull("Cur_QuotName_Eng"))
//            setCur_QuotName_Eng(jsonObject.getString("Cur_QuotName_Eng"));
//        if (!jsonObject.isNull("Cur_NameMulti"))
//            setCur_NameMulti(jsonObject.getString("Cur_NameMulti"));
//        if (!jsonObject.isNull("Cur_Name_BelMulti"))
//            setCur_Name_BelMulti(jsonObject.getString("Cur_Name_BelMulti"));
//        if (!jsonObject.isNull("Cur_Name_EngMulti"))
//            setCur_Name_EngMulti(jsonObject.getString("Cur_Name_EngMulti"));
//        if (!jsonObject.isNull("Cur_Scale")) setCur_Scale(jsonObject.getInt("Cur_Scale"));
//        if (!jsonObject.isNull("Cur_Periodicity"))
//            setCur_Periodicity(jsonObject.getInt("Cur_Periodicity"));
//        if (!jsonObject.isNull("Cur_DateStart"))
//            setCur_DateStart(jsonObject.getString("Cur_DateStart"));
//        if (!jsonObject.isNull("Cur_DateEnd")) setCur_DateEnd(jsonObject.getString("Cur_DateEnd"));


    public int getCur_ID() {
        return Cur_ID;
    }

    public void setCur_ID(int cur_ID) {
        Cur_ID = cur_ID;
    }

    public int getCur_ParentID() {
        return Cur_ParentID;
    }

    public void setCur_ParentID(int cur_ParentID) {
        Cur_ParentID = cur_ParentID;
    }

    public String getCur_Code() {
        return Cur_Code;
    }

    public void setCur_Code(String cur_Code) {
        Cur_Code = cur_Code;
    }

    public String getCur_Abbreviation() {
        return Cur_Abbreviation;
    }

    public void setCur_Abbreviation(String cur_Abbreviation) {
        Cur_Abbreviation = cur_Abbreviation;
    }

    public String getCur_Name() {
        return Cur_Name;
    }

    public void setCur_Name(String cur_Name) {
        Cur_Name = cur_Name;
    }

    public String getCur_Name_Bel() {
        return Cur_Name_Bel;
    }

    public void setCur_Name_Bel(String cur_Name_Bel) {
        Cur_Name_Bel = cur_Name_Bel;
    }

    public String getCur_Name_Eng() {
        return Cur_Name_Eng;
    }

    public void setCur_Name_Eng(String cur_Name_Eng) {
        Cur_Name_Eng = cur_Name_Eng;
    }

    public String getCur_QuotName() {
        return Cur_QuotName;
    }

    public void setCur_QuotName(String cur_QuotName) {
        Cur_QuotName = cur_QuotName;
    }

    public String getCur_QuotName_Bel() {
        return Cur_QuotName_Bel;
    }

    public void setCur_QuotName_Bel(String cur_QuotName_Bel) {
        Cur_QuotName_Bel = cur_QuotName_Bel;
    }

    public String getCur_QuotName_Eng() {
        return Cur_QuotName_Eng;
    }

    public void setCur_QuotName_Eng(String cur_QuotName_Eng) {
        Cur_QuotName_Eng = cur_QuotName_Eng;
    }

    public String getCur_NameMulti() {
        return Cur_NameMulti;
    }

    public void setCur_NameMulti(String cur_NameMulti) {
        Cur_NameMulti = cur_NameMulti;
    }

    public String getCur_Name_BelMulti() {
        return Cur_Name_BelMulti;
    }

    public void setCur_Name_BelMulti(String cur_Name_BelMulti) {
        Cur_Name_BelMulti = cur_Name_BelMulti;
    }

    public String getCur_Name_EngMulti() {
        return Cur_Name_EngMulti;
    }

    public void setCur_Name_EngMulti(String cur_Name_EngMulti) {
        Cur_Name_EngMulti = cur_Name_EngMulti;
    }

    public int getCur_Scale() {
        return Cur_Scale;
    }

    public void setCur_Scale(int cur_Scale) {
        Cur_Scale = cur_Scale;
    }

    public int getCur_Periodicity() {
        return Cur_Periodicity;
    }

    public void setCur_Periodicity(int cur_Periodicity) {
        Cur_Periodicity = cur_Periodicity;
    }

    public String getCur_DateStart() {
        return Cur_DateStart;
    }

    public void setCur_DateStart(String cur_DateStart) {
        Cur_DateStart = cur_DateStart;
    }

    public String getCur_DateEnd() {
        return Cur_DateEnd;
    }

    public void setCur_DateEnd(String cur_DateEnd) {
        Cur_DateEnd = cur_DateEnd;
    }


}



