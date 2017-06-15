//
//  AKDeviceInfo.h
//  AppIdKey
//
//  Created by Alec Laws on 2014-11-24.
//  Copyright (c) 2014 Identos. All rights reserved.
//

#import <Foundation/Foundation.h>

/** 
 This class serves as container for the ID100 Device Info data.
 */
@interface AKDeviceInfo : NSObject

/**
 @name Properties
 */

/**
 The manufacturer of the device
 */
@property(nonatomic, readonly) NSString *manufacturer  ;

/**
 * This property is for the acccessory name string.
 */
@property(nonatomic, readonly) NSString *name  ;

/**
 * This property is for the acccessory model string.
 */
@property(nonatomic, readonly) NSString *modelNumber  ;

/**
 * This property is for the serial number string of the Accessory .
 */
@property(nonatomic, readonly) NSString *serialNumber  ;

/**
 * This property is for the firmware version string .
 */
@property(nonatomic, readonly) NSString *firmwareRevision  ;

/**
 * This property is for the hardware revision string.
 */
@property(nonatomic, readonly) NSString *hardwareRevision  ;

@end
