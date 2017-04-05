//
//  AKCard.h
//  AppIdKey
//
//  Created by Alec Laws on 2014-11-24.
//  Copyright (c) 2014 Identos. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 * structure of the CArd Type
 */

typedef NS_ENUM(NSUInteger, AKCardType) {
    AKNoCard,
    
    AKCardTypeUnknown,
    AKCardTypeCredit,
    AKCardTypeHealth,
    AKCardTypeAustriaE,
};

@interface AKCard : NSObject  <NSObject>


/**
 * this method for read any cards
 */
@property (nonatomic) AKCardType cardType;

@end


