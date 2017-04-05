//
//  AKCreditCard.h
//  AppIdKey
//
//  Created by Alec Laws on 2014-11-25.
//  Copyright (c) 2014 Identos. All rights reserved.
//

#import <AppIdKey/AppIdKey.h>


#import "IDBankCard.h"

typedef NS_ENUM(NSUInteger, AKBankCardType) {
    AKCreditCard,
    AKGiroCard,
    AKMaestroCard
};

@interface AKBankCard : AKCard <IDBankCard>

@property (nonatomic) AKBankCardType type;


-(void)readTrack2Data;

/**
 * This property for the PAN.
 */
@property (nonatomic, strong) NSString* accountNumber;
/**
 * This property for the Name of the card holder.
 */
@property (nonatomic, strong) NSString* name;
/**
 * This property for the expiration Year.
 */
@property (nonatomic, strong) NSString * expirationYear;
/**
 * This property for the expiration Month.
 */
@property (nonatomic, strong) NSString * expirationMonth;
/**
 @note not populbated by readCard
 */
@property (nonatomic, strong) NSString * cvv;

/**
 * This property for country code of the credit card.
 */
@property (nonatomic, strong) NSString* countryCode;
/**
 * This property for the Application of the card.
 * MASTER VISA AMEX JCB MAESTRO...
 */
@property (nonatomic, strong) NSString* application;
/**
 * Track2 Data of the card.
 * Indentical as the magnetstipe Data
 */
@property (nonatomic, strong) NSString* track2Data;
/**
 * PAN short of the card 8 charactre hashed (5310 ########5332) to display directly without encryption
 */
@property (nonatomic, strong, readonly) NSString* panShort;


/**
 * This property for the German EC card
 * when the Application is MAESTRO Girocard and country 280 then read the BLZ and add to Account number
 */
@property (nonatomic, strong) NSString* strIBAN;


@end
