//
//  IDBankCard.h
//  IdentosTelecash
//
//  Created by Alec Laws on 2015-01-13.
//  Copyright (c) 2015 Identos. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol IDBankCard <NSObject>

-(NSString*)accountNumber;
-(NSString*)expYear;
-(NSString*)expMonth;
-(NSString*)cvv;

@end
