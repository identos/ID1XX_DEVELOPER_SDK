//
//  AKNFCCommand.h
//  AppIdKey
//
//  Created by Alec Laws on 2015-12-29.
//  Copyright © 2015 Identos. All rights reserved.
//

#import <AppIdKey/AppIdKey.h>

#import "AKCardCommand.h"

@interface AKNFCCommand : AKCardCommand

+(instancetype)nfcPowerOn;
+(instancetype)nfcPowerOff ;
+(instancetype)nfcStartDiscovery ;
+(instancetype)nfcDetectCard ;
+(instancetype)nfcTransfer ;



+(instancetype)readCard ;
+(instancetype)track2Data ;
+(instancetype)panShort;
@end


