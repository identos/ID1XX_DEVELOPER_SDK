//
//  AKDeviceInfo.h
//  AppIdKey
//
//  Created by Alec Laws on 2014-11-24.
//  Copyright (c) 2014 Identos. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface AKDeviceInfo : NSObject

@property(nonatomic, readonly) NSString *manufacturer  ;
@property(nonatomic, readonly) NSString *name  ;
@property(nonatomic, readonly) NSString *modelNumber  ;
@property(nonatomic, readonly) NSString *serialNumber  ;
@property(nonatomic, readonly) NSString *firmwareRevision  ;
@property(nonatomic, readonly) NSString *hardwareRevision  ;

@end
