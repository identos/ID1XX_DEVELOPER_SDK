//
//  IDBankCard.h
//  IdentosTelecash
//
//  Created by Alec Laws on 2015-01-13.
//  Copyright (c) 2015 Identos. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol IDBankCard <NSObject>

/**
 @name Methods
 */

/**
 This property is the account number on the Bank Card.
 */
-(NSString*)accountNumber;

/**
 This property is the experation year on the Bank Card.
 */
-(NSString*)expYear;

/**
 This property is the experation month on the Bank Card.
 */
-(NSString*)expMonth;

/**
 This property is the CVV on the Bank Card.
 */
-(NSString*)cvv;

@end
