//
//  AKCard.h
//  AppIdKey
//
//  Created by Alec Laws on 2014-11-24.
//  Copyright (c) 2014 Identos. All rights reserved.
//

#import <Foundation/Foundation.h>



typedef NS_ENUM(NSUInteger, AKCardType) {
    /** No card present */
    AKNoCard,
    
    /** Card present, but type is unknown */
    AKCardTypeUnknown,
    
    /**  Credit card present */
    AKCardTypeCredit,
   
    /**  Health card present */
    AKCardTypeHealth,
    
    /**  AustriaECard present */
    AKCardTypeAustriaE,
};


/**
 This class serves as data structure of the AKCard type
 */
@interface AKCard : NSObject  <NSObject>

/**
 @name Properties
 */

/**
 * The property is for the AKCardType read
 */
@property (nonatomic) AKCardType cardType;

@end


