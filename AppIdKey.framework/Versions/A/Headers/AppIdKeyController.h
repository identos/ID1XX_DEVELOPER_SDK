//
//  AppIdKeyController.h
//  AppIdKey
//
//  Created by Alec Laws on 2014-11-24.
//  Copyright (c) 2014 Identos. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "AKCard.h"
#import "AKDeviceInfo.h"

@interface AppIdKeyController : NSObject

+(instancetype)sharedInstance;

@property (nonatomic, readonly, getter=isDeviceConnected) BOOL deviceConnected;
-(AKDeviceInfo *)deviceInfo;
@property (nonatomic, copy) dispatch_block_t onDeviceConnected;

@property (nonatomic, readonly, getter=isCardAvailable) BOOL cardAvailable;
-(AKCardType)cardType;
-(id)readCard;

@end
