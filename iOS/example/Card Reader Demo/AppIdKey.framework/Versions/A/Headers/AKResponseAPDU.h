//
//  AKResponseAPDU.h
//  AppIdDemo
//
//  Created by Alec Laws on 2014-05-16.
//  Copyright (c) 2014 Spoke Technologies. All rights reserved.
//

#import <Foundation/Foundation.h>


typedef NS_ENUM(NSUInteger, AKResponseStatus) {
    /** sw1 IS 0x90 */
    AKResponseNormal,
    
    /** sw1 IN 0x62, 0x63 */
    AKResponseWarning,
    
    /** */
    AKResponseError
};


/**
 */
@interface AKResponseAPDU : NSObject

/**
 @name Properties
 */

/** Trailer 1 */
@property (nonatomic) unsigned char sw1;

/** Trailer 2 */
@property (nonatomic) unsigned char sw2;

/** Data returned */
@property (nonatomic, strong) NSData * data;

/**
 @name Methods
 */

/**
 The response status

 @return AKResponseStatus
 */
-(AKResponseStatus)status;


/**
 The response from the given data

 @param data
 @return AKResponseAPDU
 */
+(AKResponseAPDU*)responseFromData:(NSData*)data;

@end

