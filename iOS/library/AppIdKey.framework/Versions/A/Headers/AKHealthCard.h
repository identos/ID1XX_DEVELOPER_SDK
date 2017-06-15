//
//  AKHealthCard.h
//  AppIdKey
//
//  Created by Alec Laws on 2014-11-25.
//  Copyright (c) 2014 Identos. All rights reserved.
//

#import <AppIdKey/AppIdKey.h>

/**
 This is the class container for an AKHealthCard
 
 http://www.gematik.de/cms/de/spezifikation/release_0_5_3/release_0_5_3_egk/dokumente_egk_r053.jsp
 */
@interface AKHealthCard : AKCard

/**
 @name Properties
 */

/**
 General insurance XML Data
 */
@property (nonatomic, strong) NSString * generalInsuranceDataXML;

/**
 Personal Insurance XML Data
 */
@property (nonatomic, strong) NSString * personalInsuranceXML;


/**
 * This property
 * Insurant ID of the "Versicherten Person"
 */
@property (nonatomic, strong) NSString* insurantID;
/**
 * This property
 * birthday of the "Versicherten Person"
 */
@property (nonatomic, strong) NSString* birthday;
/**
 * This property
 * First Name
 */
@property (nonatomic, strong) NSString* firstname;
/**
 * This property
 * Last Name
 */
@property (nonatomic, strong) NSString* lastname;
/**
 * This property
 * gender
 */
@property (nonatomic, strong) NSString* gender;
/**
 * This property
 * name prefix
 */
@property (nonatomic, strong) NSString* vorsatzwort;
/**
 * This property
 * name affix
 */
@property (nonatomic, strong) NSString* namenszusatz;
/**
 * This property
 * name titel
 */
@property (nonatomic, strong) NSString* titel;
/**
 * This property
 * Address Information
 * ZIP Code of Cyti
 */
@property (nonatomic, assign) NSInteger zipCode;
/**
 * This property
 * Address Information
 * Cyti Name
 */
@property (nonatomic, strong) NSString* city;
/**
 * This property
 * Address Information
 * Street Name
 */
@property (nonatomic, strong) NSString* street;
/**
 * This property
 * Address Information
 * House Number
 */
@property (nonatomic, strong) NSString* houseNumber;
/**
 * This property
 * country code of the Card
 */
@property (nonatomic, strong) NSString* countryCode;
/**
 * This property
 *  Serial Numbver of the Card
 */
@property (nonatomic, strong) NSString* serialnumber;

/**
 * This property
 * the name of Insurance Company Name
 */
@property (nonatomic, strong) NSString* insuranceName;
/**
 * This property
 * the number of Insurance
 */
@property (nonatomic, strong) NSString* IKNumber;
/**
 * This property
 * the 2nd number of Insurance if available
 */
@property (nonatomic, strong) NSString* IKNumber2;
/**
 * This property
 * the begin date of the insurance
 */
@property (nonatomic, strong) NSString* insuranceBegin;
/**
 * This property
 * the end date of the insurance
 */
@property (nonatomic, strong) NSString* insuranceEnd;
/**
 * This property
 * the art of insurant
 * 1 insurante
 * 3 family member
 * 5 pensionar
 */
@property (nonatomic, strong) NSString* insurantArt;
/**
 * This property
 * the Status RSA
 * http://de.wikipedia.org/wiki/Versichertenstatus
 */
@property (nonatomic, strong) NSString* insurantStatus;
/**
 * This property
 * http://de.wikipedia.org/wiki/Versichertenstatus
 */
@property (nonatomic, strong) NSString* Rechtskreis;
/**
 * This property
 * http://de.wikipedia.org/wiki/Versichertenstatus
 */
@property (nonatomic, strong) NSString* isAmbulant;
/**
 * This property
 * http://de.wikipedia.org/wiki/Versichertenstatus
 */
@property (nonatomic, strong) NSString* isStationery;

/**
 AerztlicheVersorgung
 */
@property (nonatomic, strong) NSString* AerztlicheVersorgung;

/**
 ZahnaerztlicheVersorgung
 */
@property (nonatomic, strong) NSString* ZahnaerztlicheVersorgung;

/**
 VeranlassteLeistungen
 */
@property (nonatomic, strong) NSString* VeranlassteLeistungen;

/**
 WOP
 */
@property (nonatomic, strong) NSString* WOP;

/**
 @name Methods
 */

/**
 Reads the certificate data from the health card

 @return Certificate data
 */
-(NSData*)readCertificate;

@end
