//
//  AKResponseAPDU.h
//  AppIdDemo
//
//  Created by Alec Laws on 2014-05-16.
//  Copyright (c) 2014 Spoke Technologies. All rights reserved.
//

#import <Foundation/Foundation.h>


typedef NS_ENUM(NSUInteger, AKResponseStatus) {
    AKResponseNormal,   // sw1 IS 0x90
    AKResponseWarning, // sw1 IN 0x62, 0x63
    AKResponseError
};


@interface AKResponseAPDU : NSObject


@property (nonatomic) unsigned char sw1; // trailer 1
@property (nonatomic) unsigned char sw2; // trailer 2
@property (nonatomic, strong) NSData * data; // data returned

-(AKResponseStatus)status;


+(AKResponseAPDU*)responseFromData:(NSData*)data;
@end

