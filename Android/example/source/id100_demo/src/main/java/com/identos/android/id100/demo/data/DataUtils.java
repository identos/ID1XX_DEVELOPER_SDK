package com.identos.android.id100.demo.data;

import android.text.TextUtils;

import com.identos.android.id100.library.card.belgiumeid.Address;
import com.identos.android.id100.library.card.germanhealth.InsuranceData;

import java.util.ArrayList;

final class DataUtils {
    private DataUtils() {
        // hide constructor
    }

    static ArrayList<DataItem> createListFromData(com.identos.android.id100.library.card.germanhealth.PersonalData personalData) {
        ArrayList<DataItem> list = new ArrayList<>(16);

        if(personalData == null){
            return list;
        }

        addDataItemEntry(list, "Insurant ID", personalData.getInsurantId());

        list.add(new DataItemSection("Person"));
        addDataItemEntry(list, "Birthdate", personalData.getBirthdate());
        addDataItemEntry(list, "First name", personalData.getFirstName());
        addDataItemEntry(list, "Last name", personalData.getLastName());
        addDataItemEntry(list, "Gender", personalData.getGender());
        addDataItemEntry(list, "Name prefix", personalData.getNamePrefix());
        addDataItemEntry(list, "Name suffix", personalData.getNameSuffix());
        addDataItemEntry(list, "Title", personalData.getTitle());

        com.identos.android.id100.library.card.germanhealth.PersonalData.Address postalAddress = personalData.getPostalAddress();

        if (postalAddress != null) {
            list.add(new DataItemSection("Postal address"));
            addDataItemEntry(list, "Postal code", postalAddress.postalCode);
            addDataItemEntry(list, "City", postalAddress.city);
            addDataItemEntry(list, "Post box", postalAddress.postBox);
            addDataItemEntry(list, "Country code", postalAddress.countryCode);
        }

        com.identos.android.id100.library.card.germanhealth.PersonalData.Address streetAddress = personalData.getStreetAddress();

        if (streetAddress != null) {
            list.add(new DataItemSection("Street address"));
            addDataItemEntry(list, "Postal code", streetAddress.postalCode);
            addDataItemEntry(list, "City", streetAddress.city);
            addDataItemEntry(list, "Street", streetAddress.street);
            addDataItemEntry(list, "House number", streetAddress.houseNumber);
            addDataItemEntry(list, "Addition", streetAddress.addition);
            addDataItemEntry(list, "Country code", streetAddress.countryCode);
        }
        
        return list;
    }

    static ArrayList<DataItem> createListFromData(com.identos.android.id100.library.card.belgiumeid.PersonalData personalData) {
        ArrayList<DataItem> list = new ArrayList<>(16);

        if(personalData == null){
            return list;
        }

        addDataItemEntry(list, "Data version", personalData.getDataVersion());
        addDataItemEntry(list, "Card number", personalData.getCardNumber());
        addDataItemEntry(list, "Validity", personalData.getValidFrom() + " - " + personalData.getValidUntil());

        list.add(new DataItemSection("Person"));
        addDataItemEntry(list, "Name", personalData.getName());
        addDataItemEntry(list, "First two names", personalData.getFirstTwoNames());
        addDataItemEntry(list, "Initial letter of third name", personalData.getInitialLetterOfThirdName());
        addDataItemEntry(list, "Nationality", personalData.getNationality());
        addDataItemEntry(list, "Place of birth", personalData.getPlaceOfBirth());
        addDataItemEntry(list, "Date of birth", personalData.getDateOfBirth());
        addDataItemEntry(list, "Sex", personalData.getSex());
        addDataItemEntry(list, "Title", personalData.getTitle());
        addDataItemEntry(list, "Document type", personalData.getDocumentType());
        addDataItemEntry(list, "Special status", personalData.getSpecialStatus());

        list.add(new DataItemSection("Issuer"));
        addDataItemEntry(list, "ID of the National Register", personalData.getIdOfNationalRegister());
        addDataItemEntry(list, "Place of issue", personalData.getPlaceOfIssue());

        return list;
    }

    static ArrayList<DataItem> createListFromData(InsuranceData insuranceData) {
        ArrayList<DataItem> list = new ArrayList<>(16);

        if(insuranceData == null) {
            return list;
        }

        list.add(new DataItemSection("Coverage"));
        addDataItemEntry(list, "Start date", insuranceData.getStartDate());
        addDataItemEntry(list, "Expiry date", insuranceData.getExpiryDate());

        InsuranceData.Provider provider = insuranceData.getProvider();

        if (provider != null) {
            list.add(new DataItemSection("Provider"));
            addDataItemEntry(list, "Identifier", provider.identifier);
            addDataItemEntry(list, "Country code", provider.countryCode);
            addDataItemEntry(list, "Name", provider.name);
        }

        InsuranceData.Provider settlementProvider = insuranceData.getSettlementProvider();

        if (settlementProvider != null) {
            list.add(new DataItemSection("Settlement provider"));
            addDataItemEntry(list, "Identifier", settlementProvider.identifier);
            addDataItemEntry(list, "Country code", settlementProvider.countryCode);
            addDataItemEntry(list, "Name", settlementProvider.name);
        }

        list.add(new DataItemSection("Additional info"));
        addDataItemEntry(list, "Insurant type", insuranceData.getInsurantType());
        addDataItemEntry(list, "WOP", insuranceData.getWop());

        InsuranceData.Reimbursement reimbursement = insuranceData.getReimbursement();

        if (reimbursement != null) {
            list.add(new DataItemSection("Reimbursement"));
            addDataItemEntry(list, "Medical treatment", reimbursement.medicalTreatment);
            addDataItemEntry(list, "Dental treatment", reimbursement.dentalTreatment);
            addDataItemEntry(list, "Inpatient area", reimbursement.inpatientArea);
            addDataItemEntry(list, "Authorized services", reimbursement.authorizedServices);
        }

        return list;
    }

    static ArrayList<DataItem> createListFromData(Address address) {
        ArrayList<DataItem> list = new ArrayList<>(16);

        if (address == null) {
            return list;
        }

        addDataItemEntry(list, "Data version", address.getDataVersion());

        list.add(new DataItemSection("Address"));
        addDataItemEntry(list, "Street and number", address.getStreetAndNumber());
        addDataItemEntry(list, "Postcode", address.getPostcode());
        addDataItemEntry(list, "Town", address.getTown());
        return list;
    }

    private static void addDataItemEntry(ArrayList<DataItem> list, String title, String value) {
        if (!TextUtils.isEmpty(value)) {
            list.add(new DataItemEntry(title, value));
        }
    }
}
